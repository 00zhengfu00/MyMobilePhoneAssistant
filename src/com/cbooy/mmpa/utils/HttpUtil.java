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
 * Http 请求 工具类
 * @author chenhao24
 *
 */
public class HttpUtil {
	
	public static void checkUpdateInfos(final Context context,final String oldVersion,final Handler handler,final long start){
		
		// 参数检查
		if(context == null || oldVersion == null){
			return;
		}
		
		// 开启线程
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
					
					// 联网成功
					if(code / 200 == 1){
						
						// 获取 输入流 并转换成字符串
						String res = StreamUtil.readFromStream(conn.getInputStream());
						
						Log.i(StaticDatas.HTTPUTIL_LOG_TAG, "联网检查update成功,返回结果为 " + res);
						
						JSONObject jsObj = new JSONObject(res);
						
						if(! oldVersion.equals(jsObj.getString("version"))){
							msg.what = StaticDatas.VERSION_NEED_UPDATE;
							
							msg.obj = res;
						}else{
							// 不需要检查更新
							msg.what = StaticDatas.VERSION_NO_NEED_UPDATE;
						}
						
						jsObj = null;
						
						res = null;
					}
				} catch (MalformedURLException e) {
					// URL错误
					msg.what = StaticDatas.URL_ERROR;
					
					Log.e(StaticDatas.HTTPUTIL_LOG_TAG, "URL出错，异常信息为" + e.getMessage());
				} catch (IOException e) {
					// 网络连接出错
					msg.what = StaticDatas.URL_CONNECTION_ERROR;
					
					Log.e(StaticDatas.HTTPUTIL_LOG_TAG, "URL链接出错，异常信息为" + e.getMessage());
				} catch (JSONException e) {
					// JSON转换出错
					msg.what = StaticDatas.JSON_CONVERTOR_ERROR;
					
					Log.e(StaticDatas.HTTPUTIL_LOG_TAG, "JSON转换出错，异常信息为" + e.getMessage());
				}finally{
					
					// 在此计算耗时
					long current = System.currentTimeMillis();
					
					long dtime = current - start;
					
					Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "时间差" + String.valueOf(dtime));
					
					if(dtime < 2000){
						SystemClock.sleep(2000 - dtime);
						Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "睡" + String.valueOf(2000 - dtime));
					}
					
					
					// 将消息发出
					handler.sendMessage(msg);
					
					msg = null;
					conn = null;
					url = null;
				}
			}}).start();
	}
}
