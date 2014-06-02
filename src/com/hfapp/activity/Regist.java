package com.hfapp.activity;

import org.xml.sax.DTDHandler;

import com.example.config.Userconfig;
import com.example.palytogether.MainActivity;
import com.example.palytogether.R;
import com.hf.module.IModuleManager;
import com.hf.module.ManagerFactory;
import com.hf.module.ModuleConfig;
import com.hf.module.ModuleException;
import com.hf.module.info.CaptchaImageInfo;
import com.hf.module.info.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Regist extends Activity{
	private EditText username;
	private EditText userdisplayName;
	private EditText userpswd;
	private EditText reuserpswd;
	private EditText email;

	private EditText capcha;

	private ImageView imageCapcha;

	private Button ok;

	private IModuleManager manager;
	
	
	String strusername;
	String struserpswd;
	String strcapcha;
	String strcapchatoken;
	String stremail;
	
	
	
	
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(Regist.this, "网络错误", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(Regist.this, "输入有误", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(Regist.this, "注册成功", Toast.LENGTH_SHORT).show();
				ModuleConfig.cloudPassword = struserpswd;
				ModuleConfig.cloudUserName = strusername;
				Userconfig.saveUserInfo();
				Intent i = new Intent(Regist.this,MainActivity.class);
				startActivity(i);
				finish();
				break;
			case -114:
				Toast.makeText(Regist.this, "验证码超时", Toast.LENGTH_SHORT).show();
				break;
			case -104:
				Toast.makeText(Regist.this, "用户已经存在", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	
	
	private boolean isinPutOk = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		manager = ManagerFactory.getInstance().getManager();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		username = (EditText) findViewById(R.id.username);
		username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()<6){
					username.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					strusername = s.toString();
					username.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		userdisplayName = (EditText) findViewById(R.id.userdisplayName);
		userpswd = (EditText) findViewById(R.id.userpswd);
		userpswd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()<6){
					userpswd.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					userpswd.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		reuserpswd = (EditText) findViewById(R.id.reuserpswd);
		reuserpswd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()<6||!userpswd.getText().toString().equalsIgnoreCase(s.toString())){
					reuserpswd.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					struserpswd = s.toString();
					reuserpswd.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		email = (EditText) findViewById(R.id.email);
		email.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(!s.toString().contains("@")||!s.toString().contains(".")){
					email.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					stremail = s.toString();
					email.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		capcha = (EditText) findViewById(R.id.capcha);
		capcha.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()!=4){
					capcha.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					strcapcha = s.toString();
					capcha.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		imageCapcha = (ImageView) findViewById(R.id.imageCacha);
		imageCapcha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setCpacha();
			}
		});
		ok = (Button) findViewById(R.id.ok);
		setCpacha();
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isinPutOk)
					doRegister();
				else
					hand.sendEmptyMessage(1);
			}
		});
	}
	
	private void doRegister(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				UserInfo ui = new UserInfo();
				ui.setAccessKey(ModuleConfig.accessKey);
				ui.setUserName(strusername);
				ui.setDisplayName(userdisplayName.getText().toString().trim());
				ui.setPassword(struserpswd);
				ui.setEmail(stremail);
				String c = strcapchatoken+strcapcha;
				try {
					manager.registerUser(ui, c);
					hand.sendEmptyMessage(2);
				} catch (ModuleException e) {
					// TODO Auto-generated catch block
					hand.sendEmptyMessage(e.getErrorCode());
				}
			}
		}).start();
	}
	
	private void setCpacha() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					final CaptchaImageInfo icap = manager.captchaImage();
					strcapchatoken = icap.getCaptchaToken();
					hand.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							imageCapcha.setImageBitmap(icap.getBitmap());
						}
					});
					
				} catch (ModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
}
