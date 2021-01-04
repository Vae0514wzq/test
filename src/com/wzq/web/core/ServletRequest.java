package com.wzq.web.core;

public interface ServletRequest {
	public void parse();
	
	public String getParameter(String key);
	
	public String getMethod();
	
	public String getUrl();
}
