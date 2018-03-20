package com.fzm.dao;

import java.util.List;
import java.util.Map;



import org.mybatis.spring.SqlSessionTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.fzm.entity.Borrow;
import com.fzm.entity.User;
import com.fzm.entity.RecordVO;
/**
 * 
 * @Description : 充值体现记录表方法调用
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Component
public class RecordDao {
			@Autowired
			SqlSessionTemplate dao;
			/**
			 * 添加操作纪录
			 * @param map
			 * @return
			 */
			public int insert(Map<String, Object> map){
				return dao.insert("RecordMapper.insert", map);
			}
			
			
			public List<RecordVO> queryRechargeRecord(User entity){
				return dao.selectList("RecordMapper.queryRechargeRecord",entity);
			}
			
			public List<RecordVO> queryExtractList(User entity){
				return dao.selectList("RecordMapper.queryExtractList",entity);
			}
			
			public int queryAllRechargeRecord(User entity){
				return dao.selectOne("RecordMapper.queryAllRechargeRecord",entity);
			}
			
			public int queryAllExtractList(User entity){
				return dao.selectOne("RecordMapper.queryAllExtractList",entity);
			}
}
