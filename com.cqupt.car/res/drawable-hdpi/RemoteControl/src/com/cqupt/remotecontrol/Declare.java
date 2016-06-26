package com.cqupt.remotecontrol;

import java.util.List;

import android.app.Application;

import com.cqupt.model.Room;

public class Declare extends Application {
	
	private List<Room> dataArray;

	public List<Room> getDataArray() {
		return dataArray;
	}

	public void setDataArray(List<Room> dataArray) {
		this.dataArray = dataArray;
	}
	
	public Room getRoomByName(String name){
		Room room = null;
		for(int i = 0; i < dataArray.size(); i++){
			if(dataArray.get(i).getRoomName().equals(name)){
				room = dataArray.get(i);
				return room;
			}
		}
		return room;
	}
	
}
