package com.example.szhh.mainapplication.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import com.example.szhh.mainapplication.R;

/**
 * @author : majun
 * @date :2014年8月5日下午12:18:09
 * @version:v1.0+
 * @FileName:SaveInfoUtilFor95598.java
 * @ProjectName:Marketing_cellphone_client0127
 * @PackageName:com.sgcc.hcs.utils
 * @EnclosingType:
 * @Description:保存信息工具类--95598
 */
@SuppressLint("NewApi")
public class UtilFor95598 {

	/** debug开关,发布时关闭 ,置为 false */
	public static final boolean IS_DEBUG = false;
	/**
	 * 线程池内养15条线程
	 */
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(15);

	/**
	 * 
	 * @data :2014年9月1日下午1:52:17
	 * @param string
	 * @return
	 * @description :是否是汉字
	 */
	public static boolean isChinese(String string) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @data :2014年9月1日下午1:52:35
	 * @param result
	 * @return
	 * @description :获取结果长度
	 */
	public static int getResultLength(String result) {
		int len = 0;
		if (!TextUtils.isEmpty(result)) {
			for (int i = 0; i < result.length(); i++) {
				if (isChinese(result.charAt(i) + "")) {
					len += 2; // 汉字2长度
				} else {
					len += 1; // 字母 1长度
				}
			}
			return len;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @data :2014年8月6日上午9:56:10
	 * @param result
	 * @param type
	 *            1 联系人 只能是字母/汉字
	 * @return
	 * @description :规则校验
	 */
	public static boolean checking(String result, int type) {
		String regex = "";
		switch (type) {
		case 1:
			// /^[A-Za-z0-9\u4e00-\u9fa5]+$/
			regex = "^([\u4e00-\u9fa5]+|[a-zA-Z]+)$";
			regex = "^([a-zA-Z\u4e00-\u9fa5]+)$";
			if (result.matches(regex)) {
				return true;
			}
			break;
		}

		return false;
	}

	/**
	 * 
	 * @data :2014年9月18日下午3:26:44
	 * @param context
	 * @return
	 * @description :获取屏幕宽度
	 */
	public static int getScreenWidth(Activity context) {
		return getDisplayMetrics(context).widthPixels;
	}

	public static DisplayMetrics getDisplayMetrics(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	/**
	 * 
	 * @data :2014年9月18日下午3:26:44
	 * @param context
	 * @return
	 * @description :获取屏幕高度
	 */
	public static int getScreenHeight(Activity context) {
		return getDisplayMetrics(context).heightPixels;
	}

	/**
	 * 关闭软键盘
	 */
	public static void closeSoftKeyboard(View v, Activity context) {
		Log.e("majun95598", "close soft keyboard.....");
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager.isActive()) {
			inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	/**
	 * 开启或者关闭软键盘
	 */
	public static void openSoftKeyboard(View v, Activity context) {
		InputMethodManager inputManager = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// inputManager.showSoftInput(v, 0); // 开启软键盘
		inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		Log.e("majun001", "result---->" + inputManager.isActive(v));
		Log.e("majun001", "result---------->" + inputManager.isActive());
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String iDCardValidate1(String IDStr) {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9",
				"10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return errorInfo;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable<String, String> h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static boolean iDCardValidate(String IDStr) {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9",
				"10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable<String, String> h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return false;
		}
		// =====================(end)=====================
		return true;
	}

	/** 获取屏幕截图并保存 */
	public static void getScreentoShareN(Activity context) {
		Bitmap bitmap;
		View view = context.getWindow().getDecorView();
		view.buildDrawingCache();
		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		int heights = UtilFor95598.getScreenHeight(context);
		int widths = UtilFor95598.getScreenWidth(context);
		view.layout(0, 0, widths, heights);
		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);
		int statusBarHeight = 0;
		try {
			statusBarHeight = getStatusBarHeight();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Log.e("majun95598", "-----------------------");
		Log.e("majun95598", "statusBarHeight" + statusBarHeight);
		Log.e("majun95598", "heights" + heights);
		Log.e("majun95598", "widths" + widths);
		Log.e("majun95598", "-----------------------");
		/** 去掉状态栏高度和 虚拟键盘高度 如果有的话 */
		bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeights, widths,
				heights - statusBarHeights * 1);
		view.destroyDrawingCache();
		String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
				//+ "/data/" + context.getString(R.string.app_name) + "/database/"
				+ "/data/" + context.getString(R.string.app_name) + "/database/"
				+ "screen.jpg";
		File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/data/" + context.getString(R.string.app_name) + "/database/");
		File file = new File(imagePath);
		try {
			if (!path.exists()) {
				path.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
		}
	}

	/** 状态栏高度 */
	public static int getStatusBarHeight() {
		return Resources.getSystem().getDimensionPixelSize(
				Resources.getSystem().getIdentifier("status_bar_height", "dimen",
						"android"));
	}

	/** 有没有虚拟按键 ,true = 有, false = 没有*/
	public static boolean haveInventedKeyboard(Activity context) {
		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
		boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
		boolean hasMenuKey = false;
		if (Build.VERSION.SDK_INT >= 14) {
			hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
		}
		if (hasBackKey && hasHomeKey && hasMenuKey) {
			// 有虚拟按键：99%可能。
			return true;
		} else {
			// 没有虚拟按键
			return false;
		}
	}

	/** 虚拟返回菜单键实际上就是navigation_bar */
	private static int getNavigationBarHeight(Activity mActivity) {
		Resources resources = mActivity.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen",
				"android");
		int height = resources.getDimensionPixelSize(resourceId);
		Log.e("majun95598", "navigation_bar_height++++++++++++++++++++++");
		Log.e("majun95598", "heigth=" + height);
		Log.e("majun95598", "navigation_bar_height++++++++++++++++++++++");
		return height;
	}

	/** 获取wifi强度 */
	public static void getWifiInfo(Activity mContext) {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(mContext.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo.getBSSID() != null) {
			// wifi名称
			String ssid = wifiInfo.getSSID();
			// wifi信号强度
			int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
			// wifi速度
			int speed = wifiInfo.getLinkSpeed();
			// wifi速度单位
			String units = WifiInfo.LINK_SPEED_UNITS;
			System.out.println("ssid=" + ssid + ",signalLevel=" + signalLevel + ",speed="
					+ speed + ",units=" + units);
		}
	}

	/** 
	 * ------------------------------------------------------<br/>
	 * 检测wifi状态<br/>
	 * ------------------------------------------------------<br/>
	 * <receiver android:name=".NetworkConnectChangedReceiver" ><br/>
	 *       <intent-filter><br/>
	 *           <action android:name="android.net.conn.CONNECTIVITY_CHANGE" /><br/>
	 *           <action android:name="android.net.wifi.WIFI_STATE_CHANGED" /><br/>
	 *           <action android:name="android.net.wifi.STATE_CHANGE" /><br/>
	 *       </intent-filter><br/>
	 * </receiver><br/>
	 *  */
	public static void testWifi(Intent intent) {
		if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
			int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
			Log.e("H3c", "wifiState" + wifiState);
			switch (wifiState) {
			case WifiManager.WIFI_STATE_DISABLED:
				break;
			case WifiManager.WIFI_STATE_DISABLING:
				break;
			//
			}
		}
		// 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
		// 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
		if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			Parcelable parcelableExtra = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (null != parcelableExtra) {
				NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
				State state = networkInfo.getState();
				boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
				Log.e("H3c", "isConnected" + isConnected);
				if (isConnected) {
				} else {

				}
			}
		}
	}

	public static String getTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
}
