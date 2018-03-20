package com.fzm.daointerface;

import java.util.List;

import java.util.Map;

/**
 * FileName: recordIDao.java
 * @Description :TODO
 * @author 创建人 王斌
 * @date 创建时间 2017-8-10下午7:20:33
 * @version v1.0
 *
 * Modification  History:
 * Date              Author           Version        
 * -------------------------------------------------
 * 2017-8-10           王斌                                          @version          
 *
 * Why & What is modified:
 */
public interface RecordIDao {
	/**
	 * 查询充值记录
	 * */
	List<Map<String, Object>> selRecord(int uid,int startPageIndex,int rows);
	
	/**
	 * 查询提款记录
	 * */
	List<Map<String, Object>> selURecord(int uid,int startPageIndex,int rows);
	
	/**
	 * 充值记录总条数
	 * */
	int intCount(int uid);
	
	/**
	 * 提现记录总条数
	 * */
	int intCounts(int uid);
}
