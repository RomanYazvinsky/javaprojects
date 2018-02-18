package com.senla.hotel.utilities;

import java.io.FileWriter;
import java.io.IOException;
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.senla.hotel.annotations.CsvEntity;
import com.senla.hotel.annotations.CsvProperty;
import com.senla.hotel.constants.PropertyType;
import com.senla.hotel.entities.IEntity;

import utilities.EntityParser;

public class CSVModule {
	private static Logger logger = LogManager.getLogger(CSVModule.class);


	private static CsvEntity defineCsvEntity(Object obj) {
		return (CsvEntity) obj.getClass().getAnnotation(CsvEntity.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static CsvEntity defineCsvEntity(Class csvClass) {
		return (CsvEntity) csvClass.getAnnotation(CsvEntity.class);
	}

	private static void write(String path, String output) {
		try (FileWriter fileWriter = new FileWriter(path, false)) {
			fileWriter.write(output.toString());
		} catch (IOException e) {
			logger.log(Level.DEBUG, e.getMessage());
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
			logger.log(Level.DEBUG, e.getMessage());
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
						fieldOrder[csvProperty.columnNumber()] = defineCollection(field.get(entity), csvEntity.valueSeparator(),
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
			logger.log(Level.DEBUG, e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object fillFields(Object entity, String data) {
		Field[] fields = entity.getClass().getDeclaredFields();
		String[] values = data.split(",");
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				CsvProperty csvProperty = fields[i].getAnnotation(CsvProperty.class);
				if (csvProperty != null) {
					Class fieldClass = fields[i].getType();

					if (csvProperty.propertyType() == PropertyType.SIMPLE_PROPERTY) {
						fields[i].set(entity, EntityParser.defineField(fieldClass, values[csvProperty.columnNumber()]));
					}
					if (csvProperty.propertyType() == PropertyType.COMPOSITE_PROPERTY) {
						Object field;
						field = EntityParser.defineField(fieldClass, values[csvProperty.columnNumber()]);
						if (field == null) {
							field = fieldClass.newInstance();
							fieldClass.getMethod(csvProperty.setterMethod(), String.class).invoke(field,
									values[csvProperty.columnNumber()]);
						}
						fields[i].set(entity, field);

					}

					if (csvProperty.propertyType() == PropertyType.COLLECTION_PROPERTY) {
						List field = (List) fieldClass.newInstance();
						Class storagingType = Class.forName(csvProperty.storagingClass());
						Method setter = storagingType.getDeclaredMethod(csvProperty.setterMethod(), String.class);
						for(int k = csvProperty.columnNumber(); k < values.length; k++) {
							Object storagingEntity = storagingType.newInstance();
							 setter.invoke(storagingEntity, values[k]);
							 field.add(storagingEntity);
						}
						fields[i].set(entity, field);
					}
				}
			}
			return entity;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			logger.log(Level.DEBUG, e.getMessage());
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static ArrayList<Object> importAll(Class csvClass) {
		CsvEntity csvEntity = defineCsvEntity(csvClass);
		if (csvEntity == null) {
			return null;
		}
		ArrayList<Object> entities = new ArrayList<>();
		try {
			List<String> entityParameter = Files.readAllLines(Paths.get(csvEntity.filename()), StandardCharsets.UTF_8);
			for (int i = 1; i < entityParameter.size(); i++) {
				// Object entity = EntityParser.parse(csvClass, entityParameter.get(i));
				Object entity = fillFields(csvClass.newInstance(), entityParameter.get(i));
				if (entity != null) {
					entities.add(entity);
				}
			}
			return entities;
		} catch (Exception e) {
			logger.log(Level.DEBUG, e.getMessage());
			return null;
		}
	}

}
