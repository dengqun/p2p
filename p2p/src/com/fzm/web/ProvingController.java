package com.fzm.web;

import java.io.UnsupportedEncodingException;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fzm.dao.AttachmentDao;
import com.fzm.service.ProvingService;
import com.fzm.service.serviceImpl.UserLoginServiceImpl;

@Controller
@RequestMapping("api/proving/")
public class ProvingController {

	@Resource(name="proving")
	private ProvingService prov;
	@Resource(name="userLoginService")
	private UserLoginServiceImpl userLoginService;
	@Autowired
	AttachmentDao attachmentDao;
	
	private static final Logger logger = Logger.getLogger(ProvingController.class);  
	
	@RequestMapping("select")
	@ResponseBody
	public Map<String,Object> selectUserProv(HttpServletRequest req,HttpServletResponse resp){
		
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			String reqUserId = userLoginService.ValiToken(req);
			if(!"".equals(reqUserId)){
				int uid = Integer.parseInt(reqUserId);
				map = prov.proving(uid);
				return map;
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
	
	@RequestMapping("selectUserMsgOne")
	@ResponseBody
	public Map<String,Object> selectUserMsgProvOne(HttpServletRequest req,HttpServletResponse resp){
		
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			String reqUserId = userLoginService.ValiToken(req);
			if(!"".equals(reqUserId)){
				int uid = Integer.parseInt(reqUserId);
				map = prov.provingMsg1(uid);
				return map;
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
	
	@RequestMapping("selectUserMsgTwo")
	@ResponseBody
	public Map<String,Object> selectUserMsgProvTwo(HttpServletRequest req,HttpServletResponse resp){
		
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String,Object>();
		Object checkToken = req.getHeader("Authorization");
		if(checkToken!=null){
			Object reqUserId = userLoginService.ValiToken(req);
			if(reqUserId!=null){
				int uid = Integer.parseInt(reqUserId.toString());
				map = prov.provingMsg2(uid);
				return map;
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
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> updateUserProv(HttpServletRequest request,
			HttpServletResponse resp,@RequestBody String data){
		
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			Object checkToken = request.getHeader("Authorization");
			if(checkToken!=null){
				Object reqUserId = userLoginService.ValiToken(request);
				if(reqUserId!=null){
					int uid = Integer.parseInt(reqUserId.toString());
					map = prov.updateMessage(data, uid);
					return map;
					 
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
		}catch(Exception e){
			map.put("code", "190");
			map.put("StateMsg","更新用户实名认证信息失败");
			map.put("StateCode", false);
			return map;
		}
	}
	
	@RequestMapping("uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam MultipartFile[] file,int type,HttpSession session,HttpServletRequest request,
			HttpServletResponse resp) throws UnsupportedEncodingException {
		resp.setCharacterEncoding("utf-8");
		
		logger.warn("real check upload file type:========"+type);
		
		Map<String, Object> map = new HashMap<String, Object>();
		Object checkToken = request.getHeader("Authorization");
		
			
		if(checkToken!=null){
			Object uids = userLoginService.ValiToken(request);
			if(uids == null){
				map.put("code", 0);
				map.put("StateCode", false);
				map.put("StateMsg", "登录信息失效");
				return map;
			}
			String uid = uids.toString();
			String result = null;
				if(file!=null&&file.length>0){
					
				}else{
					map.put("code", 0);
					map.put("StateCode", false);
					map.put("StateMsg", "实名认证需要上传照片");
					return map; 
				}
	
				try{
					result = prov.uploadFile(file,request,uid,type);
				}catch (Exception e) {
					map.put("code", 0);
					map.put("SateCode", false);
					map.put("StateMsg", "上传时出现服务器内部错误");
					System.out.println(e.getMessage());
				}
			
			map.put("code", 200);
			map.put("StateCode", true);
			map.put("StateMsg", "附件上传成功");
			map.put("data", result);
			return map;
	
		}else{
			map.put("code", "202");
			map.put("StateMsg","token已失效,请您登陆后再重试");
			map.put("StateCode", false);
			return map;
		}
	}
}
