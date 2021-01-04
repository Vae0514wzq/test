package com.wzq.web.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.wzq.tomcat.core.ConstantInfo;
import com.wzq.tomcat.core.ParseWebXml;
import com.wzq.tomcat.util.StringUtil;

public class HttpServletResponse implements ServletResponse{
	private OutputStream os = null;
	private String basePath = ConstantInfo.BASE_PATH;
	
	public HttpServletResponse(OutputStream os) {
		this.os = os;
	}
	
	
	@Override
	public void sendRedirect(String url) {
		if(StringUtil.checkNull(url)) {
			error404(url);
			return;
		}
		
		File fl = new File(basePath , url);
		if(!fl.exists() || !fl.isFile()) {
			error404(url);
			return;
		}
		
		try {
			FileInputStream fis = new FileInputStream(fl);
			byte[] bt = new byte[fis.available()];
			fis.read(bt);
			
			String ext = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
			String contentType = ParseWebXml.getContentType(ext);
			if(StringUtil.checkNull(contentType)) {
				contentType = "text/html;charset=utf-8";
			}
			
			String reponseHeader = "HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nContent-Length: " + bt.length + "\r\n\r\n";
			os.write(reponseHeader.getBytes());
			os.write(bt);
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	private void error404(String url) {
		try {
			String data = "<h1>HTTP Status 404 - " + url + "</h1>";
			String reponseHeader = "HTTP/1.1 404 File Not Found\r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: " + data.length() + "\r\n\r\n";
			os.write(reponseHeader.getBytes());
			os.write(data.getBytes());
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		return null;
	}
	
}
