package com.example.szhh.mainapplication.view;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author <li>majun
 * @author <li>邮箱:<a href="mailto:747673016@qq.com">747673016@qq.com</a> </br>
 * @author <li>网址:<a
 *         href="https://github.com/majunm">https://github.com/majunm</a>
 * @date :2014年11月27日 下午2:23:22
 * @version:v2.0+
 * @FileName:MyViewN.java
 * @ProjectName:Marketing_cellphone_client0127
 * @PackageName:com.sgcc.hcs.view
 * @EnclosingType:
 * @Copyright: 北京国电通网络技术有限公司
 */
@SuppressLint({ "DrawAllocation", "WrongCall" })
public class MyViewN extends View {

	/**
	 * 获取的剪切图片
	 */
	Bitmap bitmapOld;
	Bitmap bitmap;
	private int left = 0;
	private int top = 0;
	private int right = 200;
	private int bottom = 200;
	/**
	 * 使点能显示出来
	 */
	private int width_scope = 20;
	private int height_scope = 20;
	/**
	 * 点击的x轴和y轴的坐标
	 */
	int xy[] = new int[2];
	/**
	 * 移动的x轴和y轴的坐标
	 */
	int temp[] = new int[2];
	int tempM[] = new int[2];

	/**
	 * 是否点到剪切框
	 */
	boolean mFlag = false;
	/**
	 * 是否移动
	 */
	boolean mFlagM = false;
	boolean mFlag1 = false;
	boolean mFlag2 = false;
	boolean kaishi = true;// 初始化
	boolean mFlagLeft = false;
	boolean mFlagRight = false;
	boolean mFlagTop = false;
	boolean mFlagBottom = false;
	int change = 0;
	int point = 0;
	/**
	 * 需要剪切的画布数据
	 */
	int tml, tmr, tmt, tmb;
	int tuodongH;// 拖动的高度
	int tuodongW;// 拖动的宽度

	/**
	 * 线的范围
	 */
	int scope = 30;
	/**
	 * 剪切框的距离
	 */
	int distance = 100;
	/**
	 * 画布宽度和高度
	 */
	int width, height;
	int widthbig, heightbig;
	/**
	 * 到边缘
	 */
	boolean edgeleftright = false;
	boolean edgetopbottom = false;

