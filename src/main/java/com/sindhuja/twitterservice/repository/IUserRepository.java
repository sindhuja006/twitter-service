package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserId;

public interface IUserRepository {
    public User addUser(User user);
    public void deleteUser(UserId userId);
    public User updateUser(User user,UserId userId);
    public User getUserById(UserId userId);
}
