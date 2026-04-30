package com.sindhuja.twitterservice.Repository;

import com.sindhuja.twitterservice.Util.TestDataSourceConfig;
import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserAlreadyExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import com.sindhuja.twitterservice.repository.UserRepositoryDb;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class UserRepositoryDbTest {

    UserRepositoryDb repositoryDb;
    DataSource dataSource;
    @BeforeAll
    void setUp() {
         dataSource= TestDataSourceConfig.createH2DataSource();
         TestDataSourceConfig.runScript(dataSource);
         repositoryDb=new UserRepositoryDb(dataSource);
    }

    //@BeforeEach
    void cleanup(){
         TestDataSourceConfig.cleanScript(dataSource);
    }

    @Test
    void addandgetUserTest(){
        UserId userId=new UserId("1");
        User user=new User(userId,"sindhu","sindhu@gmail.com");
        repositoryDb.addUser(user);
        User actual=repositoryDb.getUserById(userId);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user.getName(),actual.getName());
        Assertions.assertEquals(user.getEmail(),actual.getEmail());

    }

    @Test
    void updateUserTest(){
        UserId userId=new UserId("2");
        User user=new User(userId,"sindhu","sindhu@gmail.com");
        repositoryDb.addUser(user);
        User actual=repositoryDb.updateUser(user,userId);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user.getName(),actual.getName());
        Assertions.assertEquals(user.getEmail(),actual.getEmail());
    }

    @Test
    void deleteUserTest(){
        UserId userId=new UserId("3");
        User user=new User(userId,"sindhu","sindhu@gmail.com");
        repositoryDb.addUser(user);
        repositoryDb.deleteUser(userId);
        User actual=repositoryDb.getUserById(userId);
        Assertions.assertNull(actual);
    }

    @Test
    void verifyUserNotExistsTest(){
        UserId userId=new UserId("1");
        User user=new User(userId,"sindhu","sindhu@gmail.com");
        repositoryDb.addUser(user);
        Assertions.assertThrows(UserAlreadyExistsException.class,()-> repositoryDb.verifyUserNotExists(userId));
    }

    @Test
    void verifyUserExistsTest(){
        UserId userId=new UserId("4");
        Assertions.assertThrows(UserNotExistsException.class,()->repositoryDb.verifyUserExists(userId));
    }
}
