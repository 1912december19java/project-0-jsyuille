package com.revature.model;

import java.util.*;
import org.apache.log4j.Logger;
import com.revature.controller.UserUI;
import com.revature.repository.UserDao;
import com.revature.repository.UserDaoPostgres;
import com.revature.service.*;


public class User {

  private static Logger log = Logger.getLogger(UserUI.class);

  private int id;
  private String user_name;
  private String user_password;
  private Double account_balance;

  public User() {};

  public User(int id, String username, String password, Double account) {
    this.id = id;
    this.user_name = username;
    this.user_password = password;
    this.account_balance = account;
  }

  public static User login(User user) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter your username: ");
    String nameIn = scan.nextLine();

    System.out.println("Enter your password: ");
    String passIn = scan.nextLine();

    UserDao userDao = new UserDaoPostgres();
    log.trace("Passing in name and password for validation");
    user = userDao.validateLogin(user, nameIn, passIn);
    log.trace("Login successful, returning to menu");
    return user;
  }

  public static void register(User user) throws AccountAlreadyExistsException {
    UserDao userDao = new UserDaoPostgres();
    Scanner scn = new Scanner(System.in);

    System.out.println("Enter a new username: ");
    user.setUser_name(scn.nextLine());
    System.out.println("Enter a new password: ");
    user.setUser_password(scn.nextLine());
    user.setAccount_balance(0.00);

    userDao.validateLogin(user, user.getUser_name(), user.getUser_password());
    if (UserUI.loggedIn == true) {
      UserUI.loggedIn = false;
      throw new AccountAlreadyExistsException("This account already exists!\n");
    } else {
      userDao.save(user);
    }
  }

  public void deposit() throws NegativeTransactionException {
    UserDao userDao = new UserDaoPostgres();
    Scanner scn = new Scanner(System.in);
    Double transaction = scn.nextDouble();

    if (transaction >= 0) {
      this.setAccount_balance(this.getAccount_balance() + transaction);
      userDao.update(this);

    } else {
      log.warn("Invalid deposit entry");
      throw new NegativeTransactionException("Cannot deposit a negative amount\n");
    }

  }

  public void withdraw() throws NegativeTransactionException, WithdrawPastLimitException {
    UserDao userDao = new UserDaoPostgres();
    Scanner scn = new Scanner(System.in);
    Double transaction = scn.nextDouble();

    if (transaction >= 0 && transaction <= this.getAccount_balance()) {
      this.setAccount_balance(this.getAccount_balance() - transaction);
      userDao.update(this);

    } else if (transaction > this.getAccount_balance()) {
      log.warn("Attempted to withdraw too much");
      throw new WithdrawPastLimitException("Cannot withdraw past current account balance\n");

    } else {
      log.warn("Negative withdraw entry");
      throw new NegativeTransactionException("Cannot withdraw a negative amount\n");
    }
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getUser_password() {
    return user_password;
  }

  public void setUser_password(String user_password) {
    this.user_password = user_password;
  }

  public Double getAccount_balance() {
    return account_balance;
  }

  public void setAccount_balance(Double account_balance) {
    this.account_balance = account_balance;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", user_name=" + user_name + ", user_password=" + user_password
        + ", account_balance=" + account_balance + "]";
  }


  // @Override
  public boolean equals(User u) {

    if (this.id == u.id && this.user_name.equals(u.user_name)
        && this.user_password.equals(u.user_password)
        && this.account_balance.equals(u.account_balance))
      return true;
    else
      return false;

  }
}
