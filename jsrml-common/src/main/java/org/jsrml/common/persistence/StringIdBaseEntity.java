package org.jsrml.common.persistence;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StringIdBaseEntity extends BaseEntity<String> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, length = 64)
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
