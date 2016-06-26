package hu.Ktable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class HttpUtils { 
	public static String getData(String url){
		String myString = null;
		try {
			/* 定义获取文件内容的URL */
			URL myURL = new URL(url);
			/* 打开URL链接 */
			URLConnection ucon = myURL.openConnection();

			/* 使用InputStreams，从URLConnection读取数据 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/* 用ByteArrayBuffer做缓存 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* 将缓存的内容转化为String， 用UTF-8编码 */
			myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
			// myString = new String(baf.toByteArray());
			
		} catch (Exception e) {
			/* 捕获异常 */
			e.printStackTrace();
		}
		return myString;
		
	}
	
	
    /*public static String getData(String url) throws Exception {
        StringBuilder sb = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            InputStream instream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    instream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
        return null;
    } */
}