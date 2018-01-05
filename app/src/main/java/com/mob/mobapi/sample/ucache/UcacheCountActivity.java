/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.ucache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;
import nico.styTool.R;

import java.util.Map;

public class UcacheCountActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etTable;
	private TextView tvCount;
	private Button btnSearch;
	private Ucache api;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.ucache_api_count);
		setContentView(R.layout.activity_ucache_count);
		etTable = (EditText) findViewById(R.id.etTable);
		tvCount = (TextView) findViewById(R.id.tvCount);
		btnSearch = (Button) findViewById(R.id.btnSearch);

		btnSearch.setOnClickListener(this);

		api = (Ucache) MobAPI.getAPI(Ucache.NAME);
		etTable.setText("mobile");
		btnSearch.performClick();
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnSearch) {
			v.setEnabled(false);
			api.queryUcacheCount(etTable.getText().toString().trim(), this);
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		tvCount.setText(String.valueOf(result.get("result")));
		btnSearch.setEnabled(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		btnSearch.setEnabled(true);
	}
}
