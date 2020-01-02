package com.revature.service;

public class NegativeTransactionException extends Exception {

  public NegativeTransactionException(String message) {
    super(message);
}
}
