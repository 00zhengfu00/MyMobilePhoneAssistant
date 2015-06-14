package com.cbooy.mmpa.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.AntiTheftDialog;
import com.cbooy.mmpa.utils.StaticDatas;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

	private GridView gvFuncLists;

	private SharedPreferences sp;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			// ȷ��
			if (msg.what == StaticDatas.ANTITHEFT_DIALOG_CONFIRM) {
				boolean isConfirm = (boolean) msg.obj;

				if (isConfirm) {
					goAntiTheftActivity();
				}
			}

			// ����
			if (msg.what == StaticDatas.ANTITHEFT_DIALOG_ENTER) {
				boolean isEnter = (boolean) msg.obj;

				if (isEnter) {
					goAntiTheftActivity();
				}
			}
		}
	};

	private String[] names = new String[] { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���",
			"����ͳ��", "�ֻ�ɱ��", "��������", "�߼�����", "��������" };

	private int[] ids = new int[] { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_activity);

		gvFuncLists = (GridView) this.findViewById(R.id.gv_func_lists);

		gvFuncLists.setAdapter(new MyAdapter());

		sp = getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);

		gvFuncLists.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ���� ��������
				if (8 == position) {
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class);

					startActivity(intent);
				}

				// �ֻ�����
				if (0 == position) {

					// ȡ������
					String oldPasswd = sp.getString(StaticDatas.CONFIG_PASSWD, null);

					// û������,���� �������벢ȷ��
					if (TextUtils.isEmpty(oldPasswd)) {
						new AntiTheftDialog(HomeActivity.this, handler).confirmDialog();
					} else {
						// �Ѿ����ã�������������
						new AntiTheftDialog(HomeActivity.this, handler).enterPasswd(oldPasswd);
					}
				}
			}
		});
	}

	/**
	 * �л��� ����ҳ��
	 */
	protected void goAntiTheftActivity() {
		Intent intent = new Intent(this, AntiTheftActivity.class);

		startActivity(intent);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView != null) {
				return convertView;
			}

			View item = View.inflate(HomeActivity.this,R.layout.home_func_list_item, null);

			ImageView imgItem = (ImageView) item.findViewById(R.id.img_func_list_item);

			imgItem.setImageResource(ids[position]);

			TextView tvItem = (TextView) item.findViewById(R.id.tv_func_list_item);

			tvItem.setText(names[position]);

			return item;
		}

	}
}
