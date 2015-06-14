package com.cbooy.mmpa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.StaticDatas;

public class SmsMonitorReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, Context.MODE_PRIVATE);
		
		Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "�ж����յ�...");
		
		// �ж��Ƿ�����������
		boolean is_protected = sp.getBoolean(StaticDatas.CONFIG_IS_PROTECTED, false);
		
		// û�п����������� ��������
		if(! is_protected){
			return;
		}
		
		String safeNum = sp.getString(StaticDatas.CONFIG_SAFE_PHONE, null);
		
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		
		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			
			// ������
			String sender = smsMessage.getOriginatingAddress();
			
			Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "������ : " + sender);
			
			// �� ��ȫ����
			if(safeNum.equals(sender)){
				// �ж� �Ƿ�Ϊָ������
				String smsBody = smsMessage.getMessageBody();
				
				Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "������ : " + smsBody);
				
				//�õ��ֻ���GPS
				if("#*location*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "�õ��ֻ���GPS");
					
					abortBroadcast();
				}
				
				//���ű���Ӱ��
				if("#*alarm*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "���ű���Ӱ��");
					
					MediaPlayer mp = MediaPlayer.create(context, R.raw.ylzs);
					
					// ����ѭ������
					// mp.setLooping(true);
					
					// ���� ����
					// mp.setVolume(1.0f, 1.0f);
					
					// ����
					mp.start();
					
					abortBroadcast();
				}
				
				//Զ���������
				if("#*wipedata*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "Զ���������");
					
					abortBroadcast();
				}
				
				//Զ������
				if("#*lockscreen*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "Զ������");
					
					abortBroadcast();
				}
			}
		}
	}
}
