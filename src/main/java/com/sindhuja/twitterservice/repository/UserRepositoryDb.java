package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserAlreadyExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.DoubleToIntFunction;

public class UserRepositoryDb implements IUserRepository {

    DataSource dataSource;

    public UserRepositoryDb(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User addUser(User user) {
        try {
            Connection connection = dataSource.getConnection();
            //language=PostgreSQL
            String sql="INSERT into users(userId,name,email) VALUES (?,?,?)";
            PreparedStatement ps= connection.prepareStatement(sql);
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
        try {
            Connection con = dataSource.getConnection();
            //language=postgreSQL
            String sql="DELETE from users where userId=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1,userId.getUserValue());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User updateUser(User user, UserId userId) {
        try{
            Connection con= dataSource.getConnection();
            //language=postgreSQL
            String sql="UPDATE users SET name=?,email=? WHERE userId=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(2,user.getName());
            ps.setString(3,user.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getUserById(UserId userId) {
        try{
            Connection con= dataSource.getConnection();
            //language=PostgreSQL
            String sql="SELECT * from users WHERE id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,userId.getUserValue());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new User(new UserId(rs.getString("id")),
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
         try{
             Connection con= dataSource.getConnection();
             //language=postgreSQL
             String sql="SELECT * from users WHERE userId=?";
             PreparedStatement ps=con.prepareStatement(sql);
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
        try{
            Connection con=dataSource.getConnection();
            //language=postgreSQL
            String sql="SELECT * from users WHERE userId=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, userId.getUserValue());
            ResultSet rs=ps.executeQuery();
            int count=0;
            if(rs.next()){
                count++;
            }
            if(count==0){
                throw new UserNotExistsException("UserId" + userId +"already exists", HttpStatus.CONFLICT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
