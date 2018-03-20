package com.fzm.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RegisterFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			System.out.println("------进入到这个过滤器------");
			HttpServletRequest req = (HttpServletRequest)request;
			HttpSession session = ((HttpServletRequest) request).getSession();
			String url = req.getRequestURI();
//			String userId = session.getAttribute("userid");
//			String username =session.getAttribute("username");	//得到session中用户对应的id
//			String token = session.getAttribute("token");	//得到sesson中用户的token值
			System.out.println("session里面用户的id："+session.getAttribute("userid"));
			System.out.println("session里面的用户名称："+session.getAttribute("username"));
			System.out.println("session中该用户对应的token："+session.getAttribute("token"));
			//判断用户名称是不是等于空，等于空就说明session超时了
			if(session.getAttribute("username") != null){	//定向到主页
				System.out.println("---------------你TM还没超时，继续给你访问其他资源的权利！--------------");
				//对token做一个校验
				
				chain.doFilter(request, response);
				return ;
			}
			else{	//返回到登录页面，要求用户重新登录
				System.out.println("---------------你已经超时了，就重新登录吧！--------------");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
			/*if(!RegisterFilter.hasText(userName) && !filterPage(url)){
//			if(true){
				HttpServletResponse res=(HttpServletResponse) response ;
				//res.sendError(606);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				               
			}else{
				chain.doFilter(request, response);
				return;
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/*private String[] strs = new String[]{
			"user!findPasswordByMessage.action",
			"user!sendWlan.action",
			"user!userLogin.action",
			"login.jsp",
			"user!saveLoginLog.action"
	};*/
	private String[] strs = new String[]{"login.jsp"
	};
	public boolean filterPage(String url){
		boolean result=false;
		
		for(int i=0;i<strs.length;i++){
			if(url.endsWith(strs[i])){
				result = true;
			}
		}
		return result;
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	public static boolean hasText(String str) {

        if (!hasLength(str)) {

            return false;
        }

        int strLen = str.length();

        for (int i = 0; i < strLen; i++) {

            if (!Character.isWhitespace(str.charAt(i))) {

                return true;
            }
        }

        return false;
    }
	public static boolean hasLength(String str) {

        return ((str != null) && (str.length() > 0));
    }
}
