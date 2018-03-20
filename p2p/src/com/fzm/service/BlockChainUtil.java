/**
 * 
 */
package com.fzm.service;

import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.fzm.cgb.ProtobufBean;
import com.fzm.cgb.RequestAccountBean;
import com.fzm.http.HttpRequest;
import com.fzm.tools.ConfigReader;
import com.fzm.tools.JSONUtil;

/** 

 * @ClassName: BlockChainUtil 

 * @Description: TODO(这里用一句话描述这个类的作用) 

 * @author maamin 

 * @date 2017-8-21 下午1:35:52 

 * 
 

 */
public class BlockChainUtil {
/**
	
	* @Title: sendPost 
	
	* @Description: TODO(像区块链发送数据) 
	
	* @param @return    signdata：注册的签名
	
	* @return String    返回类型 
	
	* @throws
	 */
	/*public static boolean sendPostParam(ProtobufBean protobufBean){
		HttpRequest httpRequest = new HttpRequest();
		boolean flag = false;
		String signdata = protobufBean.getSignature();
		 String[] params = new String[1];
         params[0] = signdata;
         String jsonstr = JSON.toJSONString(new RequestAccountBean("2.0", "broadcast_tx_commit", null, params));
         try {
			String result =  httpRequest.sendPost("http://114.55.0.107:46657", jsonstr);
			System.out.println("区块链反馈的结果："+result);
			if(result!=null){	//成功
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
	}*/
	/**
	 * 
	
	* @Title: sendPostParam 
	
	* @Description: TODO(放回区块链的结果) 
	
	* @param @param protobufBean
	* @param @return    设定文件 
	
	* @return String    返回类型 
	
	* @throws
	 */
	public static String  sendPostParam(ProtobufBean protobufBean){
		HttpRequest httpRequest = new HttpRequest();
		String result = "";	//存放最终区块链放回的结果
		String signdata = protobufBean.getSignature();
		 String[] params = new String[1];
         params[0] = signdata;
         System.out.println("sign data sendpostparam sssss:"+signdata);
         String jsonstr = JSON.toJSONString(new RequestAccountBean("2.0", "broadcast_tx_commit", null, params));
         try {
			result =  httpRequest.sendPost(new ConfigReader().read().get("blockchainurl").toString(), jsonstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return result;
	}
	/**
	 * 
	
	* @Title: getHash 
	
	* @Description: TODO(从区块链返回的结果中得到hash值，错误，返回error的信息) 
	* 判断逻辑：
	* 先判断error，存在值，不用管，表示请求就错误了
	* error为空，在判断code是否为0，为0表示成功，否则表示失败
	
	* @param @param jsonData
	* @param @return    设定文件 
	
	* @return String    返回类型 
	
	* @throws
	 */
	public static boolean vilaResult(String jsonData){
		boolean flag = false;
		Map<String,Object> map = JSONUtil.jsonToMap(jsonData);	//总的map
		System.out.println(map);
		//得到第一个code的值
		String result = map.get("result").toString();	//得到result节点
		Map<String,Object> resultMap = JSONUtil.jsonToMap(result);
		String check_tx = resultMap.get("check_tx").toString();
		Map<String,Object> check_txMap = JSONUtil.jsonToMap(check_tx);
		String code1 = check_txMap.get("code").toString();
		//得到第二个code的值
		String deliver_tx = resultMap.get("deliver_tx").toString();
		Map<String,Object> deliver_txMap = JSONUtil.jsonToMap(deliver_tx);
		String code2 = deliver_txMap.get("code").toString();
		if(("0".equals(code1))&&("0".equals(code2))){	//两个都等于0才等于成功
			flag = true;
		}
		return flag;
	}
	public static void main(String[] args) {
		String jsondata = "{\"jsonrpc\":\"2.0\",\"id\":\"\",\"result\":null,\"error\":\"Error unreflecting result: Check tx failed with non-zero code: InternalError. Data: 706C6174666F726D206578697374; Log: \"}";
//		String result = getHash(jsondata);
//		System.out.println(result);
	}
}
