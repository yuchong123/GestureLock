package com.yuc.common.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.yuc.common.R;
/**
 * YUC
 * @author Administrator
 *	2016 - 1 - 23
 */
public class UnlockView extends FrameLayout{
	
	private int view_width;//整个空间宽度
	private int btn_width;//按钮宽度,直径
	private int view_height;
//	private ArrayList<int[]> btn_coord_list;
	private int[][] btn_coord;
	private List<CheckBox> checkBox_btns;
	
	/**
	 * 密码
	 */
	private List<Integer> password = new ArrayList<Integer>();
	private float x;//当前手指坐标
	private float y;
	
	private boolean isError = false;//当前密码是否正确
	private boolean isTouch = false;//是否正在操作
	
	private String rightPwd;
	private OnUnlockListener onUnlockListener;
	private OnGetPwdListener onGetPwdListener;
	
	public void setRightPwd(String rightPwd){
		this.rightPwd=rightPwd;
	}
	
	public void setOnUnlockListener(OnUnlockListener onUnlockListener) {
		this.onUnlockListener = onUnlockListener;
	}
	
	public void setOnGetPwdListener(OnGetPwdListener onGetPwdListener) {
		this.onGetPwdListener = onGetPwdListener;
	}

	/**
	 * 密码验证监听
	 * @author Administrator
	 */
	public interface OnUnlockListener{
		void onSuccess();
		void onError();
	}
	/**
	 * 密码获取监听
	 * @author Administrator
	 */
	public interface OnGetPwdListener{
		void onSetting(String pwd);
	}
	
