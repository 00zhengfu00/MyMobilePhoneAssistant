package com.cbooy.mmpa.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class InstallerApkUtil {

	private File f;
	
	private Context context;

	public InstallerApkUtil(Context context,File f) {
		this.context = context;
		this.f = f;
	}

	/**
	 * ��װ�ļ�
	 * 
	 * @param f
	 */
	public void install() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(f),"application/vnd.android.package-archive");

		context.startActivity(intent);
	}

	/**
	 * ж���ļ�
	 * 
	 * @param f
	 */
	public void unInstall() {

	}
}
