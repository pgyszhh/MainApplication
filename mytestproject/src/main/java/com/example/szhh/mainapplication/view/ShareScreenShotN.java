package com.example.szhh.mainapplication.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.szhh.mainapplication.R;

public class ShareScreenShotN extends BaseActivity {
	Context context;
	ImageView img;
	Bitmap bitmap;
	Bitmap bitmap_bg;
	LayoutParams param;
	MyViewN my;
	Button ok, cancel;
	private WindowManager windowManager;
	int height = 0;
	int width = 0;
	FrameLayout layout;
	String imagePath;
	int flag = 0;// 是否点击确定，0是剪切状态，1是分享状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.imags);
			context = this;
			init();
		} catch (Exception e) {
		}
	}

	private void init() {
		DisplayMetrics dm = new DisplayMetrics();
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		img = (ImageView) findViewById(R.id.sharescreenshot_img);
		/*imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/data/" + this.getString(R.string.app_name) + "/database/"
				+ "screen.jpg";*/
		imagePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.png";
		if (bitmap_bg == null) {
			bitmap_bg = BitmapFactory.decodeFile(imagePath);
		}

		setTitleText("裁剪图片");


		RelativeLayout title = (RelativeLayout) findViewById(R.id.title);
		title.setBackgroundResource(R.drawable.sharescreenshot_banner);

		bitmap = BitmapFactory.decodeFile(imagePath);
		initAll(dm);
	}

	private void initAll(DisplayMetrics dm) {
		layout = (FrameLayout) findViewById(R.id.sharescreenshot_layout);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, opts);
		opts.inSampleSize = 1;
		opts.inJustDecodeBounds = false;
		int widthold = opts.outWidth;
		int heightold = opts.outHeight;
		float density = (float) dm.widthPixels / (float) 640;// 屏幕比例，实际宽度/默认宽度，见SGCCWindowManager72
		float density2 = dm.density;

		Log.e("majun9550", "++++++++++++++++++++++++++++++++++++++++++++++");
		Log.e("majun9550", "1080+widthold" + widthold);
		Log.e("majun9550", "1800+heightold" + heightold);
		Log.e("majun9550", "++++++++++++++++++++++++++++++++++++++++++++++");
		height =heightold
				- (int) (context.getResources().getDimension(R.dimen.px88) * density)
				- (int) (viewInited() * density) - (int) (40 * density2);
		width = widthold * height / heightold;// 图片的高度和宽度
		int height2 = heightold
				- (int) (context.getResources().getDimension(R.dimen.px88) * density)
				- (int) (viewInited() * density);
		int width2 = widthold * height2 / heightold;// 除去状态栏和标题栏的高度和宽度
		my = new MyViewN(this, bitmap, width, height, width2, height2);

		Log.e("majun9550", "---------------------------------------------");
		Log.e("majun9550", "width2" + width2);
		Log.e("majun9550", "height2" + height2);
		Log.e("majun9550", "---------------------------------------------");
		param = new LayoutParams(width2, height2);

		param.gravity = Gravity.CENTER;
		layout.addView(my, param);
		bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
		img.setImageBitmap(bitmap);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_more_action:
			if (flag == 0) {// 剪切
				img.setImageBitmap(my.getBitmap());
				//editRightButtonBg(R.drawable.main_share);
				flag = 1;
				layout.removeView(my);
			} else {// 分享

			}

			break;
		case R.id.b_back:
			if (flag == 1) {// 剪切
				flag = 0;
//				onCreate(null);
			}
			finish();
			break;
		default:
			break;
		}
	}

	private int viewInited() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (flag == 0) {// 剪切
				finish();
			} else {
				flag = 0;
				onCreate(null);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();


	}

}
