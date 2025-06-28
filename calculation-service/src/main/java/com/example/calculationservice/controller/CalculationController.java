package com.example.calculationservice.controller;

import com.example.calculationservice.model.CalculationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculate")
public class CalculationController {

    @PostMapping
    public double calculate(@RequestBody CalculationRequest request) {
        return switch (request.getOperation()) {
            case "add" -> request.getNumber1() + request.getNumber2();
            case "subtract" -> request.getNumber1() - request.getNumber2();
            case "multiply" -> request.getNumber1() * request.getNumber2();
            case "divide" -> request.getNumber2() != 0 ? request.getNumber1() / request.getNumber2() : 0;
            default -> throw new IllegalArgumentException("Invalid operation");
        };

    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidOperation(IllegalArgumentException e) {
        return e.getMessage();
    }
}
