package org.jsrml.core.organization.service;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.jsrml.common.persistence.BaseDao;
import org.jsrml.core.organization.command.CreateAdminCommand;
import org.jsrml.core.organization.entity.Admin;
import org.jsrml.core.organization.exception.AdminException;
import org.jsrml.core.organization.qo.AdminQO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AdminService extends BaseDao<Admin, AdminQO> {

	public Admin createAdmin(CreateAdminCommand command) {

		Admin admin = new Admin();
		admin.create(command);
		admin.setAuthAccount(command.getLoginName());
		save(admin);
		
		return admin;
	}

	
	/**
	 * 后台管理员登陆
	 * 
	 * @param request
	 * @param loginName 用户名
	 * @param password 密码
	 * @return 
	 * @throws BaseException 
	 */
	public Admin login(HttpServletRequest request, 
			String loginName, String password) throws AdminException {
		
		AdminQO qo = new AdminQO();
		
		Admin admin = queryUnique(qo);
		return admin;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, AdminQO qo) {
		return criteria;
	}

	@Override
	protected Class<Admin> getEntityClass() {
		return Admin.class;
	}

}
