package my.filpper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FilpActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 

        super.onCreate(savedInstanceState); 

        setContentView(R.layout.main); 

        TextView textView = (TextView) findViewById(R.id.animation_text); 

        ImageView imageView =(ImageView) findViewById(R.id.imageViews); 

        textView.setLongClickable(true); 

        textView.setOnTouchListener(new OnTouchListener() { 

            @Override 

            public boolean onTouch(View v, MotionEvent event) { 

                return gestureDetector.onTouchEvent(event); 

            } 

        }); 

        gestureDeal = new GestureDeal(imageView); 

        gestureDetector=new GestureDetector(gestureDeal); 

    }

}