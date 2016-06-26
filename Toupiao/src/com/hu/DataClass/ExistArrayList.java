package com.hu.DataClass;

import java.util.ArrayList;
import java.util.HashMap;

public class ExistArrayList {
	/**
	 * 
	 */
	
	public int ifInMyArrayList(String s, ArrayList<String> arrayList) {
		int arrayListSize = arrayList.size();
		int i;
		for (i = 0; i < arrayListSize; i++) {
			String temp = arrayList.get(i).toString();
			if (s.equals(temp)) {
				return i;
			}
		}
		return -1;
	}

	public int ifInMyArrayList(String s, String key,
			ArrayList<HashMap<String, String>> arrayList) {
		int arrayListSize = arrayList.size();
		int i;
		for (i = 0; i < arrayListSize; i++) {
			String temp = arrayList.get(i).get(key).toString();
			if (s.equals(temp)) {
				return i;
			}
		}
		return -1;
	}
}
