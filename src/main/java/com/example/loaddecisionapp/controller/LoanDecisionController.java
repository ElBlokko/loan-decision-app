package com.example.loaddecisionapp.controller;

import com.example.loaddecisionapp.dto.LoanDecisionResponseDTO;
import com.example.loaddecisionapp.service.LoanDecisionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/loan-decision")
@Validated
public class LoanDecisionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDecisionController.class);

    private final LoanDecisionService loanDecisionService;

    @Autowired
    public LoanDecisionController(LoanDecisionService loanDecisionService) {
        this.loanDecisionService = loanDecisionService;
    }

    @PostMapping("/check")
    public ResponseEntity<LoanDecisionResponseDTO> checkLoanDecision(
            @RequestParam @Pattern(regexp = "\\d{11}") String personalCode,
            @RequestParam @Min(value = 2000, message = "Minimal amount value is 2000") @Max(value = 10000, message = "Maximum amount value is 10000") double loanAmount,
            @RequestParam @Min(value = 12, message = "Minimal period is 12 months") @Max(value = 60, message = "Maximum period is 60 months") int loanPeriod) {

        LOGGER.info("Received request to make a loan decision for person: {}, with amount: {}, for period: {}",
                personalCode, loanAmount, loanPeriod);
        LoanDecisionResponseDTO response = loanDecisionService.makeLoanDecision(personalCode, loanAmount, loanPeriod);
        return ResponseEntity.ok(response);
    }
}

