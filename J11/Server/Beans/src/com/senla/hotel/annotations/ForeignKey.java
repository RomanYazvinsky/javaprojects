package com.senla.hotel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ForeignKey {
    String tableName();
    String internalName();
    String externalName();
    boolean isEntity() default true;
}
