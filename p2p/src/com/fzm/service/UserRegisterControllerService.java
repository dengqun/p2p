/**
 * 
 */
package com.fzm.service;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fzm.cgb.KeyUtils;
import com.fzm.cgb.ProtobufBean;
import com.fzm.cgb.ProtobufUtils;
import com.fzm.cgb.RequestAccountBean;
import com.fzm.daoimpl.UserLoginDaoImpl;
import com.fzm.entity.User;
import com.fzm.http.HttpRequest;
import com.fzm.tools.ConfigReader;

/** 

 * @ClassName: UserRegisterControllerService 

 * @Description: TODO(用户注册区块链的操作类) 

 * @author maamin 

 * @date 2017-8-20 上午10:43:13 

 * 
 

 */
@Service("userRegisterControllerService")
public class UserRegisterControllerService {
	@Autowired
	private UserLoginDaoImpl userdao;
	public static HttpRequest httpRequest = new HttpRequest();
	public ProtobufBean register(String userName,String password,String userType,String randomNumber){
		ProtobufBean protobufBean = new ProtobufBean();
		User user =  userdao.getUser(userName);//通过手机号或者邮箱得到用户的信息
		System.out.println("已经在本地注册过的用户的信息:"+user);
		//将用户注册信息存放一份到区块链
		if(user != null){
			System.out.println("同时将注册信息记录到区块链……");
			String privateKey = KeyUtils.getPrivateKey(password, randomNumber);	//私钥
			String pubkey = KeyUtils.getPublicKey(privateKey);	//公钥
			
			String userUid = String.valueOf(user.getUid());
			String type = user.getType();
			String typeV = "1";
			if("投资人".equals(type)){
				typeV = "2";
			}
			System.out.println("用户注册时生成的随机数、私钥，公钥……");
			System.out.println("公钥："+pubkey);
			System.out.println("用户id："+userUid);
			System.out.println("用户类型："+type);
			SecureRandom secureRandom = new SecureRandom();
            long num = secureRandom.nextLong();
//			ProtobufUtils.requestUserCreate(operatorPrivateKey, pubkey, userUid, userPubkey, info);
//			protobufBean  = ProtobufUtils.requestUserCreate(privateKey, pubkey, userUid, pubkey, "用户注册",typeV);
            protobufBean = ProtobufUtils.requestUserCreate(userUid, pubkey, typeV,privateKey);
			System.out.println("用户注册提交区块链得到的结果："+protobufBean);
		}
		return protobufBean;
	}
	/**
	
	* @Title: sendPost 
	
	* @Description: TODO(像区块链发送数据) 
	
	* @param @return    signdata：注册的签名
	
	* @return String    返回类型 
	
	* @throws
	 */
	public static boolean sendPost(ProtobufBean protobufBean){
		boolean flag = false;
		String signdata = protobufBean.getSignature();
		 String[] params = new String[1];
         params[0] = signdata;
         System.out.println("sign data......:"+signdata);
         String jsonstr = JSON.toJSONString(new RequestAccountBean("2.0", "broadcast_tx_commit", null, params));
         try {
			String result =  httpRequest.sendPost(new ConfigReader().read().get("blockchainurl").toString(), jsonstr);
			//验证区块链返回的结果
			boolean a = BlockChainUtil.vilaResult(result);
			System.out.println(">>>>>"+result);
			if(a){	//成功
				flag =  true;
			}
			else{
				flag = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
         return flag;
	}
}
