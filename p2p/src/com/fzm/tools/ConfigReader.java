package com.fzm.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	public Properties read() throws FileNotFoundException {
		InputStream inputStream = this.getClass().getResourceAsStream("/config/P2Pconfig.properties");
//		InputStream inputStream= new FileInputStream(new File(this.getClass().getClassLoader().getResource("config.properties").toString()));
//		System.out.println(this.getClass().getClassLoader().getResource("config.properties"));
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p;

	}
}
