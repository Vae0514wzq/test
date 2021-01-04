package com.wzq.tomcat.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartTomcat {
	public static void main(String[] args) {
		try {
			new StartTomcat().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() throws IOException{
		//解析读取配置文件web.xml
		int port = Integer.parseInt(ReadConfig.getInstance().getProperty("port"));
		
		//启用一个ServerSocket
		
		ServerSocket ssk = new ServerSocket(port);
		System.out.println("服务器已启动，占用端口：" + port);
		
		new ParseWebXml();
		
		//启动一个线程或使用线程池处理用户发过来的请求 -> socket
		ExecutorService serviceThread = Executors.newFixedThreadPool(20);
		
		Socket sk = null;
		while(true) {
			sk = ssk.accept();
			serviceThread.submit(new ServerService(sk));
		}
	}
}
