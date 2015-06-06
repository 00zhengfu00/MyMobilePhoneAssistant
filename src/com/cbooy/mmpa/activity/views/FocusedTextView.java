package com.cbooy.mmpa.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * �Զ����н���
 * @author chenhao24
 *
 */
public class FocusedTextView extends TextView {

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * Ĭ�ϻ�ý���
	 */
	@Override
	public boolean isFocused() {
		return true;
	}
}
