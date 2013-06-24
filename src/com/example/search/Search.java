package com.example.search;

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

public class Search {
	public static List<List<String>> bookinfor = new ArrayList<List<String>>();
	public static List<String> bookname = new ArrayList<String>();
	public static List<Map<String,String>> bookinformation= new ArrayList<Map<String,String>>();
	public static int pagesum;
	public static int pagenumber = 1;
	private String jiansuocistring = null;
	private String jiansuocileixingstring = null;
	private String pipeifangshiString = null;
	private String ziliaoleixingsString = null;
	private String fenguanmingchenString = null;
	private String pageString = "1";
	
	private static String path = "http://211.68.37.131/book/search.jsp";
	private static URL url;

	public Search(){
	}
	
	public Search(String jiansuocistring,String jiansuocileixingstring,String pipeifangshiString,String ziliaoleixingstring,String fenguanmingchenString,String pageString){
		this.jiansuocistring =jiansuocistring;
		this.jiansuocileixingstring = jiansuocileixingstring;
		this.pipeifangshiString = pipeifangshiString;
		this.ziliaoleixingsString = ziliaoleixingstring;
		this.fenguanmingchenString = fenguanmingchenString;
		this.pageString = pageString;
	}
	
	static {
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startsearch(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("word", jiansuocistring);
		params.put("type",jiansuocileixingstring);
		params.put("cmatch", pipeifangshiString);
		params.put("recordtype", ziliaoleixingsString);
		params.put("library_id", fenguanmingchenString);
		params.put("kind", "simple");
		params.put("searchtimes", "1");
		params.put("size", "10");
		params.put("curpage", pageString);
		params.put("orderby", "title");
		params.put("ordersc", "asc");
		String result = null;
		try {
			result = sendPostMessage(params,"gb2312");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getBookList(getBookInfo(result));
			getNumberAndPageNum(result);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		
	}
	
	private String sendPostMessage(Map<String, String> params,
			String encode) throws Exception {
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
			urlConnection.setConnectTimeout(3000);
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
				return changeInputStream(urlConnection.getInputStream(),encode);
			}
		}
		return "";
	}
	
	private String changeInputStream(InputStream inputStream,String encode) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(),encode);
			} catch (Exception e) {
			}
		}
		return result;
	}

	public String getBookInfo(String str) {
		int indexa = str.indexOf("cellspacing=1>");
		indexa = str.indexOf("</tr>",indexa)+5;
		int indexb = str.indexOf("</table>", indexa);
		String bookInfoStr = str.substring(indexa, indexb);
		return bookInfoStr;
	}
	
	public void getBookList(String bookInfoStr) {
		int beginIndex = 0;
		bookinformation = new ArrayList<Map<String,String>>();
		while((beginIndex=bookInfoStr.indexOf("<tr", beginIndex))!=-1){
			Map<String,String> book = new HashMap<String, String>();
			beginIndex += 3;												//beginIndex += "<tr".length();
			int a = bookInfoStr.indexOf("','", beginIndex)+3;
			a = bookInfoStr.indexOf("','", a)+3;
			int b = bookInfoStr.indexOf("'", a);
			
			List<String> childitem = new ArrayList<String>();
			String booknameString = null;
			String bookidString = null;;
			String bookauthorString = null;
			String bookpublisherString =null;
			String booknumberString = null;
			String bookyearString = null;
			
			
			bookidString = bookInfoStr.substring(a, b);
			
			a = bookInfoStr.indexOf("> ",b)+2;
			b = bookInfoStr.indexOf("<",a);
			booknameString = bookInfoStr.substring(a, b);
			
			a = bookInfoStr.indexOf("<td>",b)+4;
			b = bookInfoStr.indexOf("&",a);
			bookauthorString = bookInfoStr.substring(a, b);
			
			a = bookInfoStr.indexOf("<td>",b)+4;
			b = bookInfoStr.indexOf("&",a);
			bookpublisherString = bookInfoStr.substring(a, b);
			
			a = bookInfoStr.indexOf("<td>",b)+4;
			b = bookInfoStr.indexOf("&",a);
			booknumberString = bookInfoStr.substring(a, b);
			
			a = bookInfoStr.indexOf("<td>",b)+4;
			b = bookInfoStr.indexOf("&",a);
			bookyearString = bookInfoStr.substring(a, b);
			
			childitem.add(bookidString);
			childitem.add(booknameString);
			childitem.add(bookauthorString);
			childitem.add(bookpublisherString);
			childitem.add(booknumberString);
			childitem.add(bookyearString);
			
			book.put("name",booknameString);
			book.put("id",bookidString);
			book.put("author","作者："+bookauthorString);
			book.put("pulisher","出版社："+bookpublisherString);
			book.put("year","出版日期："+bookyearString);
			
			bookinformation.add(book);
			bookname.add(booknameString);
			
			bookinfor.add(childitem);
		}
	}
	
	public static void getNumberAndPageNum(String result) {
		int a = result.indexOf("opac_red\">");
		int b = result.lastIndexOf("opac_red\"> <b>");
		b = result.indexOf("</b>",b);
		String str = result.substring(a + "opac_red\">".length(), b);
		int num = Integer.parseInt(str.substring(0, str.indexOf("</span>")));
		
		int pageNum = Integer
				.parseInt(str.substring(str.lastIndexOf("<b>")
						+ 3, str.length()));
		pagesum = num;
		pagenumber = pageNum;
	}
}


