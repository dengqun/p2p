/**
 * 
 */
package com.fzm.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**   
 *    
 * 项目名称：p2p   
 * 类名称：RegxUtils   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-13 下午4:08:43   
 * 修改人：maamin   
 * 修改时间：2017-8-13 下午4:08:43   
 * 修改备注：   
 * @version    
 *    
 */
public class RegxUtils {
	/** 
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
     */  
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    } 
	/**
	 * 
	* @Title: isEmail
	* @Description: TODO(判断是否为邮箱)
	* @param @param email
	* @param @return	设定文件
	* @return boolean	返回类型
	* @throws
	 */
	public static boolean isEmail(String email){
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);    
		Matcher matcher = regex.matcher(email);    
		boolean isMatched = matcher.matches(); 
		return isMatched;
	}
	public static void main(String[] args) {
	}
	
}
