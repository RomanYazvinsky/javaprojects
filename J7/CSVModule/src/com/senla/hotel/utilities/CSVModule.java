package com.senla.hotel.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.Constants;
import com.senla.hotel.entities.IEntity;

public class CSVModule {
	private static Logger logger;
	static {
		logger = Logger.getLogger(CSVModule.class.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(Constants.logFileHandler);
	}

	private static CsvEntity defineCsvEntity(Object obj) {
		Annotation[] objAnnotations = obj.getClass().getAnnotations();
		for (Annotation annotation : objAnnotations) {
			if (annotation.annotationType().equals(CsvEntity.class)) {
				return (CsvEntity) annotation;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static CsvEntity defineCsvEntity(Class csvClass) {
		Annotation[] objAnnotations = csvClass.getAnnotations();
		for (Annotation annotation : objAnnotations) {
			if (annotation.annotationType().equals(CsvEntity.class)) {
				return (CsvEntity) annotation;
			}
		}
		return null;
	}

	private static void write(String path, String output) {
		try (FileWriter fileWriter = new FileWriter(path, false)) {
			fileWriter.write(output.toString());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	private static HashMap<Field, CsvProperty> defineFields(Field[] fields) {
		HashMap<Field, CsvProperty> fieldList = new HashMap<Field, CsvProperty>();
		for (Field field : fields) {
			field.setAccessible(true);
			CsvProperty csvProperty = field.getAnnotation(CsvProperty.class);
			if (csvProperty != null) {
				fieldList.put(field, csvProperty);
			}
		}
		return fieldList;
	}

	private static String fieldsToString(String[] fields, String valueSeparator) {
		StringBuilder result = new StringBuilder();
		for (String string : fields) {
			result.append(string).append(valueSeparator);
		}
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	private static String defineCollection(Object obj, String valueSeparator, String keyMethod) {
		try {
			StringBuilder result = new StringBuilder();
			Collection<Object> collection = new ArrayList<Object>((Collection<Object>) obj);
			if (keyMethod.isEmpty()) {
				for (Object cObject : collection) {
					result.append(cObject.toString()).append(valueSeparator);
				}
			} else {
				for (Object cObject : collection) {
					Method method = cObject.getClass().getDeclaredMethod(keyMethod);
					result.append(method.invoke(obj, null).toString()).append(valueSeparator);
				}
			}
			return result.toString();
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			logger.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}

	public static void exportAll(ArrayList<? extends IEntity> entities) {
		try {
			if (entities.isEmpty()) {
				return;
			}
			IEntity entity = entities.get(0);
			CsvEntity csvEntity = defineCsvEntity(entity);
			StringBuilder output = new StringBuilder(csvEntity.csvHeader() + System.lineSeparator());
			HashMap<Field, CsvProperty> fieldMap = defineFields(entity.getClass().getDeclaredFields());
			String[] fieldOrder = new String[fieldMap.size()];
			for (IEntity iEntity : entities) {
				for (Field field : fieldMap.keySet()) {
					CsvProperty csvProperty = fieldMap.get(field);
					switch (csvProperty.propertyType()) {
					case SIMPLE_PROPERTY: {
						fieldOrder[csvProperty.columnNumber()] = field.get(iEntity).toString();
						break;
					}
					case COLLECTION_PROPERTY: {
						fieldOrder[csvProperty.columnNumber()] = defineCollection(iEntity, csvEntity.valueSeparator(),
								csvProperty.getterMethod());
						break;
					}
					default: {
						fieldOrder[csvProperty.columnNumber()] = field.get(iEntity).getClass()
								.getDeclaredMethod(csvProperty.getterMethod()).invoke(field.get(iEntity), null)
								.toString();
					}
					}

				}
				output.append(fieldsToString(fieldOrder, csvEntity.valueSeparator())).append(System.lineSeparator());
			}
			write(csvEntity.filename(), output.toString());
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | ClassCastException
				| NoSuchMethodException | InvocationTargetException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Object> importAll(Class csvClass) {
		CsvEntity csvEntity = defineCsvEntity(csvClass);
		ArrayList<Object> entities = new ArrayList<>();
		try {
			List<String> entityParameter = Files.readAllLines(Paths.get(csvEntity.filename()), StandardCharsets.UTF_8);
			for (int i = 1; i < entityParameter.size(); i++) {
				entities.add(csvClass.getDeclaredConstructor(String.class).newInstance(entityParameter.get(i)));
			}
			return entities;
		} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			logger.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}

}
