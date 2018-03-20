package com.fzm.tools;

import java.io.FileNotFoundException;
import java.util.Properties;

public class Te {
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
//		Properties p=new ConfigReader().read();
//		System.out.println(p.get("blockchainurl"));
		new Te().test();
	}
	public void test(){
//		System.out.println(System.getProperty("java.class.path"));
		System.out.println(Te.class.getResource(""));
		System.out.println(Te.class.getResource("/"));//根路径
		System.out.println(Te.class.getResource("/config.properties"));
		System.out.println(Te.class.getResource("/config/config.properties"));
		System.out.println(Te.class.getClassLoader().getResource("config/config.properties"));
		System.out.println(Te.class.getClassLoader().getResource("config.properties"));
		System.out.println(Te.class.getClassLoader().getResource("/config/config.properties"));
		System.out.println(Te.class.getClassLoader().getResource(""));//根路径
		System.out.println(Te.class.getClassLoader().getResource("/"));
		
		
	}
}
