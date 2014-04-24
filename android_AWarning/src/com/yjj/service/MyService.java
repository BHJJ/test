package com.yjj.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	private LocationManager manager;
	private MyReceiver myReceiver;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "service�Ѿ�����", Toast.LENGTH_SHORT).show();
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Location location = manager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		showLocation(location);
		myReceiver = new MyReceiver();

		// ע��һ���㲥
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.location.set");
		registerReceiver(myReceiver, filter);
	}

	private void showLocation(Location location) {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences("location", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		StringBuffer sb = new StringBuffer();
		sb.append("���ȣ�" + location.getLongitude() + "\n");
		sb.append("γ�ȣ�" + location.getLatitude() + "\n");
		sb.append("�߶ȣ�" + location.getAltitude() + "\n");
		editor.putString("location", sb.toString());
		editor.commit();
		Toast.makeText(this, sb, Toast.LENGTH_SHORT).show();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void openProximityAlert(double longitude, double latitude,
			float radius) {
		Toast.makeText(
				this,
				"�����ѿ���" + "longitude:" + longitude + "latitude:" + latitude
						+ "radius" + radius, Toast.LENGTH_SHORT).show();
		// ����Intent
		Intent intentA = new Intent();
		intentA.setAction("com.music");
		// ��Intent��װ��PendingIntent
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				MyService.this, -1, intentA, 0);
		manager.addProximityAlert(latitude, longitude, radius, -1,
				pendingIntent);
	}

	public class MyReceiver extends BroadcastReceiver {
		private double longitude;
		private double latitude;
		private float radius;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "�㲥���յ�", Toast.LENGTH_SHORT).show();
			double[] values = new double[3];
			values = intent.getDoubleArrayExtra("setLocation");
			longitude = values[0];
			latitude = values[1];
			radius = (float) values[2];
			Toast.makeText(
					MyService.this,
					"longitude" + longitude + "latitude" + latitude + "radius"
							+ radius, Toast.LENGTH_SHORT).show();
			openProximityAlert(longitude, latitude, radius);
		}
	}

}
