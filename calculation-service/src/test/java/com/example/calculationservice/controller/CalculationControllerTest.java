package com.example.calculationservice.controller;

import com.example.calculationservice.model.CalculationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculationController.class)
public class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper method to convert CalculationRequest to JSON string
    private String asJsonString(CalculationRequest request) throws Exception {
        return objectMapper.writeValueAsString(request);
    }

    @Test
    public void testAddOperation() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(5);
        request.setNumber2(7);
        request.setOperation("add");

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("12.0"));
    }

    @Test
    public void testSubtractOperation() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(10);
        request.setNumber2(3);
        request.setOperation("subtract");

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("7.0"));
    }

    @Test
    public void testMultiplyOperation() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(4);
        request.setNumber2(6);
        request.setOperation("multiply");

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("24.0"));
    }

    @Test
    public void testDivideOperation() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(20);
        request.setNumber2(5);
        request.setOperation("divide");

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("4.0"));
    }

    @Test
    public void testDivideByZeroReturnsZero() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(10);
        request.setNumber2(0);
        request.setOperation("divide");

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }

    @Test
    public void testInvalidOperationReturnsBadRequest() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(1);
        request.setNumber2(2);
        request.setOperation("mod");  // Invalid operation

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest());
    }
}
