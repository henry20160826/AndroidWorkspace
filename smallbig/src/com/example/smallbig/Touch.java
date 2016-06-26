package com.example.smallbig;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class Touch extends Activity implements OnTouchListener, OnClickListener {
	private static final String TAG = "Touch";

	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist;
	private ImageView view;
	private Button zoomIn, zoomOut;
	// button zoom
	private float scaleWidth = 1;
	private float scaleHeight = 1;
	private Bitmap bmp, zoomedBMP;
	private int zoom_level = 0;
	private static final double ZOOM_IN_SCALE = 1.25;// �Ŵ�ϵ��
	private static final double ZOOM_OUT_SCALE = 0.8;// ��Сϵ��
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// �Ŵ�ť
		zoomIn = (Button) findViewById(R.id.zoom_in);
		// ��С��ť
		zoomOut = (Button) findViewById(R.id.zoom_out);
		zoomIn.setOnClickListener(this);
		zoomOut.setOnClickListener(this);
		view = (ImageView) findViewById(R.id.imageView);
		view.setOnTouchListener(this);
		bmp = BitmapFactory.decodeResource(Touch.this.getResources(),
				R.drawable.a1_hx);

	}

	public boolean onTouch(View v, MotionEvent event) {
		// Handle touch events here...
		ImageView view = (ImageView) v;

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// ��������ģʽ
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			Log.d(TAG, "mode=DRAG");
			mode = DRAG;
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			Log.d(TAG, "mode=NONE");
			break;
		// ���ö�㴥��ģʽ
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			Log.d(TAG, "oldDist=" + oldDist);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
				Log.d(TAG, "mode=ZOOM");
			}
			break;
		// ��ΪDRAGģʽ�������ƶ�ͼƬ
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				// ����λ��
				matrix.postTranslate(event.getX() - start.x, event.getX()
						- start.x);
			}
			// ��ΪZOOMģʽ�����㴥������
			else if (mode == ZOOM) {
				float newDist = spacing(event);
				Log.d(TAG, "newDist=" + newDist);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					// �������ű�����ͼƬ�е�λ��
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		zoomedBMP = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix,
				true);
		// Perform the transformation
		view.setImageBitmap(zoomedBMP); 
		return true; // indicate event was handled
	}

	// �����ƶ�����
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// �����е�λ��
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// �Ŵ���С��ť����¼�
	@Override
	public void onClick(View v) {
		if (v == zoomIn) {
			enlarge();
		} else if (v == zoomOut) {
			small();
		}
	}

	// ��ť�����С����
	private void small() {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();

		scaleWidth = (float) (scaleWidth * ZOOM_OUT_SCALE);
		scaleHeight = (float) (scaleHeight * ZOOM_OUT_SCALE);

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		zoomedBMP = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix,
				true);
		view.setImageBitmap(zoomedBMP);
	}

	// ��ť����Ŵ���
	private void enlarge() {
		try {
			int bmpWidth = bmp.getWidth();
			int bmpHeight = bmp.getHeight();

			scaleWidth = (float) (scaleWidth * ZOOM_IN_SCALE);
			scaleHeight = (float) (scaleHeight * ZOOM_IN_SCALE);

			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			zoomedBMP = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
					matrix, true);
			view.setImageBitmap(zoomedBMP);
		} catch (Exception e) {
			// can't zoom because of memory issue, just ignore, no big deal
		}
	}
	
}