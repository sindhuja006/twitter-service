package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserAlreadyExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.DoubleToIntFunction;

@Repository
@Primary
public class UserRepositoryDb implements IUserRepository {

    DataSource dataSource;

    public UserRepositoryDb(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User addUser(User user) {
        String sql="INSERT into users(userId,name,email) VALUES (?,?,?)";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps= connection.prepareStatement(sql)){
            ps.setString(1,user.getUserId().getUserValue());
            ps.setString(2,user.getName());
            ps.setString(3,user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void deleteUser(UserId userId) {
        String sql="DELETE from users where userId=?";
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps= con.prepareStatement(sql)){
            ps.setString(1,userId.getUserValue());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User updateUser(User user, UserId userId) {
        String sql2="UPDATE users SET name=?,email=? WHERE userId=?";
        try (Connection con= dataSource.getConnection();
            PreparedStatement ps= con.prepareStatement(sql2)){
            User existingValue=getUserById(userId);
            //language=postgreSQL

            if(user.getName()==null || user.getName().trim().isEmpty() || user.getName().equalsIgnoreCase("string")){
                user.setName(existingValue.getName());
            }
            if(user.getEmail()==null || user.getEmail().trim().isEmpty() || user.getEmail().equalsIgnoreCase("string")){
                user.setEmail(existingValue.getEmail());
            }

            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,userId.getUserValue());
            int count=ps.executeUpdate();
            System.out.println("Rows affected" + count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getUserById(UserId userId) {
        String sql="SELECT * from users WHERE userId=?";
        try (Connection con= dataSource.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,userId.getUserValue());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new User(new UserId(rs.getString("userId")),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void verifyUserNotExists(UserId userId) {
        String sql="SELECT userId from users WHERE userId=?";
         try(Connection con= dataSource.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)){
             ps.setString(1, userId.getUserValue());
             ResultSet rs=ps.executeQuery();
             if(rs.next()){
                 throw new UserAlreadyExistsException("UserId" + userId + "already exists",HttpStatus.CONFLICT);
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }

    @Override

    public void verifyUserExists(UserId userId) {
        String sql="SELECT * from users WHERE userId=?";
        try(Connection con=dataSource.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1, userId.getUserValue());
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){
                throw new UserNotExistsException("UserId" + userId +"not exists", HttpStatus.CONFLICT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
