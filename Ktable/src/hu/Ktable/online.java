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

					} catch (Exception e) { // �����쳣
						Toast.makeText(online.this, e.getMessage(),
								Toast.LENGTH_LONG).show();
						Log.e("e", e.toString());
					}
				}
				// jsonAction("{\"data\":[[\"\",\"��,����,3304,����,1-17��,,ѡ��״̬:����,\",\"\",\"\",\"�ɱ���߼�������Ӧ��,������ ,4209,��ѡ,1-13��,,ѡ��״̬:����,\",\"\"],[\"ͨ��ԭ��,�׺꽭,4410,����,1-15��,,ѡ��״̬:����,\",\"\",\"�Ƽ�Ӣ���Ķ�,�Ÿ�,3108,��ѡ,1-17��,,ѡ��״̬:����,\",\"\",\"�����ֻ�Ӧ�ÿ���,���׺� ,3205,��ѡ,1-10��,,ѡ��״̬:����,\",\"\"],[\"\",\"��ų����Ų�,����,4408,����,1-15��,,ѡ��״̬:����,\",\"���Ŵ���ԭ��,������ ,3508,����,1-13��,��������,ѡ��״̬:����,\",\"\",\"\",\"\"],[\"�����Ե��ӵ�·,����,3304,����,1-17��,,ѡ��״̬:����,\",\"\",\"ͨ��ԭ��,�׺꽭,4410,����,1-15��,,ѡ��״̬:����,\",\"\",\"ë��˼����й���ɫ�������������ϵ����(��),ëԶ��,3210,����,1-17��,,ѡ��״̬:����,\",\"\"],[\"\",\"��ų����Ų�,����,4408,����,1-15��,,ѡ��״̬:����,\",\"\",\"\",\"\",\"\"],[\"\",\"\",\"\",\"\",\"\",\"\"],[\"\",\"\",\"\",\"\",\"\",\"\"]]}");

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
	 * �������������
	 */
	public static void setNetworkMethod(final Context context) {
		// ��ʾ�Ի���
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("����������ʾ")
				.setMessage("�������Ӳ�����,�Ƿ��������?")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = null;
						// �ж��ֻ�ϵͳ�İ汾 ��API����10 ����3.0�����ϰ汾
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
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
	}

	public static void progressBar(final Context context) {
		// ��ʾ�Ի���
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("�������أ���ȴ�")
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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
			/* �����ȡ�ļ����ݵ�URL */
			URL myURL = new URL(url);
			/* ��URL���� */
			URLConnection ucon = myURL.openConnection();

			/* ʹ��InputStreams����URLConnection��ȡ���� */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/* ��ByteArrayBuffer������ */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* �����������ת��ΪString�� ��UTF-8���� */
			myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
			// myString = new String(baf.toByteArray());

		} catch (Exception e) {
			/* �����쳣 */
			Toast.makeText(this, e.toString(), 3000).show();
		}
		return myString;

	}

	public void jsonAction(String strResult) {
		try {
			JSONArray jsonArray = new JSONObject(strResult)
					.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {// ѭ��:ÿ��
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
			Log.v("Error", "Json��������");
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
			StrKe[0][0] = "���ļ�����";
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_LONG);
		}
	}

}
