package com.revature.model;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.revature.controller.UserUI;
import com.revature.repository.UserDao;
import com.revature.repository.UserDaoPostgres;
import com.revature.service.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTesting {

  private static UserUI menu;
  private static User user;
  private static User userMatch;
  private static UserDao userDao;


  @Test //(expected = WithdrawPastLimitException.class)
  public void testValidate() {
    
    String userLogin = "Bob";
    String userPassword = "1234";
    //connect = UserDaoPostgres.setConn(connect);
    
    user = userDao.validateLogin(user, userLogin, userPassword);

    assertTrue(userMatch.equals(user));
  }
  
  @Test
  public void testGet() {
    
    user = userDao.get(31);
    assertTrue(user.equals(userMatch));
  }
  
  
  @Test
  public void testEquals() {
    
    user = new User(31,"Bob","1234",1000.89);

    
    assertTrue(userMatch.equals(user));
  }

  @Before
  public void setUp() {
    user = new User();
    userDao = new UserDaoPostgres();
    userMatch = new User(31,"Bob","1234",1000.89);
  }

  @After
  public void tearDown() {
    menu = null;
  }
  
}
