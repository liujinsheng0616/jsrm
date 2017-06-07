package org.jsrml.core.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jsrml.common.persistence.BaseDao;

@Service
@Transactional(rollbackFor=Exception.class)
public class LoginService {
	
	@Autowired
	private BaseDao BaseDao;
	
	public String loginInfo() throws Exception{
		return BaseDao.queryCount();
	}
}
