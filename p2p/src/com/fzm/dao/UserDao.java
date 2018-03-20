package com.fzm.dao;

import org.mybatis.spring.SqlSessionTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


import com.fzm.entity.User;
/**
 * 
 * @Description : 用户表相关方法调用
 * @author sdy
 * @date 2017-08-01
 * 
 */
@Component
public class UserDao {
			@Autowired
			SqlSessionTemplate dao;
			/**
			 * 通过用户查询用户Id
			 * @param entity	传入用户实体类
			 * @return	返回用户信息
			 */
			public User findById(User entity){
				return dao.selectOne("UserMapper.findById",entity);
			}
			
			
			
			/**
			 * 更新用户余额
			 * @param entity
			 * @return
			 */

			public int updateBalance(User entity) {
				return	dao.update("UserMapper.updateBalance",entity);
				
			}
			/**
			 * 更新用户积分余额
			 * @param entity
			 * @return
			 */

			public int updateUserAsset(User entity) {
				return dao.update("UserMapper.updateUserAsset",entity);
			}
			
}
