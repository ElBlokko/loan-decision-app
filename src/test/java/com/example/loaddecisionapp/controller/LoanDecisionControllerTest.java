package com.example.loaddecisionapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.loaddecisionapp.dto.Decision;
import com.example.loaddecisionapp.dto.LoanDecisionResponseDTO;
import com.example.loaddecisionapp.service.LoanDecisionService;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(LoanDecisionController.class)
class LoanDecisionControllerTest {

    @MockBean
    private LoanDecisionService loanDecisionService;

    @InjectMocks
    private LoanDecisionController loanDecisionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidCheckLoanDecision() throws Exception {
        LoanDecisionResponseDTO mockResponse = new LoanDecisionResponseDTO(Decision.POSITIVE, 5000, 12);
        when(loanDecisionService.makeLoanDecision(any(), anyDouble(), anyInt())).thenReturn(mockResponse);

        mockMvc.perform(post("/loan-decision/check")
                        .param("personalCode", "49002010976")
                        .param("loanAmount", "5000")
                        .param("loanPeriod", "24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("POSITIVE"))
                .andExpect(jsonPath("$.approvedAmount").value(5000));
    }

    @ParameterizedTest
    @MethodSource("invalidParamsProvider")
    public void testInvalidParameters(String personalCode, double loanAmount, int loanPeriod, String expectedMessage)
            throws Exception {
        mockMvc.perform(post("/loan-decision/check")
                        .param("personalCode", personalCode)
                        .param("loanAmount", String.valueOf(loanAmount))
                        .param("loanPeriod", String.valueOf(loanPeriod)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(expectedMessage));
    }

    private static Stream<Arguments> invalidParamsProvider() {
        return Stream.of(
                Arguments.of("49002010976", 1500.0, 24, "Minimal amount value is 2000"),
                Arguments.of("49002010976", 5000.0, 6, "Minimal period is 12 months"));
    }
}
