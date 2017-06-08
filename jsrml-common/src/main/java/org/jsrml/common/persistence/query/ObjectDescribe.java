package org.jsrml.common.persistence.query;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @类功能说明：领域模型描述
 * @类修改者：
 * @修改日期：2014-10-11下午3:54:51
 * @修改说明：
 * @公司名称：
 * @作者：
 * @创建时间：2014-10-11下午3:54:51
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface ObjectDescribe {

	/**
	 * @方法功能说明：对象英文名，当为空字符串时取类名
	 * @修改者名字：
	 * @修改时间：2014-10-11下午3:12:14
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String en() default "";
	
	/**
	 * @方法功能说明：对象中文名
	 * @修改者名字：
	 * @修改时间：2014-10-11下午3:13:51
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String cn();
	
	/**
	 * @方法功能说明：对象描述
	 * @修改者名字：
	 * @修改时间：2014-10-11下午3:14:11
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String desc() default "";
	
	/**
	 * @方法功能说明：类型
	 * @修改者名字：
	 * @修改时间：2014-10-11下午5:02:36
	 * @修改内容：
	 * @参数：@return
	 * @return:ObjectType
	 * @throws
	 */
	ObjectType type();

}
