/**
 * 
 */
package com.fzm.service.serviceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fzm.cgb.KeyUtils;
import com.fzm.cgb.ProtobufBean;
import com.fzm.daoimpl.UserLoginDaoImpl;
import com.fzm.entity.TokenUser;
import com.fzm.entity.User;
import com.fzm.http.HttpRequest;
import com.fzm.redis.Redis;
import com.fzm.service.UserRegisterControllerService;
import com.fzm.service.serviceInterface.UserLoginServiceInterface;
import com.fzm.tools.JSONUtil;
import com.fzm.tools.token.tokenImpl.RedisTokenManager;

/**
 * 
 * 项目名称：cgb_p2p 类名称：UserLoginServiceImpl 类描述： 创建人：maamin 创建时间：2017-8-9 下午4:30:27
 * 修改人：maamin 修改时间：2017-8-9 下午4:30:27 修改备注：
 * 
 * @version
 * 
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginServiceInterface {
	public RedisTokenManager redisTokenManager = new RedisTokenManager();
	@Autowired
	private UserLoginDaoImpl userdao;
	@Autowired
	private UserRegisterControllerService userRegisterControllerService;
	@Autowired
	private DataSourceTransactionManager txManager;
	HttpRequest httpRequest = new HttpRequest();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fuzamei.cgb.service.serviceInterface.UserLoginServiceInterface#
	 * verificationUser(java.lang.String, java.lang.String)
	 */
	public Map<String, Object> verificationUser(String username, String password) {
		// TODO Auto-generated method stub
		/** 2017年9月19号下午加入redis实现**/
		Redis redis = new Redis();
		Map<String, Object> map = new LinkedHashMap<String, Object>();//存放最终返回的结果
		Map<String, Object> map1 = new LinkedHashMap<String, Object>(); // 存放里层的数据
		String nameAndPassword = username+"&"+password;	//拼接，前端用户的用户名和密码用连接符拼接
		if(redis.exists(nameAndPassword)){	//redis中存在该用户信息，默认为登录成功
			String random = redis.get(nameAndPassword).split("&")[0];
			int userId = Integer.parseInt(redis.get(nameAndPassword).split("&")[1]);
			String userType = redis.get(nameAndPassword).split("&")[2];
			String token = redisTokenManager.createToken(userId);
			updateTokenByUid(userId, token);	//跟新token进token表
			map1.put("token", token);
			map1.put("userid", userId);
			map1.put("random", random);
			map1.put("usertype", userType);
			map = JSONUtil.getJsonMap(200, true, "登录成功", map1);
			redis.set(nameAndPassword, token+"&"+userId);
			redis.expire(nameAndPassword, 600);
		}
		else{	//redis中没有该用户信息，查询数据库，看数据库中是否存在该用户信息
			User user = userdao.verificationUser(username, password);
			if (user != null) { // 数据库中存在该用户
				String token = redisTokenManager.createToken(user.getUid());	//根据用户的uid创建一个token
				updateTokenByUid(user.getUid(), token);	//跟新token进token表
				map1.put("token", token);
				map1.put("userid", user.getUid());
				map1.put("random", user.getRandomNumber());	//随机数
				map1.put("usertype", user.getType());
				map = JSONUtil.getJsonMap(200, true, "登录成功", map1);
				//2017年9月19日，存入redis
				redis.set(nameAndPassword, token+"&"+user.getUid()+"&"+user.getType());
				redis.expire(nameAndPassword, 600);
			}
			else {	//数据库中不存在该用户
				map = JSONUtil.getJsonMap(12431, false, "登录失败", map1);
			}
		}
		return map;
		/** 之前的做法，没有用到redis **/
		/*User user = userdao.verificationUser(username, password);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>(); // 存放里层的数据
		if (user != null) { // 验证成功
			map.put("code", 200);
			map.put("success", true);
			map.put("message", "登录成功");
			String token = redisTokenManager.createToken(user.getUid());
			System.out.println("生成的token为：" + token);
			map1.put("token", token);
			map1.put("userid", user.getUid());
			map1.put("username", user.getName());
			map1.put("usertype", user.getType());
			map1.put("randomNum", user.getRandomNumber());
			// 在token表中看用户之前有没有对应的token，如果有更新之前的变为现在的
			boolean isToken = userdao.valiToken(user.getUid());
			if (isToken) {
				// 更新token
				userdao.updateTokenByUid(user.getUid(), token);
			} else {
				// 将token和用户id存放到数据表中
				userdao.saveToken(user.getUid(), token);
			}
		} else { // 用户名和密码不匹配
			map.put("code", 10007);
			map.put("error", "用户名或者密码不匹配");
			if (username == null || username == "") {
				map.put("code", 10012);
				map.put("error", "用户名为空");
			}
			if (password == null || password == "") {
				map.put("code", 20061);
				map.put("error", "用户密码为空");
			}
			map.put("success", false);
			map1.put("token", "");
			map1.put("userid", "");
			map1.put("username", "");
			map1.put("usertype", "");
			map1.put("randomNum", "");
		}
		map.put("data", map1);
		return map;*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fuzamei.cgb.service.serviceInterface.UserLoginServiceInterface#
	 * verificationToken(int, java.lang.String)
	 */
	@Override
	public boolean verificationToken(int userid, String token) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new LinkedHashMap<String, Object>(); // 存放返回的结果
		TokenUser user = userdao.verificationToken(userid);
		if (user == null) { // token表没有用户对应的信息
			return true;
		} else { // 有用户对应的信息，看token是否一致
			String tokenDatabase = user.getToken();
			System.out
					.println("+++++++++++++++++++++准备将token和数据库进行比对+++++++++++++++++++");
			System.out.println(">>>>>>前台传过来的用户id：" + userid);
			System.out.println("------------数据库中的token---前台传的token----------"
					+ token + ">>>>>" + token);
			if (token.equals(tokenDatabase)) { // 一样的话，验证成功
				return false;
			} else { // 验证失败
				return true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.service.serviceInterface.UserLoginServiceInterface#valicaToken
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public TokenUser getTokenUser(String uid) {
		// TODO Auto-generated method stub
		return userdao.getTokenUser(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.service.serviceInterface.UserLoginServiceInterface#register(java
	 * .lang.String, java.lang.String, java.lang.String,
	 * com.fzm.service.serviceImpl.UserLoginServiceImpl)
	 */
	@Override
	public synchronized Map<String, Object> register(String username,
			String password, String type, UserLoginServiceImpl userLoginService) {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = txManager.getTransaction(def);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		boolean flag = userdao.findUserById(username, password, type);
		int code = 0;
		String message = "";
		boolean success = false;
		String suijinum = "";
		if (!flag) {// 用户可以注册
			int f = userdao.register(username, password, type); // true：成功，false：失败
			if (f == 1) {
				// 注册成功后 ，将区块链信息存放一份到区块链
				String randomNumber = KeyUtils.getRandom();// 生成随机数随机数
				suijinum = randomNumber;
				ProtobufBean protobufBean = userRegisterControllerService
						.register(username, password, type, randomNumber);
				boolean flag1 = userRegisterControllerService
						.sendPost(protobufBean);
				// boolean flag1 = false;
				if (flag1) {
					// 在取款连注册成功后，表中状态标识位变为1
					userdao.updateUserFlag(username, randomNumber);
					txManager.commit(status);
					code = 200;
					message = "用户注册成功并写入到区块链";
					map.put("message", message);
					success = true;
				} else {
					txManager.rollback(status);
					code = -1;
					message = "注册未写入到区块链";
					map.put("message", message);
					success = true;
				}
			} else if (f == 0) {
				txManager.rollback(status);
				code = -1;
				message = "用户注册失败";
				map.put("error", message);
				success = false;
			} else if (f == -1) {
				txManager.rollback(status);
				code = -1;
				message = "用户名格式不对";
				map.put("error", message);
				success = false;
			}
		} else {
			txManager.rollback(status);
			code = -1;
			message = "用户已经存在";
			map.put("error", message);
			success = false;
		}
		map.put("code", code);
		// map.put("message", message);
		map.put("success", success);
		map.put("randomNumber", suijinum);
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fzm.service.serviceInterface.UserLoginServiceInterface#ValiToken(
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String ValiToken(HttpServletRequest req) {
		// TODO Auto-generated method stub
		// Bearer 298cc26837914824966ee3488b583092&1
		String auth = req.getHeader("Authorization");
		if (!"".equals(auth)) {
			String token = auth.split("&")[0].replace("Bearer ", "");
			String uid = auth.split("&")[1];
			// return valicaToken(uid,token);
			TokenUser tokenUser = userdao.getTokenUser(uid);
			if (tokenUser != null) {
				String token1 = tokenUser.getToken().toString();
				if (token.equals(token1)) { // 相等
					return String.valueOf(tokenUser.getUid());
				}
				return "";
			} else {
				return "";
			}
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see com.fzm.service.serviceInterface.UserLoginServiceInterface#updateTokenByUid(int, java.lang.String)
	 */
	@Override
	public void updateTokenByUid(int userid, String token) {
		// TODO Auto-generated method stub
		// 在token表中看用户之前有没有对应的token，如果有更新之前的变为现在的
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				TransactionStatus status = txManager.getTransaction(def);
				boolean isToken = userdao.valiToken(userid);
				if (isToken) {
					// 更新token
					userdao.updateTokenByUid(userid, token);
					txManager.commit(status);
				} else {
					// 将token和用户id存放到数据表中
					userdao.saveToken(userid, token);
					txManager.commit(status);
				}
	}
}
