package com.example.task.demo.service;

import com.example.task.demo.dto.WeatherResponseDTO;
import com.example.task.demo.entity.WeatherData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface WeatherService {
    WeatherResponseDTO getWeather(String pincode, LocalDate date);
}
