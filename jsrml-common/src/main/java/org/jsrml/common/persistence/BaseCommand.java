package org.jsrml.common.persistence;
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class BaseCommand implements Serializable {
	
	private String token;
	private RequestHead head;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}

}
