package org.jsrml.common.persistence;

import org.jsrml.common.persistence.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseDao {
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Transactional(readOnly = true)
	public String queryCount() throws Exception{
		return commonMapper.queryCount();
	}
	
}
