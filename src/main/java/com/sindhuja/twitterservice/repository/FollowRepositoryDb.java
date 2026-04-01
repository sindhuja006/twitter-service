package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
@Primary
public class FollowRepositoryDb implements IFollowRepository{

    DataSource dataSource;

    public FollowRepositoryDb(DataSource dataSource){
        this.dataSource=dataSource;
    }
    @Override
    public void followUser(UserId followerId, UserId followeeId) {
        try{
            Connection con= dataSource.getConnection();
            String sql="INSERT into follow(followerId,followeeId) VALUES (?,?)";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1,followerId.getUserValue());
            ps.setString(2,followeeId.getUserValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unFollowUser(UserId followerId, UserId followeeId) {
          try{
              Connection con= dataSource.getConnection();
              String sql="DELETE FROM follow WHERE followerId=? AND followeeId=?";
              PreparedStatement ps=con.prepareStatement(sql);
              ps.setString(1, followerId.getUserValue());
              ps.setString(2,followeeId.getUserValue());
              ps.executeUpdate();
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
    }

    @Override
    public Set<UserId> getFollowers(UserId followeeId) {
        Set<UserId> resultSet;
        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT followerId from follow where followeeId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, followeeId.getUserValue());
            ResultSet rs = ps.executeQuery();
            resultSet = new HashSet<>();
            while (rs.next()) {
                resultSet.add(new UserId(rs.getString("followerId")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public Set<UserId> getFollowing(UserId followerId) {
        Set<UserId> resultSet;
        try{
            Connection con= dataSource.getConnection();
            String sql="SELECT followeeId from follow WHERE followerId=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, followerId.getUserValue());
            ResultSet rs= ps.executeQuery();
            resultSet=new HashSet<>();
            while(rs.next()){
                resultSet.add(new UserId(rs.getString("followeeId")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
}
