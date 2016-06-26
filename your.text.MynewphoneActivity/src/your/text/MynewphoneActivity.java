package your.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
public class MynewphoneActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button bu1=(Button)findViewById(R.id.bu1);
        bu1.setText("Next");
		bu1.setOnClickListener(new bu1Listener());
    }
    class bu1Listener implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent in=new Intent();
			in.setClass(MynewphoneActivity.this, secondActivity.class);
			MynewphoneActivity.this.startActivity(in);
		}
    	
    }
    
}