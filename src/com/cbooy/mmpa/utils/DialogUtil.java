package com.cbooy.mmpa.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;

public class DialogUtil {
	
	private Context context;
	
	private Handler handler;
	
	public DialogUtil(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
	}
	
	/**
	 * �����ĶԻ���,��ʾ�Ƿ���Ҫ����
	 * @param context
	 * @param desc
	 * @param handler
	 */
	public void alertUpdateInfos(String desc){
		
		AlertDialog.Builder dialogBuilder = new Builder(context);
		
		dialogBuilder.setTitle("ϵͳ����");
		
		dialogBuilder.setMessage(desc);
		
		final Message msg = Message.obtain();
		
		msg.what = StaticDatas.IS_DOWNLOAD_NEW_VERSION;
		
		// ���ȷ����ť�� ������
		dialogBuilder.setPositiveButton("��������", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
				
				msg.obj = true;
				
				handler.sendMessage(msg);
			}});
		
		// ���ȡ����ť�� ������
		dialogBuilder.setNegativeButton("�´���˵", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				msg.obj = false;
				
				handler.sendMessage(msg);
			}});
		
		// ���ȡ����ť�ļ���
		dialogBuilder.setOnCancelListener(new OnCancelListener(){
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				
				msg.what = StaticDatas.DIALOG_DISMISS;
				
				handler.sendMessage(msg);
			}});
		
		dialogBuilder.show();
	}
}
