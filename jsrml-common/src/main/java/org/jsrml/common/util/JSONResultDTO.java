package org.jsrml.common.util;

public class JSONResultDTO {

	private String code;
	private String message;
	private Object data;
	private String dataType;
	
	public final static String SYSTEM_ERROR = "system_error";
	public final static String NO_RESULT_TYPE = "no_result_type";
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
