package com.wzq.tomcat.core;

public interface ConstantInfo {
	String BASE_PATH = ReadConfig.getInstance().getProperty("path");
}
