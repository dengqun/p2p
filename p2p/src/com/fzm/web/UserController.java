/**
 * 
 */
package com.fzm.web;

import java.util.LinkedHashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fzm.service.serviceImpl.UserLoginServiceImpl;
import com.fzm.tools.JSONUtil;


/**
 * 
* @file_name: UserController.java
* @Description: TODO 用户管理的action
* @author: Ma Amin
* @date: 2017-8-31 下午4:56:33 
* @version 1.0
 */
@Controller
@SessionAttributes("username")
public class UserController {
	@Autowired
	private HttpServletRequest req;
	@Autowired
	private UserLoginServiceImpl userLoginService;
	/**
	 * 
	* @Title: LoginAction
	* @Description: TODO 登录功能
	* @param @param data
	* @param @return
	* @return Map<String,Object>
	* @author: Ma Amin
	* @date: 2017-8-31 下午4:56:11
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> LoginAction(@RequestBody String data){
		System.out.println("p2p前端登录……");
		Map<String, Object> map = new LinkedHashMap<String, Object>();	//最终返回的结果
		Map<String, Object> jso = JSONUtil.jsonToMap(data); // json转map
		String username = "";	//用户名
		String password = "";	//密码
		if(jso.get("username") != null){
			username = jso.get("username").toString();
		}
		if(jso.get("password")!=null){
			password = jso.get("password").toString();
		}
		if ((username == null) || ("".equals(username))) { // 用户名为空
			map = JSONUtil.getJsonMap(12431, false, "用户名为空", null);
			return map;
		} else if ((password == null) || ("".equals(password))) { // 密码为空
			map = JSONUtil.getJsonMap(20061, false, "密码为空", null);
			return map;
		}
		System.out.println("用户名："+username);
		System.out.println("密码："+password);
		//都不为空则进行下面的判断
		map = userLoginService.verificationUser(username,password);
		return map;
	}
	/**
	 * 
	* @Title: register
	* @Description: TODO 用户注册
	* @param @param data
	* @param @return
	* @return Map<String,Object>
	* @author: Ma Amin
	* @date: 2017-8-31 下午4:57:15
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> register(@RequestBody String data){
		Map<String,Object> map = new LinkedHashMap<String,Object>();	//结果
		Map<String,Object> mapJson = JSONUtil.jsonToMap(data);
		String username = mapJson.get("username").toString();	//用户名可能是手机号，也可能是邮箱
		String password = mapJson.get("password").toString();
		String type = mapJson.get("type").toString();
		//分装map数据
		map = userLoginService.register(username,password,type,userLoginService);
		return map;
	}
	/**
	 * 
	* @Title: getSession
	* @Description: TODO 获取session对象
	* @param @return
	* @return HttpSession
	* @author: Ma Amin
	* @date: 2017-8-31 下午4:57:35
	 */
	public static HttpSession getSession() { 
	    HttpSession session = null; 
	    try { 
	        session = getRequest().getSession(); 
	    } catch (Exception e) {} 
	        return session; 
	}
	/**
	 * 
	* @Title: getRequest
	* @Description: TODO 获取HttpServletRequest对象
	* @param @return
	* @return HttpServletRequest
	* @author: Ma Amin
	* @date: 2017-8-31 下午4:57:49
	 */
	public static HttpServletRequest getRequest() { 
	    ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
	    return attrs.getRequest(); 
	} 
}
