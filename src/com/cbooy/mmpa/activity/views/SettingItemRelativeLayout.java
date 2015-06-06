package com.cbooy.mmpa.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.R;

public class SettingItemRelativeLayout extends RelativeLayout {

	private TextView textTitle;
	
	private TextView textDesc;
	
	private CheckBox chkboxSelected;
	
	public SettingItemRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView(context);
	}

	private void initView(Context context) {
		View view = View.inflate(context, R.layout.setting_items, this);
		
		textTitle = (TextView) view.findViewById(R.id.tv_title);
		
		textDesc = (TextView) view.findViewById(R.id.tv_desc);
		
		chkboxSelected = (CheckBox) view.findViewById(R.id.cb_status);
	}
	
	/**
	 * ���ñ���
	 * @param title
	 */
	public void setTitle(String title){
		textTitle.setText(title);
	}
	
	/**
	 * ��������
	 * @param desc
	 */
	public void setDesc(String desc){
		textDesc.setText(desc);
	}
	
	/**
	 * �����Ƿ񱻵��
	 * @param checked
	 */
	public void setCheckd(boolean checked){
		chkboxSelected.setChecked(checked);
	}
	
	/**
	 * ��ȡ�����ؼ��Ƿ񱻵��
	 * @return
	 */
	public boolean isChecked(){
		return chkboxSelected.isChecked();
	}
}
