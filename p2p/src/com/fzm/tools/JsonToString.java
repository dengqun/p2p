package com.fzm.tools;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * FileName: jsonToString.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-13下午3:14:56
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-13           王斌                                          @version          
 *
 * Why & What is modified:
 */
@Service("json")
public class JsonToString {

	public Map<String,Object> jsonToMap(String data){
		return (Map<String,Object>)JSON.parse(data);
	}
}
