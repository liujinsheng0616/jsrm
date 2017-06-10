package org.jsrml.common.persistence;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

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
			} catch (Exception e) {
			}
			currentClazz = currentClazz.getSuperclass();
		}
		return null;
	}

	private static Object createInitializeFailedObject(Object obj) {
		if (obj instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) obj;
			Serializable id = proxy.getHibernateLazyInitializer()
					.getIdentifier();
			if (id == null) {
				return null;
			}
			try {
				Class<?> entityClass = proxy.getHibernateLazyInitializer()
						.getPersistentClass();
				Field idField = getEntityIdField(entityClass);
				if (idField != null) {
					Object entity = entityClass.newInstance();
					idField.setAccessible(true);
					idField.set(entity, id);
					return entity;
				}
			} catch (Exception e1) {
			}
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

	@SuppressWarnings("unchecked")
	protected static <T> T getProperty(Object obj, Class<T> clazz) {
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
			} catch (Exception e) {
				return null;
			}
		}
		return (T) obj;
	}

	public abstract T getId();

	public abstract void setId(T id);

}
