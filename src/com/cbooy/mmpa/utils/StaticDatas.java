package com.cbooy.mmpa.utils;

/**
 * Ϊ�˼�����ľ�̬�����Ķ������ȫ���ľ�̬��������ȫ�������������һ������
 * 
 * @author chenhao24
 * 
 */
public class StaticDatas {
	// �汾��Ϣ������,��Ҫ����
	public final static int VERSION_NEED_UPDATE = 10001;

	// �汾��Ϣ������,����Ҫ����
	public final static int VERSION_NO_NEED_UPDATE = 10002;

	// ���������URL����
	public final static int URL_ERROR = 10003;

	// ���ؽ��ȵ� ״̬��
	public final static int DOWNLOAD_PROCESSING = 10004;

	// URL���ӳ���
	public final static int URL_CONNECTION_ERROR = 10004;

	// JSONת������
	public final static int JSON_CONVERTOR_ERROR = 10005;

	// �Ƿ������ļ�״̬��
	public final static int IS_DOWNLOAD_NEW_VERSION = 10006;

	// ϵͳ�����Ի���ȡ��
	public final static int DIALOG_DISMISS = 10007;

	// ������������ȷ��
	public final static int ANTITHEFT_DIALOG_CONFIRM = 10008;

	// ����������������
	public final static int ANTITHEFT_DIALOG_ENTER = 10009;

	// �ļ����سɹ�
	public final static int DOWNLOAD_SUCCESS = 0;

	// com.cbooy.mmpa.utils.HttpUtil ��־ TAG
	public final static String HTTPUTIL_LOG_TAG = "HttpUtil";

	// com.cbooy.mmpa.BootActivity ��־TAG
	public final static String BOOTACTIVITY_LOG_TAG = "BootActivity";

	// com.cbooy.mmpa.activity.HomeActivity ��־ TAG
	public final static String HOMEACTIVITY_LOG_TAG = "HomeActivity";

	// com.cbooy.mmpa.activity.antithefts.BaseSetupActivity ��־ TAG
	public final static String BASESETUPACTIVITY_LOG_TAG = "BaseSetupActivity";

	// com.cbooy.mmpa.receiver.BootCompletedReceiver ��־ TAG
	public final static String BootCompletedReceiver_LOG_TAG = "BootCompletedReceiver";

	// com.cbooy.mmpa.utils.ContactsHelperUtil ��־ TAG
	public final static String CONTACTSHELPERUTIL_LOG_TAG = "ContactsHelperUtil";
	
	// com.cbooy.mmpa.activity.antithefts.ContactsReaderActivity  ��־ TAG
	public final static String CONTACTSREADERACTIVITY_LOG_TAG = "ContactsReaderActivity";
	
	// com.cbooy.mmpa.activity.AntiTheftActivity ��־ TAG
	public final static String ANTITHEFTACTIVITY_LOG_TAG = "AntiTheftActivity";
	
	// com.cbooy.mmpa.receiver.SmsMonitorReceiver ��־ TAG 
	public final static String SMSMONITORRECEIVER_LOG_TAG = "SmsMonitorReceiver";
	
	// ���ð�ȫ����� ��ǩ
	public final static String CONFIG_SAFE_PHONE = "bind_phone";
	
	// ���� SIM�� �󶨵� ���к�
	public final static String CONFIG_SIM_SERIA_NUM = "sim";
	
	// common SharedPreferences config
	public final static String SP_CONFIG_FILE = "config";
	
	// �������õ�����
	public final static String CONFIG_PASSWD = "passwd";
	
	// �����Ƿ��Զ�����
	public final static String CONFIG_IS_UPDATE = "is_update";
	
	// �����Ƿ�������
	public final static String CONFIG_IS_PROTECTED = "is_procted";
}
