package org.jsrml.common.persistence.hibernate.dialect;

/**
 * 字段声明 - MYSQL
 * @author 
 */
public interface ColumnDefinitionMysql {
	// ---------------------<Hibernate自动创建表用>---------------------
	public static final String MONEY_COLUMN = "decimal(26,4)";
	public static final String DATE_COLUMN = "datetime";
	public static final String TYPE_NUM_COLUMN = "int(11)";
	public static final String LONG_NUM_COLUMN = "bigint(20)";
	public static final String NUM_COLUMN = "int(11)";
	public static final String DOUBLE_COLUMN = "double";
	public static final String COORDINATE_COLUMN = "double(10,6)";
	public static final String CHAR_COLUMN = "char(1)";
	public static final String TEXT_COLUMN = "text";
	public static final String MEDIUM_TEXT_COLUMN = "mediumtext";
}