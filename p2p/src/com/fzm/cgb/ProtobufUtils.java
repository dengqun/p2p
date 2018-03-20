package com.fzm.cgb;

import gfp2p.Api;
import gfp2p.Api.UserType;

import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import com.fzm.eddsa.EdDSAEngine;
import com.fzm.eddsa.EdDSAPrivateKey;
import com.fzm.eddsa.spec.EdDSANamedCurveTable;
import com.fzm.eddsa.spec.EdDSAParameterSpec;
import com.fzm.eddsa.spec.EdDSAPrivateKeySpec;
import com.fzm.http.HttpRequest;
import com.google.protobuf.ByteString;


/**
 * Created by zhengfan on 2017/7/17.
 * Explain
 */
public class ProtobufUtils {
	public static HttpRequest httpRequest = new HttpRequest();
    /**
     * 
    * @Title: requestUserCreate
    * @Description: TODO 前端用户注册 --老合约
    * @param @param operatorPrivateKey
    * @param @param pubkey
    * @param @param userUid
    * @param @param userPubkey
    * @param @param info
    * @param @param type
    * @param @return
    * @return ProtobufBean
    * @author: Ma Amin
    * @date: 2017-9-11 下午4:50:13
     */
   /* public static ProtobufBean requestUserCreate(String operatorPrivateKey, String pubkey,
                                                 String userUid, String userPubkey
            , String info,String type) {

        ProtobufBean protobufBean = new ProtobufBean();
        //创建requestCreateAccount对象，设置参数
        Api.RequestUserCreate.Builder builder = Api.RequestUserCreate.newBuilder();
        try {
            builder.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(pubkey)));
            builder.setUserUid(userUid);
            builder.setUserPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
            SecureRandom secureRandom = new SecureRandom();
            long randomnumber = secureRandom.nextLong();
            protobufBean.setInstructionId(randomnumber);
            builder.setInstructionId(protobufBean.getInstructionId());
            builder.setActionId(Api.MessageType.MsgUserCreate);
            builder.setType(Api.UserType.U_INVESTOR);
            if("1".equals(type)){
            	builder.setType(Api.UserType.U_BORROWER);
            }
            //request携带请求信息
            final Api.WriteRequest.Builder request = Api.WriteRequest.newBuilder();
            request.setUserCreate(builder);
            byte[] bytePrivateKey = HexUtil.hexString2Bytes(operatorPrivateKey);
            //将requestCreateAccount对象签名
            Api.RequestUserCreate requestUserCreate = builder.build();
            //签名啊
            ByteString signByteString = getSign(requestUserCreate.toByteArray(), bytePrivateKey);
            //签名后的结果也给request
            request.setSign(signByteString);
            //将携带着签名后的参数信息通过http给合约，在callback里解析
            Api.WriteRequest request1 = request.build();
            byte[] requestByte = request1.toByteArray();
            String signdata = HexUtil.bytes2HexString(requestByte);
            //8月20日被maamin注释protobufBean.setSignature(signdata);
            protobufBean.setSignature(signdata);
            //8月20日被maamin添加
        } catch (Exception e) {
            e.printStackTrace();
        }
        return protobufBean;
    }*/
	/**
	 * 
	* @Title: requestUserCreate
	* @Description: TODO 用户注册--新合约
	* @param @param userUid---用户在系统中唯一表示符
	* @param @param userPubkey---用户和一个公钥绑定
	* @param @param type---用户类型
	* @param @return
	* @return ProtobufBean
	* @author: Ma Amin
	* @date: 2017-9-11 下午4:56:08
	 */
    public static ProtobufBean requestUserCreate(String userUid, String userPubkey
            ,String type,String privateKey) {
//    	String operatorPrivateKey = "90b289fda1fb0439158f837bbe60cc1ec99616dd0bc6335d6fd0bf3d22888e20";
        ProtobufBean protobufBean = new ProtobufBean();
        //创建 RequestUserCreate 对象，设置参数
        Api.RequestUserCreate.Builder builder = Api.RequestUserCreate.newBuilder();
        try {
        	builder.setUserUid(userUid);
        	builder.setUserPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
        	if("1".equals(type)){
        		builder.setType(UserType.U_BORROWER);
        	}
        	else{	//等于2
        		builder.setType(UserType.U_INVESTOR);
        	}
        	SecureRandom secureRandom = new SecureRandom();
            long instructionId = secureRandom.nextLong();	//随机数
            protobufBean.setInstructionId(instructionId);
            //将requestCreateAccount对象签名
            Api.RequestUserCreate requestBuyServe = builder.build();
            //request携带请求信息
            Api.Request.Builder request = Api.Request.newBuilder();
            request.setUserCreate(builder);
            request.setInstructionId(instructionId);
            request.setUid(userUid);
            request.setActionId(Api.MessageType.MsgUserCreate);
            request.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
            Api.Request request1 = request.build();
            //用操作者的私钥签名
            byte[] bytePrivateKey = HexUtil.hexString2Bytes(privateKey);
            //签名啊
            ByteString signByteString = getSign(request1.toByteArray(), bytePrivateKey);
            //签名后的结果也给request
            request.setSign(signByteString);
            //将携带着签名后的参数信息通过http给合约，在callback里解析
            Api.Request request2 = request.build();
            byte[] requestByte = request2.toByteArray();
           // String signdata = HexUtil.bytes2HexString(requestByte);
            String signdateBase64 = encodeBase64(requestByte);
            protobufBean.setSignature(signdateBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return protobufBean;
    }

    
/*public static ProtobufBean RequestWithdraw(String operatorPrivateKey,String operatorpublicKey,String userUid, String userPubkey, String rMoney) {

ProtobufBean protobufBean = new ProtobufBean();

//创建requestCreateAccount对象，设置参数
Api.RequestWithdraw.Builder builder = Api.RequestWithdraw.newBuilder();
try {
	builder.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(operatorpublicKey)));
	builder.setUserPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
builder.setUserUid(userUid);
builder.setRmb(Math.round(Double.parseDouble(rMoney)));

//builder.setInfo(ByteString.copyFrom(HexUtil.hexString2Bytes(HexUtil.string2HexString(info))));
//builder.setInfo(ByteString.copyFrom(info.getBytes()));\
SecureRandom secureRandom = new SecureRandom();
long randomnumber = secureRandom.nextLong();

protobufBean.setInstructionId(randomnumber);
builder.setInstructionId(protobufBean.getInstructionId());

builder.setActionId(Api.MessageType.MsgWithdraw);

//builder.setType(Api.UserType.U_BORROWER);
//request携带请求信息
final Api.WriteRequest.Builder request = Api.WriteRequest.newBuilder();
request.setWithdraw(builder);
//request.setUserCreate(builder);
byte[] bytePrivateKey = HexUtil.hexString2Bytes(operatorPrivateKey);

//将requestCreateAccount对象签名
Api.RequestWithdraw requestWithdraw = builder.build();
//签名啊
ByteString signByteString = getSign(requestWithdraw.toByteArray(), bytePrivateKey);

//签名后的结果也给request
request.setSign(signByteString);

//将携带着签名后的参数信息通过http给合约，在callback里解析
Api.WriteRequest request1 = request.build();
byte[] requestByte = request1.toByteArray();
String signdata = HexUtil.bytes2HexString(requestByte);
protobufBean.setSignature(signdata);

} catch (Exception e) {
e.printStackTrace();
}
return protobufBean;

}


public static ProtobufBean RequestRecharge(String operatorPrivateKey,String operatorpublicKey,String userUid, String userPubkey, String rMoney) {

ProtobufBean protobufBean = new ProtobufBean();

//创建requestCreateAccount对象，设置参数
Api.RequestDeposit.Builder builder = Api.RequestDeposit.newBuilder();
try {
	builder.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(operatorpublicKey)));
	//builder.setUserPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
	builder.setUserUid(userUid);
//	builder.setRmb(Math.round(Double.parseDouble(rMoney)));
	builder.setRmb((long) (Double.parseDouble(rMoney)*100));

//builder.setInfo(ByteString.copyFrom(HexUtil.hexString2Bytes(HexUtil.string2HexString(info))));
//builder.setInfo(ByteString.copyFrom(info.getBytes()));\
	SecureRandom secureRandom = new SecureRandom();
	long randomnumber = secureRandom.nextLong();

	protobufBean.setInstructionId(randomnumber);
	builder.setInstructionId(protobufBean.getInstructionId());

	builder.setActionId(Api.MessageType.MsgDeposit);

//builder.setType(Api.UserType.U_BORROWER);
//request携带请求信息
final Api.WriteRequest.Builder request = Api.WriteRequest.newBuilder();
request.setDeposit(builder);
//request.setUserCreate(builder);
byte[] bytePrivateKey = HexUtil.hexString2Bytes(operatorPrivateKey);

//将requestCreateAccount对象签名
Api.RequestDeposit requestDeposit = builder.build();
//签名啊
ByteString signByteString = getSign(requestDeposit.toByteArray(), bytePrivateKey);

//签名后的结果也给request
request.setSign(signByteString);

//将携带着签名后的参数信息通过http给合约，在callback里解析
Api.WriteRequest request1 = request.build();
byte[] requestByte = request1.toByteArray();
String signdata = HexUtil.bytes2HexString(requestByte);
protobufBean.setSignature(signdata);

} catch (Exception e) {
e.printStackTrace();
}
return protobufBean;

}
    
    *//**
     * RequestInitPlatform
     * @param operatorPrivateKey
     * @param pubkey
     * @param platformPubkey
     * @param info
     * @return
     *//*
    public static ProtobufBean requestInitPlatform(String operatorPrivateKey, String pubkey,String platformPubkey,
                                            String info) {

        ProtobufBean protobufBean = new ProtobufBean();

        //创建requestCreateAccount对象，设置参数
        Api.RequestInitPlatform.Builder builder = Api.RequestInitPlatform.newBuilder();
        try {
            builder.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(pubkey)));
          //  builder.setPlatformPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(platformPubkey)));
         //   builder.setInfo(ByteString.copyFrom(HexUtil.hexString2Bytes(HexUtil.string2HexString(info))));

            protobufBean.setInstructionId(protobufBean.getInstructionId());
            builder.setInstructionId(protobufBean.getInstructionId());
            builder.setActionId(Api.MessageType.MsgInitPlatform);

            //request携带请求信息
            final Api.WriteRequest.Builder request = Api.WriteRequest.newBuilder();
            request.setInitPlatform(builder);
            byte[] bytePrivateKey = HexUtil.hexString2Bytes(operatorPrivateKey);

            //将requestCreateAccount对象签名
            Api.RequestInitPlatform requestInitPlatform = builder.build();
            //签名啊
            ByteString signByteString = getSign(requestInitPlatform.toByteArray(), bytePrivateKey);

            //签名后的结果也给request
            request.setSign(signByteString);

            //将携带着签名后的参数信息通过http给合约，在callback里解析
            Api.WriteRequest request1 = request.build();
            byte[] requestByte = request1.toByteArray();
            String signdata = HexUtil.bytes2HexString(requestByte);
            protobufBean.setSignature(signdata);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return protobufBean;
    }*/
    
    public static ProtobufBean RequestRecharge(String operatorPrivateKey,String operatorpublicKey,String userUid, String userPubkey, String rMoney) {

    	ProtobufBean protobufBean = new ProtobufBean();

    	//创建requestCreateAccount对象，设置参数
    	Api.RequestDeposit.Builder builder = Api.RequestDeposit.newBuilder();
    	try {
//    		builder.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(operatorpublicKey)));
//    		builder.setUserPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
    		builder.setUserUid(userUid);
//    		builder.setRmb(Math.round(Double.parseDouble(rMoney)));
    		builder.setRmb((long) (Double.parseDouble(rMoney)*100));
    		
    	//builder.setInfo(ByteString.copyFrom(HexUtil.hexString2Bytes(HexUtil.string2HexString(info))));
    	//builder.setInfo(ByteString.copyFrom(info.getBytes()));\
    		SecureRandom secureRandom = new SecureRandom();
    		long randomnumber = secureRandom.nextLong();

    		protobufBean.setInstructionId(randomnumber);
//    		builder.setInstructionId(protobufBean.getInstructionId());

//    		builder.setActionId(Api.MessageType.MsgDeposit);
    		
    	

    	Api.Request.Builder request = Api.Request.newBuilder();
//        request.setUserCreate(builder);
    	request.setDeposit(builder);
        request.setInstructionId(randomnumber);
        request.setActionId(Api.MessageType.MsgDeposit);
//        request.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(userPubkey)));
        request.setPubkey(ByteString.copyFrom(HexUtil.hexString2Bytes(operatorpublicKey)));
        Api.Request request1 = request.build();
        //用操作者的私钥签名
        byte[] bytePrivateKey = HexUtil.hexString2Bytes(operatorPrivateKey);
        //签名啊
        ByteString signByteString = getSign(request1.toByteArray(), bytePrivateKey);
        //签名后的结果也给request
        request.setSign(signByteString);
        //将携带着签名后的参数信息通过http给合约，在callback里解析
        Api.Request request2 = request.build();
        byte[] requestByte = request2.toByteArray();
       // String signdata = HexUtil.bytes2HexString(requestByte);
        String signdateBase64 = encodeBase64(requestByte);
        protobufBean.setSignature(signdateBase64);

    	} catch (Exception e) {
    	e.printStackTrace();
    	}
    	return protobufBean;

    	}
    
    private static ByteString getSign(byte[] requestByte, byte[] bytePrivateKey) {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("Ed25519");
        byte[] signbyte = new byte[0];
        try {
            Signature sgr = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
            EdDSAPrivateKeySpec privKey = new EdDSAPrivateKeySpec(bytePrivateKey, spec);
            PrivateKey sKey = new EdDSAPrivateKey(privKey);
            sgr.initSign(sKey);
            sgr.update(requestByte);
            signbyte = sgr.sign();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return ByteString.copyFrom(signbyte,0,64);
    }
    public static String encodeBase64(byte[]input) throws Exception{  
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
        Method mainMethod= clazz.getMethod("encode", byte[].class);  
        mainMethod.setAccessible(true);  
         Object retObj=mainMethod.invoke(null, new Object[]{input});  
         return (String)retObj;  
    }  
    
    
    

}

