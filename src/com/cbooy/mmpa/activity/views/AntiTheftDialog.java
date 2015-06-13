package com.cbooy.mmpa.activity.views;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.MD5Utils;
import com.cbooy.mmpa.utils.StaticDatas;

public class AntiTheftDialog {
	
	private Context context;
	
	private SharedPreferences sp;
	
	private Handler handler;
	
	private Message msg;
	
	public AntiTheftDialog(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		msg = Message.obtain();
	}
	
	/**
	 * ��������
	 */
	public void confirmDialog() {
		
		msg.what = StaticDatas.ANTITHEFT_DIALOG_CONFIRM;
		
		AlertDialog.Builder builder = new Builder(context);
		
		final AlertDialog dialog = builder.create();
		
		View view = View.inflate(context, R.layout.anti_theft_confirm, null);
		
		final EditText edPasswd1 = (EditText) view.findViewById(R.id.et_confirm_passwd1);
		
		final EditText edPasswd2 = (EditText) view.findViewById(R.id.et_confirm_passwd2);
		
		Button btnOK = (Button) view.findViewById(R.id.btn_confirm_ok);
		
		// ���ȷ��
		btnOK.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				String passwd1 = edPasswd1.getText().toString().trim();
				String passwd2 = edPasswd2.getText().toString().trim();
				
				if(TextUtils.isEmpty(passwd1) && TextUtils.isEmpty(passwd2)){
					Toast.makeText(context, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!passwd1.equals(passwd2)){
					Toast.makeText(context, "�������벻һ��", Toast.LENGTH_SHORT).show();
					edPasswd1.setText("");
					edPasswd2.setText("");
					return;
				}
				
				dialog.dismiss();
				
				// �������óɹ� �洢������
				Editor editor = sp.edit();
				
				editor.putString("passwd", MD5Utils.md5(passwd2));
				
				editor.commit();
				
				msg.obj = true;
				
				handler.sendMessage(msg);
			}});
		
		Button btnCancel = (Button) view.findViewById(R.id.btn_confirm_cancel);
		
		// ���ȡ��
		btnCancel.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}});
		
		dialog.setView(view, 0, 0, 0, 0);
		
		dialog.show();
	}
	
	/**
	 * ��������
	 * @param oldPasswd
	 */
	public void enterPasswd(final String oldPasswd) {
		
		msg.what = StaticDatas.ANTITHEFT_DIALOG_ENTER;
		
		AlertDialog.Builder builder = new Builder(context);
		
		final AlertDialog dialog = builder.create();
		
		View view = View.inflate(context, R.layout.anti_theft_enter, null);
		
		final EditText edPasswd1 = (EditText) view.findViewById(R.id.et_enter_passwd1);
		
		Button btnOK = (Button) view.findViewById(R.id.btn_enter_ok);
		
		// ���ȷ��
		btnOK.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				String passwd1 = edPasswd1.getText().toString().trim();
				
				if(TextUtils.isEmpty(passwd1)){
					Toast.makeText(context, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!MD5Utils.md5(passwd1).equals(oldPasswd)){
					Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
					edPasswd1.setText("");
					return;
				}
				
				dialog.dismiss();
				
				msg.obj = true;
				
				handler.sendMessage(msg);
			}});
		
		Button btnCancel = (Button) view.findViewById(R.id.btn_enter_cancel);
		
		// ���ȡ��
		btnCancel.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}});
		
		dialog.setView(view, 0, 0, 0, 0);
		
		dialog.show();
	}
}	
