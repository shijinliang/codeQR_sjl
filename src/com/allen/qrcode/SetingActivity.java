package com.allen.qrcode;

import net.youmi.android.offers.OffersManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.myqrcode.R;

public class SetingActivity extends BaseActivity {
	ListView ListView;

	// 展示的文字
	private String[] texts = new String[] { "意见反馈", "精品推荐", "更多内容敬请期待" };
	// 展示的图片
	private int[] images = new int[] { R.drawable.xa, R.drawable.xa,
			R.drawable.xa, R.drawable.xa };

	ImageView imageView;
	TextView title;

	LeftMenuAdapter leftMenuAdapter;
	LeftMenuOnClick leftMenuOnClick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seting);
		imageView = (ImageView) findViewById(R.id.btn_back);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SetingActivity.this.finish();

			}
		});
		leftMenuOnClick = new LeftMenuOnClick();
		ListView = (ListView) findViewById(R.id.listview);
		leftMenuAdapter = new LeftMenuAdapter();
		ListView.setAdapter(leftMenuAdapter);
		ListView.setOnItemClickListener(leftMenuOnClick);
		
		
		SpotManager.getInstance(SetingActivity.this).showSpotAds(SetingActivity.this, new SpotDialogListener() {
			
			@Override
			public void onSpotClosed() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "展示关闭", 1).show();
			}
			
			@Override
			public void onShowSuccess() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "展示成功", 1).show();
			}
			
			@Override
			public void onShowFailed() {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "展示失败", 1).show();
			}
		});

	}

	public class LeftMenuOnClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			switch (arg2) {

			case 0:

				sendEmail(SetingActivity.this);
				break;
			case 1:
				OffersManager.getInstance(SetingActivity.this).showOffersWall();

				break;
			/*case 2:
				Uri weatheruri = Uri
						.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.allen.weather");
				Intent intent1 = new Intent(Intent.ACTION_VIEW, weatheruri);
				startActivity(intent1);
				break;
				*/
			case 2:
				Toast.makeText(SetingActivity.this, "更多内容正在加班开发中。。。", 0).show();
				break;

			}

		}

	}

	public class LeftMenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return texts.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			if (view == null) {
				view = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.item_seting, null);

				imageView = (ImageView) view
						.findViewById(R.id.imageView_left_menu);
				title = (TextView) view.findViewById(R.id.left_menu_title);

			}

			imageView.setImageResource(images[position]);
			title.setText(texts[position]);
			return view;
		}

	}

	/**
	 * 这是一个简单的测试，不支持带附件，多人，抄送发送等。
	 * 
	 * @param context
	 */
	public void sendEmail(Context context) {
		// Intent intent = new Intent();
		// intent.setData(Uri.parse("mailto:"));
		Intent intent = new Intent(Intent.ACTION_SEND);

		String[] tos = { "sjl_leaf@163.com" };

		intent.putExtra(Intent.EXTRA_EMAIL, tos); // 收件者
		intent.setType("message/rfc822"); // 设置邮件格式

		/* 设置邮件的标题 */
		intent.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
		/* 设置邮件的内容 */
		intent.putExtra(Intent.EXTRA_TEXT, "请详细描述使用本程序过程中遇到的问题，我们会第一时间给您解决！");
		// 开始调用
		context.startActivity(intent);
	}

}
