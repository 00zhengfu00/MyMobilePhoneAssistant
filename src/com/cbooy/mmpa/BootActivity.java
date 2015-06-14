package com.cbooy.mmpa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.activity.HomeActivity;
import com.cbooy.mmpa.model.UpdateVersionInfo;
import com.cbooy.mmpa.utils.DialogUtil;
import com.cbooy.mmpa.utils.HttpUtil;
import com.cbooy.mmpa.utils.JsonForObjectConverter;
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
	
	// �����µİ汾��Ϣ
	private UpdateVersionInfo updateVersioInfo = null;
	
	// ��ʾ���ؽ�����Ϣ
	private TextView tvDisplayDownloadProcess = null;
	
	// �����ļ�
	private SharedPreferences sp;
	
	// Ĭ���Ƿ� �������
	private boolean isConfigCheckUpdate;
	
	// ��ͬ�߳�֮��ͨ��
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				
				// ��Ҫ����
				case StaticDatas.VERSION_NEED_UPDATE:
					
					checkUpdateFiles((String)msg.obj);
					
					break;
					
				// �Ƿ�����
				case StaticDatas.IS_DOWNLOAD_NEW_VERSION:
					
					boolean isUpdate = (boolean) msg.obj;
					
					Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "�Ƿ����ص� �ж� " + isUpdate);
					
					if(isUpdate){
						new HttpUtil(BootActivity.this,handler).downloadFiles(updateVersioInfo.getUpdate_url(),tvDisplayDownloadProcess);
					}else{
						
						// ����Home ҳ��
						goHomeActivity();
					}
					
					break;
				
				// �����Ի���ȡ��
				case StaticDatas.DIALOG_DISMISS :
					
					// ������ҳ��
					goHomeActivity();
					
					break;
				default:
					
					// ������ҳ��
					goHomeActivity();
					
					break;
			}
		}
	};
	
	// ������
	private void checkUpdateFiles(String res) {
		updateVersioInfo = JsonForObjectConverter.StringToObject(res,UpdateVersionInfo.class);
		
		// �����Ի�����ʾ�Ƿ����
		new DialogUtil(this,handler).alertUpdateInfos(updateVersioInfo.getDesc());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.boot_activity);
		
		// ��ʼ��View
		initViews();
		
		// ��ʼ�� ���� 
		initDatas();
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
		versionName = new PackageManagerUtil().getVersion(this);
		
		// ���ð汾
		tvShowVersion.setText("�汾: " + versionName);
		
		sp = this.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		isConfigCheckUpdate = sp.getBoolean(StaticDatas.CONFIG_IS_UPDATE, true);
		
		if(isConfigCheckUpdate){
			// ���汾��Ϣ�Ƿ���Ҫ����
			new HttpUtil(this,handler).checkUpdateInfos(versionName,start);
		}else{
			
			handler.postDelayed(new Runnable(){
				@Override
				public void run() {
					// ������ҳ
					goHomeActivity();
				}}, 2000);
		}
	}

	/**
	 * �Ա�ҳ���һЩView��ʼ��
	 */
	private void initViews() {
		
		// �汾��Ϣ�Ķ���
		tvShowVersion = (TextView) this.findViewById(R.id.tv_show_version);
		
		// ���ز����ļ�
		layoutBoot = (RelativeLayout) this.findViewById(R.id.boot_activity_layout);
		
		// ��ʾ���ؽ���
		tvDisplayDownloadProcess = (TextView) this.findViewById(R.id.tv_show_download_process);
		
		// ����Ч��
		displayForAnimation();
	}
	
	/**
	 * ����չʾЧ��
	 */
	private void displayForAnimation() {
		AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
		
		animation.setDuration(1500);
		
		layoutBoot.setAnimation(animation);
	}
}
