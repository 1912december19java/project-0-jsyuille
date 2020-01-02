package com.revature.repository;

import java.util.List;
import com.revature.model.User;


public interface UserDao {

  User get(int id);
  
  public User validateLogin(User user, String userLogin, String userPassword);

  void save(User user);

  void update(User user);
}
