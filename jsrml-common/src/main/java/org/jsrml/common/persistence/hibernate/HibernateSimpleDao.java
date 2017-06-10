package org.jsrml.common.persistence.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jsrml.common.pagination.Pagination;
import org.jsrml.common.util.MyBeanUtils;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;


@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class HibernateSimpleDao {
	
	/**
	 * 日志，可用于子类
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * HIBERNATE 的 order 属性
	 */
	protected static final String ORDER_ENTRIES = "orderEntries";

	/**
	 * 通过HQL查询对象列表
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 */
	protected List find(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 通过HQL查询唯一对象
	 */
	protected Object findUnique(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	/**
	 * 通过Finder获得分页数据
	 * 
	 * @param finder
	 *            Finder对象
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	protected Pagination find(Finder finder, int pageNo, int pageSize) {
		int totalCount = countQueryResult(finder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		List list = query.list();
		p.setList(list);
		return p;
	}

	/**
	 * 通过Finder获得列表数据
	 * 
	 * @param finder
	 * @return
	 */
	protected List find(Finder finder) {
		Query query = finder.createQuery(getSession());
		List list = query.list();
		return list;
	}

	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 */
	protected Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}
	

	/**
	 * 通过Criteria获得分页数据
	 * 
	 * @param crit
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected Pagination findByCriteria(Criteria crit, int pageNo, int pageSize) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setSelectTotalCount(true);
		pagination.setCheckPassLastPageNo(true);
		return findByCriteria(crit, pagination);
	}

	/**
	 * 通过Criteria获得分页数据
	 * 
	 * @param crit
	 * @param pageNo
	 * @param pageSize
	 * @param checkPassLastPage
	 * @param b 
	 * @return
	 */
	protected Pagination findByCriteria(Criteria crit, Pagination pagination) {
		CriteriaImpl impl = (CriteriaImpl) crit;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) MyBeanUtils
					.getFieldValue(impl, ORDER_ENTRIES);
			MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList());
		} catch (Exception e) {
			throw new RuntimeException(
					"cannot read/write 'orderEntries' from CriteriaImpl", e);
		}

		int totalCount =0;
		if (pagination.isSelectTotalCount()) {
			try{
				
				Number n =  ((Number) crit.setProjection(Projections.countDistinct("id")).uniqueResult());
				if(n==null)
					throw new RuntimeException("NULL 数量");
				totalCount = n.intValue();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//TODO tandeng 20140817 update end
		Pagination p = new Pagination(pagination.getPageNo(), pagination.getPageSize(), totalCount, pagination.getOffset());
		if (pagination.isSelectTotalCount() && totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}

		// 检查页码是否超过最后一页
		if (pagination.isCheckPassLastPageNo() && pagination.getPageNo() > p.getPageNo()) {
			p.setList(new ArrayList());
			return p;
		}

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		crit.setProjection(projection);
		if (projection == null) {
			crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			crit.setResultTransformer(transformer);
		}
		try {
			MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, orderEntries);
		} catch (Exception e) {
			throw new RuntimeException(
					"set 'orderEntries' to CriteriaImpl faild", e);
		}
		crit.setFirstResult(p.getFirstResult());
		crit.setMaxResults(p.getPageSize());
		p.setList(crit.list());
		return p;
	}

	/**
	 * 获得Finder的记录总数
	 * 
	 * @param finder
	 * @return
	 */
	protected int countQueryResult(Finder finder) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		return ((Number) query.iterate().next()).intValue();
	}
	
	protected SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void save(Object entity) throws DataAccessException {
		getSession().save(entity);
	}
	
	public void saveOrUpdate(Object entity) throws DataAccessException {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Object entity) throws DataAccessException {
		getSession().delete(entity);
	}
	
	/**
	 * 不获取实体，直接执行删除操作
	 * @param clazz
	 * @param id
	 */
	public void deleteById(Class<?> clazz, Serializable id) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete ").append(clazz.getSimpleName()).append(" where id=?");
		getSession().createQuery(hql.toString()).setParameter(0, id).executeUpdate();
	}

	public void update(Object entity) throws DataAccessException {
		getSession().update(entity);
	}

	public void merge(Object entity) throws DataAccessException {
		getSession().merge(entity);
	}

	public <T> T get(Class<T> clazz, Serializable id) throws DataAccessException {
		return (T) getSession().get(clazz, id);
	}
	public <T> T load(Class<T> clazz, Serializable id) throws DataAccessException {
		return (T) getSession().load(clazz, id);
	}
	
	public int executeUpdate(String hql, Object... obj){
		return createQuery(hql, obj).executeUpdate();
	}

	/**
	 * 将entity踢出session缓存
	 * @param entity
	 */
	public void evict(Object entity){
		getSession().evict(entity);
	}
}
