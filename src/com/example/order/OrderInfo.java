package com.example.order;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class OrderInfo {

	public static String PATH ="http://211.68.37.131/note/holdretrieve.jsp?kind=query";
	private static URL url;

	

	static {
		try {
			url = new URL(PATH);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String sendPostMessage(Map<String, String> params, String encode)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				try {
					buffer.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(), encode))
							.append("&");

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 删除掉最后一个&
			buffer.deleteCharAt(buffer.length() - 1);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setConnectTimeout(30000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			byte[] mydata = buffer.toString().getBytes();
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length",
					String.valueOf(mydata.length));
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(mydata);
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				return changeInputStream(urlConnection.getInputStream(), encode);

			}
		}
		return "错误";
	}

	private static  String changeInputStream(InputStream inputStream, String encode) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return result;
	}
	
	public static List<Map<String,String>> getNotice(String htmlStr){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		int a = 0;
		while((a=htmlStr.indexOf("<TR", a))!=-1){
			Map<String,String> map = new HashMap<String,String>();
			int b;
			a = htmlStr.indexOf("<td>", a)+4;
			b = htmlStr.indexOf("&nbsp;",a);
			map.put("dztm", "读者条码："+htmlStr.substring(a, b));
			
			a = htmlStr.indexOf("<td>", b)+4;
			b = htmlStr.indexOf("&nbsp;",a);
			map.put("dzxm", "读者姓名："+htmlStr.substring(a, b));
			
			a = htmlStr.indexOf("<td>", b)+4;
			b = htmlStr.indexOf("&nbsp;",a);
			map.put("tstitle", "图书题名："+htmlStr.substring(a, b));
			
			a = htmlStr.indexOf("<td>", b)+4;
			b = htmlStr.indexOf("&nbsp;",a);
			map.put("tstm", "图书条码："+htmlStr.substring(a, b));
			
			a = htmlStr.indexOf("<td>", b)+4;
			a = htmlStr.indexOf("<td>", a)+4;
			b = htmlStr.indexOf("&nbsp;",a);
			map.put("qsdd", "取书地点："+htmlStr.substring(a, b));
			
			a = htmlStr.indexOf("<td>", b)+4;
			b = htmlStr.indexOf("&nbsp;",a);
			map.put("jzrq", "截止日期："+htmlStr.substring(a, b));
			list.add(map);
		}
	
		return list;
		
	}
	
	

	


}