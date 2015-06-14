package com.cbooy.mmpa.activity.antithefts;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.SettingItemView;
import com.cbooy.mmpa.utils.StaticDatas;

public class SetupTwoActivity extends BaseSetupActivity implements OnClickListener{
	
	// Sim��������
	private SettingItemView setItem;
	
	private String sim;
	
	private TelephonyManager tm;
	
	@Override
	public void init() {
		setContentView(R.layout.setup_two_activity);
		
		nextActivity = SetupThreeActivity.class;
		
		preActivity = SetupOneActivity.class;
		
		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		
		// ��ȡ ����Item����
		setItem = (SettingItemView) this.findViewById(R.id.is_bind_sim_card);
		
		// ��Ӽ�����
		setItem.setOnClickListener(this);
		
		// ��ȡ���õ� sim ��
		sim = sp.getString(StaticDatas.CONFIG_SIM_SERIA_NUM, null);
		
		// �п����� 
		if(TextUtils.isEmpty(sim)){
			setItem.setCheckd(false);
		}else{
			setItem.setCheckd(true);
		}
	}
	
	@Override
	public void nextStep() {
		
		if(TextUtils.isEmpty(sim)){
			bindSim();
		}
		
		Intent intent = new Intent(this,SetupThreeActivity.class);
		
		startActivity(intent);
		
		finish();
		
		overridePendingTransition(R.anim.next_tran_in, R.anim.next_tran_out);
	}

	@Override
	public void onClick(View v) {
		
		// ��ȡ��ǰ״̬
		boolean currentStatus = setItem.isChecked();
		
		// ����
		setItem.setCheckd(! currentStatus);
		
		if(! currentStatus){
			bindSim();
		}
	}
	
	private void bindSim(){
		Editor editor = sp.edit();
		
		String num = tm.getSimSerialNumber();
		
		if(TextUtils.isEmpty(num)){
			Toast.makeText(this, "û��SIM��", Toast.LENGTH_SHORT).show();
			return;
		}
		
		editor.putString(StaticDatas.CONFIG_SIM_SERIA_NUM, num);
		
		editor.commit();
	}
}