	/**
	 *  重置9宫格
	 * @param context
	 */
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (!isTouch) {
					reset();
				}
				break;
			}
		}

	};

	public UnlockView(Context context) {
		super(context);
		initView();
	}

	public UnlockView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public UnlockView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//高度,和宽度一样
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	private void initView() {
		inflate(getContext(), R.layout.unlock_layout, this);
		setBackgroundColor(Color.parseColor("#01000000"));
		
		CheckBox unlock_cb_0 = (CheckBox) findViewById(R.id.unlock_cb_0);
		CheckBox unlock_cb_1 = (CheckBox) findViewById(R.id.unlock_cb_1);
		CheckBox unlock_cb_2 = (CheckBox) findViewById(R.id.unlock_cb_2);
		CheckBox unlock_cb_3 = (CheckBox) findViewById(R.id.unlock_cb_3);
		CheckBox unlock_cb_4 = (CheckBox) findViewById(R.id.unlock_cb_4);
		CheckBox unlock_cb_5 = (CheckBox) findViewById(R.id.unlock_cb_5);
		CheckBox unlock_cb_6 = (CheckBox) findViewById(R.id.unlock_cb_6);
		CheckBox unlock_cb_7 = (CheckBox) findViewById(R.id.unlock_cb_7);
		CheckBox unlock_cb_8 = (CheckBox) findViewById(R.id.unlock_cb_8);
		
		checkBox_btns = new ArrayList<CheckBox>();
		checkBox_btns.add(unlock_cb_0);
		checkBox_btns.add(unlock_cb_1);
		checkBox_btns.add(unlock_cb_2);
		checkBox_btns.add(unlock_cb_3);
		checkBox_btns.add(unlock_cb_4);
		checkBox_btns.add(unlock_cb_5);
		checkBox_btns.add(unlock_cb_6);
		checkBox_btns.add(unlock_cb_7);
		checkBox_btns.add(unlock_cb_8);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		view_width = getWidth();
		view_height = getHeight();
		View lock_btn = findViewById(R.id.unlock_cb_0);
		btn_width = lock_btn.getWidth();
		
		//各按钮中点坐标
		int[] btn1_coord = {btn_width/2,btn_width/2};
		int[] btn2_coord = {view_width/2,btn_width/2};
		int[] btn3_coord = {view_width-(btn_width/2),btn_width/2};
		int[] btn4_coord = {btn_width/2,view_height/2};
		int[] btn5_coord = {view_width/2,view_height/2};
		int[] btn6_coord = {view_width-(btn_width/2),view_height/2};
		int[] btn7_coord = {btn_width/2,view_height-(btn_width/2)};
		int[] btn8_coord = {view_width/2,view_height-(btn_width/2)};
		int[] btn9_coord = {view_width-(btn_width/2),view_height-(btn_width/2)};
		
		btn_coord = new int[9][2];
		btn_coord[0][0] = btn1_coord[0];
		btn_coord[0][1] = btn1_coord[1];
		
		btn_coord[1][0] = btn2_coord[0];
		btn_coord[1][1] = btn2_coord[1];
		
		btn_coord[2][0] = btn3_coord[0];
		btn_coord[2][1] = btn3_coord[1];
		
		btn_coord[3][0] = btn4_coord[0];
		btn_coord[3][1] = btn4_coord[1];
		
		btn_coord[4][0] = btn5_coord[0];
		btn_coord[4][1] = btn5_coord[1];
		
		btn_coord[5][0] = btn6_coord[0];
		btn_coord[5][1] = btn6_coord[1];
		
		btn_coord[6][0] = btn7_coord[0];
		btn_coord[6][1] = btn7_coord[1];
		
		btn_coord[7][0] = btn8_coord[0];
		btn_coord[7][1] = btn8_coord[1];
		
		btn_coord[8][0] = btn9_coord[0];
		btn_coord[8][1] = btn9_coord[1];
		
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		if (password!=null) {
			Paint paint=new Paint();
			//画线
			if (isError) {
				paint.setColor(Color.RED);
			}else{
				paint.setColor(Color.YELLOW);
			}
			paint.setAlpha(125);
			paint.setStrokeWidth(10);
	//		canvas.drawLine(50, 50, 500, 500, paint);
			
			for (int i = 0; i < password.size(); i++) {
				if (i==password.size()-1) {
					//最后一个,只有一个
					if (x>0&&y>0) {
						canvas.drawLine(btn_coord[password.get(i)-1][0], btn_coord[password.get(i)-1][1], x, y, paint);
					}
				}else{
					//不是第一个,也不是最后一个
					canvas.drawLine(btn_coord[password.get(i)-1][0], btn_coord[password.get(i)-1][1], btn_coord[password.get(i+1)-1][0], btn_coord[password.get(i+1)-1][1], paint);
				}
			}
		}
		
		
		super.onDraw(canvas);
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
//		Log.e("yuc", "X:"+e.getX());
		
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTouch=true;
			reset();
			break;
		case MotionEvent.ACTION_MOVE:
			
			x = e.getX();
			y = e.getY();
			
			//判断当前位置是否处于某个按钮之中
			for (int i = 0; i < btn_coord.length; i++) {
				float dx=x-btn_coord[i][0];
				float dy=y-btn_coord[i][1];
				if ((dx*dx)+(dy*dy)<=((btn_width*btn_width)/2)) {
//					Log.e("yuc", "选中:"+(i+1));
					if (!password.contains(i+1)) {
						password.add(i+1);
						checkBox_btns.get(i).setChecked(true);
					}
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
			isTouch=false;
			x=0;
			y=0;
			StringBuffer sb = new StringBuffer("");
			for (Integer i : password) {
				sb.append(i);
			}
			//正确密码为空则不判断
			if (TextUtils.isEmpty(rightPwd)) {
				reset();
				if (onGetPwdListener!=null) {
					onGetPwdListener.onSetting(sb.toString());
				}
			}else{
				//错误后,变红
				if (sb.toString().equals(rightPwd)) {
					isError=false;
				}else{
					isError=true;
				}
				
				if (!isError) {
					if (onUnlockListener!=null) {
						onUnlockListener.onSuccess();
					}
				}else{
					if (onUnlockListener!=null) {
						onUnlockListener.onError();
					}
				}
				
				for (CheckBox checkBox : checkBox_btns) {
					checkBox.setEnabled(false);
				}
//			Toast.makeText(getContext(), "密码:"+password, 0).show();
				if (isError) {
					Message msg=new Message();
					msg.what=1;
					handler.sendMessageDelayed(msg, 600);
				}else{
					reset();
				}
			}
			
			break;
		}
		
		invalidate();
		
		return true;
	}
	
	private void reset() {
		//重置
		if (password.size()>0) {
			password.clear();
			isError=false;
			for (CheckBox cb : checkBox_btns) {
				cb.setEnabled(true);
				cb.setChecked(false);
			}
			invalidate();
		}
	}
	
}
