package com.yjj.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_awarning.R;
import com.yjj.service.MusicService;
import com.yjj.service.MyService;

public class MainActivity extends Activity implements OnClickListener {
	private TextView tv_longitude, tv_latitude, tv_radius, tv_telephone;
	private EditText et_longitude, et_latitude, et_radius, et_telephone;
	private TextView showLocation;
	private Button btn_true, btn_set, btn_test;
	private SharedPreferences sp, sp_num;
	private SharedPreferences.Editor editor;
	private double[] values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewInit();
		startSer();
		sp = getSharedPreferences("location", MODE_PRIVATE);
		sp_num = getSharedPreferences("num", MODE_PRIVATE);
		editor = sp_num.edit();
	}

	private void startSer() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, MyService.class);
		startService(intent);
		Intent intentMUSI = new Intent(MainActivity.this, MusicService.class);
		startService(intentMUSI);
	}

	private void viewInit() {
		// TODO Auto-generated method stub
		tv_longitude = (TextView) findViewById(R.id.tv_longitude);
		tv_latitude = (TextView) findViewById(R.id.tv_latitude);
		tv_radius = (TextView) findViewById(R.id.tv_radius);
		tv_telephone = (TextView) findViewById(R.id.tv_telephone);
		btn_true = (Button) findViewById(R.id.btn_true);
		btn_set = (Button) findViewById(R.id.btn_set);
		btn_test = (Button) findViewById(R.id.test);
		btn_set.setOnClickListener(this);
		btn_true.setOnClickListener(this);
		btn_test.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_set:
			LinearLayout view = (LinearLayout) getLayoutInflater().inflate(
					R.layout.iten_set, null);
			showLocation = (TextView) view.findViewById(R.id.showLocatin);
			showLocation.setText(sp.getString("location", null));
			et_longitude = (EditText) view.findViewById(R.id.et_longitude);
			et_latitude = (EditText) view.findViewById(R.id.et_latitude);
			et_radius = (EditText) view.findViewById(R.id.et_radius);
			et_telephone = (EditText) view.findViewById(R.id.et_telephone);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("设置参数");
			builder.setView(view);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							tv_longitude.setText(et_longitude.getText()
									.toString());
							tv_latitude.setText(et_latitude.getText()
									.toString());
							tv_radius.setText(et_radius.getText().toString());
							tv_telephone.setText(et_telephone.getText()
									.toString());

							values = new double[3];
							values[0] = Double.parseDouble(tv_longitude
									.getText().toString().trim());
							values[1] = Double.parseDouble(tv_latitude
									.getText().toString().trim());
							values[2] = Double.parseDouble(tv_radius.getText()
									.toString().trim());
							editor.putString("num", tv_telephone.getText()
									.toString().trim());
							editor.commit();

						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			builder.create();
			builder.show();
			break;
		case R.id.btn_true:
			if (values != null) {
				Intent intent = new Intent();
				intent.setAction("com.location.set");
				intent.putExtra("setLocation", values);
				sendBroadcast(intent);
			} else {
				Toast.makeText(this, "请设置相关错误", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.test:
			Intent intentTest = new Intent();
			intentTest.setAction("com.music");

			sendBroadcast(intentTest);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

	}

}
