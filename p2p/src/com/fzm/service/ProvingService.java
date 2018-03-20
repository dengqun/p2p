package com.fzm.service;


import java.io.File;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import com.fzm.dao.AttachmentDao;
import com.fzm.daoimpl.UserDaoImpl;
import com.fzm.daointerface.UserIDao;
import com.fzm.entity.AttachmentVO;
import com.fzm.entity.User;
import com.fzm.tools.JsonToString;

/**
 * FileName: provingController.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-10下午5:09:52
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-10           王斌                                          @version          
 *
 * Why & What is modified:
 */
@Service("proving")
public class ProvingService {

	@Resource(name="json")
	private JsonToString json;
	@Resource(name="user")
	private UserDaoImpl userv;
	@Autowired
	AttachmentDao attachmentDao;
	@Autowired
	UserIDao udao;
	@Autowired
	private DataSourceTransactionManager txManager;
	
	/**
	 * 查询用户实名认证情况
	 * @param uid 用户id
	 * */
	public Map<String, Object>  proving(int uid){
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> userList = userv.gainUser(uid);
		map.put("data", userList);
		map.put("code", "200");
		map.put("StateMsg","查询用户实名认证信息成功");
		map.put("StateCode", true);
		return map;
	}
	
	/**
	 * 显示用户实名认证信息
	 * @param uid 用户id
	 * */
	public Map<String, Object>  provingMsg1(int uid){
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> userList = udao.gainUserMsg1(uid);
		String userid = String.valueOf(uid);
		List<Map<String,Object>> AttachmentList = attachmentDao.selectAttachment(userid);
		map.put("data", userList);
		map.put("attachment", AttachmentList);
		map.put("code", "200");
		map.put("StateMsg","查询用户实名认证信息成功");
		map.put("StateCode", true);
		return map;
	}
	
	/**
	 * 显示用户实名认证信息
	 * @param uid 用户id
	 * */
	public Map<String, Object>  provingMsg2(int uid){
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> userList = udao.gainUserMsg2(uid);
		map.put("data", userList);
		map.put("code", "200");
		map.put("StateMsg","查询用户实名认证信息成功");
		map.put("StateCode", true);
		return map;
	}
	
	/**
	 * 更新用户实名认证信息
	 * @param uid 用户id
	 * @param data 用户实名认证的信息
	 * */
	
	public Map<String,Object> updateMessage(String data,int uid) throws UnsupportedEncodingException{
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = txManager.getTransaction(def);
		
		Map<String,Object> map = new HashMap<String, Object>();
		User vo = new User();
		vo.setUid(uid);
		Map<String, Object> mapJson=  json.jsonToMap(data);
			if(mapJson.size()>0){
				/*
				 * 用户身份证实名认证
				 * Start
				 * */
				if(mapJson.get("name")!=null){
					String name = mapJson.get("name").toString();
					String bankName = mapJson.get("bankName").toString();
				
					vo.setName(new String(name.getBytes("iso-8859-1"),"utf-8"));
					vo.setIdNum(mapJson.get("idNum").toString());
					vo.setBankNo(mapJson.get("bankNo").toString());
					vo.setBankName(new String(bankName.getBytes("iso-8859-1"),"utf-8"));
					vo.setBankPhone(mapJson.get("bankPhone").toString());
					System.out.println(mapJson);
					if(userv.updateUser(vo)>0){
						vo = userv.findUserList(uid);
						if("借款人".equals(vo.getType())){
							vo.setCredit("A");
							vo.setCreditNum(1000000);
							userv.updateCredit(vo);
							txManager.commit(status);
							map.put("code", 200);
							map.put("StateMsg","更新实名认证信息成功");
							map.put("StateCode", true);
							return map;
						}else{
							txManager.commit(status);
							map.put("code", 200);
							map.put("StateMsg","更新实名认证信息成功");
							map.put("StateCode", true);
							return map;
						}
					}else{
						txManager.rollback(status);
						map.put("code", 199);
						map.put("StateMsg","更新实名认证信息失败");
						map.put("StateCode", false);
						return map;
					}
				}
				/*
				 * 用户身份证实名认证
				 * End
				 * */
				
				/*
				 * 用户手机号认证
				 * Start
				 * */
				if(mapJson.get("phone")!=null){
					String phone = mapJson.get("phone").toString();
					if(userv.intPhone(phone)==0){
						vo.setPhone(mapJson.get("phone").toString());
						vo.setUid(uid);
						vo.setPhone(phone);
						int count = userv.updateUser(vo);
						if(count>0){
							txManager.commit(status);
							map.put("code", 200);
							map.put("StateMsg","更新实名认证信息成功");
							map.put("StateCode", true);
							return map;
						}else{
							txManager.rollback(status);
							map.put("code", 199);
							map.put("StateMsg","更新实名认证信息失败");
							map.put("StateCode", false);
							return map;
						}
					}else{
						txManager.rollback(status);
						map.put("code", 210);
						map.put("StateMsg","手机号已存在");
						map.put("StateCode", true);
						return map;
					}
				}
				/*
				 * 用户手机号认证
				 * End
				 * */
				
				/*
				 * 用户邮箱认证
				 * Start
				 * */
				if(mapJson.get("email") != null){
					String email = mapJson.get("email").toString();
					if(userv.intEmail(email)==0){
						vo.setUid(uid);
						vo.setEmail(mapJson.get("email").toString());
						int count = userv.updateUser(vo);
						if(count>0){
							txManager.commit(status);
							map.put("code", 200);
							map.put("StateMsg","更新实名认证信息成功");
							map.put("StateCode", true);
							return map;
						}else{
							txManager.rollback(status);
							map.put("code", 0);
							map.put("StateMsg","更新实名认证信息失败");
							map.put("StateCode", false);
							return map;
						}
						
					}else{
						txManager.rollback(status);
						map.put("code", 0);
						map.put("StateMsg","邮箱已存在");
						map.put("StateCode", false);
						return map;
					}
				}else{
					txManager.commit(status);
					map.put("code", 200);
					map.put("StateMsg","验证成功");
					map.put("StateCode", true);
					return map;
				}
				/*
				 * 用户邮箱认证
				 * End
				 * */
			}else{
				txManager.rollback(status);
				map.put("code", 198);
				map.put("StateMsg","请填写后再提交");
				map.put("StateCode", true);
				return map;
			}
	}
	
