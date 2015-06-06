package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.SettingItemRelativeLayout;

public class SettingActivity extends Activity {

	private SettingItemRelativeLayout settingView;
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting_activity);
		
		sp = this.getSharedPreferences("update_info", MODE_PRIVATE);
		
		final Editor editor = sp.edit();
		
		settingView = (SettingItemRelativeLayout) this.findViewById(R.id.item_setting);
		
		settingView.setTitle("�����Ƿ��Զ�����");
		
		boolean isUpdate = sp.getBoolean("is_update", true);
		
		if(isUpdate){
			setChecked(true,"�����Զ�����");
		}else{
			setChecked(false,"ȡ���Զ�����");
		}
		
		settingView.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// �Ѿ���ѡ��
				if(settingView.isChecked()){
					editor.putBoolean("is_update", false);
					
					setChecked(false,"ȡ���Զ�����");
				}else{	// û�б�ѡ��
					
					editor.putBoolean("is_update", true);
					
					setChecked(true,"�����Զ�����");
				}
				
				editor.commit();
			}});
	}
	
	/**
	 * ��װ����
	 */
	private void setChecked(boolean checked,String desc){
		settingView.setCheckd(checked);
		settingView.setDesc(desc);
	}
}
