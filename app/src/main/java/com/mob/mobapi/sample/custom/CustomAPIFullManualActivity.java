/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.custom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.CustomAPI;
import com.mob.mobapi.MobAPI;
import nico.styTool.R;
import com.mob.tools.utils.Hashon;

public class CustomAPIFullManualActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private int reqCounter;
	private LinearLayout llCustom;
	private EditText etPath;
	private ArrayList<View> llParams;
	private View llFile;
	private TextView tvJson;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_full_manual);
		llCustom = (LinearLayout) findViewById(R.id.llCustom);
		etPath = (EditText) findViewById(R.id.etPath);
		findViewById(R.id.btnAddParam).setOnClickListener(this);
		findViewById(R.id.btnRemoveParam).setOnClickListener(this);
		findViewById(R.id.btnAddFile).setOnClickListener(this);
		findViewById(R.id.btnRemoveFile).setOnClickListener(this);
		findViewById(R.id.btnGet).setOnClickListener(this);
		findViewById(R.id.btnPost).setOnClickListener(this);
		tvJson = (TextView) findViewById(R.id.tvJson);
		llParams = new ArrayList<View>();
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnAddParam: {
				// 添加新的请求参数
				View llParam = View.inflate(this, R.layout.view_custom_param, null);
				llCustom.addView(llParam, 1 + llParams.size());
				llParams.add(llParam);
				findViewById(R.id.btnRemoveParam).setEnabled(true);
			} break;
			case R.id.btnRemoveParam: {
				// 删除最后一个请求参数
				if (llParams.size() > 0) {
					llCustom.removeViewAt(llParams.size());
					llParams.remove(llParams.size() - 1);
					if (llParams.isEmpty()) {
						findViewById(R.id.btnRemoveParam).setEnabled(false);
					}
				}
			} break;
			case R.id.btnAddFile: {
				// 添加文件
				if (llFile == null) {
					llFile = View.inflate(this, R.layout.view_custom_file, null);
					llCustom.addView(llFile, llCustom.getChildCount() - 3);
					findViewById(R.id.btnRemoveFile).setEnabled(true);
					findViewById(R.id.btnGet).setEnabled(false);
				}
			} break;
			case R.id.btnRemoveFile: {
				// 删除文件
				if (llFile != null) {
					llCustom.removeView(llFile);
					llFile = null;
					findViewById(R.id.btnRemoveFile).setEnabled(false);
					findViewById(R.id.btnGet).setEnabled(true);
				}
			} break;
			case R.id.btnGet: {
				// 执行GET请求
				requestGet();
			} break;
			case R.id.btnPost: {
				// 执行POST请求
				requestPost();
			} break;
		}
	}

	/* 执行GET请求 */
	private void requestGet() {
		String path = etPath.getText().toString().trim();
		HashMap<String, String> reqParams = new HashMap<String, String>();
		for (View v : llParams) {
			EditText etKey = (EditText) v.findViewById(R.id.etKey);
			EditText etValue = (EditText) v.findViewById(R.id.etValue);
			String key = etKey.getText().toString().trim();
			String value = etValue.getText().toString().trim();
			reqParams.put(key, value);
		}
		reqCounter++;
		int action = reqCounter;

		CustomAPI api = MobAPI.getCustomAPI();
		api.get(path, reqParams, action, this);
	}

	/* 执行POST请求 */
	private void requestPost() {
		String path = etPath.getText().toString().trim();
		HashMap<String, String> reqParams = new HashMap<String, String>();
		for (View v : llParams) {
			EditText etKey = (EditText) v.findViewById(R.id.etKey);
			EditText etValue = (EditText) v.findViewById(R.id.etValue);
			String key = etKey.getText().toString().trim();
			String value = etValue.getText().toString().trim();
			reqParams.put(key, value);
		}
		String fileName = null;
		File file = null;
		if (llFile != null) {
			EditText etKey = (EditText) llFile.findViewById(R.id.etKey);
			EditText etValue = (EditText) llFile.findViewById(R.id.etValue);
			fileName = etKey.getText().toString().trim();
			file = new File(etValue.getText().toString().trim());
		}
		reqCounter++;
		int action = reqCounter;

		CustomAPI api = MobAPI.getCustomAPI();
		api.post(path, reqParams, fileName, file, action, this);
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
