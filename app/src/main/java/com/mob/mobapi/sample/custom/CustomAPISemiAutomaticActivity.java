/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.custom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.CustomAPI;
import com.mob.mobapi.MobAPI;
import nico.styTool.R;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAPISemiAutomaticActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener, OnClickListener {
	private int reqCounter;
	private Spinner spAPI;
	private Spinner spPath;
	private LinearLayout llParams;
	private List<Map<String, Object>> apis;
	private List<Map<String, Object>> urlList;
	private String path;
	private TextView tvJson;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_semi_automatic);
		spAPI = (Spinner) findViewById(R.id.spAPI);
		spAPI.setOnItemSelectedListener(this);
		spPath = (Spinner) findViewById(R.id.spPath);
		spPath.setOnItemSelectedListener(this);
		llParams = (LinearLayout) findViewById(R.id.llParams);
		findViewById(R.id.btnSearch).setOnClickListener(this);
		tvJson = (TextView) findViewById(R.id.tvJson);

		getAPIList();
	}

	private void getAPIList() {
		new Thread() {
			public void run() {
				try {
					final List<Map<String, Object>> list = MobAPI.queryAPIs();
					runOnUiThread(new Runnable() {
						public void run() {
							onAPIListGot(list);
						}
					});
				} catch (final Throwable e) {
					runOnUiThread(new Runnable() {
						public void run() {
							onError(null, 0, e);
						}
					});
				}
			}
		}.start();
	}

	private void onAPIListGot(List<Map<String, Object>> apis) {
		if (apis == null) {
			apis = new ArrayList<Map<String, Object>>();
		}
		this.apis = apis;

		ArrayList<String> apiNames = new ArrayList<String>();
		for (Map<String, Object> api : apis) {
			apiNames.add(ResHelper.toString(api.get("name")));
		}
		Collections.sort(apiNames);
		spAPI.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, apiNames));
	}

	@SuppressWarnings("unchecked")
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.equals(spAPI)) {
			String name = (String) spAPI.getSelectedItem();
			for (Map<String, Object> api : apis) {
				if (name.equals(api.get("name"))) {
					urlList = (List<Map<String, Object>>) api.get("urlList");
					ArrayList<String> urlDesp = new ArrayList<String>();
					for (Map<String, Object> url : urlList) {
						urlDesp.add(ResHelper.toString(url.get("description")));
					}
					Collections.sort(urlDesp);
					spPath.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, urlDesp));
					break;
				}
			}
		} else if (parent.equals(spPath)) {
			String description = (String) spPath.getSelectedItem();
			for (Map<String, Object> url : urlList) {
				if (description.equals(url.get("description"))) {
					String urlStr = ResHelper.toString(url.get("url"));
					path = urlStr.replace("http://apicloud.mob.com", "");
					List<Map<String,Object>> params = (List<Map<String,Object>>) url.get("params");
					llParams.removeAllViews();
					for (Map<String,Object> param : params) {
						if ("key".equals(param.get("name")) && "appkey".equals(param.get("description"))) {
							continue;
						}
						View llParam = View.inflate(this, R.layout.view_custom_param_auto, null);
						TextView tvKey = (TextView) llParam.findViewById(R.id.tvKey);
						tvKey.setText(ResHelper.toString(param.get("name")));
						EditText etValue = (EditText) llParam.findViewById(R.id.etValue);
						etValue.setHint(ResHelper.toString(param.get("description")));
						llParams.addView(llParam);
					}
				}
			}
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void onClick(View v) {
		HashMap<String, String> reqParams = new HashMap<String, String>();
		for (int i = 0, size = llParams.getChildCount(); i < size; i++) {
			View item = llParams.getChildAt(i);
			TextView tvKey = (TextView) item.findViewById(R.id.tvKey);
			String key = tvKey.getText().toString().trim();
			EditText etValue = (EditText) item.findViewById(R.id.etValue);
			String value = etValue.getText().toString().trim();
			if (value.length() > 0) {
				reqParams.put(key, value);
			}
		}
		reqCounter++;
		int action = reqCounter;
		CustomAPI api = MobAPI.getCustomAPI();
		api.get(path, reqParams, action, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		Hashon hason = new Hashon();
		String json = hason.fromHashMap((HashMap<String, Object>) result);
		tvJson.setText(hason.format(json));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

}
