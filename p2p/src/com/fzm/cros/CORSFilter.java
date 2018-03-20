/**
 * 
 */
package com.fzm.cros;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
/**   
 *    
 * 项目名称：cgb_p2p   
 * 类名称：CORSFilter   
 * 类描述：   
 * 创建人：maamin   
 * 创建时间：2017-8-10 下午5:44:53   
 * 修改人：maamin   
 * 修改时间：2017-8-10 下午5:44:53   
 * 修改备注：   
 * @version    
 *    
 */
public class CORSFilter implements Filter {
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    System.out.println("---------------------response头部加上一些信息，解决跨域访问问题-----------------");
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    chain.doFilter(req, res);
}
public void init(FilterConfig filterConfig) {}
public void destroy() {}
}
