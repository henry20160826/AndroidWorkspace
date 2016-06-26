package com.bky.camerabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class GPSHelper {

	private static final int CHECK_INTERVAL = 1000 * 30;
	private LocationManager locationManager;
	private MyLocationListener locationListener;
	private Location currentLocation;
	private MainActivity activity;
	
	public GPSHelper(MainActivity activity){
		this.activity = activity;
		locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);  
	}
	
	public boolean check(){
		return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
	}
	
	public void startListen(){
		if(!check()){
			new AlertDialog.Builder(activity)
			.setTitle("提示")
			.setMessage("请打开您的GPS模块...")
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
			        activity.startActivityForResult(intent,0); //此为设置完成后返回到获取界面
				}
			})
			.setNegativeButton("否", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					activity.finish();
				}
			})
			.show();
	        return ;
		}
		if(locationListener == null){
	        locationListener = new MyLocationListener();
	        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 1000, 0, locationListener); 
		}
	}
	
	public void stopListen(){
		if(locationListener != null){
			locationManager.removeUpdates(locationListener);
			locationListener = null;
		}
	}
	
	public Location getLastLocation(){
		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	protected boolean isBetterLocation(Location location, Location currentBestLocation){
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > CHECK_INTERVAL;
		boolean isSignificantlyOlder = timeDelta < -CHECK_INTERVAL;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location,
		// use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must
			// be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	
	
	class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (currentLocation != null) {
				if (isBetterLocation(location, currentLocation)) {
					currentLocation = location;
					System.out.println("经度：" + location.getLongitude() + "纬度：" + location.getLatitude());
				} else {
					System.out.println("not very good");
				}
			} else {
				System.out.println("It's first location");
				currentLocation = location;
				System.out.println("经度：" + location.getLongitude() + "纬度：" + location.getLatitude());
			}
			// 移除基于LocationManager.NETWORK_PROVIDER的监听器
			if (LocationManager.NETWORK_PROVIDER.equals(location.getProvider())) {
				locationManager.removeUpdates(this);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			activity.show("您正在使用GPS，请打开！");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			activity.show("GPS恢复！");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println("status:" + status);
		}


	};
}
