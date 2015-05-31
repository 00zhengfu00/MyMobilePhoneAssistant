package com.cbooy.mmpa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.activity.HomeActivity;
import com.cbooy.mmpa.utils.HttpUtil;
import com.cbooy.mmpa.utils.PackageManagerUtil;
import com.cbooy.mmpa.utils.StaticDatas;

@SuppressLint("HandlerLeak")
public class BootActivity extends Activity {
	
	// ��ʾ�汾�ŵ�TextView
	private TextView tvShowVersion;
	
	// �汾
	private String versionName;
	
	// ����ʱ������
	private long start = 0;
	
	// boot activity �� ��������
	private RelativeLayout layoutBoot = null;
	
	// ��ͬ�߳�֮��ͨ��
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				
				// ������
				case StaticDatas.VERSION_NEED_UPDATE:
					
					String res = (String) msg.obj;
					
					Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "��Ϣ�� " + res);
					
					break;
					
				default:
					
					// ������ҳ��
					goHomeActivity();
					
					break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.boot_activity);
		
		// ��ʼ��View
		initViews();
		
		// ��ʼ�� ���� 
		initDatas();
		
		// ����Ч��
		displayForAnimation();
	}
	
	private void displayForAnimation() {
		AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
		
		animation.setDuration(1500);
		
		layoutBoot.setAnimation(animation);
	}

	// ������ҳ��
	private void goHomeActivity() {
		
		// ��ת�� Homeҳ
		Intent intent = new Intent(this,HomeActivity.class);
		
		startActivity(intent);
		
		finish();
	}

	/**
	 * ��ʼ������
	 */
	private void initDatas() {
		
		// ����ʱ������
		start = System.currentTimeMillis();
		
		// ��ȡ�汾��
		versionName = PackageManagerUtil.getVersion(this);
		
		// ���ð汾
		tvShowVersion.setText("�汾: " + versionName);
		
		// ���汾��Ϣ�Ƿ���Ҫ����
		HttpUtil.checkUpdateInfos(this, versionName, handler,start);
	}

	/**
	 * �Ա�ҳ���һЩView��ʼ��
	 */
	private void initViews() {
		
		// �汾��Ϣ�Ķ���
		tvShowVersion = (TextView) this.findViewById(R.id.tv_show_version);
		
		// ���ز����ļ�
		layoutBoot = (RelativeLayout) this.findViewById(R.id.boot_activity_layout);
	}
}
