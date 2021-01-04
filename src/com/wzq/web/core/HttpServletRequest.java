package com.wzq.web.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzq.tomcat.util.StringUtil;

public class HttpServletRequest implements ServletRequest{
	private String method;
	private String url;
	private InputStream is;
	private BufferedReader reader;
	private Map<String, String> parameter = new HashMap<String, String>();
	private String protocalVersion;
	
	public HttpServletRequest(InputStream is) {
		this.is = is;
		parse();
	}
	
	
	@Override
	public void parse() {
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			List<String> headers = new ArrayList<String>();
			String line = null;
			while((line = reader.readLine()) != null && !"".equals(line)) {
				headers.add(line);
			}
			
			parseFirseLine(headers.get(0));
			
			parseParameter(headers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void parseParameter(List<String> headers) {
		
		
	}
	
	private void parseFirseLine(String str) {
		if(StringUtil.checkNull(str)) {
			return;
		}
		
		String[] arrs = str.split(" ");
		this.method = arrs[0];
		if(arrs[1].contains("?")) {
			this.url = arrs[1].substring(0, arrs[1].indexOf("?"));
		}else {
			this.url = arrs[1];
		}
		this.protocalVersion = arrs[2];
	}
	
	@Override
	public String getParameter(String key) {
		return this.parameter.getOrDefault(key, null);
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getUrl() {
		return this.url;
	}
	
	public String getProtocalVersion() {
		return protocalVersion;
	}

}
