package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserAlreadyExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    JdbcTemplate jdbcTemplate;

    public UserRepositoryDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT into users(userId,name,email) VALUES (?,?,?)";
        jdbcTemplate.update(sql,
                user.getUserId().getUserValue(),
                user.getName(),
                user.getEmail());
        return user;
    }

    @Override
    public void deleteUser(UserId userId) {
        String sql = "DELETE from users where userId=?";
        jdbcTemplate.update(sql,userId.getUserValue());
    }

    @Override
    public User updateUser(User user, UserId userId) {
        String sql2 = "UPDATE users SET name=?,email=? WHERE userId=?";
        jdbcTemplate.update(sql2,
                user.getName(),
                user.getEmail(),
                userId.getUserValue());
        return user;
    }

//   RowMapper<User>  userRowMapper = (rs, rowcount) ->
//           User.builder().userId(new UserId(rs.getString("userId")))
//                   .name(rs.getString("name"))
//                   .email(rs.getString("email"))
//                   .build(),
//                userId.getValue());

    @Override
    public User getUserById(UserId userId) {
        String sql = "SELECT * from users WHERE userId=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowcount) ->
                User.builder().userId(new UserId(rs.getString("userId")))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .build(),
                userId.getValue());

    }

    @Override
    public void verifyUserNotExists(UserId userId) {
        String sql = "SELECT count(*) from users WHERE userId=?";
        Integer user = jdbcTemplate.queryForObject(sql,Integer.class,userId.getUserValue());
        if (user>0) {
            throw new UserAlreadyExistsException("UserId" + userId + "already exists", HttpStatus.CONFLICT);
        }
    }

    @Override

    public void verifyUserExists(UserId userId) {
        String sql = "SELECT count(*) from users WHERE userId=?";
        Integer user = jdbcTemplate.queryForObject(sql,Integer.class, userId.getUserValue());
        if (user == null || user==0) {
            throw new UserNotExistsException("UserId" + userId + "NOT exists", HttpStatus.CONFLICT);
        }
    }
}
