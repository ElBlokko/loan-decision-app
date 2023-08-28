package com.example.loaddecisionapp.service;

import com.example.loaddecisionapp.dto.LoanDecisionResponseDTO;

public interface LoanDecisionService {

    LoanDecisionResponseDTO makeLoanDecision(String personalCode, double loanAmount, int loanPeriod);
}
