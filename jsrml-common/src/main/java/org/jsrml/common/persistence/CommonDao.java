package org.jsrml.common.persistence;

import org.jsrml.common.pagination.Pagination;
import org.jsrml.common.persistence.query.QueryCondition;
import org.jsrml.common.persistence.query.QueryConditionGroup;
import org.jsrml.common.persistence.query.QueryConditionType;
import org.jsrml.common.persistence.query.QueryConfig;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

/**
 * @类功能说明：公共DAO，auto*()查询
 * @类修改者：
 * @修改日期：2015-3-27下午5:59:33
 * @修改说明：
 * @公司名称：
 * @作者：
 * @创建时间：2015-3-27下午5:59:33
 */
@SuppressWarnings("rawtypes")
@Repository
@Transactional(rollbackFor = Exception.class)
public class CommonDao extends BaseDao<BaseEntity, BaseQO> {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private ThreadLocal<Class<? extends BaseEntity>> entityClass = new ThreadLocal<Class<? extends BaseEntity>>();

	/**
	 * @方法功能说明：设置查询实体
	 * @修改者名字：
	 * @修改时间：2015-3-27下午5:58:06
	 * @修改内容：
	 * @参数：@param clazz
	 * @参数：@return
	 * @return:CommonDao
	 * @throws
	 */
	public CommonDao forEntity(Class<? extends BaseEntity> clazz) {
		entityClass.set(clazz);
		return this;
	}
	
