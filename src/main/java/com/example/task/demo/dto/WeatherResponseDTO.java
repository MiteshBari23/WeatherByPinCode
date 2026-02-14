package com.example.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class WeatherResponseDTO {
    private String pincode;
    private LocalDate date;
    private double temp;
    private int humidity;
    private String description;
}
