package com.cbooy.mmpa.utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.cbooy.mmpa.R;

/**
 * Http ���� ������
 * @author chenhao24
 *
 */
public class HttpUtil {
	
	private Context context;
	
	private Handler handler;
	
	public HttpUtil(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
	}
	
	/**
	 * �����ļ�����Ŀ��sdcard
	 * @param downloadUrl
	 */
	public void downloadFiles(String downloadUrl){
		
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			
			String fileName = Environment.getExternalStorageDirectory().
					getAbsolutePath() + "/mmap" + 
					downloadUrl.substring(downloadUrl.lastIndexOf("/"));
			
			Log.i(StaticDatas.HTTPUTIL_LOG_TAG, "�����������ļ�����Ϊ: " + fileName);
			
			FinalHttp httpFinal = new FinalHttp();
			
			httpFinal.download(downloadUrl, fileName, new AjaxCallBack<File>() {

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					Log.i(StaticDatas.HTTPUTIL_LOG_TAG, "����ʧ��");
					Toast.makeText(context, "����ʧ��", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onLoading(long count, long current) {
					super.onLoading(count, current);
					
					Message msg = Message.obtain();
					
					msg.what = StaticDatas.DOWNLOAD_PROCESSING;
					
					msg.obj = current / count ;
					
					Log.i(StaticDatas.HTTPUTIL_LOG_TAG, "���� onLoading " + current / count);
					
					handler.sendMessage(msg);
				}

				@Override
				public void onSuccess(File t) {
					super.onSuccess(t);
					
					Log.i(StaticDatas.HTTPUTIL_LOG_TAG, "���سɹ�");
				}
				
			});
		}
	}
	
	/**
	 * ��������ķ���,���ڴ���������Context��˲������óɾ�̬����
	 * @param context
	 * @param oldVersion
	 * @param handler
	 * @param start
	 */
	public void checkUpdateInfos(final String oldVersion,final long start){
		
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
