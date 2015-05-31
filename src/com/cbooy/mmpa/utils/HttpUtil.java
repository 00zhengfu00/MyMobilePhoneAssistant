package com.cbooy.mmpa.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.cbooy.mmpa.R;

/**
 * Http ���� ������
 * @author chenhao24
 *
 */
public class HttpUtil {
	
	public static void checkUpdateInfos(final Context context,final String oldVersion,final Handler handler,final long start){
		
		// �������
		if(context == null || oldVersion == null){
			return;
		}
		
		// �����߳�
		new Thread(new Runnable(){
			@Override
			public void run() {
				
				URL url = null;
				
				HttpURLConnection conn = null;
				
				Message msg = Message.obtain();
				
				try {
					url = new URL(context.getString(R.string.check_update_url));
					
					conn = (HttpURLConnection) url.openConnection();
					
					conn.setRequestMethod("GET");
					
					conn.setConnectTimeout(4 * 1000);
					
					int code = conn.getResponseCode();
					
					// �����ɹ�
					if(code / 200 == 1){
						
						// ��ȡ ������ ��ת�����ַ���
						String res = StreamUtil.readFromStream(conn.getInputStream());
						
						Log.i(StaticDatas.HTTPUTIL_LOG_TAG, "�������update�ɹ�,���ؽ��Ϊ " + res);
						
						JSONObject jsObj = new JSONObject(res);
						
						if(! oldVersion.equals(jsObj.getString("version"))){
							msg.what = StaticDatas.VERSION_NEED_UPDATE;
							
							msg.obj = res;
						}else{
							// ����Ҫ������
							msg.what = StaticDatas.VERSION_NO_NEED_UPDATE;
						}
						
						jsObj = null;
						
						res = null;
					}
				} catch (MalformedURLException e) {
					// URL����
					msg.what = StaticDatas.URL_ERROR;
					
					Log.e(StaticDatas.HTTPUTIL_LOG_TAG, "URL�����쳣��ϢΪ" + e.getMessage());
				} catch (IOException e) {
					// �������ӳ���
					msg.what = StaticDatas.URL_CONNECTION_ERROR;
					
					Log.e(StaticDatas.HTTPUTIL_LOG_TAG, "URL���ӳ����쳣��ϢΪ" + e.getMessage());
				} catch (JSONException e) {
					// JSONת������
					msg.what = StaticDatas.JSON_CONVERTOR_ERROR;
					
					Log.e(StaticDatas.HTTPUTIL_LOG_TAG, "JSONת�������쳣��ϢΪ" + e.getMessage());
				}finally{
					
					// �ڴ˼����ʱ
					long current = System.currentTimeMillis();
					
					long dtime = current - start;
					
					Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "ʱ���" + String.valueOf(dtime));
					
					if(dtime < 2000){
						SystemClock.sleep(2000 - dtime);
						Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "˯" + String.valueOf(2000 - dtime));
					}
					
					
					// ����Ϣ����
					handler.sendMessage(msg);
					
					msg = null;
					conn = null;
					url = null;
				}
			}}).start();
	}
}
