package com.senla.hotel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)

public @interface CsvEntity {
	public String csvHeader();
	public String filename();
	public String valueSeparator();
	public String entityId();
}
