package com.hfapp.activity;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.palytogether.R;
import com.hf.module.IModuleManager;
import com.hf.module.ManagerFactory;
import com.hf.module.ModuleException;
import com.hf.module.info.ADCinfo;
/**
 * 设备的校准
 */
public class SensorActivity extends Activity {
	ImageButton adjust,auto,setting;
	EditText designation,unit,measured_value,compensation_coefficient,
			Compensation_constant,limit_lower,limit_upper;
	IModuleManager manager;
	String mac;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_set);
		manager = ManagerFactory.getInstance().getManager();
		mac = getIntent().getStringExtra("mac");
		initWidget();
		calibration();
	}
	
	
	private void initWidget(){
		adjust = (ImageButton)findViewById(R.id.adjust);
		auto = (ImageButton)findViewById(R.id.auto);
		setting = (ImageButton)findViewById(R.id.setting);
		designation = (EditText)findViewById(R.id.designation);
		unit = (EditText)findViewById(R.id.unit);
		measured_value = (EditText)findViewById(R.id.measured_value);
		compensation_coefficient = (EditText)findViewById(R.id.compensation_coefficient);
		Compensation_constant = (EditText)findViewById(R.id.Compensation_constant);
		limit_lower = (EditText)findViewById(R.id.limit_lower);
		limit_upper = (EditText)findViewById(R.id.limit_upper);
	}
	
	private void calibration(){
		adjust.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(SensorActivity.this)
					.setTitle("校准设置")
					.setMessage("您确定要校准数据")
					.setCancelable(false)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							Toast.makeText(SensorActivity.this, "数据更新成功", 1).show();
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					}).show();
			}
		});
		auto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(SensorActivity.this, "自动更新中", 1).show();
			}
		});
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(SensorActivity.this, "设置中", 1).show();
			}
		});
	}
	
	
}

























