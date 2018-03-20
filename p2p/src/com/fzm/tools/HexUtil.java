package com.fzm.tools;

import java.util.UUID;


/**
 * Created by zhengfan on 2017/6/23.
 * Explain  16进制，字符串等转换
 */
public class HexUtil {

    /**
     * @param b 字节数组
     * @return 16进制字符串
     * @throws
     * @Description:字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    //16进制字符串转字符串
    public static String hexToString(String strPart) {
        byte[] baKeyword = new byte[strPart.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(strPart.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            strPart = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return strPart;
    }


    /**
     * @param src 16进制字符串
     * @return 字节数组
     * @throws
     * @Description:16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }


    /**
     * @param strPart 字符串
     * @return 16进制字符串
     * @throws
     * @Description:字符串转16进制字符串
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();

    }

    /**
     * @param src 16进制字符串
     * @return 字节数组
     * @throws
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     */
    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
                    16).byteValue();
        }
        return temp;
    }

    /**
     * @param src
     * @return
     * @throws
     * @Title:char2Byte
     * @Description:字符转成字节数据char-->integer-->byte
     */
    public static Byte char2Byte(Character src) {
        return Integer.valueOf((int) src).byteValue();
    }

    /**
     * @param a   转化数据
     * @param len 占用字节数
     * @return
     * @throws
     * @Title:intToHexString
     * @Description:10进制数字转成16进制
     */
    private static String intToHexString(int a, int len) {
        len <<= 1;
        String hexString = Integer.toHexString(a);
        int b = len - hexString.length();
        if (b > 0) {
            for (int i = 0; i < b; i++) {
                hexString = "0" + hexString;
            }
        }
        return hexString;
    }


    public static String bytes2string(byte[] bs) {
        char[] cs = new char[bs.length];
        for (int p = 0; p < bs.length; p++) {
            cs[p] = (char) (bs[p] & 0xFF);
        }
        return new String(cs);
    }
    
    public static void main(String [] args){
    	System.out.println(HexUtil.class.getName().concat(".java"));
    	System.out.println(UUID.randomUUID().toString());
    	System.out.println(new HexUtil().hexToString("706C6174666F726D206578697374"));
    }
}

