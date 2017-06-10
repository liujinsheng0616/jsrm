package org.jsrml.common.persistence;


public class RequestHead {
	/**
	 * 已登录用户id
	 */
	private String currentUserId;
	
	/**
	 * 来自哪个端
	 */
	private String clientType;
	public final static String CLIENT_TYPE_WEB = "web";

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 发送时间戳，为空或者和服务器时间误差3分钟以上视为无效请求
	 */
	private long timestamp;
	
	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
