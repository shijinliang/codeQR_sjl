package com.allen.qrcode;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.myqrcode.R;

public class ShowActivity extends BaseActivity {
	private TextView txt1, TV_show_type, iamgeTV;
	private String message;
	private Button btn_URL, btnMore, btn_TEXT;
	private Bundle bundle;

	private LinearLayout miniLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		AdManager.getInstance(this).init(Constants.APPId, Constants.appSecret, false);
		OffersManager.getInstance(this).onAppLaunch();
		
		SpotManager.getInstance(this).loadSpotAds();
		// 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);

		initView();
		initIntent();

	}

	private void initView() {
		txt1 = (TextView) findViewById(R.id.txt1);
		TV_show_type = (TextView) findViewById(R.id.show_type);
		btn_URL = (Button) findViewById(R.id.url_button);
		btn_TEXT = (Button) findViewById(R.id.text_button);
		btnMore = (Button) findViewById(R.id.btn_set);
		iamgeTV = (TextView) findViewById(R.id.image_title);

		btn_URL.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				playVibrate();
				Intent urlIntent = new Intent("android.intent.action.VIEW", Uri
						.parse(message));
				startActivity(urlIntent);

			}
		});
		btn_TEXT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				playVibrate();
				copy(message, ShowActivity.this);
			}
		});
		btnMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				playVibrate();
				Toast.makeText(ShowActivity.this, "本软件由多米广告赞助支持！", 1).show();
				showInterstitialAd(ShowActivity.this);

				Intent intent = new Intent();
				intent.setClass(ShowActivity.this, SetingActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initIntent() {
		bundle = getIntent().getExtras();
		message = bundle.getString("msg");
		this.showBannerAD();
		if (isURL(message)) {
			
			TV_show_type.setText("网址:");
			iamgeTV.setBackgroundResource(R.drawable.url72);
			btn_URL.setVisibility(View.VISIBLE);
		} else {
			btn_TEXT.setVisibility(View.VISIBLE);
		}
		if (bundle != null) {
			txt1.setText(message);

		}
	}

	private boolean isURL(String msg) {
		// String regex =
		// "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		// Pattern patt = Pattern.compile(regex);
		// Matcher matcher = patt.matcher(msg);
		// boolean isMatch = matcher.matches();
		// if (!isMatch) {
		// System.out.println("您输入的URL地址不正确");
		// return false;
		// } else {
		// return true;
		// }
		if (msg.startsWith("HTTP://") || msg.startsWith("HTTPS://")
				|| msg.startsWith("http://") || msg.startsWith("https://")) {
			return true;
		} else {
			return false;
		}

	}

	private static final long VIBRATE_DURATION = 50;

	private void playVibrate() {

		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(VIBRATE_DURATION);

	}

	public static void copy(String str, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(str);
		Toast.makeText(context, "复制成功", 0).show();
	}

	public void showInterstitialAd(Activity activity) {

	}

	public void showBannerAD() {
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);

		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.linearLayout);

		// 将广告条加入到布局中
		adLayout.addView(adView);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		OffersManager.getInstance(this).onAppExit();
	}
}
