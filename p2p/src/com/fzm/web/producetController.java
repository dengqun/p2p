package com.fzm.web;

import java.util.HashMap;



import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fzm.service.ProducetService;
import com.fzm.service.serviceImpl.UserLoginServiceImpl;

@Controller
@RequestMapping("api/invest/")
public class producetController {

	@Resource(name="investAction")
	private ProducetService financing;
	@Resource(name="userLoginService")
	private UserLoginServiceImpl userLoginService;
	
	/**
	 * 获取用户的信息
	 * */
	@RequestMapping("userMessage")
	@ResponseBody
	public Map<String,Object> userMessage(HttpServletRequest req,HttpServletResponse resp){
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				int uid = Integer.parseInt(reqUserId.toString());
				map = financing.userMessage(uid);
				return map;
			}else{
				map.put("code", "201");
				map.put("StateMsg","请您登陆后再重试");
				map.put("StateCode", false);
				return map;
			}
		}else{
			map.put("code", "202");
			map.put("StateMsg","登陆超时，请您重新登陆");
			map.put("StateCode", false);
			return map;
		}
	}
	
	/**
	 * 获取登陆用户所有的没有投资的理财产品
	 * @param  uid   用户的id
	 * */
	@RequestMapping("nonentity")
	@ResponseBody
	public Map<String,Object> achieve(HttpServletRequest req,HttpServletResponse resp){
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				int uid = Integer.parseInt(reqUserId.toString());
				Object pageIndex =req.getParameter("pageIndex");
				Object rows = req.getParameter("rows");
				map = financing.achieve(pageIndex, rows, uid);
				return map;
			}else{
				map.put("code", "201");
				map.put("StateMsg","请您登陆后再重试");
				map.put("StateCode", false);
				return map;
			}
		}else{
			map.put("code", "202");
			map.put("StateMsg","登陆超时，请您重新登陆");
			map.put("StateCode", false);
			return map;
		}
	}
	

	/**
	 * 获取登陆用户所有的已经投资的理财产品
	 * */
	@RequestMapping("exist")
	@ResponseBody
	public Map<String,Object> achieveByFid(HttpServletRequest req,HttpServletResponse resp){
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				int uid = Integer.parseInt(reqUserId.toString());
				int Invest_stateNo = Integer.parseInt(req.getParameter("ufsid").toString());
				Object page =req.getParameter("pageIndex");
				Object row = req.getParameter("rows");
				map = financing.achieveByFid(page, row, uid,Invest_stateNo);
				return map;
			}else{
				map.put("code", "201");
				map.put("StateMsg","请您登陆后再重试");
				map.put("StateCode", false);
				return map;
			}
		}else{
			map.put("code", "202");
			map.put("StateMsg","token已失效");
			map.put("StateCode", false);
			return map;
		}
	}
	
	/**
	 * 充值和提款记录
	 * */
	@RequestMapping("record")
	@ResponseBody
	public Map<String,Object> record(HttpServletRequest req,HttpServletResponse resp){
	
		Map<String,Object> map = new HashMap<String,Object>();
		resp.setCharacterEncoding("utf-8");
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				int uid = Integer.parseInt(reqUserId.toString());
				Object rocordType = req.getParameter("rType");
				Object page =req.getParameter("pageIndex");
				Object row = req.getParameter("rows");
				map = financing.record(rocordType, page, row, uid);
				return map;
			}else{
				map.put("code", "201");
				map.put("StateMsg","请您登陆后再重试");
				map.put("StateCode", false);
				return map;
			}
		}else{
			map.put("code", "201");
			map.put("StateMsg","token已失效,请您登陆后再重试");
			map.put("StateCode", false);
			return map;
		}
	}
	
	@RequestMapping("queren")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest req,HttpServletResponse resp){
		resp.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String message;
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				int uid = Integer.parseInt(reqUserId.toString());
				Object producetNo = req.getParameter("fid");
				String qianming = req.getParameter("sign");
				Object Money= req.getParameter("iMoney");
				if(Money!=null && producetNo!=null){
					double investMoney = Double.parseDouble(Money.toString());
					String producetid = producetNo.toString();
					map = financing.update(investMoney, uid, producetid, qianming);
					return map;
				}else{
					message = "您的投资金额不符合要求";
					map.put("code", 202);
					map.put("StateCode", false);
					map.put("StateMsg", message);
					return map;
				}
			}else{
				message = "登陆超时，请重新登陆";
				map.put("code", 201);
				map.put("StateCode", false);
				map.put("StateMsg", message);
				return map;
			}
		}else{
			message = "token已失效，请重新登陆";
			map.put("code", 201);
			map.put("StateCode", false);
			map.put("StateMsg", message);
			return map;
		}
	}
}
