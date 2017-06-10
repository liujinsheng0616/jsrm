package org.jsrml.common.persistence.hibernate.dialect;

/**
 * 字段声明 - ORACLE
 * 
 * @author 
 */
public interface ColumnDefinitionOracle {
	// ---------------------<Hibernate自动创建表用>---------------------
	public static final String MONEY_COLUM = "NUMBER(26,4)";
	public static final String DATE_COLUM = "DATE";
	public static final String TYPE_NUM_COLUM = "NUMBER(7)";
	public static final String LONG_NUM_COLUM = "NUMBER(20)";
	public static final String NUM_COLUM = "NUMBER(16)";
	public static final String DOUBLE_COLUM = "NUMBER";
	public static final String CHAR_COLUM = "CHAR(1)";
	public static final String TEXT_COLUM = "CLOB";
}
