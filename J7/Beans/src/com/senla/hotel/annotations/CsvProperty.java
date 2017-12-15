package com.senla.hotel.annotations;

import java.lang.annotation.*;

import com.senla.hotel.constants.PropertyType;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CsvProperty {
	public PropertyType propertyType();

	public int columnNumber();

	public String getterMethod() default "";
}
