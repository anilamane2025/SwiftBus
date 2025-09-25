package com.anil.swiftBus.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private String region;
    private boolean mobileOnly;
    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        this.region = constraintAnnotation.region();
        this.mobileOnly = constraintAnnotation.mobileOnly();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Let @NotBlank handle empties; this validator only checks format/region/type.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        try {
            // Parse relative to region. "ZZ" = infer from +country code if present.
            PhoneNumber number = phoneUtil.parse(value, region);

            String regionToCheck = "ZZ".equals(region)
                    ? phoneUtil.getRegionCodeForNumber(number)
                    : region;

            if (regionToCheck == null || regionToCheck.trim().isEmpty()) {
                return false;
            }

            // Valid for region?
            if (!phoneUtil.isValidNumberForRegion(number, regionToCheck)) {
                return false;
            }

            // Require mobile (or fixed_line_or_mobile) if configured
            if (mobileOnly) {
                PhoneNumberType type = phoneUtil.getNumberType(number);
                if (!(type == PhoneNumberType.MOBILE || type == PhoneNumberType.FIXED_LINE_OR_MOBILE)) {
                    return false;
                }
            }

            // Reject obvious junk like all same digits (e.g., 1111111111)
            String national = phoneUtil.getNationalSignificantNumber(number);
            if (national != null && allSameDigits(national)) {
                return false;
            }

            return true;

        } catch (NumberParseException e) {
            return false;
        }
    }

    private boolean allSameDigits(String s) {
        if (s.length() <= 1) return true;
        char first = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != first) return false;
        }
        return true;
    }
}
