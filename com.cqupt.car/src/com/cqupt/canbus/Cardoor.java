package com.cqupt.canbus;




import com.bky.camerabase.R;

import android.R.integer;
import android.widget.ImageView;

public class Cardoor {

	/**
	 * @param args
	 */
	public int[] doorCloesImagesId;
	public int[] doorOpenImagesId;
	public ImageView[] imageViews;
	public int[] doorstate;

	public Cardoor(ImageView[] imageViews) {
		// TODO Auto-generated constructor stub
		doorCloesImagesId = new int[] { R.drawable.doorclose_1,
				R.drawable.doorclose_2, R.drawable.doorclose_3,
				R.drawable.doorclose_4 };
		doorOpenImagesId = new int[] { R.drawable.dooropen_1,
				R.drawable.dooropen_2, R.drawable.dooropen_3,
				R.drawable.dooropen_4 };
		doorstate = new int[] { 2, 2, 2, 2 };
		// 0表示关门状态，1表示开门状态,2表示未知状态
		// ImageView长度需要大于等于4

		this.imageViews = imageViews;
	}

	public void showAllDoorClose() {
		for (int i = 0; i < 4; i++) {
			imageViews[i].setImageResource(doorCloesImagesId[i]);
		}
	}

	public void showAllDoorOpen() {
		for (int i = 0; i < 4; i++) {
			imageViews[i].setImageResource(doorOpenImagesId[i]);
		}
	}

	public int toint(String temp) {
		// 0-3 关门
		// 4-7 开门
		if (temp.equals("0")) {
			return 0;
		}
		if (temp.equals("1")) {
			return 1;
		}
		if (temp.equals("2")) {
			return 2;
		}
		if (temp.equals("3")) {
			return 3;
		}
		if (temp.equals("4")) {
			return 4;
		}
		if (temp.equals("5")) {
			return 5;
		}
		if (temp.equals("6")) {
			return 6;
		}
		if (temp.equals("7")) {
			return 7;
		}
		return 8;
	}

	public void getDoorChange(String temp) {
		int state = toint(temp);
		if (0 == state / 4) {
			imageViews[state % 4]
					.setImageResource(doorCloesImagesId[state % 4]);
		} else {
			imageViews[state % 4].setImageResource(doorOpenImagesId[state % 4]);
		}
		doorstate[state % 4] = state / 4;
	}

	public int[] getDoorState() {
		return doorstate;

	}
}
