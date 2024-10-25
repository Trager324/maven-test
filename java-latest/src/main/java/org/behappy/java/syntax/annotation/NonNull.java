package org.behappy.java.syntax.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Nonnull
@TypeQualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNull {
}
