package com.revature.service;

public class WithdrawPastLimitException extends Exception {

  public WithdrawPastLimitException (String message) {
    super(message);
  }
}
