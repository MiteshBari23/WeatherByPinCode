package com.example.task.demo.exception;

public class WeatherApiException extends RuntimeException{
    public WeatherApiException(String message){
        super(message);
    }
}
