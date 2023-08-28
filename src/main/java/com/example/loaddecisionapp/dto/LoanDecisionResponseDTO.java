package com.example.loaddecisionapp.dto;

public record LoanDecisionResponseDTO(Decision decision, double approvedAmount, int suggestedLoanPeriod) {

}
