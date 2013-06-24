package com.example.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class borrowBook {

	public static List<Map<String, String>> borrowBookList = new ArrayList<Map<String, String>>();
	private static String PATH = "http://211.68.37.131/book/detailBook.jsp?rec_ctrl_id=";

	public String getResult(String id) throws IOException {
		String result = null;
		try {
			URL url = new URL(PATH + id);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setReadTimeout(4000);
			urlConnection.setRequestMethod("GET");
			if (urlConnection.getResponseCode() == 200) {
				result = readData(urlConnection.getInputStream());
				return result;
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		return result;
	}

	public void startBorrowBook(String id) {
		try {
			getDetail(getResult(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String readData(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		// byte[] data = request.getParameter("title").getBytes("ISO-8859-1");
		byte[] data = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(data)) != -1) {
			buffer.write(data, 0, len);
		}
		return new String(buffer.toByteArray(), "GB2312");

	}

	public void getDetail(String str) {

		borrowBookList = new ArrayList<Map<String, String>>();
		int a = str.indexOf("97%\">") + 5;
		a = str.indexOf("97%\">", a) + 5;
		a = str.indexOf("97%\">", a) + 5;
		a = str.indexOf("97%\">", a) + 5;
		int b = str.indexOf("<table", a);
		String detailStr = str.substring(a, b);

		int beginIndex = 0;
		int times = 1;
		while ((beginIndex = detailStr.indexOf("<tr", beginIndex)) != -1) {
			Map<String, String> borrow = new HashMap<String, String>();
			beginIndex += 3;
			if (times % 2 == 0) {
				a = detailStr.indexOf("<td>", beginIndex) + 4;
				a = detailStr.indexOf(">", a) + 1;
				b = detailStr.indexOf("<", a);
				String TstmString = detailStr.substring(a, b);// 图书条码

				a = detailStr.indexOf("<td>", b) + 4;
				a = detailStr.indexOf(">", a) + 1;
				b = detailStr.indexOf("<", a);
				String SshString = detailStr.substring(a, b);// 索书号

				a = detailStr.indexOf("<td>", b) + 4;
				a = detailStr.indexOf("<td>", a) + 4;
				a = detailStr.indexOf("<td>", a) + 4;
				a = detailStr.indexOf(">", a) + 1;
				b = detailStr.indexOf("<", a);
				String CsbmString = detailStr.substring(a, b);// 藏书部门

				a = detailStr.indexOf("<td>", b) + 4;
				a = detailStr.indexOf(">", a) + 1;
				b = detailStr.indexOf("<", a);
				String LtztString = detailStr.substring(a, b);// 流通状态

				borrow.put("Tstm", TstmString);
				borrow.put("Ssh", SshString);
				borrow.put("Csbm", CsbmString);
				borrow.put("Ltzt", LtztString);
				borrowBookList.add(borrow);

			}

			times++;
		}
	}
}
