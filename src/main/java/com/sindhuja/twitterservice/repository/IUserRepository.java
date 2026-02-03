package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserId;

public interface IUserRepository {
    User addUser(User user);
    void deleteUser(UserId userId);
    User updateUser(User user,UserId userId);
    User getUserById(UserId userId);

    void verifyUserNotExists(UserId userId);
    void verifyUserExists(UserId userId);
}
