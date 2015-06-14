package com.cbooy.mmpa.service;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.cbooy.mmpa.utils.StaticDatas;

public class BestLocationListener implements LocationListener {

	private Editor editor = null;
	
	private InputStream ins = null;
	
	public BestLocationListener(Context context) {
		SharedPreferences sp = context.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, Context.MODE_PRIVATE);
		
		try {
			ins = context.getAssets().open("axisoffset.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		editor = sp.edit();
	}

	/**
	 * ���� λ�� �仯
	 */
	@Override
	public void onLocationChanged(Location location) {
		//��ȡ��ȷ��
		//float accuracy = location.getAccuracy();
		
		try {
			ModifyOffset offset = ModifyOffset.getInstance(ins);
			
			PointDouble pd = offset.c2s(new PointDouble(location.getLongitude(),location.getLatitude()));
			
			//��ȡ����
			double longitude = pd.x;
			
			//��ȡγ��
			double latitude = pd.y;
			
			//��λ����Ϣ������
			editor.putString(StaticDatas.CONFIG_LOCATION_INFO, new StringBuilder().append(longitude).append(":").append(latitude).toString());
			
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

}
