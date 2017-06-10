package org.jsrml.core.organization.qo;

import org.jsrml.common.persistence.BaseQO;
import org.jsrml.common.persistence.query.QueryCondition;
import org.jsrml.common.persistence.query.QueryConfig;

@QueryConfig(daoBeanId = "adminService")
@SuppressWarnings("serial")
public class AdminQO extends BaseQO<String> {

	@QueryCondition(name = "name", ifTrueUseLike = "nameLike")
	private String name;

	private Boolean nameLike = false;

	@QueryCondition(name = "authAccount")
	private String authAccount;

	@QueryCondition(name = "telephone", ifTrueUseLike = "telephoneLike")
	private String telephone;

	private Boolean telephoneLike = false;

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
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

	public Boolean getTelephoneLike() {
		return telephoneLike;
	}

	public void setTelephoneLike(Boolean telephoneLike) {
		this.telephoneLike = telephoneLike;
	}

	public String getAuthAccount() {
		return authAccount;
	}

	public void setAuthAccount(String authAccount) {
		this.authAccount = authAccount;
	}

}