	/**
	 * @方法功能说明：根据QO注解自动查询唯一
	 * @修改者名字：
	 * @修改时间：2015-3-27下午5:58:26
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:T
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T autoQueryUnique(BaseQO qo) {
		return (T) super.queryUnique(qo);
	}

	/**
	 * @方法功能说明：根据QO注解自动查询列表
	 * @修改者名字：
	 * @修改时间：2015-3-27下午5:58:35
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<T>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> autoQueryList(BaseQO qo) {
		return (List<T>) super.queryList(qo);
	}

	/**
	 * @方法功能说明：根据QO注解自动查询列表
	 * @修改者名字：
	 * @修改时间：2015-3-27下午5:58:56
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param offset
	 * @参数：@param maxSize
	 * @参数：@return
	 * @return:List<T>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> List<T> autoQueryList(BaseQO qo, Integer offset, Integer maxSize) {
		return (List<T>) super.queryList(qo, offset, maxSize);
	}

	/**
	 * @方法功能说明：根据QO注解自动分页查询
	 * @修改者名字：
	 * @修改时间：2015-3-27下午5:59:05
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination autoQueryPagination(Pagination pagination) {
		return super.queryPagination(pagination);
	}

	/**
	 * @方法功能说明：得到QO有注解的属性MAP
	 * @修改者名字：
	 * @修改时间：2015-3-27下午4:54:10
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:Map<String,List<Field>>
	 * @throws
	 */
	private Map<String, List<Field>> getQoFieldMap(BaseQO qo) {
		
		Map<String, List<Field>> map = new HashMap<String, List<Field>>();
		map.put(null, new ArrayList<Field>());
		List<Field> fieldList = new ArrayList<Field>();
		if (qo == null)
			return map;

		Class<?> currentClass = qo.getClass();
		while (currentClass.getSuperclass() != null
				&& !currentClass.equals(BaseQO.class)) {
			Field[] fields = currentClass.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fields));
			currentClass = currentClass.getSuperclass();
		}

		// 检查QO属性
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			if (field.getAnnotation(QueryCondition.class) == null) {
				fieldList.remove(i--);
				continue;
			}
			field.setAccessible(true);
			QueryConditionGroup queryConditionGroup = field.getAnnotation(QueryConditionGroup.class);
			if (queryConditionGroup != null) {
				String groupName = queryConditionGroup.value();
				if (!map.containsKey(groupName))
					map.put(groupName, new ArrayList<Field>());
				map.get(groupName).add(field);
			} else
				map.get(null).add(field);
		}

		return map;
	}
	
	
	
	/**
	 * @方法功能说明：判断是否为true
	 * @修改者名字：
	 * @修改时间：2015-3-27下午4:54:37
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@param name
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean isTrue(BaseQO qo, String name) {

		if (StringUtils.isBlank(name))
			return false;
		
		Field field = null;
		Class<?> currentClass = qo.getClass();
		while (field == null && currentClass.getSuperclass() != null
				&& !currentClass.equals(BaseQO.class)) {
			try {
				field = currentClass.getDeclaredField(name);
			} catch (Exception e) { }
			currentClass = currentClass.getSuperclass();
		}
		if (field == null)
			return false;
		
		if (!(field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)))
			return false;

		Boolean b = null;
		try {
			field.setAccessible(true);
			b = (Boolean) field.get(qo);
		} catch (Exception e) { }
		if (b == null || b == false)
			return false;

		return true;
	}
	
	/**
	 * @方法功能说明：如果是String判断是否为空
	 * @修改者名字：
	 * @修改时间：2015-3-27下午4:54:54
	 * @修改内容：
	 * @参数：@param obj
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean checkIfStringBlank(Object obj) {
		if (!(obj instanceof String))
			return false;
		if (StringUtils.isBlank((String) obj))
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQO qo) {
		
		if (!qo.getEnableQueryAnnotation()) {
			return criteria;
		}
		
		Map<String, List<Field>> fieldMap = getQoFieldMap(qo);
		
		for (Entry<String, List<Field>> entry : fieldMap.entrySet()) {

			List<Criterion> criterions = new ArrayList<Criterion>();

			for (Field field : entry.getValue()) {
				QueryCondition queryCondition = field.getAnnotation(QueryCondition.class);

				String propertyName = queryCondition.name();
				Object fieldValue = null;
				try {
					fieldValue = field.get(qo);
				} catch (Exception e) { }
				
				if (fieldValue == null)
					continue;
				if (checkIfStringBlank(fieldValue))
					continue;

				// like查询
				if (isTrue(qo, queryCondition.ifTrueUseLike())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.ANYWHERE));
					continue;
				}

				// 完全匹配
				if (QueryConditionType.EQ.equals(queryCondition.type())) {
					criterions.add(Restrictions.eq(propertyName, fieldValue));
				} 
				// 完全匹配或为NULL
				else if (QueryConditionType.EQ_OR_NULL.equals(queryCondition.type())) {
					criterions.add(Restrictions.eqOrIsNull(propertyName, fieldValue));
				} 
				// 不匹配
				else if (QueryConditionType.NE.equals(queryCondition.type())) {
					criterions.add(Restrictions.ne(propertyName, fieldValue));
				} 
				// 不匹配或为NULL
				else if (QueryConditionType.NE_OR_NULL.equals(queryCondition.type())) {
					criterions.add(Restrictions.neOrIsNotNull(propertyName, fieldValue));
				} 
				// 大于等于
				else if (QueryConditionType.GE.equals(queryCondition.type())) {
					criterions.add(Restrictions.ge(propertyName, fieldValue));
				} 
				// 大于
				else if (QueryConditionType.GT.equals(queryCondition.type())) {
					criterions.add(Restrictions.gt(propertyName, fieldValue));
				} 
				// 小于等于
				else if (QueryConditionType.LE.equals(queryCondition.type())) {
					criterions.add(Restrictions.le(propertyName, fieldValue));
				} 
				// 小于
				else if (QueryConditionType.LT.equals(queryCondition.type())) {
					criterions.add(Restrictions.lt(propertyName, fieldValue));
				} 
				// 模糊匹配任何位置
				else if (QueryConditionType.LIKE_ANYWHERE.equals(queryCondition.type())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.ANYWHERE));
				} 
				// 模糊匹配开始位置
				else if (QueryConditionType.LIKE_START.equals(queryCondition.type())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.START));
				} 
				// 模糊匹配结尾
				else if (QueryConditionType.LIKE_END.equals(queryCondition.type())) {
					criterions.add(Restrictions.like(propertyName, (String) fieldValue, MatchMode.END));
				}// 不为空
				else if (QueryConditionType.IS_NULL.equals(queryCondition.type())) {
					criterions.add(Restrictions.isNull(propertyName));
				}
				else if (QueryConditionType.IS_NOT_NULL.equals(queryCondition.type())) {
					criterions.add(Restrictions.isNotNull(propertyName));
				}
				
				// 包含
				else if (QueryConditionType.IN.equals(queryCondition.type())) {
					if (field.getType().isArray()) {
						Object[] objects = (Object[]) fieldValue;
						if (objects.length == 0)
							continue;
						criterions.add(Restrictions.in(propertyName, objects));
					} else {
						Collection<Object> objects = (Collection<Object>) fieldValue;
						if (objects.size() == 0)
							continue;
						criterions.add(Restrictions.in(propertyName, objects));
					}
				} 
				// 不包含
				else if (QueryConditionType.NOT_IN.equals(queryCondition.type())) {
					if (field.getType().isArray()) {
						Object[] objects = (Object[]) fieldValue;
						if (objects.length == 0)
							continue;
						criterions.add(Restrictions.not(Restrictions.in(propertyName, objects)));
					} else {
						Collection<Object> objects = (Collection<Object>) fieldValue;
						if (objects.size() == 0)
							continue;
						criterions.add(Restrictions.not(Restrictions.in(propertyName, objects)));
					}
				} 
				// 排序
				else if (QueryConditionType.ORDER.equals(queryCondition.type())) {
					Number num = (Number) fieldValue;
					if (num.intValue() == 0)
						continue;
					criteria.addOrder(num.intValue() > 0 ? Order.asc(propertyName) : Order.desc(propertyName));
				} 
				// 立即加载
				else if (QueryConditionType.FETCH_EAGER.equals(queryCondition.type())) {
					if (fieldValue instanceof Boolean && ((Boolean) fieldValue))
						criteria.setFetchMode(propertyName, FetchMode.JOIN);
					else
						continue;
				}
				
				// -------------- QO联表查询 --------------
				if (!BaseQO.class.isAssignableFrom(field.getType()))
					continue;

				BaseQO joinQo = (BaseQO) fieldValue;
				QueryConfig queryObject = field.getType().getAnnotation(QueryConfig.class);
				
				if (queryObject == null)
					continue;
				
				BaseDao<?, BaseQO> dao = (BaseDao<?, BaseQO>) applicationContext.getBean(queryObject.daoBeanId());
				if (dao == null)
					continue;
				
				if (queryCondition.batchResult()) {
					criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				}
				
				Criteria joinCriteria = null;
				
				// 内联表
				if (QueryConditionType.JOIN.equals(queryCondition.type())) {
					joinCriteria = criteria.createCriteria(propertyName, null, JoinType.INNER_JOIN);
				} 
				// 左外联表
				else if (QueryConditionType.LEFT_JOIN.equals(queryCondition.type())) {
					joinCriteria = criteria.createCriteria(propertyName, null, JoinType.LEFT_OUTER_JOIN);
				} 
				// 右外联表
				else if (QueryConditionType.RIGHT_JOIN.equals(queryCondition.type())) {
					joinCriteria = criteria.createCriteria(propertyName, null, JoinType.RIGHT_OUTER_JOIN);
				}
				
				// 联表条件判断
				if (joinCriteria != null)
					dao.buildCriteriaOut(joinCriteria, joinQo);
			}

			// 组装条件
			if (criterions.size() > 0) {
				if (entry.getKey() == null)
					for (Criterion criterion : criterions)
						criteria.add(criterion);
				else {
					Criterion[] criterionsArray = new Criterion[criterions.size()];
					criterions.toArray(criterionsArray);
					criteria.add(Restrictions.or(criterionsArray));
				}
			}
		}

		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<BaseEntity> getEntityClass() {
		if (entityClass.get() == null)
			throw new RuntimeException("entityClass未指定");
		return (Class<BaseEntity>) entityClass.get();
	}

}
