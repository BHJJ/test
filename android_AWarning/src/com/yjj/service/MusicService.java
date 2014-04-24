package com.yjj.service;

import java.io.IOException;

import com.example.android_awarning.R;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class MusicService extends Service {
	private MyBroadastRe broadastRe;
	private SharedPreferences sp_num;
	private MediaPlayer player;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		broadastRe = new MyBroadastRe();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.music");
		registerReceiver(broadastRe, filter);
		sp_num = getSharedPreferences("num", MODE_PRIVATE);
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

	public void playMusic() {
		if (player != null) {
			player.release();
		}
		player = MediaPlayer.create(this, R.raw.aa);

		player.start();
	}

	public void SendSMS(String content, String num) {

		SmsManager.getDefault().sendTextMessage(num, null, content, null, null);
	};

	class MyBroadastRe extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String num = sp_num.getString("num", "10086");
			Toast.makeText(MusicService.this, "num" + num, Toast.LENGTH_SHORT)
					.show();
			playMusic();
			if (intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,
					false)) {
				SendSMS("小偷进来了", num);
			} else {
				SendSMS("小偷跑了", num);
			}
		}

	}
}
