package com.example.task.demo.controller;

import com.example.task.demo.dto.WeatherResponseDTO;
import com.example.task.demo.entity.WeatherData;
import com.example.task.demo.service.WeatherService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponseDTO> getWeather(
            @RequestParam String pincode,
            @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date);

        WeatherResponseDTO weatherResponseDTO = weatherService.getWeather(pincode, parsedDate);
        return ResponseEntity.ok(weatherResponseDTO);
    }
}

