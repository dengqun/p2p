package com.fzm.web;

import java.util.HashMap;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fzm.service.BorrowService;
import com.fzm.service.serviceImpl.UserLoginServiceImpl;

@Controller
@RequestMapping("api/borrow/")
public class BorrowController {

	@Resource(name="userLoginService")
	private UserLoginServiceImpl userLoginService;
	@Resource(name="borrow")
	private BorrowService borrow;
	
	@RequestMapping("message")
	@ResponseBody
	public Map<String,Object> select(HttpServletRequest req,HttpServletResponse resp){
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				Object checkProducetId = req.getParameter("fid").toString();
				if(checkProducetId!=null){
					String producetid = checkProducetId.toString();
					map = borrow.select(producetid);
					return map;
				}else{
					map.put("code", "201");
					map.put("StateMsg","请重试");
					map.put("StateCode", false);
					return map;
				}
			}else{
				map.put("code", "201");
				map.put("StateMsg","请您登陆后再重试");
				map.put("StateCode", false);
				return map;
			}
		}else{
			map.put("code", "202");
			map.put("StateMsg","token已失效,请您登陆后再重试");
			map.put("StateCode", false);
			return map;
		}
	}
}
