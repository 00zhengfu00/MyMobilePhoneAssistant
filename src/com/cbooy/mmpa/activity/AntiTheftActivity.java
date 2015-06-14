package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.antithefts.SetupOneActivity;
import com.cbooy.mmpa.utils.StaticDatas;

public class AntiTheftActivity extends Activity {

	private SharedPreferences sp = null;
	
	private TextView tvSafePhone;
	
	private ImageView imageLock;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ���� ���� ���ҳ
		setContentView(R.layout.antitheft_activity);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		boolean isSetup = sp.getBoolean("is_setup", false);
		
		tvSafePhone = (TextView) this.findViewById(R.id.safe_phone);
		
		imageLock = (ImageView) this.findViewById(R.id.iv_lock);
		
		if (!isSetup) {
			// ����������ҳ
			Intent intent = new Intent(this, SetupOneActivity.class);

			startActivity(intent);

			finish();
		} else {
			String safePhone = sp.getString(StaticDatas.CONFIG_SAFE_PHONE, null);
			
			Log.i(StaticDatas.ANTITHEFTACTIVITY_LOG_TAG, "��ȫ���� : " + safePhone);
			
			if(! TextUtils.isEmpty(safePhone)){
				tvSafePhone.setText(safePhone);
			}
			
			boolean is_protected = sp.getBoolean(StaticDatas.CONFIG_IS_PROTECTED, false);
			
			Log.i(StaticDatas.ANTITHEFTACTIVITY_LOG_TAG, "�Ƿ������� : " + is_protected);
			
			if(is_protected){
				imageLock.setImageResource(R.drawable.lock);
			}else{
				imageLock.setImageResource(R.drawable.unlock);
			}
		}
	}

	public void reEnterSetup(View v) {
		// ����������ҳ
		Intent intent = new Intent(this, SetupOneActivity.class);

		startActivity(intent);

		finish();
	}
}
