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
		
		Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "有短信收到...");
		
		// 判断是否开启防盗保护
		boolean is_protected = sp.getBoolean(StaticDatas.CONFIG_IS_PROTECTED, false);
		
		// 没有开启防盗保护 不做处理
		if(! is_protected){
			return;
		}
		
		String safeNum = sp.getString(StaticDatas.CONFIG_SAFE_PHONE, null);
		
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		
		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			
			// 发送者
			String sender = smsMessage.getOriginatingAddress();
			
			Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "发送者 : " + sender);
			
			// 是 安全号码
			if(safeNum.equals(sender)){
				// 判断 是否为指定命令
				String smsBody = smsMessage.getMessageBody();
				
				Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "短信体 : " + smsBody);
				
				//得到手机的GPS
				if("#*location*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "得到手机的GPS");
					
					abortBroadcast();
				}
				
				//播放报警影音
				if("#*alarm*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "播放报警影音");
					
					MediaPlayer mp = MediaPlayer.create(context, R.raw.ylzs);
					
					// 设置循环播放
					// mp.setLooping(true);
					
					// 设置 音量
					// mp.setVolume(1.0f, 1.0f);
					
					// 播放
					mp.start();
					
					abortBroadcast();
				}
				
				//远程清除数据
				if("#*wipedata*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "远程清除数据");
					
					abortBroadcast();
				}
				
				//远程锁屏
				if("#*lockscreen*#".equals(smsBody)){
					Log.i(StaticDatas.SMSMONITORRECEIVER_LOG_TAG, "远程锁屏");
					
					abortBroadcast();
				}
			}
		}
	}
}
