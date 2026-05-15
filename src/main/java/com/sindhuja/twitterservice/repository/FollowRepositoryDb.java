package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Follow;
import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Primary
public class FollowRepositoryDb implements IFollowRepository{

   JdbcTemplate jdbcTemplate;

    public FollowRepositoryDb(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public void followUser(UserId followerId, UserId followeeId) {
            String sql="INSERT into follow(followerId,followeeId) VALUES (?,?)";
            jdbcTemplate.update(sql,followerId,followeeId);
    }

    @Override

    public void unFollowUser(UserId followerId, UserId followeeId) {
              String sql="DELETE FROM " +
                      "follow WHERE followerId=? AND followeeId=?";
              jdbcTemplate.update(sql,followerId,followeeId);
    }

    @Override
    public Set<UserId> getFollowers(UserId followeeId) {

            String sql = "SELECT followerId from follow where followeeId=?";
            List<UserId> list=jdbcTemplate.query(sql,(rs, rownum)->
                            new UserId(rs.getString("followerId")),
                            followeeId.getUserValue());
            return new HashSet<>(list);
    }

    @Override
    public Set<UserId> getFollowing(UserId followerId) {
            String sql="SELECT followeeId from follow WHERE followerId=?";
            List<UserId> list=jdbcTemplate.query(sql,(rs,rownum)->
                    new UserId(rs.getString("followeeId")),
                    followerId.getUserValue());
            return new HashSet<>(list);
    }
}
