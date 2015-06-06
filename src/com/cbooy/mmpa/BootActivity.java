package com.cbooy.mmpa;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.cbooy.mmpa.utils.InstallerApkUtil;
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
	
	// ��ͬ�߳�֮��ͨ��
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				
				// ������
				case StaticDatas.VERSION_NEED_UPDATE:
					
					checkUpdateFiles(msg);
					
					break;
					
				// �Ƿ�����
				case StaticDatas.IS_DOWNLOAD_NEW_VERSION:
					
					boolean isUpdate = (boolean) msg.obj;
					
					if(isUpdate){
						new HttpUtil(BootActivity.this,handler).downloadFiles(updateVersioInfo.getUpdate_url());
					}else{
						
						// ����Home ҳ��
						goHomeActivity();
					}
					
					break;
				
				// ���ؽ�����ʾ
				case StaticDatas.DOWNLOAD_PROCESSING :
					
					tvDisplayDownloadProcess.setText("��ǰ���ؽ���Ϊ:" + msg.obj + "%");
					
					break;
				
				// ��װ�ļ�
				case StaticDatas.DOWNLOAD_SUCCESS:
					
					// ��װ�ļ�
					new InstallerApkUtil(BootActivity.this,(File)msg.obj).install();;
					
					break;
				default:
					
					// ������ҳ��
					goHomeActivity();
					
					break;
			}
		}
	};
	
	// ������
	private void checkUpdateFiles(Message msg) {
		String res = (String) msg.obj;
		
		updateVersioInfo = JsonForObjectConverter.StringToObject(res,UpdateVersionInfo.class);
		
		Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "���������Ϣ: " + updateVersioInfo);
		
		// �����Ի�����ʾ�Ƿ����
		new DialogUtil().alertUpdateInfos(BootActivity.this, updateVersioInfo.getDesc(),handler);
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
		
		// ���汾��Ϣ�Ƿ���Ҫ����
		new HttpUtil(this,handler).checkUpdateInfos(versionName,start);
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
