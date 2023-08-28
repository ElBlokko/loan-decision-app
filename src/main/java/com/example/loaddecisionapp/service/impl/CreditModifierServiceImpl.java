package com.example.loaddecisionapp.service.impl;

import com.example.loaddecisionapp.service.CreditModifierService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CreditModifierServiceImpl implements CreditModifierService {

    private static final Map<String, Integer> creditModifiers = new HashMap<>();

    static {
        creditModifiers.put("49002010965", -1);  // Debt
        creditModifiers.put("49002010976", 100);  // Segment 1
        creditModifiers.put("49002010987", 300);  // Segment 2
        creditModifiers.put("49002010998", 1000); // Segment 3
    }

    @Override
    public int getCreditModifier(String personalCode) {
        return creditModifiers.getOrDefault(personalCode, 100);
    }
}

