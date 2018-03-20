/**
 * 
 */
package com.fzm.daoimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fzm.daointerface.UserLoginDaoInterface;
import com.fzm.entity.TokenUser;
import com.fzm.entity.User;
import com.fzm.entity.UserBackstage;
import com.fzm.tools.RegxUtils;

/**
 * 
 * 项目名称：cgb_p2p 类名称：UserLoginDaoImpl 类描述： 创建人：maamin 创建时间：2017-8-9 下午4:41:51
 * 修改人：maamin 修改时间：2017-8-9 下午4:41:51 修改备注：
 * 
 * @version
 * 
 */
@Repository("userdao")
public class UserLoginDaoImpl implements UserLoginDaoInterface {
	@Autowired
	private SqlSessionTemplate dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.p2p.dao.daointerface.UserLoginDaoInterface#verificationUser(java
	 * .lang.String, java.lang.String)
	 */
	public User verificationUser(String username, String password) {
		// TODO Auto-generated method stub

		String sqlQuery = "UserMapper.vliUser1";
		Map<String, Object> mapParam = new LinkedHashMap<String, Object>();
		mapParam.put("name", username);
		mapParam.put("password", password);
		User user = dao.selectOne(sqlQuery, mapParam); // 名字做主键，不允许数据库中存在同名的用户
		System.out.println("查询到用户的信息：" + user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fzm.p2p.dao.daointerface.UserLoginDaoInterface#saveToken(int,
	 * java.lang.String)
	 */
	public void saveToken(int uid, String token) {
		// TODO Auto-generated method stub

		TokenUser tokenUser = new TokenUser(uid, token, "");
		String sql = "com.fzm.mapper.TokenUserMapper.insertTokenRecords";
		dao.update(sql, tokenUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.p2p.dao.daointerface.UserLoginDaoInterface#verificationToken(int)
	 */
	public TokenUser verificationToken(int uid) {
		// TODO Auto-generated method stub

		String sqlQuery = "com.fzm.mapper.TokenUserMapper.verificationToken";
		TokenUser tokenUser = dao.selectOne(sqlQuery, uid);
		return tokenUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fzm.p2p.dao.daointerface.UserLoginDaoInterface#valiToken(int)
	 */
	public boolean valiToken(int userId) {
		// TODO Auto-generated method stub

		String sql = "com.fzm.mapper.TokenUserMapper.queryTokenByUid";
		TokenUser user = dao.selectOne(sql, userId);
		if (user != null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.p2p.dao.daointerface.UserLoginDaoInterface#updateTokenByUid(int,
	 * java.lang.String)
	 */
	public void updateTokenByUid(int uid, String token) {
		// TODO Auto-generated method stub

		TokenUser user = new TokenUser(uid, token);
		String sql = "com.fzm.mapper.TokenUserMapper.updateToken";
		dao.update(sql, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.p2p.dao.daointerface.UserLoginDaoInterface#getTokenUser(java.
	 * lang.String)
	 */
	@Override
	public TokenUser getTokenUser(String uid) {
		// TODO Auto-generated method stub
		String sql = "com.fzm.mapper.TokenUserMapper.vaToken";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("uid", uid);
		TokenUser user = dao.selectOne(sql, map);
		System.out.println("++++++++++++++++++++");
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.daointerface.UserLoginDaoInterface#findUserById(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean findUserById(String username, String password, String typeInt) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String type = "";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("username", username);
		map.put("password", password);
		if ("1".equals(typeInt)) { // 借款用户
			type = "借款人";
		} else if ("2".equals(typeInt)) {
			type = "投资人";
		}
		map.put("type", type);
		String sql = "UserMapper.selectUserByPhoneAndEmail";
		User user = dao.selectOne(sql, map);
		if (user != null) { // 用户已经存在
			return true;
		} else { // 用户不存在可以注册
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.daointerface.UserLoginDaoInterface#register(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public int register(String username, String password, String typeInt) {
		// TODO Auto-generated method stub
		int result = 0; // 注册失败
		String sql = "";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String type = "";
		map.put("username", username);
		map.put("password", password);
		Date date = new Date();
		SimpleDateFormat SimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// 格式化当前日期
		String time = SimpleDateFormat.format(date);
		System.out.println("注册时间：" + time);
		if ("1".equals(typeInt)) { // 借款用户
			type = "借款人";
			// 注册时，默认给借款人一个信用总额和信用评级
			map.put("money", 0);
			map.put("credit", "-");
			map.put("shenyukejie", 0);
		} else if ("2".equals(typeInt)) {
			type = "投资人";
			map.put("money", 0);
			map.put("credit", "-");
			map.put("shenyukejie", 0);
		}
		map.put("time", time);
		map.put("type", type);
		map.put("flag", 0);
		if (RegxUtils.isEmail(username)) {// 邮箱注册
			sql = "UserMapper.insertUserByEmail";
			int num = dao.insert(sql, map);
			if (num > 0) { // 邮箱号注册成功
				result = 1;
			}
		} else if (RegxUtils.isChinaPhoneLegal(username)) { // 手机号注册
			sql = "UserMapper.insertUserByPhone";
			int num = dao.insert(sql, map);
			if (num > 0) {
				result = 1; // 手机号注册成功
			}
		} else { // 格式不对
			result = -1;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fzm.daointerface.UserLoginDaoInterface#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		String sql = "UserMapper.selectUserByPhoneAndEmail";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("username", username);
		User user = dao.selectOne(sql, map);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.daointerface.UserLoginDaoInterface#updateUserFlag(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public void updateUserFlag(String name, String randomNumber) {
		// TODO Auto-generated method stub
		String sql = "UserMapper.updateUserFlag";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("flag", 1);
		map.put("name", name);
		map.put("randomNumber", randomNumber);
		dao.update(sql, map);
	}

}
