package com.allen.qrcode;

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

import com.liang.myqrcode.R;
import com.eadver.offer.banner.BannerSDK;
import com.eadver.offer.sdk.YjfSDK;
import com.eadver.offer.sdk.view.BannerView;

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
		
		YjfSDK.getInstance(this, null).initInstance( "77135","EM6ZEUTKPJ9XKP3V4NYMUFH4YJYHSXNA47", "85247","" );
		YjfSDK.getInstance(this, null).setCoopInfo(null);
		//通知栏上通知当天的新任务和可完成任务的个数
		YjfSDK.getInstance(this, null).setDoNotify(false);

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

		miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);

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
				Toast.makeText(ShowActivity.this, "本软件由易积分平台赞助支持！", 1).show();
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
		if (isURL(message)) {
			this.showBannerAD();
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
		BannerView bannerView = BannerSDK.getInstance(this).getBanner();
		LinearLayout adLayout = (LinearLayout)findViewById(R.id.linearLayout);
		BannerSDK.getInstance(this,null).showBanner(bannerView);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		YjfSDK.getInstance(this, null).recordAppClose();
		super.onDestroy();
		
	}
}
