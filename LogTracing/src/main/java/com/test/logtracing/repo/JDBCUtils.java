package com.test.logtracing.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.test.logtracing.LogTracingApplication;
import com.test.logtracing.constant.Constants;
import com.test.logtracing.pojo.Log;

@Component
public class JDBCUtils {
	

	private static final Logger log = LogManager.getLogger(LogTracingApplication.class);
    private static String jdbcURL = Constants.jDBC_URL;
    private static String jdbcUsername = Constants.JDBC_USERNAME;
    private static String jdbcPassword = Constants.JDBC_PASSWORD;

    public Connection getConnection() {
    	
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection (jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return connection;
    }

    public void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                log.error("SQLState: {}" , ((SQLException) e).getSQLState());
                log.error("Error Code: {}" , ((SQLException) e).getErrorCode());
                log.error("Message: {}" , e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                	log.error("Cause: {}" , t);
                    t = t.getCause();
                }
            }
        }
    }
    
    public void commitLogToFile(Log log) {
    	//TODO: use this method to commit data to database.
    }
    
}
