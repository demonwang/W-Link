package com.hfapp.activity;

//import android.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.config.LocalModuleContainer;
import com.example.palytogether.R;
import com.hf.module.IModuleManager;
import com.hf.module.ManagerFactory;
import com.hf.module.ModuleException;
import com.hf.module.impl.LocalModuleInfoContainer;
import com.hf.module.info.ADCinfo;
import com.hf.module.info.ModuleInfo;

/**
 * �豸��Ϣ�� ͨ������豸ͼ�꣬���뵽����
 * 
 * @author Administrator
 * 
 */
public class ADCModuleActivity extends Activity{
	private static String TAG = "SecondActivity--->";
	ModuleInfo mi;
	TextView tva, tvb, tvc, tvd, tve, tvf, tvg;
	ImageButton shu;// �ж�ADֵ������1����ɫ��С��1���߹رգ���ɫ
	ImageButton yuan;// ����GPIO��status
	ImageButton clock;// ��ʱ���ã��˰汾�Ըù���û��ʵ�֣�
	ImageButton switcher0, switcher1, switcher2;// ����GPIO״̬��ͬ�����
	ADCinfo adcvalues; // �豸��Ϣ
	IModuleManager manager;// ���L�ڼ�U�ڻ������ܵķ�װ���������û������ܡ�ģ������ܡ��豸���ƹ��ܡ������������ܡ�
	String mac;// ��ModuleList�л�ȡ�ĵ�ַ
	boolean status = false;// ��ȡ�豸��״̬(Բ��GPIO״̬)
	boolean state = false;// �����豸״̬(���õ�״̬)
	boolean isonline = false;// �ж��Ƿ�����

	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:// ���δ��ȡ�����ݣ���ť���ɵ��
				tva.setClickable(false);
				tvb.setClickable(false);
				tvc.setClickable(false);
				tvd.setClickable(false);
				tve.setClickable(false);
				tvf.setClickable(false);
				tvg.setClickable(false);
				clock.setClickable(false);
				yuan.setClickable(false);
				switcher1.setClickable(false);
				switcher2.setClickable(false);
				switcher0.setClickable(false);
				yuan.setImageResource(R.drawable.adcswitchb);
				switcher1.setImageResource(R.drawable.adcturnoff);
				switcher2.setImageResource(R.drawable.adcturnoff);
				switcher0.setImageResource(R.drawable.adcturnoff);
				break;
			case 2:
				tve.setText(data);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		manager = ManagerFactory.getInstance().getManager();
		mac = getIntent().getStringExtra("mac");
		mi = LocalModuleInfoContainer.getInstance().get(mac);
		init();
		initActionbar();
		judge_GPIOState();
		getADCValues();
	}

	/*
	 * ��һ�������ActionBar
	 */
	private void initActionbar() {
		ActionBar bar = getActionBar();
		bar.setCustomView(R.layout.layout_actionbar);
		bar.setDisplayShowCustomEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayShowTitleEnabled(false);
		ImageView backBtn = (ImageView) findViewById(R.id.back);
		ImageView okBtn = (ImageView) findViewById(R.id.ok);
		okBtn.setImageResource(R.drawable.setactionbar);
		TextView title = (TextView) findViewById(R.id.tv_title);
		title.setText(mi.getName());
		okBtn.setOnClickListener(new OnClickListener() {// OK���ܼ��������Ͻǣ���������豸���޸�ҳ�棬�����޸��豸���ơ�ͼƬ���Լ�ɾ���豸

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ADCModuleActivity.this,
						ModuleModify.class);
				i.putExtra("mac", mac);
				startActivity(i);
				finish();
			}
		});
		backBtn.setOnClickListener(new OnClickListener() {// �˳����������Ͻǣ�����˳��ý��档ֱ��finish��������
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/*
	 * �ڶ�������ʼ�����
	 */
	public void init() {
		tva = (TextView) findViewById(R.id.tva);
		tvb = (TextView) findViewById(R.id.tvb);
		tvc = (TextView) findViewById(R.id.tvc);
		tvd = (TextView) findViewById(R.id.tvd);
		tve = (TextView) findViewById(R.id.tve);
		tvf = (TextView) findViewById(R.id.tvf);
		tvg = (TextView) findViewById(R.id.tvg);
		shu = (ImageButton) findViewById(R.id.sx);
		yuan = (ImageButton) findViewById(R.id.yq);
		clock = (ImageButton) findViewById(R.id.clock);
		switcher0 = (ImageButton) findViewById(R.id.switcher1);
		switcher1 = (ImageButton) findViewById(R.id.switcher2);
		switcher2 = (ImageButton) findViewById(R.id.switcher3);
	}

	/*
	 * ���������ж��Ƿ�������
	 */
	private void judge_GPIOState() {
		new Thread(new Runnable() {
			public void run() {
				try {
					status = manager.getHelper().getHFGPIO(mac, 11);
					status0 = manager.getHelper().getHFGPIO(mac, 0);
					status1 = manager.getHelper().getHFGPIO(mac, 1);
					status2 = manager.getHelper().getHFGPIO(mac, 2);
					isonline = true;
					skip();
				} catch (Exception e) {
					e.printStackTrace();
					isonline = false;
					hand.sendEmptyMessage(1);
					return;
				}
			}
		}).start();
	}



	// ����豸��Ϣ��ת���޸Ľ���
	Intent intent;
	public void skip() {
		intent = new Intent(ADCModuleActivity.this, SensorActivity.class);
		intent.putExtra("mac", mac);
		tva.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		tvb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		tvc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		tvd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		tve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		tvf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		tvg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(intent);
			}
		});
		clock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(ADCModuleActivity.this, "��ģ�����ڵ�����", 1).show();
			}
		});
		yuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				setGPIOState();
				if (state == false) {
					state = true;
					yuan.setImageResource(R.drawable.adcswitcha);
				} else if (state == true) {
					state = false;
					yuan.setImageResource(R.drawable.adcswitchb);
				}
			}
		});
		switcher0.setOnClickListener(new OnClickListener() { // new

					@Override
					public void onClick(View view) {
						setGPIOState0();
						if (state0 == false) {
							state0 = true;
							switcher0.setImageResource(R.drawable.adcturnon);
						} else if (state0 == true) {
							state0 = false;
							switcher0.setImageResource(R.drawable.adcturnoff);
						}
					}
				});
		switcher1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				setGPIOState1();
				if (state1 == false) {
					state1 = true;
					switcher1.setImageResource(R.drawable.adcturnon);
				} else if (state1 == true) {
					state1 = false;
					switcher1.setImageResource(R.drawable.adcturnoff);
				}
			}
		});
		switcher2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				setGPIOState2();
				if (state2 == false) {
					state2 = true;
					switcher2.setImageResource(R.drawable.adcturnon);
				} else if (state2 == true) {
					state2 = false;
					switcher2.setImageResource(R.drawable.adcturnoff);
				}
			}
		});
	}

	boolean status0;// new
	boolean status1;
	boolean status2;

	// �ж�GPIO״̬
	private void getGPIOStatus() {
		new Thread(new Runnable() {
			public void run() {
				try {
					status = manager.getHelper().getHFGPIO(mac, 11);
					Log.i("info",status+"");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void getGPIOStatus0() {
		new Thread(new Runnable() {
			public void run() {
				try {
					status0 = manager.getHelper().getHFGPIO(mac, 0);
					Log.i("info",status0+"");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void getGPIOStatus1() {
		new Thread(new Runnable() {
			public void run() {
				try {
					status1 = manager.getHelper().getHFGPIO(mac, 1);
					Log.i("info",status1+"");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void getGPIOStatus2() {
		new Thread(new Runnable() {
			public void run() {
				try {
					status2 = manager.getHelper().getHFGPIO(mac, 2);
					Log.i("info",status2+"");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	boolean state0;// new
	boolean state1;
	boolean state2;

	// ����GPIO״̬
	private void setGPIOState() {
		new Thread(new Runnable() {
			public void run() {
				try {
					getGPIOStatus();
					state = manager.getHelper().setHFGPIO(mac, 11, status);
					Log.i("info", state+"----state");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void setGPIOState0() {
		new Thread(new Runnable() {
			public void run() {
				try {
					getGPIOStatus0();
					state0 = manager.getHelper().setHFGPIO(mac, 0, status0);
					Log.i("info", state0 + "----state0");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void setGPIOState1() {
		new Thread(new Runnable() {
			public void run() {
				try {
					getGPIOStatus1();
					state1 = manager.getHelper().setHFGPIO(mac, 1, status1);
					Log.i("info", state1 + "----state1");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void setGPIOState2() {
		new Thread(new Runnable() {
			public void run() {
				try {
					getGPIOStatus2();
					state2 = manager.getHelper().setHFGPIO(mac, 2, status2);
					Log.i("info", state2 + "----state2");
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	// ��ȡ����
	private void getADCValues() {
		Toast.makeText(this, "Ŭ�������豸��Ϣ�У����Ժ�", 1).show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					adcvalues = manager.getHelper().getHFADCModuleValues(mac);
					new Handler(getMainLooper()).post(new Runnable() {
						int adc1_data = adcvalues.getAD1Value() * 3300 / 4096;
						int adc2_data = adcvalues.getAD2Value() * 3300 / 4096;
						int adc3_data = adcvalues.getAD3Value() * 3300 / 4096;
						int adin_data = adcvalues.getDoValue() * 3300 / 4096;
						int hum_tem_am2301 = adcvalues.getAm2301();
						int tem_ds18b20 = adcvalues.getDs18b20();
						int data = adcvalues.getAC_FB();

						@Override
						public void run() {
							tva.setText("" + adc1_data / 1000 + "." + adc1_data
									% 1000 / 100 + adc1_data % 100 / 10 + " mV");// adc1
							tvb.setText("" + adc2_data / 1000 + "." + adc2_data
									% 1000 / 100 + adc2_data % 100 / 10 + " mV");// adc2
							tvc.setText("" + adc3_data / 1000 + "." + adc3_data
									% 1000 / 100 + adc3_data % 100 / 10 + " mV");// adc3
							tvd.setText(tem_ds18b20 * 625 / 10000 + "."
									+ (tem_ds18b20 * 625 % 10000) / 100 + " 'C");// �¶�
							tve.setText("" + adin_data / 1000 + "." + adin_data
									% 1000 / 100 + adin_data % 100 / 10);// DOֵ
							tvf.setText("" + (hum_tem_am2301 & 0x7fff) / 10.0
									+ " 'C");
							tvg.setText(((hum_tem_am2301 & 0xffff0000) >> 16)
									/ 10.0 + "");
							if (data >= 1) {
								shu.setImageResource(R.drawable.adcswitchc);
							} else {
								shu.setImageResource(R.drawable.adcswitchd);
								hand.sendEmptyMessage(1);
							}
						}
					});
				} catch (ModuleException e) {
					hand.sendEmptyMessage(1);
					Log.i("info", TAG + "--11");
					e.printStackTrace();
				}
			}
		}).start();
	}

	ADCinfo info;
	String data;

	private void getInfo() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					info = manager.getHelper().getHFADCModuleValues(mac);
					Log.i("info", "info��ֵ=====" + info);
					int adin_data = info.getDoValue() * 3300 / 4096;
					data = adin_data / 1000 + "." + adin_data % 1000 / 100
							+ adin_data % 100 / 10;
					Log.i("info", "data��ֵΪ" + data);
					hand.sendEmptyMessage(2);
				} catch (ModuleException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void onRestart() {
		// getADCValues();
		getInfo();
		super.onRestart();
	}

}
