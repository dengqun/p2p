package com.fzm.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Description : 实现字符串和时间类型的装换
 * @author sdy
 * @date 2017-08-01
 * 
 */
public class ChangeDate {
	/**
	 * 
	 * @param date
	 *            时间字符串
	 * @return 时间类型数据
	 * @throws ParseException
	 */
	public static Date changeDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

	/**
	 * 
	 * @param date
	 *            时间类型数据
	 * @return 时间字符串
	 * @throws ParseException
	 */
	public static String changeString(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

}
