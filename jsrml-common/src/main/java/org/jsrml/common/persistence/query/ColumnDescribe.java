package org.jsrml.common.persistence.query;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @类功能说明：字段描述
 * @类修改者：
 * @修改日期：2014-10-11下午3:10:50
 * @修改说明：
 * @公司名称：
 * @作者：
 * @创建时间：2014-10-11下午3:10:50
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ColumnDescribe {
	
	/**
	 * @方法功能说明：字段英文名，当为空字符串时取字段名
	 * @修改者名字：
	 * @修改时间：2014-10-11下午3:12:14
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String en() default "";
	
	/**
	 * @方法功能说明：字段中文名
	 * @修改者名字：
	 * @修改时间：2014-10-11下午3:13:51
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String cn();
	
	/**
	 * @方法功能说明：字段描述
	 * @修改者名字：
	 * @修改时间：2014-10-11下午3:14:11
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String desc() default "";
	
}
