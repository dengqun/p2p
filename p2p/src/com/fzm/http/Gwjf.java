/*package com.fzm.http;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.fuzamei.jarlibrary.utils.KeyUtils;
import com.fuzamei.jarlibrary.utils.ProtobufBean;
import com.fuzamei.jarlibrary.utils.ProtobufUtils;

public class Gwjf {
	
	
	// serverUrl
	//杭州  121.41.33.113:9903
	//北京  211.160.73.224:9901
	//https://122.244.77.186:8088
	private  String serverUrl = "http://10.1.33.74:9901";
	private  Request request=RequestFactory.createRequest(serverUrl);

	
	public boolean configServerUrl(String ip , Integer port) {
		if (ip != null && !ip.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            if (ip.matches(regex)) {
            	
            	if(port!=null && 65535>=port && port>0){
               
            	this.serverUrl = "http://"+ ip + ":" + port;
            	
            	return true;
            	}else{
            		return false;
            	}
            
            } else {
                // 返回判断信息
                return false;
            }
		}
		return false;
		
	}
	

	// 用户注册
	public String requestUserCreate(String uid, String info)
			throws Exception {
		if(("").equals(uid)){
			return "uid  is empty";
		}
		
		String url =  this.serverUrl + "/v1/user/register";
		String parameter = "";
		String random = KeyUtils.getRandom();
		String privateKey = KeyUtils.getPrivateKey(uid, random);
		String publicKey = KeyUtils.getPublicKey(privateKey);

		

		String instructionId = "";
		String signature = "";
		if(("").equals(privateKey)||("").equals(publicKey)){
			return "privateKey or publicKey is emtpy";
		}
		ProtobufBean protobufBean = ProtobufUtils.requestUserCreate(privateKey,
				publicKey, uid, publicKey, info);
		
		
		instructionId = "" + protobufBean.getInstructionId();
		signature = protobufBean.getSignature();
		if(("").equals(instructionId)||("").equals(signature)){
			return "instructionId or signature is empty";
		}
		
		parameter = "uid=" + uid + "&publickey=" + publicKey + "&info=" + info
				+ "&instructionId=" + instructionId + "&signature=" + signature
				+ "&random=" + random;
		String message = request.sendPost(url, parameter);
		return message;

	}

	// 获取用户随机数及公钥
	private String GetUserInfo(String uid) throws Exception {
		if(("").equals(uid)){
			return "uid is empty";
		}
		String url =  this.serverUrl + "/v1/query/user-info";
		String parameter = "uid=" + uid;
		String message = request.sendPost(url, parameter);
		
		return message;
	}

	// 获取积分余额
	private String GetUnspent(String uid) throws Exception {
		if(("").equals(uid)){
			return "uid is empty";
		}
		String url =  this.serverUrl + "/v1/query/amount";
		String parameter = "uid=" + uid;
		String message = request.sendPost(url, parameter);
		
		return message;
	}

	private String GetPlatForm() throws Exception {
		String url =  this.serverUrl + "/v1/query/platform-info";
		String parameter = "platform_verify_key=SGCC_33_XLSWDL_FZM@";
		String message = request.sendPost(url, parameter);
	
		return message;
	}

	// 设置积分兑换比例
	public String requestSetCompanyExchangeRate(String companyId, int amount_op,
			int amount,  String info) throws Exception {
		if(("").equals(companyId)||("").equals(amount_op)||("").equals(amount)){
			return "companyId  or amount_op or amount is empty";
		}
		String message = "";
		String url =  this.serverUrl + "/v1/user/rate-set";
		String parameter = "";
		message = GetPlatForm();
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map =(Map<String,Object>)JSON.parse(message);
		
		if (200 == (Integer) map.get("code")) {
			@SuppressWarnings("unchecked")
			Map<String,Object> maps = (Map<String,Object>)JSON.parse("" + map.get("data"));
			String random = (String) maps.get("random");
			

			String publicKey = (String) maps.get("publickey");
			String privateKey = KeyUtils.getPrivateKey("gjdwjfxt", random);
		

			
			if(("").equals(privateKey)||("").equals(publicKey)){
				return "instructionId or signature is empty";
			}
			
			
			ProtobufBean pbSetCompanyExchangeRate = ProtobufUtils
					.requestSetCompanyExchangeRate(privateKey, publicKey,
							companyId, amount_op, amount, info);
			
			
			String instructionId = ""+pbSetCompanyExchangeRate.getInstructionId();
			String signature =  pbSetCompanyExchangeRate.getSignature();
			if(("").equals(instructionId)||("").equals(signature)){
				return "instructionId or signature is empty";
			}
			
			try {
				parameter = "companyId=" + companyId + "&amount_op="
						+ amount_op + "&amount=" + amount + "&publickey="
						+ publicKey + "&info=" + info + "&instructionId="
						+ pbSetCompanyExchangeRate.getInstructionId()
						+ "&signature="
						+ pbSetCompanyExchangeRate.getSignature();
			
				message = request.sendPost(url, parameter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			
			return message;
		}
		return message;
	}

	// 积分兑入
	public String requestBuyGwpoints(String companyId, int amount, String uid,
			 String recordId) throws Exception {
		if(("").equals(companyId)||("").equals(amount)||("").equals(uid)||("").equals(recordId)){
			return "companyId or amount or recordId is empty";
		}
		String message = "";
		String parameter = "";
		String url =  this.serverUrl + "/v1/point/exchange-in";
		message = this.GetUserInfo(uid);
		if(("").equals(message)){
			return "message is empty";
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>) JSON.parse(message);

		if (200 == (Integer) map.get("code")) {
			@SuppressWarnings("unchecked")
			Map<String,Object> maps = (Map<String,Object>) JSON.parse("" + map.get("data"));
			String random = (String) maps.get("random");

			String publicKey = (String) maps.get("publickey");
			String privateKey = KeyUtils.getPrivateKey(uid, random);

			ProtobufBean pbBuyGwpoints = ProtobufUtils.requestBuyGwpoints(
					privateKey, publicKey, uid, companyId, amount);
		
			String instructionId = ""+pbBuyGwpoints.getInstructionId();
			String signature =  pbBuyGwpoints.getSignature();
			if(("").equals(instructionId)||("").equals(signature)){
				return "instructionId or signature is empty";
			}
			try {
				
				parameter = "companyId=" + companyId + "&amount=" + amount
						+ "&uid=" + uid + "&publickey=" + publicKey
						+ "&instructionId=" + pbBuyGwpoints.getInstructionId()
						+ "&signature=" + pbBuyGwpoints.getSignature()
						+ "&record_id=" + recordId;
				
				message = request.sendPost(url, parameter);
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		
			return message;
		} else {
			return message;
		}

	}

	// 兑出
	public String requestSellGwPoints(String companyId, int amount, String uid,
			 String recordId) throws Exception {
		if(("").equals(companyId)||("").equals(amount)||("").equals(uid)||("").equals(recordId)){
			return "companyId or amount  or recordId is empty";
		}
		String message = "";
		String url =  this.serverUrl + "/v1/point/exchange-out";
		String parameter = "";
		message = this.GetUserInfo(uid);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>) JSON.parse(message);

		String mapString = "" + map.get("data");
		if (200 == (Integer) map.get("code")) {
			@SuppressWarnings("unchecked")
			Map<String,Object> maps = (Map<String,Object>) JSON.parse(mapString);
			String random = (String) maps.get("random");

			String publicKey = (String) maps.get("publickey");
			if(("").equals(random)||("").equals(publicKey)){
				return "random or publickey is empty";
			}
			String privateKey = KeyUtils.getPrivateKey(uid, random);

			ProtobufBean pbSellGwpoints = ProtobufUtils.requestSellGwPoints(
					privateKey, publicKey, uid, companyId, amount);
			

			try {
				String instructionId = ""+pbSellGwpoints.getInstructionId();
				String signature =  pbSellGwpoints.getSignature();
				if(("").equals(instructionId)||("").equals(signature)){
					return "instructionId or signature is empty";
				}
				parameter = "companyId=" + companyId + "&amount=" + amount
						+ "&uid=" + uid + "&publickey=" + publicKey
						+ "&instructionId=" + pbSellGwpoints.getInstructionId()
						+ "&signature=" + pbSellGwpoints.getSignature()
						+ "&record_id=" + recordId;
				
				message = request.sendPost(url, parameter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}

			
			return message;
		} else {
			return message;
		}
	}

	// 同步
	public String requestSyncPoints(int GwAmount, String uid)
			throws Exception {
		if(("").equals(uid)||("").equals(GwAmount)){
			return "uid or GwAmount is empty";
		}
		String message = "";

		String url =  this.serverUrl + "/v1/point/sync";
		String parameter = "";

		String usermessage = this.GetUserInfo(uid);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>) JSON.parse(usermessage);

		if (200 == (Integer) map.get("code")) {
			@SuppressWarnings("unchecked")
			Map<String,Object> maps = (Map<String,Object>) JSON.parse("" + map.get("data"));
			String random = (String) maps.get("random");

			String publicKey = (String) maps.get("publickey");
			String privateKey = KeyUtils.getPrivateKey(uid, random);

			message = this.GetUnspent(uid);

			if (("").equals(message)) {
				return "getUnspent Exception ";
			}
			@SuppressWarnings("unchecked")
			Map<String,Object> map1 = (Map<String,Object>) JSON.parse(message);

			if (200 == (Integer) map1.get("code")) {
				@SuppressWarnings("unchecked")
				Map<String,Object> maps1 = (Map<String,Object>) JSON.parse("" + map1.get("data"));
				
				int blockChainAmount = Integer.parseInt(""
						+ maps1.get("amount"));
				int amount = 0;
				int type = 0;
				if (GwAmount > blockChainAmount) {
					amount = GwAmount - blockChainAmount;
					type = 1;
				} else if (GwAmount < blockChainAmount) {
					amount = blockChainAmount - GwAmount;
					type = 2;
				} else {
					return message;
				}
				
				

				ProtobufBean pbSyncPoints = ProtobufUtils.requestSyncPoints(
						privateKey, publicKey, uid, publicKey, amount, type);
				

				try {
					String instructionId = ""+pbSyncPoints.getInstructionId();
					String signature =  pbSyncPoints.getSignature();
					if(("").equals(instructionId)||("").equals(signature)){
						return "instructionId or signature is empty";
					}
					parameter = "type=" + type + "&amount=" + amount + "&uid="
							+ uid + "&publickey=" + publicKey
							+ "&instructionId="
							+ pbSyncPoints.getInstructionId() + "&signature="
							+ pbSyncPoints.getSignature();
					message = request.sendPost(url, parameter);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}
				
				return message;
			} else {
				return message;
			}
		} else {
			return usermessage;

		}

	}

	// 初始化平台
	public String initPlant() throws Exception {

		String message = "";
		String url =  this.serverUrl + "/v1/user/platform-init";
		String parameter = "";
		String info = "SGCC";
		String random = KeyUtils.getRandom();
		try {
			parameter = "password=" + "gjdwjfxt" + "&random=" + random + "&info="
					+ info;
			
			message = request.sendPost(url, parameter);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return message;
	}

	// 获取交易信息
	public String getTransactionInfo(String recordId) throws Exception {
		if(("").equals(recordId)){
			return "recordId is empty";
		}
		String message = "";
		
		String url =  this.serverUrl + "/v1/query/transaction-info";
		String param = "record_id=" + recordId;
		message = request.sendGet(url, param);
		
		return message;

	}
}
*/