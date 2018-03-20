/**
 * 
 */
package com.fzm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fzm.service.serviceImpl.UserLoginServiceImpl;


/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：APIInterceptor   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-4 上午9:56:03   
 * 修改人：maamin   
 * 修改时间：2017-8-4 上午9:56:03   
 * 修改备注：   
 * @version    
 *    
 */
public class APIInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserLoginServiceImpl userLoginService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("进入所有请求的拦截器APIInterceptor……");
		//对用户id对应的token进行验证
//		String token = request.getParameter("token");
//		String userid = request.getParameter("userid");
		String tokenAndUserId = request.getHeader("Authorization").toString();
		String token = tokenAndUserId.split("&")[0].toString().replace("Bearer ", "");
		String userid = tokenAndUserId.split("&")[1];
		System.out.println("++++++++++++++++++++++++++请求中的token为："+token);
		System.out.println("++++++++++++++++++++++++++请求中的用户id为："+userid);
		if(token == null){
			return false;
		}
		//对token进行验证
		boolean flag = userLoginService.verificationToken(Integer.parseInt(userid), token);
		if(flag){
			System.out.println("验证失败！");
			return false;
		}
		else {
			return true;
		}

		
		/*// token is not needed when debug
		if(token == null) return true;  // !! remember to comment this when deploy on server !!
		
		Enumeration paraKeys = request.getParameterNames();
		String encodeStr = "";
		while (paraKeys.hasMoreElements()) {
			String paraKey = (String) paraKeys.nextElement();
			if(paraKey.equals("token")) 
				break;
			String paraValue = request.getParameter(paraKey);
			encodeStr += paraValue;
		}
		encodeStr += Default.TOKEN_KEY;
		Log.out(encodeStr);
		
		if ( ! token.equals(DigestUtils.md5Hex(encodeStr))) {
			response.setStatus(500);
			return false;
		}
		
		return true;*/
	}

	@Override
	public void postHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		System.out.println(request);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
