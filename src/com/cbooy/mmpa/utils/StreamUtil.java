package com.cbooy.mmpa.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * ����ش�������
 * @author chenhao24
 *
 */
public class StreamUtil {
	
	/**
	 * @param is ������
	 * @return String ���ص��ַ���
	 * @throws IOException
	 */
	public static String readFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		
		int len = 0;
		
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		
		is.close();
		
		String result = baos.toString();
		
		baos.close();
		
		return result;
	}
	
	/**
	 * �ַ�������ת��
	 * @param resource
	 * @param resCode
	 * @param destCode
	 * @return
	 */
	public static String decodeString(String resource,String resCode,String destCode){
		try {
			return new String(resource.getBytes(resCode),destCode);
		} catch (UnsupportedEncodingException e) {
			return resource;
		}
	}
}
