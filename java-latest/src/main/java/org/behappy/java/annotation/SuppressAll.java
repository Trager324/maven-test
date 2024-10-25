package org.behappy.java.annotation;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SuppressWarnings("all")
@TypeQualifierDefault({ElementType.TYPE})
public @interface SuppressAll {
}
