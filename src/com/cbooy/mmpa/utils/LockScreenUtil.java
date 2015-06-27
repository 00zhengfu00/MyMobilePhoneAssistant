package com.cbooy.mmpa.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.cbooy.mmpa.receiver.MyAdmin;

public class LockScreenUtil {

	private static Context context = null;
	
	/**
	 * ���ò��Է���
	 */
	private static DevicePolicyManager dpm = null;
	
	private LockScreenUtil(){
		
	}

	public static LockScreenUtil inst(Context ct) {
		
		context = ct;
		
		dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		
		return new LockScreenUtil();
	}
	
	/**
	 * ��������,���ж��Ƿ���Ȩ��
	 */
	public void lockScreen(){
		
		ComponentName who = new ComponentName(context,MyAdmin.class);
		
		if(dpm.isAdminActive(who)){
			
			// ����
			dpm.lockNow();
			
			//������������
			dpm.resetPassword("123", 0);
			
			//���Sdcard�ϵ�����
			// dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
		}else{
			// û��Ȩ��,��Ҫ�ȿ�ͨ
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			
	        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
	        
	        //��������ԱȨ��
	        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"һ������");
	        
	        context.startActivity(intent);
		}
	}
	
	public void removePermission() {
		
		// 1.���������ԱȨ��
		ComponentName mDeviceAdminSample = new ComponentName(context,MyAdmin.class);
		
		dpm.removeActiveAdmin(mDeviceAdminSample);
		
		// 2.��ͨӦ�õ�ж��
		Intent intent = new Intent();
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		
		intent.setAction("android.intent.action.VIEW");
		
		intent.addCategory("android.intent.category.DEFAULT");
		
		intent.setData(Uri.parse("package:" + context.getPackageName()));
		
		context.startActivity(intent);
	}
}
