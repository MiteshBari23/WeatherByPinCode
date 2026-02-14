package com.example.task.demo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeoCodingClient {
    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    public double[] getLatLon(String pincode) {
//
//        String url = "http://api.openweathermap.org/geo/1.0/direct?q="
//                + pincode + "&limit=1&appid=" + apiKey;
        String geoUrl = "https://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",IN&appid=" + apiKey;
        Map<String, Object> response =
                restTemplate.getForObject(geoUrl, Map.class);

        System.out.println("Geo API Response: " + response);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Invalid pincode");
        }

        double lat = ((Number) response.get("lat")).doubleValue();
        double lon = ((Number) response.get("lon")).doubleValue();
        return new double[]{lat, lon};
    }
}

