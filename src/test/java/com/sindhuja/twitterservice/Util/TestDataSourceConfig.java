package com.sindhuja.twitterservice.Util;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;

public class TestDataSourceConfig{

    static DataSource dataSource;



    public static synchronized DataSource createH2DataSource() {

        if(dataSource==null) {
            //1 create hick ds config
            //2. assign values to config
            //3. hds(ds)
            HikariDataSource config=new HikariDataSource();
            config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            config.setUsername("sa");
            config.setPassword("");
            config.setDriverClassName("org.h2.Driver");
            config.setMaximumPoolSize(10);
            config.setAutoCommit(true);
            dataSource=new HikariDataSource(config);
        }

        return dataSource;
    }

    public static void runScript(DataSource dataSource){
        try{
            Connection con= dataSource.getConnection();
            ScriptUtils.executeSqlScript(con, new ClassPathResource("schema.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanScript(DataSource dataSource){
        try{
            Connection con= dataSource.getConnection();
            ScriptUtils.executeSqlScript(con,new ClassPathResource("cleanup/cleanupuser.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
