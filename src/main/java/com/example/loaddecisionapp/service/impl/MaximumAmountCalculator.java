package com.example.loaddecisionapp.service.impl;

public class MaximumAmountCalculator {

    private MaximumAmountCalculator() {
    }

    public static double getMaxApprovedAmount(double loadPeriod, int creditModifier) {
        return Math.min(10000, loadPeriod * creditModifier);
    }

    public static int findSuggestedLoanPeriod(double loanAmount, int loanPeriod, double creditModifier) {
        if (loanPeriod > 60) {
            return 60;
        }
        double creditScore = (creditModifier / loanAmount) * loanPeriod;
        if (creditScore >= 1) {
            return loanPeriod;
        } else {
            return findSuggestedLoanPeriod(loanAmount, loanPeriod + 1, creditModifier);
        }
    }
}
