/**
 * 
 */
package com.fzm.tools.token.tokenInterface;



/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：TokenManager   
 * 类描述：   对token操作的接口
 * 创建人：maamin   
 * 创建时间：2017-8-1 下午2:58:46   
 * 修改人：maamin   
 * 修改时间：2017-8-1 下午2:58:46   
 * 修改备注：   
 * @version    
 *    
 */
public interface TokenManager {
	 //创建一个token关联指定用户
	public String createToken(int userId);
	//检查token是否有效
	public boolean checkToken(String token);
	//从加密字符串解析token，authentication加密后的字符串
	public String getToken(String authentication);
	//根据登录的用户id清楚token
	public void deleteToken(int userId);
	
}
