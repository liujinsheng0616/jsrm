package org.jsrml.common.persistence;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;


public class BaseQO<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private RequestHead head;

	/**
	 * 别名，在进行关联查询时设置关联对象的别名
	 */
	private String alias;
	/**
	 * 需要筛选出来的实体字段名，可以带“.”进入组件对象的下一级属性。用于非select *查询
	 */
	private String[] projectionProperties;
	private String[] projectionDistinctProperties;

	// ------------------延迟加载条件------------------
	// ------------------排序条件------------------
	// ------------------属性条件------------------
	/**
	 * 实体ID
	 */
	private T id;
	/**
	 * 实体ID集合，设置后会进行in查询
	 */
	private List<T> ids;

	// ------------------不包含的属性条件------------------
	/**
	 * 不包含的ID集合，设置后会进行not in查询
	 */
	private T[] idNotIn;

	// ------------------状态类条件------------------

	// 分页条件
	private Integer pageNo = 1;

	private Integer pageSize = 10;

	private Integer offset = 0;

	/**
	 * 是否解析QO上的注解，当注解中的条件解析方式不适合自己的查询需要时关闭，进行手工条件组装
	 */
	private Boolean enableQueryAnnotation = true;

	/**
	 * 是否关联查询了集合
	 */
	private Boolean batchResult = false;

	/**
	 * 查询结果类型
	 */
	private Integer resultType;

	public final static Integer RESULT_TYPE_COUNT = 0;
	public final static Integer RESULT_TYPE_UNIQUE = 1;
	public final static Integer RESULT_TYPE_LIST = 2;
	public final static Integer RESULT_TYPE_PAGINATION = 3;

	private Integer draw;

	public final static Integer ORDER_ASC = 1;
	public final static Integer ORDER_DESC = -1;

	/**
	 * 悲观读锁
	 */
	private boolean readLock;

	/**
	 * 悲观写锁
	 */
	private boolean writeLock;

	/**
	 * 自增别名
	 */
	private Integer aliasNum = 0;

	/**
	 * 数据使用场景，用于权限判断
	 */
	private String dataUseScene;

	/**
	 * 返回值是否允许空
	 */
	private Boolean allowEmply = false;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public T[] getIdNotIn() {
		return idNotIn;
	}

	public void setIdNotIn(T[] idNotIn) {
		this.idNotIn = idNotIn;
	}

	public void setIds(List<T> ids) {
		this.ids = ids;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String[] getProjectionProperties() {
		return projectionProperties;
	}

	public void setProjectionProperties(String[] projectionProperties) {
		this.projectionProperties = projectionProperties;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public List<T> getIds() {
		return ids;
	}

	public Boolean getEnableQueryAnnotation() {
		return enableQueryAnnotation;
	}

	public void setEnableQueryAnnotation(Boolean enableQueryAnnotation) {
		this.enableQueryAnnotation = enableQueryAnnotation;
	}

	public Boolean getBatchResult() {
		return batchResult;
	}

	public void setBatchResult(Boolean batchResult) {
		this.batchResult = batchResult;
	}

	public Integer getResultType() {
		return resultType;
	}

	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public boolean isReadLock() {
		return readLock;
	}

	public void setReadLock(boolean readLock) {
		this.readLock = readLock;
	}

	public boolean isWriteLock() {
		return writeLock;
	}

	public void setWriteLock(boolean writeLock) {
		this.writeLock = writeLock;
	}

	/**
	 * @return the aliasNum
	 */
	public Integer getAliasNum() {
		return aliasNum;
	}

	/**
	 * @param aliasNum
	 *            the aliasNum to set
	 */
	public void setAliasNum(Integer aliasNum) {
		this.aliasNum = aliasNum;
	}

	public String getDataUseScene() {
		return dataUseScene;
	}

	public void setDataUseScene(String dataUseScene) {
		this.dataUseScene = dataUseScene;
	}

	public String[] getProjectionDistinctProperties() {
		return projectionDistinctProperties;
	}

	public void setProjectionDistinctProperties(String[] projectionDistinctProperties) {
		this.projectionDistinctProperties = projectionDistinctProperties;
	}

	public Boolean getAllowEmply() {
		return allowEmply;
	}

	public void setAllowEmply(Boolean allowEmply) {
		this.allowEmply = allowEmply;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

	public RequestHead getHead() {
		return head;
	}

	public void setHead(RequestHead head) {
		this.head = head;
	}

}