CREATE TABLE IF NOT EXISTS users (
    userId varchar primary key ,
    name varchar(50) not null ,
    email varchar(100) not null unique
);

CREATE TABLE IF NOT EXISTS tweets(
    userId varchar References users(userId),
    tweetId Integer not null,
    message varchar(280),
    insertTime TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS follow(
    followerId varchar References users(userId),
    followeeId varchar References users(userId)
);