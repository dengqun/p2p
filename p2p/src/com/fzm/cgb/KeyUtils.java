package com.fzm.cgb;


import com.fzm.eddsa.EdDSAPrivateKey;
import com.fzm.eddsa.spec.EdDSANamedCurveTable;
import com.fzm.eddsa.spec.EdDSAParameterSpec;
import com.fzm.eddsa.spec.EdDSAPrivateKeySpec;
import com.fzm.service.BlockChainUtil;
import com.fzm.theromus.sha.Keccak;
import com.fzm.theromus.sha.Parameters;
import com.fzm.theromus.utils.HexUtils;

import java.util.UUID;

/**
 * Created by zhengfan on 2017/7/13.
 * Explain
 */
public class KeyUtils {

    /**
     * getprivatekey
     * @param password
     * @param random
     * @return
     */
    public static String getPrivateKey(String password, String random) {
        String tempString = "";
        try{
            String key = password + random;
            Keccak keccak = new Keccak();
            String s = HexUtils.getHex(key.getBytes());
            tempString = keccak.getHash(s, Parameters.KECCAK_256);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tempString;
    }

    /**
     * getpublickey
     * @param privateKey
     * @return
     */
    public static String getPublicKey(String privateKey) {
        String publickey = "";
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("Ed25519");
        try {
            EdDSAPrivateKeySpec privKey = new EdDSAPrivateKeySpec(HexUtil.hexString2Bytes(privateKey), spec);
            EdDSAPrivateKey sKey = new EdDSAPrivateKey(privKey);
            publickey = HexUtil.bytes2HexString(sKey.getAbyte()).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publickey;
    }

    /**
     * random to getprivatekey
     * @return
     */
    public static String getRandom() {
        String s = "";
        try{
            String str = UUID.randomUUID().toString().replace("-", "");
            s = str.substring(str.length() - 16);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
    public static void main(String[] args) {
    	String userUid = "1";
    	String type = "1";	//1--借款用用户，2--投资用户
    	String password = "123456";		//要注册用户的密码
		String random = "b3ec927eae2ee505";	//生成的随机数
		String privateKey = getPrivateKey(password, random);	//生成的私钥
		String userPubkey = getPublicKey(privateKey);			//生成的公钥
		System.out.println("随机数："+random);
		System.out.println("私钥："+privateKey);
		System.out.println("公钥："+userPubkey);
		ProtobufBean protobufBean = ProtobufUtils.requestUserCreate(userUid, userPubkey, type, privateKey);
		String result1 = BlockChainUtil.sendPostParam(protobufBean);
		System.out.println(result1);
	}


}
