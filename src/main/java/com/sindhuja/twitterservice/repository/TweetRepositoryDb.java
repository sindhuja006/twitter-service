package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.TweetIdNotExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class TweetRepositoryDb implements ITweetRepository{
    DataSource dataSource;
    public TweetRepositoryDb(DataSource dataSource){
        this.dataSource=dataSource;
    }

    @Override
    public Tweet postTweet(UserId userId, Tweet tweet) {
        try{
            Connection con= dataSource.getConnection();
            String sql="INSERT INTO tweets(userId,tweetId,message) VALUES (?,?,?)";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1, userId.getUserValue());
            ps.setInt(2,tweet.getTweetId().getTweetValue());
            ps.setString(3,tweet.getMessage());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tweet;
    }

    @Override
    public void deleteTweet(UserId userId, TweetId tweetId) {
        try{
            Connection con= dataSource.getConnection();
            String sql="DELETE FROM tweets WHERE tweetId=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1,tweetId.getTweetValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tweet> getTweets(UserId userId) {
        List<Tweet> tweetList;
        try {
            Connection con = dataSource.getConnection();
            String sql = "SELECT * from tweets WHERE userId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId.getUserValue());
            ResultSet rs = ps.executeQuery();
            tweetList = new ArrayList<>();
            while (rs.next()) {
                OffsetDateTime offsetDateTime = rs.getObject("insertTime", OffsetDateTime.class);
                LocalDateTime localDateTime=offsetDateTime.toLocalDateTime();
                Tweet tweet = new Tweet(new UserId(rs.getString("userId")),
                        new TweetId(rs.getInt("tweetId")),
                        rs.getString("message"),
                        localDateTime);
                tweetList.add(tweet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tweetList;
    }


    @Override
    public void verifyTweetExists(UserId userId, TweetId tweetId) {
        try{
            Connection con=dataSource.getConnection();
            String sql="SELECT * from tweets WHERE tweetId=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1,tweetId.getTweetValue());
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){
                throw new TweetIdNotExistsException("TweetId" + tweetId + "not exists", HttpStatus.CONFLICT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
