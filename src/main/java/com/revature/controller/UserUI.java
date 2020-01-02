package com.revature.controller;

import com.revature.model.User;
import com.revature.repository.UserDaoPostgres;
import com.revature.service.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import org.apache.log4j.Logger;


public class UserUI extends User {

  public static Boolean loggedIn = false;


  public static void display(User user) {
    
    NumberFormat formatter = new DecimalFormat("#0.00");

    try {

      while (UserUI.loggedIn == false) {

        System.out.println("Welcome! Press 1 to Login | Press 2 to Register | Press 3 to Exit");
        Scanner scn = new Scanner(System.in);

        switch (scn.nextLine()) {
          case "1":
            user = User.login(user);
            if (loggedIn == true) {
              System.out.println("You're logged in!\n");
              System.out.println("Welcome " + user.getUser_name() + "!");
            }
            break;
          case "2":
            try {
              User.register(user);
            } catch (AccountAlreadyExistsException e) {
              System.err.print(e);
            }
            break;
          case "3":
            System.out.println("Have a nice day!");
            scn.close();
            System.exit(0);
            break;
          default:
            System.out.println("Invalid input, please try again");
            return;
        }

        while (UserUI.loggedIn == true) {
          System.out.println("Press 1 to Logout | Press 2 to Check your balance "
              + "| Press 3 to Deposit to your account | Press 4 to Withdraw from your account");

          switch (scn.nextLine()) {
            case "1":
              UserUI.loggedIn = false;
              System.out.println("You're signed out\n");
              // scn.reset();
              break;
            case "2":
              System.out.println(
                  "\nYour balance is: " + formatter.format(user.getAccount_balance()) + "\n");
              break;
            case "3":
              System.out.println("Enter the amount you want to deposit: ");
              try {
                user.deposit();
              } catch (NegativeTransactionException e) {
                System.err.print(e);
              }
              System.out.println(
                  "Your current balance is: " + formatter.format(user.getAccount_balance()) + "\n");
              break;
            case "4":
              System.out.println("Enter the amount you want to withdraw: ");
              try {
                user.withdraw();
              } catch (NegativeTransactionException e) {
                System.err.print(e);
              } catch (WithdrawPastLimitException e) {
                System.err.print(e);
              }
              System.out.println(
                  "Your current balance is: " + formatter.format(user.getAccount_balance()) + "\n");
              break;
            default:
              System.out.println("Invalid input, please try again");
              break;
          }
        }
      }



    } catch (NoSuchElementException e) {

    }
  }
}
