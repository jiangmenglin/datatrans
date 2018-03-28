package com.hanke.clobrw.main;

import java.io.IOException;
import java.util.Properties;

import com.hanke.clobrw.translate.DefaultTranslate;
import com.hanke.clobrw.translate.Translatable;
import com.hanke.clobrw.util.XMLUtil;

public class Application {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("param.properties"));
			Translatable translate = new DefaultTranslate(
					XMLUtil.getListMapHolder(prop.getProperty("action")));
			translate.translate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
