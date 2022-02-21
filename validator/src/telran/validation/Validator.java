package telran.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.time.*;
import java.util.*;
import telran.validation.constraint.*;

public class Validator {
	
	private static final String VALID_NAME = "Valid";

	public static List<String> validate(Object obj) {
		
		var errors = new ArrayList<String>();
		var clazz = obj.getClass();
		var fields = clazz.getDeclaredFields();
		
		for (Field field: fields) {
			
			field.setAccessible(true);
			var anObjs = field.getAnnotations();
			for (Annotation anObj: anObjs) {
				var methodName = anObj.annotationType().getSimpleName();
				if (!methodName.equals(VALID_NAME)) {
					try {
						var method = Validator.class.getDeclaredMethod(methodName, Object.class, 
								Field.class);
						var res = method.invoke(null, obj, field);
						if (res != null) {
							errors.add(res.toString());
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				else {
					try {
						errors.addAll(validate(field.get(obj)));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		
		return errors;
	}
	
	private static String Email(Object obj, Field fld) {
		var anObj = fld.getAnnotation(Email.class);
		var emailPattern = "^[\\w._%+-]+@[\\w.-]+\\.\\w{2,6}$";
		try {
			var value = fld.get(obj).toString();
			if (!value.matches(emailPattern)) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	
	private static String Future(Object obj, Field fld) {
		var anObj = fld.getAnnotation(Future.class);
		try {
			var value = (LocalDate)fld.get(obj);
			if (value.isBefore(LocalDate.now())) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	
	private static String Max(Object obj, Field fld) {
		var anObj = fld.getAnnotation(Max.class);
		var max = anObj.value();
		try {
			var value = (Number)fld.get(obj);
			if (value.longValue() > max) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	
	private static String Min(Object obj, Field fld) {
		var anObj = fld.getAnnotation(Min.class);
		var min = anObj.value();
		try {
			var value = (Number)fld.get(obj);
			if (value.longValue() < min) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	
	private static String NotEmpty(Object obj, Field fld) {
		var anObj = fld.getAnnotation(NotEmpty.class);
		try {
			var value = fld.get(obj);
			if (value == null || value.toString().isEmpty()) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	
	private static String NotNull(Object obj, Field fld) {
		var anObj = fld.getAnnotation(NotNull.class);
		try {
			var value = fld.get(obj);
			if (value == null) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
	
	private static String Past(Object obj, Field fld) {
		var anObj = fld.getAnnotation(Past.class);
		try {
			var value = (LocalDate)fld.get(obj);
			if (value.isAfter(LocalDate.now())) {
				return String.format("%s: %s", fld.getName(), anObj.message());
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}
}
