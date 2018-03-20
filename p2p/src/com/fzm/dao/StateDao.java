package com.fzm.dao;

import java.util.Map;



import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
/**
 * 
 * @Description : 表相关方法调用
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Component
public class StateDao {
	@Autowired
	private SqlSessionTemplate dao;
	/**
	 * 添加状态记录
	 * @param map
	 */
	public int insertBor(Map<String, Object> map) {
		
		return dao.insert("StateMapper.insertBor",map);
	}

}
