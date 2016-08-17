package com.example.szhh.mainapplication.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * 
 * BaseActivity.java 作用 Activity基类 
 * 
 * @author <a href="mailto:snowpenglei@gmail.com">@author LP</a>
 * 
 * @data  2014-7-18
 * 
 * version 1.0
 * 
 * Copyright sgcc
 */


public class BaseActivity extends FragmentActivity implements OnClickListener {

	protected String TAG = "Activity_Log";
	protected TextView t_text, back_text;
	protected ImageView t_img;
	protected ImageButton t_back, t_more;
	protected RelativeLayout title;
	protected TimeOut timout;
	protected boolean isSetStatusBarColor = true;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*if(isSetStatusBarColor) {
			setColor(this,getResources().getColor(Color.BLACK));
		}
		CommonActivityManager.getActivityManager().pushActivity(this);
		SGCCWindowManager.getInstance(getBaseContext()).initScreenParams();
		//initCrashHandler();*/


		setTimeOut();
		//sendHotFunction(BaseActivity.this.getClass().getName(),MyApplication.getInstance());
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && isSetStatusBarColor) {
			// 设置根布局的参数
			ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
			rootView.setFitsSystemWindows(true);
			rootView.setClipToPadding(true);
		}
	}

	/**
	 * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值
	 */
	public static void setColor(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 设置状态栏透明
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 生成一个状态栏大小的矩形
			View statusView = createStatusView(activity, color);
			// 添加 statusView 到布局中
			ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
			decorView.addView(statusView);

		}
	}


	/**
	 * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity * @param color 状态栏颜色值 * @return 状态栏矩形条
	 */
	private static View createStatusView(Activity activity, int color) {
		// 获得状态栏高度
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

		// 绘制一个和状态栏一样高的矩形
		View statusView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				statusBarHeight);
		statusView.setLayoutParams(params);
		statusView.setBackgroundColor(color);
		return statusView;
	}

	/**
	 * 统计访问次数
	 *
	 * @param className 类名
	 * @param context 上下文
	 */
	int delay = 5000;

	private void sendHotFunction(String className, Context context) {
		int point = className.lastIndexOf(".");
		String name = className.substring(point + 1, className.length());

		String number = "";


		if (name.equals("NewsListActivity")) {//我的消息
			number = "1020100";
		} else if (name.equals("BussinessBranchFirstActivity")) {//网点导航
			number = "1020204";
		} else if (name.equals("PayMainActivity")) {//支付购电
			number = "1020204";
		} else if (name.equals("EleFeeBalanceActivity")) {//电费余额
			number = "1020201";
		} else if (name.equals("PaymentRecordInfoActivity")) {//交费购电记录
			number = "1020202";
		} else if (name.equals("PowerElectricity")) {//电量电费
			number = "1020203";
		} else if (name.equals("MessageSubscibeActivity2")) {//信息订阅
			number = "1020501";
		} else if (name.equals("QueryKeyChangeBeforeActivity")) {//用电查询密码修改
			number = "1020601";
		} else if (name.equals("OnlineCustomerServiceActivity")) {//在线客服
			number = "1030100";
		} else if (name.equals("OutAgeNoTiceActivity") || name.equals("OutAgeNoTice_ChooseTimeActivity")) {//停电公告
			number = "1030500";
		} else if (name.equals("EleFeeBalanceActivity")) {//用电知识
			number = "1030600";
		} else if (name.equals("ProfileShowActivity")) {//帐号信息维护
			number = "1010100";
		} else if (name.equals("RegisterPlaceChooseActivity")) {//切换地区
			number = "1010200";
		} else if (name.equals("ServiceOpenActivity")) {//服务开通
			number = "1010300";
		} else if (name.equals("AccountChangeActivity2")) {//切换用电客户
			number = "1010400";
		} else if (name.equals("KeyChangeActivity")) {//登录密码修改
			number = "1010500";
		} else if (name.equals("UpdateCheckActivity")) {//检查更新
			number = "1010600";
		} else if (name.equals("FAQActivity")) {//应用常见问题
			number = "1010700";
		} else if (name.equals("AboutUsActivity")) {//关于我们
			number = "1010900";
		} else if (name.equals("H_AuthorizationServiceActivity")) {//授权服务
			number = "1011100";

		}
		try {
//			Timer timer=new Timer();
//			timer.schedule(new HotFunctionTimeTask(number, context), delay);
			if (number.length() > 0) {
			/*	if(Config.IS_JSON){
					HotFunctionTaskJson hotFunctionTask=new HotFunctionTaskJson(number, context);
					hotFunctionTask.executeOnExecutor(MyApplication.executorService);
				}else{
					HotFunctionTask hotFunctionTask=new HotFunctionTask(number, context);
					hotFunctionTask.executeOnExecutor(MyApplication.executorService);
				}*/
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setTimeOut() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.sgcc.hcs.timeout");
		timout = new TimeOut();
		this.registerReceiver(timout, filter);
	}

	public void isShowing() {
		/*if (reLoginDialog!=null && reLoginDialog.isShowing()) {
			reLoginDialog.dismiss();
		}*/
	}

	@Override
	public void onClick(View view) {

	}

	//ReLoginDialog reLoginDialog;
	private class TimeOut extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			//			if (arg0 instanceof MainActivityN) {
			//				return;
			//			}
			if (isFinishing()) {
				return;
			}
			//reLoginDialog = new ReLoginDialog(BaseActivity.this, null);
			//reLoginDialog.show();
			//			AlertDialog.Builder builder = new Builder(BaseActivity.this);
			//			builder.setTitle("长时间未操作，请重新登录");
			//			builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			//				
			//				@Override
			//				public void onClick(DialogInterface dialog, int which) {
			//					// TODO Auto-generated method stub
			//					dialog.dismiss();
			//					Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
			//					startActivity(intent);
			//				}
			//			});
			//			builder.show();
		}

	}



	@Deprecated
	public void setTitleText(String text) {
		t_text.setText(text);
	}



	protected <T> void toOtherActivityOnResult(Class<T> cls, int requestCode) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.setClass(getBaseContext(), cls);
		// startActivity(intent);
		startActivityForResult(intent, requestCode);
	}

	protected <T> void toOtherActivityOnResult(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
	}

	/**
	 * 重载toOtherActivity
	 *
	 * @param cls    跳转activity
	 * @param
	 */
	protected <T> void toOtherActivity(Class<?> cls, Bundle bdl) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.setClass(getBaseContext(), cls);
		intent.putExtras(bdl);
		startActivity(intent);
	}

	/**
	 * 重载toOtherActivity
	 *
	 * @param cls 跳转activity
	 * @param
	 */
	protected <T> void toOtherActivity(Class<?> cls) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.setClass(getBaseContext(), cls);
		startActivity(intent);
	}

	protected <T> void toOtherActivity(Intent intent) {
		startActivity(intent);
	}

	protected void closeActivity() {
		finish();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("YCW", "onResume");
		//		if (MyApplication.jumpDialog) {
		//			MyApplication.jumpDialog = false;
		//			new ReLoginDialog(BaseActivity.this,null).show();
		//		}
	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(timout);
		super.onDestroy();
	}


	//	class HotFunctionTimeTask extends TimerTask{
	//		ClickTheStatisticsHotFunctionRequestEntity requestEnity;
	//		ClickTheStatisticsHotFunctionResponseEntity responseEnity;
	//		String resultCode = "";// 返回结果码
	//		String serialNumber="";//编号 
	//		Context ctx;
	//		
	//		
	//		public HotFunctionTimeTask(String number,Context context) {
	//			// TODO Auto-generated constructor stub
	//			this.serialNumber= number;
	//			this.ctx=context;
	//		}
	//
	//		@Override
	//		public void run() {
	//			// TODO Auto-generated method stub
	//			try {
	//				requestEnity = new ClickTheStatisticsHotFunctionRequestEntity(serialNumber);
	//				responseEnity = new ClickTheStatisticsHotFunctionResponseEntity();
	//				int result = DataPool.getInstance().ClickTheStatisticsHotFunction(ctx,requestEnity,responseEnity);
	//				Logout.e("hotfunction", "serialNumber result="+result);
	//			} catch (Exception e) {
	//				// TODO: handle exception
	//			}
	//
	//		}
	//		
	//	}

}
