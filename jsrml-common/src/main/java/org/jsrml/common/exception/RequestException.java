package org.jsrml.common.exception;

import org.jsrml.common.persistence.BaseException;

@SuppressWarnings("serial")
public class RequestException extends BaseException {

	public final static String TOKEN_WRONG = "token_wrong";	//	验签不通过
	public final static String TIMESTAMP_INVALID = "timestamp_invalid";	//	时间戳无效
	
	public RequestException(String code, String message) {
		super(code, message);
	}
}
