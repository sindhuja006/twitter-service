package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.TweetIdNotExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
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
   JdbcTemplate jdbcTemplate;
    public TweetRepositoryDb(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Tweet postTweet(UserId userId, Tweet tweet) {

        String sql="INSERT INTO tweets(userId,tweetId,message) VALUES (?,?,?)";
        jdbcTemplate.update(sql,tweet.getTweetId(),
                tweet.getMessage(),
                tweet.getInsertTime());
        return tweet;
    }

    @Override
    public void deleteTweet(UserId userId, TweetId tweetId) {
        String sql="DELETE FROM tweets WHERE tweetId=?";
        jdbcTemplate.update(sql,userId.getUserValue());
    }

    @Override
    public List<Tweet> getTweets(UserId userId) {
        List<Tweet> tweetList;
        String sql = "SELECT * from tweets WHERE userId=?";
        tweetList=jdbcTemplate.query(sql,(rs,rownum)->
                Tweet.builder().userId(userId)
                        .tweetId(new TweetId(rs.getInt("tweetId")))
                        .message(rs.getString("message"))
                        .insertTime(rs.getObject("insertTime",LocalDateTime.class))
                        .build());
        return tweetList;
    }


    @Override
    public void verifyTweetExists(UserId userId, TweetId tweetId) {
            String sql="SELECT count(*) from tweets WHERE tweetId=?";
            Integer count=jdbcTemplate.queryForObject(sql,Integer.class,tweetId.getTweetValue());
            if(count == null || count==0){
                throw new TweetIdNotExistsException("TweetId"+ tweetId + "not exists",HttpStatus.CONFLICT);
            }
    }
}
