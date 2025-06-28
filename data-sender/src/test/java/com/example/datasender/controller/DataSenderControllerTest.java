package com.example.datasender.controller;

import com.example.datasender.config.AppConfig; // <-- Import the config class
import com.example.datasender.model.CalculationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import; // <-- Import annotation
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DataSenderController.class, properties = {
        "calculation.service.url=http://mocked-url/api/calculate"
})
@Import(AppConfig.class) // <-- ADD THIS LINE
public class DataSenderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // This MockBean will now correctly replace the RestTemplate bean
    // that is defined in the imported AppConfig
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSendCalculationReturnsResultFromCalculationService() throws Exception {
        // Arrange
        CalculationRequest request = new CalculationRequest();
        request.setNumber1(4);
        request.setNumber2(5);
        request.setOperation("add");

        // Mock RestTemplate to return 9.0 when called
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.eq(Double.class)))
                .thenReturn(9.0);

        // Act & Assert
        mockMvc.perform(post("/api/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("9.0"));
    }
}