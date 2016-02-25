package com.yuc.common;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuc.common.view.UnlockView;
import com.yuc.common.view.UnlockView.OnGetPwdListener;
import com.yuc.common.view.UnlockView.OnUnlockListener;

public class MainActivity extends Activity {
	
	private UnlockView unlock;
	private EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		unlock = (UnlockView) findViewById(R.id.unlock);
		et = (EditText) findViewById(R.id.et);
		
//		unlock.setRightPwd("123");
		unlock.setOnUnlockListener(new OnUnlockListener() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(MainActivity.this, "密码正确", 0).show();
			}
			@Override
			public void onError() {
				Toast.makeText(MainActivity.this, "密码错误", 0).show();
			}
		});
		unlock.setOnGetPwdListener(new OnGetPwdListener() {
			
			@Override
			public void onSetting(String pwd) {
				Toast.makeText(MainActivity.this, "密码:"+pwd, 0).show();
			}
		});
	}
	
	/**
	 * 设置密码模式
	 * @param view
	 */
	public void setting(View view){
		unlock.setRightPwd(null);
	}
	
	
	/**
	 * 验证密码模式
	 * @param view
	 */
	public void verify(View view){
		String rightPwd = et.getText().toString();
		if (TextUtils.isEmpty(rightPwd)) {
			Toast.makeText(this, "请输入正确密码", 0).show();
			return;
		}
		unlock.setRightPwd(rightPwd);
	}
	
	
	
}
