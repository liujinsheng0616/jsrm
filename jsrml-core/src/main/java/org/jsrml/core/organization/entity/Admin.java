package org.jsrml.core.organization.entity;

import org.jsrml.common.persistence.StringIdBaseEntity;
import org.jsrml.common.util.UUIDGenerator;
import org.jsrml.core.organization.command.CreateAdminCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name = M.TABLE_PREFIX + "ADMIN")
@SuppressWarnings("serial")
public class Admin extends StringIdBaseEntity {

	@Column(name = "NAME", length = 128)
	private String name;

	@Column(name = "TELEPHONE", length = 32)
	private String telephone;

	@Column(name = "LAST_LOGIN_DATE", columnDefinition = M.DATE_COLUMN)
	private Date lastLoginDate;

	@Column(name = "AUTH_ACCOUNT", length = 64)
	private String authAccount;

	@Column(name = "PASSWORD", length = 32)
	private String password;

	public void create(CreateAdminCommand command) {
		setId(UUIDGenerator.getUUID());
		setName(command.getName());
		setTelephone(command.getTelephone());
		setAuthAccount(command.getLoginName());
		setPassword(command.getPassword());
		
	}


	public void modifyLastLogin(){
		setLastLoginDate(new Date());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getAuthAccount() {
		return authAccount;
	}

	public void setAuthAccount(String authAccount) {
		this.authAccount = authAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
