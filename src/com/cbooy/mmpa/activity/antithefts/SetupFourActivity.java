package com.cbooy.mmpa.activity.antithefts;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.AntiTheftActivity;
import com.cbooy.mmpa.utils.StaticDatas;

public class SetupFourActivity extends BaseSetupActivity {
	
	private CheckBox chkBoxProtected;

	@Override
	public void nextStep() {
		Editor editor = sp.edit();

		editor.putBoolean("is_setup", true);

		editor.commit();

		Intent intent = new Intent(this, AntiTheftActivity.class);

		startActivity(intent);

		finish();
		
		overridePendingTransition(R.anim.next_tran_in, R.anim.next_tran_out);
	}

	@Override
	public void init() {
		setContentView(R.layout.setup_four_activity);

		preActivity = SetupThreeActivity.class;
		
		chkBoxProtected = (CheckBox) this.findViewById(R.id.chk_is_protected);
		
		boolean is_protected = sp.getBoolean(StaticDatas.CONFIG_IS_PROTECTED, false);
		
		// ���� ��ʾ ����
		setProtectedView(is_protected);
		
		chkBoxProtected.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				setProtectedView(isChecked);
				
				// ��������
				Editor editor = sp.edit();
				
				editor.putBoolean(StaticDatas.CONFIG_IS_PROTECTED, isChecked);
				
				editor.commit();
			}});
	}
	
	private void setProtectedView(boolean isChecked){
		if(isChecked){
			chkBoxProtected.setChecked(isChecked);
			chkBoxProtected.setText("���ѿ�����������");
		}else{
			chkBoxProtected.setChecked(isChecked);
			chkBoxProtected.setText("��δ������������");
		}
	}
}
