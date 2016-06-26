package hu.Ktable;

import static hu.Ktable.MyConfig.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class online extends Activity {
	private Button download;
	private EditText stunum;
	private String result, url;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online);
		download = (Button) findViewById(R.id.download);
		stunum = (EditText) findViewById(R.id.stunum);
		download.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isConn(getApplicationContext()) == false) {
					setNetworkMethod(online.this);
				} else {
					String num = new String();
					num = stunum.getText().toString();
					url = "http://219.153.62.70/cskebiao/?num=" + num;
					try {
						Runnable downloadRun = new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								result = new String();
								result = getData(url);

								if (null != result) {
									jsonAction(result);
									combination();
									change = true;
								} else {
									Toast.makeText(online.this, "get nothing",
											Toast.LENGTH_LONG).show();
								}
							}
						};
						new Thread(downloadRun).start();

					} catch (Exception e) { // 捕获异常
						Toast.makeText(online.this, e.getMessage(),
								Toast.LENGTH_LONG).show();
						Log.e("e", e.toString());
					}
				}
				// jsonAction("{\"data\":[[\"\",\"非,林云,3304,必修,1-17周,,选课状态:正常,\",\"\",\"\",\"可编程逻辑器件及应用,刘科征 ,4209,限选,1-13周,,选课状态:正常,\",\"\"],[\"通信原理,雷宏江,4410,必修,1-15周,,选课状态:正常,\",\"\",\"科技英语阅读,张刚,3108,限选,1-17周,,选课状态:正常,\",\"\",\"智能手机应用开发,刘兆宏 ,3205,任选,1-10周,,选课状态:正常,\",\"\"],[\"\",\"电磁场与电磁波,王熠,4408,必修,1-15周,,选课状态:正常,\",\"电信传输原理,鲍宁海 ,3508,必修,1-13周,连上三节,选课状态:正常,\",\"\",\"\",\"\"],[\"非线性电子电路,林云,3304,必修,1-17周,,选课状态:正常,\",\"\",\"通信原理,雷宏江,4410,必修,1-15周,,选课状态:正常,\",\"\",\"毛泽东思想和中国特色社会主义理论体系概论(上),毛远芳,3210,必修,1-17周,,选课状态:正常,\",\"\"],[\"\",\"电磁场与电磁波,王熠,4408,必修,1-15周,,选课状态:正常,\",\"\",\"\",\"\",\"\"],[\"\",\"\",\"\",\"\",\"\",\"\"],[\"\",\"\",\"\",\"\",\"\",\"\"]]}");

			}
		});
	}

	public static boolean isConn(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = conManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		if (mobile == State.CONNECTED) {
			bisConnFlag = true;
		}
		return bisConnFlag;
	}

	/*
	 * 打开设置网络界面
	 */
	public static void setNetworkMethod(final Context context) {
		// 提示对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("网络设置提示")
				.setMessage("网络连接不可用,是否进行设置?")
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = null;
						// 判断手机系统的版本 即API大于10 就是3.0或以上版本
						if (android.os.Build.VERSION.SDK_INT > 10) {
							intent = new Intent(
									android.provider.Settings.ACTION_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName component = new ComponentName(
									"com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(component);
							intent.setAction("android.intent.action.VIEW");
						}
						context.startActivity(intent);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
	}

	public static void progressBar(final Context context) {
		// 提示对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("正在下载，请等待")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
	}

	/*
	 * public String httpPost(String url, String num) {
	 * HttpEntityEnclosingRequestBase httprequest = new HttpPost(url);
	 * List<NameValuePair> param = new ArrayList<NameValuePair>(); String
	 * strResult = ""; param.add(new BasicNameValuePair("num", num)); try {
	 * httprequest.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
	 * HttpResponse httpResponse = new DefaultHttpClient()
	 * .execute(httprequest); if (httpResponse.getStatusLine().getStatusCode()
	 * == 200) { strResult = EntityUtils.toString(httpResponse.getEntity()); }
	 * else { Toast.makeText( this,
	 * "Download Fail!Please check your network is connected.", 3000).show(); }
	 * } catch (Exception e) { Toast.makeText(this, "Network is not connected.",
	 * 3000).show(); } return strResult; }
	 */
	public String getData(String url) {
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
			Toast.makeText(this, e.toString(), 3000).show();
		}
		return myString;

	}

	public void jsonAction(String strResult) {
		try {
			JSONArray jsonArray = new JSONObject(strResult)
					.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {// 循环:每天
				JSONArray day = ((JSONArray) jsonArray.opt(i));
				for (int j = 0; j < day.length(); j++) {
					String course = day.opt(j).toString();
					if (course.length() >= 10) {
						String[] courseDetail = course.split(",");
						ke[i][j].data = new String[8];
						for (int k = 0; k < BOX_NUMBER-1; k++) {
							ke[i][j].data[k] = courseDetail[k];
						}
						ke[i][j].data[7] = "";
					} else {
						ke[i][j].data = new String[8];
						for (int k = 0; k < BOX_NUMBER; k++) {
							ke[i][j].data[k] = "";
						}
					}
				}
			}
		} catch (JSONException e) {
			Log.v("Error", "Json解析错误");
		}
	}

	private void combination() {
		int i, j, k;
		String[] something = new String[8];
		something[0] = "\n";
		something[1] = "\t";
		something[2] = "\t";
		something[3] = "\t";
		something[4] = "\n";
		something[5] = "\n";
		something[6] = "\t";
		something[7] = "";
		try {
			for (i = 0; i < 7; i++) {
				for (j = 0; j < 6; j++) {
					for (k = 0; k < BOX_NUMBER; k++) {
						if (ke[i][j].data == null) {
							StrKe[i][j] = "";
							break;
						} else {
							if (0 == k) {
								StrKe[i][j] = ke[i][j].data[k];
							} else {
								StrKe[i][j] += something[k - 1]
										+ ke[i][j].data[k];
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StrKe[0][0] = "读文件错误";
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_LONG);
		}
	}

}
