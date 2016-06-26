package com.xunfang.io;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xunfang.io.so.Linux;
/**
 * <p>Title��IO DEMO</p>
 * <p>Description��������</p>
 * <p>Company��������Ѷ��ͨ�ż������޹�˾ </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @version 1.0.0.0
 * @author sas
 */
public class Main extends Activity implements OnClickListener,OnCheckedChangeListener{
	private ImageView revert;//����"��ť"
	private TextView title;//����
	private Button pin1_btn,pin3_btn,pin5_btn,pin7_btn,read_btn;
	private Button pin1_write_btn,pin3_write_btn,pin5_write_btn,pin7_write_btn,write_btn;
	private CheckBox set_pin1,set_pin3,set_pin5,set_pin7;
	private int flag = -1;//���IO���Ƿ�򿪳ɹ�
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //���ô��ڲ���
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        //�Զ��崰����ʽ
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_title);
		revert = (ImageView)findViewById(R.id.common_title_revert);
		title = (TextView)findViewById(R.id.common_title_title);
		title.setText(R.string.app_name);
		revert.setOnClickListener(this);
		pin1_btn = (Button)findViewById(R.id.pin1_btn);
		pin3_btn = (Button)findViewById(R.id.pin3_btn);
		pin5_btn = (Button)findViewById(R.id.pin5_btn);
		pin7_btn = (Button)findViewById(R.id.pin7_btn);
		read_btn = (Button)findViewById(R.id.read_btn);
		pin1_write_btn = (Button)findViewById(R.id.pin1_write_btn);
		pin3_write_btn = (Button)findViewById(R.id.pin3_write_btn);
		pin5_write_btn = (Button)findViewById(R.id.pin5_write_btn);
		pin7_write_btn = (Button)findViewById(R.id.pin7_write_btn);
		write_btn = (Button)findViewById(R.id.write_btn);
		set_pin1 = (CheckBox)findViewById(R.id.set_pin1);
		set_pin3 = (CheckBox)findViewById(R.id.set_pin3);
		set_pin5 = (CheckBox)findViewById(R.id.set_pin5);
		set_pin7 = (CheckBox)findViewById(R.id.set_pin7);
		//select_pin = (RadioGroup)findViewById(R.id.select_pin);
		
		read_btn.setOnClickListener(this);
		pin1_write_btn.setOnClickListener(this);
		pin3_write_btn.setOnClickListener(this);
		pin5_write_btn.setOnClickListener(this);
		pin7_write_btn.setOnClickListener(this);
		write_btn.setOnClickListener(this);
		set_pin1.setOnCheckedChangeListener(this);
		set_pin3.setOnCheckedChangeListener(this);
		set_pin5.setOnCheckedChangeListener(this);
		set_pin7.setOnCheckedChangeListener(this);
		//select_pin.setOnCheckedChangeListener(this);
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	flag = Linux.open();
    	if(flag>0){
    		title.setText(getText(R.string.app_name)+"---��IO�ڳɹ�");
    		//Toast.makeText(this, "��IO�ڳɹ�", Toast.LENGTH_SHORT).show();
    	}else{
    		title.setText(getText(R.string.app_name)+"---��IO��ʧ��");
    	}
    }
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Linux.close();
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view instanceof ImageView || view instanceof Button){
			switch (view.getId()) {
			case R.id.common_title_revert:
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setIcon(android.R.drawable.ic_menu_help);
				dialog.setTitle("ȷ���˳���");
				dialog.setPositiveButton("ȷ��", new OnClickLiner_OK());
				dialog.setNegativeButton("ȡ��", new OnClickLiner_Cancel());
				dialog.show();
				break;
			case R.id.read_btn:
				if(flag > 0){
					byte[] state = Linux.read();
		    		if(state != null){
		    			pin1_btn.setText(state[0]+"");
		    			pin3_btn.setText(state[1]+"");
		    			pin5_btn.setText(state[2]+"");
		    			pin7_btn.setText(state[3]+"");
		    			/*if(state[0] == 0x0){
		    				set_pin1.setChecked(false);
		    			}else if(state[0] == 0x1){
		    				set_pin1.setChecked(true);
		    			}
		    			if(state[1] == 0x0){
		    				set_pin3.setChecked(false);
		    			}else if(state[1] == 0x1){
		    				set_pin3.setChecked(true);
		    			}
		    			if(state[2] == 0x0){
		    				set_pin5.setChecked(false);
		    			}else if(state[2] == 0x1){
		    				set_pin5.setChecked(true);
		    			}
		    			if(state[3] == 0x0){
		    				set_pin7.setChecked(false);
		    			}else if(state[3] == 0x1){
		    				set_pin7.setChecked(true);
		    			}*/
		    		}
				}else{
					Toast.makeText(this, "��IO��ʧ��", Toast.LENGTH_SHORT).show();
					pin1_btn.setText("δ֪");
	    			pin3_btn.setText("δ֪");
	    			pin5_btn.setText("δ֪");
	    			pin7_btn.setText("δ֪");
				}
				break;
			case R.id.write_btn:
				if(flag > 0){
					int result = Linux.write(new byte[]{(byte)Integer.parseInt((String)pin1_write_btn.getText()),
							(byte)Integer.parseInt((String)pin3_write_btn.getText()),
							(byte)Integer.parseInt((String)pin5_write_btn.getText()),
							(byte)Integer.parseInt((String)pin7_write_btn.getText())});
		    		if(result > 0 ){
		    			Toast.makeText(this, "����IO�ڳɹ�", Toast.LENGTH_SHORT).show();
		    		}else{
		    			Toast.makeText(this, "����IO��ʧ��", Toast.LENGTH_SHORT).show();
		    		}
				}else{
					Toast.makeText(this, "��IO��ʧ��", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.pin1_write_btn:
			case R.id.pin3_write_btn:
			case R.id.pin5_write_btn:
			case R.id.pin7_write_btn:
				Button btn = (Button)view;
				if("1".equals(btn.getText())){
					btn.setText("0");
				}else if("0".equals(btn.getText())){
					btn.setText("1");
				}
				break;
			default:
				break;
			}
		}
	}
	/**
	 * ����menu��ʱ�Ļص�����
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Ϊ�˵������ѡ��
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, getText(R.string.quit)).setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	/**
	 * �˵���ѡ�ѡ��ʱ�Ļص�����
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case Menu.FIRST + 1:// �˳�
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setIcon(android.R.drawable.ic_menu_help);
			dialog.setTitle("ȷ���˳���");
			dialog.setPositiveButton("ȷ��", new OnClickLiner_OK());
			dialog.setNegativeButton("ȡ��", new OnClickLiner_Cancel());
			dialog.show();
			return true;
		}
		return false;
	}
	/**
	 * alertDialog�����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class OnClickLiner_OK implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Main.this.finish();
		}
	}

	/**
	 * alertDialog�����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	class OnClickLiner_Cancel implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			dialog.cancel();
		}
	}
	/**
	 * �������ؼ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �жϷ��ؼ��Ƿ񱻰���
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setIcon(android.R.drawable.ic_menu_help);
			dialog.setTitle("ȷ���˳���");
			dialog.setPositiveButton("ȷ��", new OnClickLiner_OK());
			dialog.setNegativeButton("ȡ��", new OnClickLiner_Cancel());
			dialog.show();
		}
		return false;
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.set_pin1:
			if(flag > 0){
				int cmd = 0;
				if(isChecked){
					cmd = 1;
				}else{
					cmd = 0;
				}
				int result = Linux.ioctl(0, cmd);
	    		if(result == 0 ){
	    			Toast.makeText(this, "����PIN1�ɹ�", Toast.LENGTH_SHORT).show();
	    		}else{
	    			Toast.makeText(this, "����PIN1ʧ��", Toast.LENGTH_SHORT).show();
	    		}
			}else{
				Toast.makeText(this, "��IO��ʧ��", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.set_pin3:
			if(flag > 0){
				int cmd = 0;
				if(isChecked){
					cmd = 1;
				}else{
					cmd = 0;
				}
				int result = Linux.ioctl(1, cmd);
	    		if(result == 0 ){
	    			Toast.makeText(this, "����PIN3�ɹ�", Toast.LENGTH_SHORT).show();
	    		}else{
	    			Toast.makeText(this, "����PIN3ʧ��", Toast.LENGTH_SHORT).show();
	    		}
			}else{
				Toast.makeText(this, "��IO��ʧ��", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.set_pin5:
			if(flag > 0){
				int cmd = 0;
				if(isChecked){
					cmd = 1;
				}else{
					cmd = 0;
				}
				int result = Linux.ioctl(2, cmd);
	    		if(result == 0 ){
	    			Toast.makeText(this, "����PIN5�ɹ�", Toast.LENGTH_SHORT).show();
	    		}else{
	    			Toast.makeText(this, "����PIN5ʧ��", Toast.LENGTH_SHORT).show();
	    		}
			}else{
				Toast.makeText(this, "��IO��ʧ��", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.set_pin7:
			if(flag > 0){
				int cmd = 0;
				if(isChecked){
					cmd = 1;
				}else{
					cmd = 0;
				}
				int result = Linux.ioctl(3, cmd);
	    		if(result == 0 ){
	    			Toast.makeText(this, "����PIN7�ɹ�", Toast.LENGTH_SHORT).show();
	    		}else{
	    			Toast.makeText(this, "����PIN7ʧ��", Toast.LENGTH_SHORT).show();
	    		}
			}else{
				Toast.makeText(this, "��IO��ʧ��", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
	
}