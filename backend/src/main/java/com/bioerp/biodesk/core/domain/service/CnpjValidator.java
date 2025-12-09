package com.bioerp.biodesk.core.domain.service;

public class CnpjValidator {

    public String validateAndNormalize(String cnpj) {
        String normalized = normalize(cnpj);
        if (!isValid(normalized)) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }
        return normalized;
    }

    private String normalize(String cnpj) {
        if (cnpj == null) {
            throw new IllegalArgumentException("CNPJ is required");
        }
        String digits = cnpj.replaceAll("\\D", "");
        if (digits.length() != 14) {
            throw new IllegalArgumentException("CNPJ must have 14 digits");
        }
        return digits;
    }

    private boolean isValid(String digits) {
        if (digits.chars().distinct().count() == 1) {
            return false;
        }
        int[] numbers = digits.chars().map(Character::getNumericValue).toArray();
        int firstCheck = calculateCheckDigit(numbers, 12);
        int secondCheck = calculateCheckDigit(numbers, 13);
        return numbers[12] == firstCheck && numbers[13] == secondCheck;
    }

    private int calculateCheckDigit(int[] numbers, int length) {
        int[] weights = length == 12 ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2} :
                new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += numbers[i] * weights[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
