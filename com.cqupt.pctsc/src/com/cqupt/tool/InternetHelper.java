package com.cqupt.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cqupt.data.Constant;

public class InternetHelper {

	public static final String LOGIN_PATH = Constant.PATH + "/login.php";
	public static final String PIC_AND_GPS_PATH = Constant.PATH
			+ "/getSource.php";
	public static final String FORM_NAME_USERNAME = "username";
	public static final String FORM_NAME_PASSWORD = "psw";

	/* ��½ ��ȡ����ҳ�� */
	public String login(String username, String password) throws IOException {
		Document document = Jsoup.connect(LOGIN_PATH).timeout(10000)
				.data(FORM_NAME_USERNAME, username)
				.data(FORM_NAME_PASSWORD, password).post();
		return document.body().text();
	}

	/* ��url��ȡͼƬ��Ϣ ��ʱ���ͼƬ��ַ��Ҫ�þ��������ͼƬ�ࣩ */
	public String getPicData(String EID, String md5) throws IOException {
		Document document = Jsoup.connect(PIC_AND_GPS_PATH).data("md5", md5)
				.data("EID", EID).data("info_type", "0").get();
		return document.body().text();
	}

	/* ��url��ȡ�ǣУ���Ϣ ��ʱ���������Ҫ�þ�������ɣ����ࣩ */
	public String getGPSData(String EID, String md5) throws IOException {
		Document document = Jsoup.connect(PIC_AND_GPS_PATH).data("md5", md5)
				.data("EID", EID).data("info_type", "1").get();
		return document.body().text();
	}

	/* ��url ��ȡһ��ͼƬ */
	public byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection httpURLconnection = (HttpURLConnection) url
				.openConnection();
		httpURLconnection.setRequestMethod("GET");
		httpURLconnection.setReadTimeout(6 * 1000);
		InputStream in = null;
		if (httpURLconnection.getResponseCode() == 200) {
			in = httpURLconnection.getInputStream();
			byte[] result = readStream(in);
			in.close();
			return result;
		}
		return null;
	}

	/* ��������ת�����ֽ����� */
	public byte[] readStream(InputStream in) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		return outputStream.toByteArray();
	}

}
