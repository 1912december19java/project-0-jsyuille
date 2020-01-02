package com.revature.repository;

import java.sql.*;
import java.util.List;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import com.revature.controller.UserUI;
import com.revature.model.User;
import com.revature.service.AccountAlreadyExistsException;

public class UserDaoPostgres implements UserDao {

  private static Logger log = Logger.getLogger(UserDaoPostgres.class); // Named with whatever class
                                                                       // you're currently in

  protected static Connection conn;

  static {
    try {

      conn = DriverManager.getConnection(System.getenv("connstring"), System.getenv("username"),
          System.getenv("password"));
      // log.info("Connected to database");
    } catch (SQLException e) {
      log.error("Failed to connect to database", e);
    }
  }

  @Override
  public User get(int id) {

    User out = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = conn.prepareStatement("SELECT DISTINCT * FROM user_table WHERE id = ?");
      stmt.setInt(1, id);

      if (stmt.execute()) {
        rs = stmt.getResultSet();
      }

      while (rs.next()) {
        out = new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("user_password"),
            rs.getDouble("account_balance"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return out;
  }


  @Override
  public void save(User user) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(
          "INSERT INTO user_table(user_name, user_password, account_balance) VALUES (?,?,?)");

      stmt.setString(1, user.getUser_name());
      stmt.setString(2, user.getUser_password());
      stmt.setDouble(3, user.getAccount_balance());

      int userCreated = stmt.executeUpdate();
      if (userCreated > 0) {
        System.out.println("Your account was registered successfully!");
      }
      // stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(User user) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(
          "UPDATE user_table SET user_name = ?, user_password = ?, account_balance = ? WHERE id = ?");
      stmt.setString(1, user.getUser_name());
      stmt.setString(2, user.getUser_password());
      stmt.setDouble(3, user.getAccount_balance());
      stmt.setInt(4, user.getId());

      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Override
  public List<User> getAll() {
    List<User> all = new ArrayList<User>();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = conn.prepareStatement("SELECT * FROM user_table ORDER BY id");
      if (stmt.execute()) {
        rs = stmt.getResultSet();
      }
      while (rs.next()) {
        all.add(new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("user_password"),
            rs.getDouble("account_balance")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return all;
  }

  @Override
  public User validateLogin(User user, String userLogin, String userPassword) {

    PreparedStatement stmt = null;
    ResultSet rs = null;
    log.trace("Connecting to database");
    try {
      stmt = conn
          .prepareStatement("SELECT * FROM user_table WHERE user_name = ? AND user_password = ?");
      stmt.setString(1, userLogin);
      stmt.setString(2, userPassword);
      if (stmt.execute()) {
        log.trace("Pulling up results");
        rs = stmt.getResultSet();
      }
      while (rs.next()) {

        if (userLogin.equals(rs.getString(2)) && userPassword.equals(rs.getString(3))) {
          log.trace("Account found. Copying over the information");
          user = new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("user_password"),
              rs.getDouble("account_balance"));

          user.setId(rs.getInt("id"));
          user.setUser_name(rs.getString("user_name"));
          user.setUser_password(rs.getString("user_password"));
          user.setAccount_balance(rs.getDouble("account_balance"));

          UserUI.loggedIn = true;
          break;
        }
      }

      if (UserUI.loggedIn == false) {
        log.trace("Username and password combination did not match any results from the database");
        System.out.println("Incorrect username or password");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return user;

  }

}
