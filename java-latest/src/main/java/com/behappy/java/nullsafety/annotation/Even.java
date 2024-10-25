package com.behappy.java.nullsafety.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@TypeQualifier(applicableTo = Number.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Even {
    When when() default When.ALWAYS;

    class Checker implements TypeQualifierValidator<Even> {
        @Nonnull
        public When forConstantValue(@Nonnull Even annotation, Object v) {
            if (!(v instanceof Number value))
                return When.NEVER;
            return (value.intValue() & 1) == 0 ? When.ALWAYS : When.NEVER;
        }
    }
}
