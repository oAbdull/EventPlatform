package org.example.userservice.exception;

public class AdminAlreadyCreatedException extends Exception{
    public AdminAlreadyCreatedException() {
        super("Admin already created..!!");
    }
}
