package com.example.datasender.controller;

import com.example.datasender.model.CalculationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/send")
public class DataSenderController {

    private final RestTemplate restTemplate;
    private final String calculationServiceUrl;

    public DataSenderController(RestTemplate restTemplate,
                                @Value("${calculation.service.url}") String calculationServiceUrl) {
        this.restTemplate = restTemplate;
        this.calculationServiceUrl = calculationServiceUrl;
    }

    @PostMapping
    public ResponseEntity<Double> sendCalculation(@RequestBody CalculationRequest request) {
        Double result = restTemplate.postForObject(calculationServiceUrl, request, Double.class);
        return ResponseEntity.ok(result);
    }
}
