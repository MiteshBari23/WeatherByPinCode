package com.example.task.demo.repository;

import com.example.task.demo.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepo extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByPincodeAndDate(String pincode, LocalDate date);
}
