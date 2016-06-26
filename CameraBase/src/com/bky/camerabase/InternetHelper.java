package com.bky.camerabase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;

public class InternetHelper {
	
	public static final String SERVICE_PATH = "http://skyqqcloud.vicp.net/TopSafeService";
	
	public InternetHelper(MainActivity activity){
	}
	
	public static void sendData(String data){
		sendData(data, "upData.php");
	}
	
	public static void sendData(String data, String page){
		try {
			Jsoup.connect(SERVICE_PATH + "/" + page).data("data", MainActivity.EID + data).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("��������ʧ��");
			System.out.println(e);
		}
	}
	
	public static String sendPic(byte[] image){
		return sendPic("upFile.php", image);
	}
	
    public static String sendPic(String page, byte[] image){ 
    	String imgName = System.currentTimeMillis() + ".jpg";
    	String name = "file";
        String result="";
        String uploadUrl;
        String MULTIPART_FORM_DATA = "multipart/form-data";   
        String BOUNDARY = "---------7d4a6d158c9"; //���ݷָ���  
        if (!page.equals("")) {  
            uploadUrl = SERVICE_PATH + "/" + page;  
	        try {  
	            URL url = new URL(uploadUrl);    
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
	            conn.setDoInput(true);//��������    
	            conn.setDoOutput(true);//�������    
	            conn.setUseCaches(false);//��ʹ��Cache    
	            conn.setConnectTimeout(6000);// 6�������ӳ�ʱ  
	            conn.setReadTimeout(6000);// 6���Ӷ����ݳ�ʱ  
	            conn.setRequestMethod("POST");              
	            conn.setRequestProperty("Connection", "Keep-Alive");    
	            conn.setRequestProperty("Charset", "UTF-8");    
	            conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);  
	              
	            StringBuilder sb = new StringBuilder();    
	                
	            //�ϴ��ı��������֣���ʽ��ο�����
	            HashMap<String, Object> params = new HashMap<String, Object>();
	            params.put("EID", MainActivity.EID);
	            for (Map.Entry<String, Object> entry : params.entrySet()) {//�������ֶ�����    
	                sb.append("--");    
	                sb.append(BOUNDARY);    
	                sb.append("\r\n");    
	                sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");    
	                sb.append(entry.getValue());    
	                sb.append("\r\n");    
	            }    
	  
	            sb.append("--");    
	            sb.append(BOUNDARY);    
	            sb.append("\r\n");    
	  
	            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());  
	            dos.write(sb.toString().getBytes());  
	              
	            if (!imgName.equals("")&&!imgName.equals(null)) {  
	            	dos.writeBytes("Content-Disposition: form-data; name=\""+name+"\"; filename=\"" + imgName + "\"" + "\r\n"+"Content-Type: image/jpeg\r\n\r\n");  
	            	dos.write(image,0,image.length);  
	            	dos.writeBytes("\r\n");  
	            	dos.writeBytes("--" + BOUNDARY + "--\r\n");  
	                dos.flush();  
	                InputStream is = conn.getInputStream();
	                InputStreamReader isr = new InputStreamReader(is, "utf-8");  
	                BufferedReader br = new BufferedReader(isr);  
	                result = br.readLine();  
	           }   
	        }catch (Exception e) {
	        	System.out.println(e);
	        	return null;
	        }  
        }  
        return result;  
          
    }  
}