	/**
	 * 实名认证上传身份证
	 * @param files 文件
	 * @param uid 用户id
	 * @param type 身份证正反面  1正面  2反面
	 * */
	public String uploadFile(MultipartFile[] files,HttpServletRequest request,String uid,int type){
//		String uid = (String)request.getSession().getAttribute("userid");
		String attachmentid="";
		//处理附件开始
		 for(int i = 0;i<files.length;i++){
               MultipartFile file = files[i];  
               if (!file.isEmpty()) {  
   	        	String uuid=UUID.randomUUID().toString().replace("-","");
   	        	String prefix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
   	            try {  
   	            	
   	                String filePath = request.getSession().getServletContext().getRealPath("/") + "/fileUpload/"  
   	                        + uuid+"."+prefix;  
   	                file.transferTo(new File(filePath));  
   	                AttachmentVO attachment=   new AttachmentVO();
   	                attachment.setUrl(request.getContextPath()+"/fileUpload/"+ uuid+"."+prefix);
   	                attachment.setCreatetime(new Date());
   	                attachment.setRelationid(uid);
   	                attachment.setType(prefix);
   	               
   	                attachment.setDescri(String.valueOf(type));
   	                
   	                attachment.setName(file.getOriginalFilename());
   	                attachment.setStatus("1");
   	            attachmentDao.insertAttachment1(attachment);
   	           if("".equals(attachmentid)){
     	             attachmentid=String.valueOf(attachment.getId());
   	           }else{
     	             attachmentid=attachmentid+","+String.valueOf(attachment.getId());
   	           }
   	            } catch (Exception e) {  
   	                e.printStackTrace();  
   	            }  
   	        
   	        } 
		 }
		 return attachmentid;
		//处理附件结束
	}
}

//					if(count>0){
						//判断用户实名认证是否完毕，实名认证成功后进行更新用户的信用额度
//						vo = userv.findUserList(uid);
//						if("借款人".equals(vo.getType())){
//							if(vo.getName()!=null&vo.getPhone()!=null||vo.getEmail()!=null){
//								vo.setCredit("C");
//								vo.setCreditNum(10000);
//								userv.updateCredit(vo);
//								map.put("code", 200);
//								map.put("StateMsg","更新借款人实名认证信息成功");
//								map.put("StateCode", true);
//								return map;
//							}else{
//								map.put("code", 198);
//								map.put("StateMsg","更新投资人信用等级和信用总额失败");
//								map.put("StateCode", false);
//								return map;
//							}
//						}else{
//							map.put("code", 200);
//							map.put("StateMsg","更新投资人实名认证信息成功");
//							map.put("StateCode", true);
//							return map;
//						}
//					}else{
//						map.put("code", 199);
//						map.put("StateMsg","插入失败");
//						map.put("StateCode", false);
//						return map;
//					}
	