	/**
	 * @param context
	 * @param bitmap
	 * @param width
	 * @param height
	 * @param widthbig
	 * @param heightbig
	 */
	public MyViewN(Context context, Bitmap bitmap, int width, int height, int widthbig,
			int heightbig) {
		super(context);
		// TODO Auto-generated constructor stub
		this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
		this.width = width;
		this.height = height;
		this.widthbig = widthbig;
		this.heightbig = heightbig;
		this.width_scope = (widthbig - width) / 2;
		this.height_scope = (heightbig - height) / 2;
		right = width - left;
		bottom = height - top;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// 点击下
			xy[0] = (int) event.getX();// 获取x轴数据
			xy[1] = (int) event.getY();// 获取y轴数据
			if (xy[0] < right + scope + width_scope
					&& xy[0] > right - scope + width_scope
					&& xy[1] < bottom + scope + height_scope
					&& xy[1] > bottom - scope + height_scope) {// 右下点
				point = 3;
			} else if (xy[0] < right + scope + width_scope
					&& xy[0] > right - scope + width_scope
					&& xy[1] < top + scope + height_scope
					&& xy[1] > top - scope + height_scope) {// 右上点
				point = 2;
			} else if (xy[0] < left + scope + width_scope
					&& xy[0] > left - scope + width_scope
					&& xy[1] < top + scope + height_scope
					&& xy[1] > top - scope + height_scope) {// 左上点
				point = 1;
			} else if (xy[0] < left + scope + width_scope
					&& xy[0] > left - scope + width_scope
					&& xy[1] < bottom + scope + height_scope
					&& xy[1] > bottom - scope + height_scope) {// 左下点
				point = 4;
			}
			if (xy[0] < right + scope + width_scope
					&& xy[0] > right - scope + width_scope) {// 右线
				mFlagRight = true;
			}
			if (xy[0] < left + scope + width_scope && xy[0] > left - scope + width_scope) {// 左线
				mFlagLeft = true;
			}
			if (xy[1] < top + scope + height_scope && xy[1] > top - scope + height_scope) {// 上线
				mFlagTop = true;
			}
			if (xy[1] < bottom + scope + height_scope
					&& xy[1] > bottom - scope + height_scope) {// 下线
				mFlagBottom = true;
			}
			if (xy[0] < right + width_scope && xy[0] > left + width_scope
					&& xy[1] < bottom + height_scope && xy[1] > top + height_scope) {// 如果在框里
				mFlag2 = false;
				mFlag = true;
				mFlag1 = false;
			} else {
				mFlag = false;
			}
			break;
		case MotionEvent.ACTION_MOVE:// 拖动
			temp[0] = (int) event.getX();// 移动后的x坐标
			temp[1] = (int) event.getY();// 移动后的y坐标
			if (point != 0) {// 在点上
				mFlagM = true;// 移动了
				if (right - left - distance < 0 || bottom - top - distance < 0
						|| temp[0] - width_scope > width
						|| temp[1] - height_scope > height || temp[0] < 0 + width_scope
						|| temp[1] < 0 + height_scope) {
					// System.out.print("");
					// return true;
				} else {
					if (point == 1) {
						left = temp[0] - width_scope;
						top = temp[1] - height_scope;
					} else if (point == 2) {
						right = temp[0] - width_scope;
						top = temp[1] - height_scope;
					} else if (point == 3) {
						right = temp[0] - width_scope;
						bottom = temp[1] - height_scope;
					} else if (point == 4) {
						left = temp[0] - width_scope;
						bottom = temp[1] - height_scope;
					}
					invalidate();
				}
			} else if (mFlagLeft || mFlagRight || mFlagBottom || mFlagTop) {// 在边线上
				mFlagM = true;// 移动了
				if (mFlagLeft) {
					if (temp[0] > right - distance - width_scope) {
						return true;
					}
					if (temp[0] < 0 + width_scope) {
						left = 0;
					} else {
						left = temp[0] - width_scope;
					}
				} else if (mFlagRight) {
					if (temp[0] < left + distance - width_scope) {
						return true;
					}
					if (temp[0] > width + width_scope) {
						right = width;
					} else {
						right = temp[0] - width_scope;
					}

				} else if (mFlagBottom) {
					if (temp[1] < top + distance + height_scope) {
						return true;
					}
					if (temp[1] > height + height_scope) {
						bottom = height;
					} else {
						bottom = temp[1] - height_scope;
					}
				} else if (mFlagTop) {
					if (temp[1] > bottom - distance + height_scope) {
						return true;
					}
					if (temp[1] < 0 + height_scope) {
						top = 0;
					} else {
						top = temp[1] - height_scope;
					}
				}
				invalidate();
			} else {
				if (mFlag) {// 点在剪切框
					if (xy[0] == temp[0] && xy[1] == temp[1]) {// 没有移动

					} else {
						mFlagM = true;// 移动了

						if (right + temp[0] - left - (xy[0] - tml) > width
								|| edgeleftright) {
							edgeleftright = true;
							// right = width;
						} else {
							right = right + temp[0] - left - (xy[0] - tml);
						}
						if (bottom + temp[1] - top - (xy[1] - tmt) > height
								|| edgetopbottom) {
							edgetopbottom = true;
							// bottom = height;
						} else {
							bottom = bottom + temp[1] - top - (xy[1] - tmt);
						}
						if (temp[0] - (xy[0] - tml) < 0 || edgeleftright) {
							edgeleftright = true;
							// left = 0;
						} else {
							left = temp[0] - (xy[0] - tml);
						}
						if (temp[1] - (xy[1] - tmt) < 0 || edgetopbottom) {
							edgetopbottom = true;
							// top = 0;
						} else {
							top = temp[1] - (xy[1] - tmt);
						}

						invalidate();

					}
				}
			}

			if (mFlag1) {
				if (temp[0] > xy[0] || temp[1] < xy[1]) {
					mFlag2 = true;
					change += 5;
					tempM[0] = right = left + 80 + change;
					tempM[1] = bottom = top + 80 + change;
					invalidate();
				} else {
					change -= 5;
					tempM[0] = right = left + 80 + change;
					tempM[1] = bottom = top + 80 + change;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			tml = left;
			tmt = top;
			tmr = right;
			tmb = bottom;
			mFlagLeft = false;
			mFlagRight = false;
			mFlagBottom = false;
			mFlagTop = false;
			edgeleftright = false;
			edgetopbottom = false;
			drawBitmap();
			point = 0;
			break;
		}
		return true;

	}

	private void drawBitmap() {
		if (bitmapOld == null) {
			drawBackGroundBitmap();
		} else {
			bitmapOld = null;
			drawBackGroundBitmap();
		}
	}

	public Bitmap getBitmap() {
		return bitmapOld;
	}

	public void drawLogical(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeMiter(6);
		mPaint.setStrokeWidth(3);
		mPaint.setColor(Color.RED);
		canvas.drawRect(left, top, right, bottom, mPaint);
		tuodongH = bottom - top;
		tuodongW = right - left;
		Paint mPaint1 = new Paint();
		mPaint1.setColor(Color.BLACK);
		mPaint1.setAlpha(100);
		canvas.drawRect(0, 0, width, top, mPaint1);
		canvas.drawRect(0, bottom, width, height, mPaint1);
		canvas.drawRect(0, top, left, bottom, mPaint1);
		canvas.drawRect(right, top, width, bottom, mPaint1);
	}

	protected void onDraw(Canvas canvas) {
		beginDrawing(canvas);
	}

	/**
	 * @Date :2014-12-1下午10:40:03
	 * @Description :开始绘制吧
	 * @param canvas
	 */
	private void beginDrawing(Canvas canvas) {
		if (mFlag1 && mFlag2) {
			drawLogical(canvas);
		} else if (point != 0 && mFlagM) {// 在点上移动
			drawLogical(canvas, false);
		} else if (mFlagLeft || mFlagRight || mFlagBottom || mFlagTop && mFlagM) {// 边线移动
			drawLogical(canvas, false);
		} else if (mFlag = true && mFlagM == true) {// 在剪切框中移动
			drawLogical(canvas, false);
		} else if (kaishi) {// 初始化
			/** 绘制逻辑 */
			drawLogical(canvas, true);
		} else {
		}
		super.onDraw(canvas);
	}

	protected void onDraw1(Canvas canvas) {
		if (mFlag1 && mFlag2) {
			drawLogical(canvas);
			return;
		}
		if (point != 0 && mFlagM) {// 在点上移动
			Paint mPaint = new Paint();
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeMiter(6);
			mPaint.setStrokeWidth(3);
			mPaint.setColor(Color.RED);
			mPaint.setFilterBitmap(true);
			canvas.drawRect(left + width_scope, top + height_scope, right + width_scope,
					bottom + height_scope, mPaint);
			Paint mPaint1 = new Paint();
			mPaint1.setColor(Color.BLACK);
			mPaint1.setAlpha(100);
			mPaint1.setFilterBitmap(true);
			canvas.drawRect(width_scope, height_scope, width + width_scope, top
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope, bottom + height_scope, width + width_scope,
					height + height_scope, mPaint1);
			canvas.drawRect(width_scope, top + height_scope, left + width_scope, bottom
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope + right, top + height_scope, width + width_scope,
					bottom + height_scope, mPaint1);
			drawAllPoint_init(canvas);
			return;
		}
		if (mFlagLeft || mFlagRight || mFlagBottom || mFlagTop && mFlagM) {// 边线移动
			Paint mPaint = new Paint();
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeMiter(6);
			mPaint.setStrokeWidth(3);
			mPaint.setColor(Color.RED);
			mPaint.setFilterBitmap(true);
			canvas.drawRect(left + width_scope, top + height_scope, +right + width_scope,
					bottom + height_scope, mPaint);
			Paint mPaint1 = new Paint();
			mPaint1.setColor(Color.BLACK);
			mPaint1.setAlpha(100);
			mPaint1.setFilterBitmap(true);
			canvas.drawRect(width_scope, height_scope, width + width_scope, top
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope, bottom + height_scope, width + width_scope,
					height + height_scope, mPaint1);
			canvas.drawRect(width_scope, top + height_scope, left + width_scope, bottom
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope + right, top + height_scope, width + width_scope,
					bottom + height_scope, mPaint1);
			drawAllPoint_init(canvas);
			return;
		}
		if (mFlag = true && mFlagM == true) {// 在剪切框中移动
			Paint mPaint = new Paint();
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeMiter(6);
			mPaint.setStrokeWidth(3);
			mPaint.setColor(Color.RED);
			mPaint.setFilterBitmap(true);
			canvas.drawRect(left + width_scope, top + height_scope, right + width_scope,
					bottom + height_scope, mPaint);
			Paint mPaint1 = new Paint();
			mPaint1.setColor(Color.BLACK);
			mPaint1.setAlpha(100);
			mPaint1.setFilterBitmap(true);
			canvas.drawRect(width_scope, height_scope, width + width_scope, top
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope, bottom + height_scope, width + width_scope,
					height + height_scope, mPaint1);
			canvas.drawRect(width_scope, top + height_scope, left + width_scope, bottom
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope + right, top + height_scope, width + width_scope,
					bottom + height_scope, mPaint1);
			drawAllPoint_init(canvas);
			return;
		}
		if (kaishi) {// 初始化
			Paint mPaint = new Paint();
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeMiter(6);
			mPaint.setStrokeWidth(3);
			mPaint.setColor(Color.RED);
			Log.e("majun95590", "---------------------");
			Log.e("majun95590", "---------------------");
			Log.e("majun95590", "---------------------");
			mPaint.setFilterBitmap(true);
			canvas.drawRect(left + width_scope, top + height_scope, right + width_scope,
					bottom + height_scope, mPaint);
			Paint mPaint1 = new Paint();
			mPaint1.setColor(Color.BLACK);
			mPaint1.setAlpha(100);
			mPaint1.setFilterBitmap(true);
			canvas.drawRect(width_scope, height_scope, width + width_scope, top
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope, bottom + height_scope, width + width_scope,
					height + height_scope, mPaint1);
			canvas.drawRect(width_scope, top + height_scope, left + width_scope, bottom
					+ height_scope, mPaint1);
			canvas.drawRect(width_scope + right, top + height_scope, width + width_scope,
					bottom + height_scope, mPaint1);
			kaishi = false;
			tml = left;
			tmt = top;
			tmr = right;
			tmb = bottom;
			drawAllPoint_init(canvas);
			drawBitmap();
		} else {
			return;
		}
		super.onDraw(canvas);
	}

	/**
	 * @Date :2014-12-1下午10:33:05
	 * @Description :绘制逻辑
	 * @param canvas
	 */
	private void drawLogical(Canvas canvas, boolean needDrawBitmap) {
		Paint mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeMiter(6);
		mPaint.setStrokeWidth(3);
		mPaint.setColor(Color.RED);
		Log.e("majun95590", "---------------------");
		Log.e("majun95590", "---------------------");
		Log.e("majun95590", "---------------------");
		mPaint.setFilterBitmap(true);
		canvas.drawRect(left + width_scope, top + height_scope, right + width_scope,
				bottom + height_scope, mPaint);
		Paint mPaint1 = new Paint();
		mPaint1.setColor(Color.BLACK);
		mPaint1.setAlpha(100);
		mPaint1.setFilterBitmap(true);
		canvas.drawRect(width_scope, height_scope, width + width_scope, top
				+ height_scope, mPaint1);
		canvas.drawRect(width_scope, bottom + height_scope, width + width_scope, height
				+ height_scope, mPaint1);
		canvas.drawRect(width_scope, top + height_scope, left + width_scope, bottom
				+ height_scope, mPaint1);
		canvas.drawRect(width_scope + right, top + height_scope, width + width_scope,
				bottom + height_scope, mPaint1);
		kaishi = false;
		drawAllPoint_init(canvas);
		if (needDrawBitmap) {
			tml = left;
			tmt = top;
			tmr = right;
			tmb = bottom;
			drawBitmap();
		}
	}

	private void drawAllPoint_init(Canvas canvas) {
		drawPoint(canvas, left + width_scope, top + height_scope);
		drawPoint(canvas, left + width_scope, bottom + height_scope);
		drawPoint(canvas, right + width_scope, top + height_scope);
		drawPoint(canvas, right + width_scope, bottom + height_scope);
	}

	/**
	 * 画点
	 * 
	 * @param canvas
	 * @param x
	 *            x轴
	 * @param y
	 *            y轴
	 */
	private void drawPoint(Canvas canvas, int x, int y) {
		Log.e("majun95590", "---------------------");
		Log.e("majun95590", "-drawPoint------");
		Log.e("majun95590", "---------------------");
		Paint mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setStrokeMiter(6);
		mPaint.setStrokeWidth(3);
		mPaint.setColor(Color.WHITE);
		mPaint.setAlpha(200);
		canvas.drawCircle(x, y, 20, mPaint);
	}
	
	public void drawBackGroundBitmap() {
		Log.e("majun95598", "=========================");
		Log.e("majun95598", "tml=" + tml);
		Log.e("majun95598", "tmt=" + tmt);
		Log.e("majun95598", "=========================");
		bitmapOld = Bitmap.createBitmap(bitmap, tml, tmt, (tmr - tml) <= 0 ? 1
				: (tmr - tml), (tmb - tmt) <= 0 ? 1 : (tmb - tmt), null, true);
	}

}
