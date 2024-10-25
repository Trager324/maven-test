package org.behappy.java.syntax.annotation;

import lombok.Data;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ThreadSafe
@TypeQualifierDefault({ElementType.TYPE})
public @interface ImmutablePackage {
}
