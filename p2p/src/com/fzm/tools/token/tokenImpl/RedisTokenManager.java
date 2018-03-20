/**
 * 
 */
package com.fzm.tools.token.tokenImpl;

import java.util.UUID;
import org.springframework.stereotype.Component;

import com.fzm.tools.token.tokenInterface.TokenManager;



/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：RedisTokenManager   
 * 类描述： 通过redis存储和验证token的实现类  
 * 创建人：maamin   
 * 创建时间：2017-8-1 下午3:09:22   
 * 修改人：maamin   
 * 修改时间：2017-8-1 下午3:09:22   
 * 修改备注：   @component （把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
 * @version    
 *    
 */
@Component
public class RedisTokenManager implements TokenManager{
	/* (non-Javadoc)
	 * @see com.fuzamei.token.interfac.TokenManager#createToken(long)
	 */
	@Override
	public String createToken(int userId) {
		System.out.println("生成token中……");
		//使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        return token;
	}

	/* (non-Javadoc)
	 * @see com.fuzamei.token.interfac.TokenManager#checkToken(com.fuzamei.token.model.TokenModel)
	 */
	@Override
	public boolean checkToken(String token) {
		/*if (model == null) {
            return false;
        }
		//加了个string强制转换
        String token = (String) redis.boundValueOps(model.getUserId()).get();
		//拿到token
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(model.getUserId()).expire(model.getUserId(), TimeUnit.HOURS);
		return true;*/
		return false;
	}

	/* (non-Javadoc)
	 * @see com.fuzamei.token.interfac.TokenManager#getToken(java.lang.String)
	 */
	@Override
	public String  getToken(String authentication) {
		if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
//        return new TokenModel(userId, token);
        return token;
	}

	/* (non-Javadoc)
	 * @see com.fuzamei.token.interfac.TokenManager#deleteToken(long)
	 */
	@Override
	public void deleteToken(int userId) {
		
	}
}
