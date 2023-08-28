package com.example.loaddecisionapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.example.loaddecisionapp.dto.Decision;
import com.example.loaddecisionapp.dto.LoanDecisionResponseDTO;
import com.example.loaddecisionapp.service.CreditModifierService;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoanDecisionServiceImplTest {

    @Mock
    private CreditModifierService creditModifierService;

    @InjectMocks
    private LoanDecisionServiceImpl loanDecisionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("loanDecisionArguments")
    void testMakeLoanDecision(Decision expectedDecision, double loanAmount, int loanPeriod, int creditModifier,
            double expectedApprovedAmount) {
        when(creditModifierService.getCreditModifier(anyString())).thenReturn(creditModifier);
        LoanDecisionResponseDTO response = loanDecisionService.makeLoanDecision("someCode", loanAmount, loanPeriod);
        assertEquals(expectedDecision, response.decision());
        assertEquals(expectedApprovedAmount, response.approvedAmount());
    }

    private static Stream<Arguments> loanDecisionArguments() {
        return Stream.of(
                Arguments.of(Decision.POSITIVE, 4000.0, 24, 300, 7200.0),
                Arguments.of(Decision.NEGATIVE, 3000.0, 36, -1, 0.0)
        );
    }

    @Test
    void testLoanDecisionWithSuggestedLoanPeriod() {
        when(creditModifierService.getCreditModifier(anyString())).thenReturn(60);
        LoanDecisionResponseDTO response = loanDecisionService.makeLoanDecision("someCode", 1800, 20);
        assertEquals(Decision.NEGATIVE, response.decision());
        assertEquals(1800, response.approvedAmount());
        assertEquals(30, response.suggestedLoanPeriod());
    }
}
