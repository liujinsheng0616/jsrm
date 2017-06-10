package org.jsrml.core.organization.exception;

import org.jsrml.common.persistence.BaseException;

@SuppressWarnings("serial")
public class AdminException extends BaseException {

	public final static String ADMIN_NOT_FOUND = "admin_not_found";

	public AdminException(Integer code, String message) {
		super(code, message);
	}

	public AdminException(String code, String message) {
		super(code, message);
	}

}
