package org.jsrml.common.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 针对有Hibernate代理的类进行JSON转化
 * 
 * @author 
 */
public class JSONUtils {
	
	protected final static ValueFilter filter = new ValueFilter() {
		@Override
		public Object process(Object source, String name, Object value) {
			if (value == null)
				return null;
			return getProperty(value, value.getClass());
		}
	};

	/**
	 * 将对象转换为JSON
	 * 
	 * @param o
	 * @param prettyFormat
	 * @return
	 */
	public static String c(Object o) {
		return JSON.toJSONString(o, filter);
	}
	
	/**
	 * 将对象转换为JSON
	 * 
	 * @param o
	 * @param prettyFormat
	 * @return
	 */
	public static String c(Object o, SerializerFeature... features) {
		return JSON.toJSONString(o, filter, features);
	}
	
	/**
	 * 将JSON转换为对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T p(String json, Class<T> clazz) {
		if (json == null || clazz == null) {
			return null;
		}
		return JSON.parseObject(json, clazz);
	}
	
	/**
	 * 将JSON转换为数组对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <E> List<E> pa(String json, Class<E> clazz) {
		if (json == null || clazz == null) {
			return null;
		}
		return JSON.parseArray(json, clazz);
	}


	/**
	 * 获取有@Id注解的Entity属性
	 * 
	 * @param clazz
	 * @return
	 */
	private static Field getEntityIdField(Class<?> clazz) {
		Class<?> currentClazz = clazz;
		while (currentClazz != Object.class) {
			try {
				Field[] fields = currentClazz.getDeclaredFields();
				for (Field field : fields) {
					if (field.getAnnotation(Id.class) != null) {
						return field;
					}
				}
			} catch (Exception e) { }
			currentClazz = currentClazz.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 创建延迟加载失败的对象
	 * 
	 * @param obj
	 * @return
	 */
	private static Object createInitializeFailedObject(Object obj) {
		if (obj instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) obj;
			Serializable id = proxy.getHibernateLazyInitializer().getIdentifier();
			if (id == null) {
				return null;
			}
			try {
				Class<?> entityClass = proxy.getHibernateLazyInitializer().getPersistentClass();
				Field idField = getEntityIdField(entityClass);
				if (idField != null) {
					Object entity = entityClass.newInstance();
					idField.setAccessible(true);
					idField.set(entity, id);
					return entity;
				}
			} catch (Exception e1) { }
			return null;
		} else if (obj instanceof PersistentCollection) {
			if (obj instanceof List) {
				return new ArrayList<Object>();
			} else if (obj instanceof Map) {
				return new HashMap<Object, Object>();
			} else if (obj instanceof Set) {
				return new HashSet<Object>();
			}
			return null;
		}
		return obj;
	}
	
	/**
	 * 获取属性，遇到延迟加载异常返回NULL
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getProperty(Object obj, Class<T> clazz) {
		if (obj == null || clazz == null) {
			return (T) obj;
		}
		if (!Hibernate.isInitialized(obj)) {
			try {
				Hibernate.initialize(obj);
			} catch (LazyInitializationException e) {
				return (T) createInitializeFailedObject(obj);
			} catch (ObjectNotFoundException e) {
				return (T) createInitializeFailedObject(obj);
			} catch (HibernateException e) {
				return null;
			}
		}
		return (T) obj;
	}
		
}
