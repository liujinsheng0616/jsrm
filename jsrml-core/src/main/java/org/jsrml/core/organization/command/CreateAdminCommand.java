package org.jsrml.core.organization.command;

import org.jsrml.common.persistence.BaseCommand;

@SuppressWarnings("serial")
public class CreateAdminCommand extends BaseCommand {
	
	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 电话
	 */
	private String telephone;
	

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 明文密码
	 */
	private String password;


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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
