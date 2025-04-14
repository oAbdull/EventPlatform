package org.example.userservice.exception;

public class InvalidUserException extends Exception{
  public InvalidUserException() {
    super("Invalid username or password");
  }
}
