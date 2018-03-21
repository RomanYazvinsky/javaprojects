package com.senla.hotel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.senla.hotel.constants.PropertyType;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CsvProperty {
	public PropertyType propertyType();

	public int columnNumber();

	public String getterMethod() default "";
	
	public String setterMethod() default "valueOf";

	public String storagingClass() default "";
}
