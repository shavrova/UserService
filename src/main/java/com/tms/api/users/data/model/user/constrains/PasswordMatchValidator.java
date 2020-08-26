package com.tms.api.users.data.model.user.constrains;

import com.tms.api.users.util.exception.PasswordDoesNotMatchException;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {
        field = constraintAnnotation.field();
        fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        if (fieldValue != null && fieldValue.equals(fieldMatchValue)) {
            return true;
        }
        throw new PasswordDoesNotMatchException("Passwords didn't match");
    }
}
