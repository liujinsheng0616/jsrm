package org.jsrml.common.persistence.query;

public enum QueryConditionType {
	/**
	 * 完全匹配
	 */
	EQ,
	/**
	 * 完全匹配或为NULL
	 */
	EQ_OR_NULL,
	/**
	 * 不匹配
	 */
	NE,
	/**
	 * 不匹配或为NULL
	 */
	NE_OR_NULL,
	/**
	 * 大于等于
	 */
	GE,
	/**
	 * 大于
	 */
	GT,
	/**
	 * 小于等于
	 */
	LE,
	/**
	 * 小于
	 */
	LT,
	/**
	 * 模糊匹配任何位置
	 */
	LIKE_ANYWHERE,
	/**
	 * 模糊匹配开始位置
	 */
	LIKE_START,
	/**
	 * 模糊匹配结尾
	 */
	LIKE_END,
	/**
	 * 包含
	 */
	IN,
	/**
	 * 不包含
	 */
	NOT_IN,
	/**
	 * 为空
	 */
	IS_NULL,
	/**
	 * 不为空
	 */
	IS_NOT_NULL,
	/**
	 * 排序:>0升序，<0降序，=0不排序。
	 */
	ORDER,
	/**
	 * 立即加载
	 */
	FETCH_EAGER,
	/**
	 * 内联表
	 */
	JOIN,
	/**
	 * 左外联表
	 */
	LEFT_JOIN,
	/**
	 * 右外联表
	 */
	RIGHT_JOIN,;
}
