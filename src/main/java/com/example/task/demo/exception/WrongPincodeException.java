package com.example.task.demo.exception;

public class WrongPincodeException extends RuntimeException{
    public WrongPincodeException(String message){
        super(message);
    }
}
