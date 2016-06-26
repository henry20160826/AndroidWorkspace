package com.cqupt.canbus;




import com.bky.camerabase.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>Title��3G����������ƽ̨</p>
 * <p>Description���Զ���Ľ������ؼ�</p>
 * <p>Company��������Ѷ��ͨ�ż������޹�˾ </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @version 1.0.0.0
 * @author 3G�ն�Ӧ�ÿ����� 
 */
public class CommonTitle extends LinearLayout{
	
	private ImageView logo;//��˾logo
	private TextView  title;//����
	private ImageView revert;//���ذ�ť
  
    public CommonTitle(Context context) {  
        this(context, null);  
    }
  
    public CommonTitle(Context context,AttributeSet attrs) {  
        super(context,attrs);  
        // ���벼��  
        LayoutInflater.from(context).inflate(R.layout.common_title_item, this, true);
        //��ÿؼ�ʵ��
        logo = (ImageView) findViewById(R.id.common_title_logo);  
        title = (TextView) findViewById(R.id.common_title_title);  
        revert = (ImageView) findViewById(R.id.common_title_revert);
    }  
    
    public ImageView getLogo() {
		return logo;
	}

	public void setLogo(ImageView logo) {
		this.logo = logo;
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public ImageView getRevert() {
		return revert;
	}

	public void setRevert(ImageView revert) {
		this.revert = revert;
	}
}
