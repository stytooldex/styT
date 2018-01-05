/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.weather;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import nico.styTool.R;

import java.util.Map;

public class QueryTypeActivity extends dump.z.BaseActivity_ implements APICallback {
	private TextView textView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.weather_query_type);
		textView = new TextView(this);
		textView.setBackgroundColor(0xFFFFFFFF);
		textView.setTextColor(0xFF000000);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		setContentView(textView);

		((Weather) MobAPI.getAPI(Weather.NAME)).queryType(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case Weather.ACTION_TYPE: {
				Object type = result.get("result");
				if (type != null) {
					textView.setText(type.toString());
				}
			} break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
