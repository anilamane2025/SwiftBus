package com.anil.swiftBus.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {

    /**
     * 2-letter region code (ISO 3166-1 alpha-2).
     * Use "IN" to force India.
     * Use "ZZ" to auto-detect from a +country code in the value (e.g., +91...).
     */
    String region() default "IN";

    /** Only allow mobile or fixed_line_or_mobile types. */
    boolean mobileOnly() default true;

    //String message() default "{phone.invalid}";
    String message() default "Enter a valid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}