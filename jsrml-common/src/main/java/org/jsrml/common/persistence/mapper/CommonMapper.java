package org.jsrml.common.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	/**
	 * @Title: getDomeTest 
	 * @Description: Dao层测试
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public String queryCount() throws Exception;
}
