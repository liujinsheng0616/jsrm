package org.jsrml.common.persistence.hibernate;

import java.lang.reflect.Field;
import java.util.List;
import org.hibernate.transform.ResultTransformer;

/**
 * Hibernate结果集转换器
 * 
 * @author 
 */
public class MyResultTransformer implements ResultTransformer {
	private static final long serialVersionUID = 1L;

	private Class<?> clazz;

	public MyResultTransformer(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Field getClassField(Class<?> clazz, String name) {
		Field field = null;
		boolean end = false;
		boolean error = false;
		Class<?> currentClass = clazz;
		while (field == null && !end && !error) {
			try {
				field = currentClass.getDeclaredField(name);
				field.setAccessible(true);
			} catch (SecurityException e) {
				e.printStackTrace();
				error = true;
			} catch (NoSuchFieldException e) {
				currentClass = currentClass.getSuperclass();
				if (currentClass == Object.class) {
					end = true;
				}
			}
		}
		return field;
	}

	public Object getObjectInstance() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setValue(Object targetObj, String name, Object value) {
		String[] names = name.split("\\.");
		Object currentObj = targetObj;
		for (int i = 0; i < names.length; i++) {
			try {
				Field field = getClassField(currentObj.getClass(), names[i]);
				if (field == null || currentObj == null) {
					break;
				}
				if (i < names.length - 1) {
					try {
						Object fieldObj = field.get(currentObj);
						if (fieldObj == null) {
							try {
								fieldObj = field.getType().newInstance();
								field.set(currentObj, fieldObj);
							} catch (InstantiationException e) {
								e.printStackTrace();
							}
						}
						currentObj = fieldObj;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					try {
						field.set(currentObj, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object obj = getObjectInstance();
		for (int i = 0; i < aliases.length; i++) {
			if (aliases[i] != null) {
				setValue(obj, aliases[i], tuple[i]);
			}
		}
		return obj;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}

}
