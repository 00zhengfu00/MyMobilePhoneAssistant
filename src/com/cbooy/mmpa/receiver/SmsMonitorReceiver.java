package com.cbooy.mmpa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.service.GPSMonitorService;
import com.cbooy.mmpa.utils.LockScreenUtil;
import com.cbooy.mmpa.utils.StaticDatas;

public class SmsMonitorReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, Context.MODE_PRIVATE);
		
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
			
			// �� ��ȫ����
			if(safeNum.contains(sender)){
				// �ж� �Ƿ�Ϊָ������
				String smsBody = smsMessage.getMessageBody();
				
				//�õ��ֻ���GPS
				if("#*location*#".equals(smsBody)){
					
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "������ : " + smsBody);
					
					// ���� ��ȡλ�õ� ����
					Intent serviceIntent = new Intent(context,GPSMonitorService.class);
					
					context.startService(serviceIntent);
					
					String location = sp.getString(StaticDatas.CONFIG_LOCATION_INFO, null);
					
					if(!TextUtils.isEmpty(location)){
						SmsManager.getDefault().sendTextMessage(
								safeNum, 
								null, 
								new StringBuilder().append("��ȡλ����Ϣ : ").append(location).toString(), 
								null, 
								null
							);
					}
					
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "�õ��ֻ���GPS" + location);
					
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
					
					LockScreenUtil.inst(context).lockScreen();
					
					abortBroadcast();
				}
			}
		}
	}
}
