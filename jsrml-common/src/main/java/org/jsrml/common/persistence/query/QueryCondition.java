package org.jsrml.common.persistence.query;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @类功能说明：查询属性
 * @类修改者：
 * @修改日期：2015-3-27上午9:30:31
 * @修改说明：
 * @公司名称：
 * @作者：
 * @创建时间：2015-3-27上午9:30:31
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface QueryCondition {
	
	/**
	 * @方法功能说明：ENTITY属性名称
	 * @修改者名字：
	 * @修改时间：2015-3-27下午6:06:18
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String name();

	/**
	 * @方法功能说明：查询类型
	 * @修改者名字：
	 * @修改时间：2015-3-27下午6:06:27
	 * @修改内容：
	 * @参数：@return
	 * @return:QueryAttributeType
	 * @throws
	 */
	QueryConditionType type() default QueryConditionType.EQ;
	
	/**
	 * @方法功能说明：如果用LIKE查询的判断属性名称
	 * @修改者名字：
	 * @修改时间：2015-3-27下午6:06:39
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String ifTrueUseLike() default "";
	
	boolean batchResult() default false;
	
	
}
