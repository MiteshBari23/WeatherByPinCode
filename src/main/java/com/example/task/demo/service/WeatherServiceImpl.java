package com.example.task.demo.service;

import com.example.task.demo.client.GeoCodingClient;
import com.example.task.demo.client.WeatherClient;
import com.example.task.demo.dto.WeatherResponseDTO;
import com.example.task.demo.entity.PincodeLocationData;
import com.example.task.demo.entity.WeatherData;
import com.example.task.demo.repository.PinCodeLocationRepo;
import com.example.task.demo.repository.WeatherRepo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepo weatherRepo;
    private final PinCodeLocationRepo pinCodeLocationRepo;
    private final GeoCodingClient geoCodingClient;
    private final WeatherClient weatherClient;

    @Override
    public WeatherResponseDTO getWeather(String pincode, LocalDate date) {

        // if already exits
        Optional<WeatherData> existing = weatherRepo.findByPincodeAndDate(pincode, date);

        if (existing.isPresent()) {
            System.out.println("Returning from DB");
            WeatherData data = existing.get();
            return WeatherResponseDTO.builder()
                    .pincode(data.getPincode())
                    .date(data.getDate())
                    .temp(data.getTemperature())
                    .humidity(data.getHumidity())
                    .description(data.getDescription())
                    .build();
        }

        //get lat lan check db first
        PincodeLocationData location = pinCodeLocationRepo.findByPincode(pincode)
                .orElseGet(() -> fetchAndSaveLocation(pincode));

        System.out.println("FINAL LAT: " + location.getLatitude());
        System.out.println("FINAL LON: " + location.getLongitude());

        // API call
        Map<String, Object> response = weatherClient.getWeather(location.getLatitude(), location.getLongitude());

        //data extraction
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");

        double temp = ((Number) main.get("temp")).doubleValue();
        int humidity = ((Number) main.get("humidity")).intValue();
        String description = (String) weatherList.get(0).get("description");

        // db save
        WeatherData weatherData = WeatherData.builder()
                .pincode(pincode)
                .date(date)
                .temperature(temp)
                .humidity(humidity)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
    weatherRepo.save(weatherData);
        System.out.println("new pincode added to db");
        return WeatherResponseDTO.builder()
                .pincode(weatherData.getPincode())
                .date(weatherData.getDate())
                .temp(weatherData.getTemperature())
                .humidity(weatherData.getHumidity())
                .description(weatherData.getDescription())
                .build();
    }

    private PincodeLocationData fetchAndSaveLocation(String pincode) {
        double[] latLon = geoCodingClient.getLatLon(pincode);

        if (latLon[0] == 0.0 || latLon[1] == 0.0) {
            throw new RuntimeException("Invalid location data");
        }
        System.out.println("LAT: " + latLon[0]);
        System.out.println("LON: " + latLon[1]);
        PincodeLocationData location = PincodeLocationData.builder()
                .pincode(pincode)
                .latitude(latLon[0])
                .longitude(latLon[1])
                .createdAt(LocalDateTime.now())
                .build();

        return pinCodeLocationRepo.save(location);

    }

}
