package com.example.loaddecisionapp.service;

import static com.example.loaddecisionapp.service.impl.MaximumAmountCalculator.findSuggestedLoanPeriod;
import static com.example.loaddecisionapp.service.impl.MaximumAmountCalculator.getMaxApprovedAmount;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class MaximumAmountCalculatorTest {

    @ParameterizedTest
    @MethodSource("amountProvider")
    void testGetMaxApprovedAmount(double loanAmount, int creditModifier, double expected) {
        double result = getMaxApprovedAmount(loanAmount, creditModifier);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> amountProvider() {
        return Stream.of(
                Arguments.of(3000.0, 2, 6000.0),
                Arguments.of(1000.0, 2, 2000.0),
                Arguments.of(8000.0, 5, 10000.0)
        );
    }

    @Test
    void testValidLoanPeriod() {
        int result = findSuggestedLoanPeriod(5000, 12, 300);
        assertEquals(17, result);
    }

    @ParameterizedTest
    @CsvSource({"3000, 12, 100, 30", "2000, 12, 300, 12", "5000, 50, 200, 50"})
    void testSuggestedLoanPeriod(double loanAmount, int initialLoanPeriod, double creditModifier,
            int expectedLoanPeriod) {
        int result = findSuggestedLoanPeriod(loanAmount, initialLoanPeriod, creditModifier);
        assertEquals(expectedLoanPeriod, result);
    }
}

