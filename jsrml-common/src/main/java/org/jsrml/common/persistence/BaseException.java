package org.jsrml.common.persistence;

@SuppressWarnings("serial")
public class BaseException extends Exception {

	private int code;

	private String codeStr;

	public final static String NECESSARY_PARAM_NULL = "necessary_param_null"; // 缺少必需的参数
	public final static String ENTITY_NOT_FOUND = "entity_not_found"; // 实体不存在
	
	public BaseException() {
		super();
	}

	public BaseException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BaseException(String codeStr, String message) {
		super(message);
		this.codeStr = codeStr;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

}
