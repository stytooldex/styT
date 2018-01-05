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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;
import java.util.Map;

public class UcacheGetActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etTable;
	private EditText etKey;
	private TextView tvKey;
	private TextView tvValue;
	private View btnSearch;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucache_get);
		etTable = ResHelper.forceCast(findViewById(R.id.etTable));
		etKey   = ResHelper.forceCast(findViewById(R.id.etKey));
		tvKey = ResHelper.forceCast(findViewById(R.id.tvKey));
		tvValue   = ResHelper.forceCast(findViewById(R.id.tvValue));
		btnSearch = findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);

		etTable.setText("mobile");
		etKey.setText("mobile");//bW9iaWxl
	}

	public void onClick(View v) {
		v.setEnabled(false);
		Ucache api = (Ucache) MobAPI.getAPI(Ucache.NAME);
		String table = etTable.getText().toString().trim();
		try {
			String key = UcacheAPIActivity.encodeData(etKey.getText().toString().trim());
			api.queryUcacheGet(table, key, this);
		} catch (Throwable t) {
			t.printStackTrace();
			Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
			v.setEnabled(true);
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		tvKey.setText(com.mob.tools.utils.ResHelper.toString(res.get("k")));
		tvValue.setText(com.mob.tools.utils.ResHelper.toString(res.get("v")));
		btnSearch.setEnabled(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		btnSearch.setEnabled(true);
	}
}
