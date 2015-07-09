package com.allen.qrcode;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.liang.myqrcode.R;
//import com.readystatesoftware.systembartint.SystemBarTintManager;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// // ����״̬���Ĺ���ʵ��
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// // ����״̬������
		// tintManager.setStatusBarTintEnabled(true);
		// // �����������
		// tintManager.setNavigationBarTintEnabled(true);
		// tintManager.setStatusBarTintColor(getResources().getColor(R.color.red));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		//SystemBarTintManager tintManager = new SystemBarTintManager(this);
		//tintManager.setStatusBarTintEnabled(true);
		//tintManager.setStatusBarTintResource(R.color.hailan);

	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
