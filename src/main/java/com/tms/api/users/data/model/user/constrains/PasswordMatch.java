package com.tms.api.users.data.model.user.constrains;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Fields doesn't match!";

    Class< ? >[] groups() default {};
    Class< ? extends Payload>[] payload() default {};
    String field();
    String fieldMatch();

    @Target({
            TYPE,
            ANNOTATION_TYPE
    })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PasswordMatch[] value();
    }
}