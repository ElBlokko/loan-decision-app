package com.example.loaddecisionapp.service.impl;

import static com.example.loaddecisionapp.service.impl.MaximumAmountCalculator.findSuggestedLoanPeriod;
import static com.example.loaddecisionapp.service.impl.MaximumAmountCalculator.getMaxApprovedAmount;

import com.example.loaddecisionapp.dto.Decision;
import com.example.loaddecisionapp.dto.LoanDecisionResponseDTO;
import com.example.loaddecisionapp.service.CreditModifierService;
import com.example.loaddecisionapp.service.LoanDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanDecisionServiceImpl implements LoanDecisionService {

    @Autowired
    private CreditModifierService creditModifierService;

    public LoanDecisionResponseDTO makeLoanDecision(String personalCode, double loanAmount, int loanPeriod) {
        LoanDecisionResponseDTO response;
        int creditModifier = creditModifierService.getCreditModifier(personalCode);

        if (creditModifier == -1) {
            response = new LoanDecisionResponseDTO(Decision.NEGATIVE, 0, loanPeriod);
        } else {
            double creditScore = (creditModifier / loanAmount) * loanPeriod;
            double maxApprovedAmount = getMaxApprovedAmount(loanPeriod, creditModifier);

            if (creditScore >= 1) {
                response = new LoanDecisionResponseDTO(Decision.POSITIVE, maxApprovedAmount, loanPeriod);
            } else {
                int suggestedLoanPeriod = findSuggestedLoanPeriod(loanAmount, loanPeriod, creditModifier);
                maxApprovedAmount = getMaxApprovedAmount(suggestedLoanPeriod, creditModifier);
                response = new LoanDecisionResponseDTO(Decision.NEGATIVE, maxApprovedAmount, suggestedLoanPeriod);
            }
        }
        return response;
    }
}

