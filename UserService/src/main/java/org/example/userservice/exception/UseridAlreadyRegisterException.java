package org.example.userservice.exception;

public class UseridAlreadyRegisterException extends Exception {
  public UseridAlreadyRegisterException() {
    super("Userid already registered");
  }
}
