/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.iktoken;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.IKToken;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IKTokenAPIActivity extends dump.z.BaseActivity_ implements APICallback, AdapterView.OnItemClickListener {
	private boolean isQuerying = false;

	private EditText etText;
	private TextView tvResultTitle;
	private TextView tvResult;

	private SimpleAdapter categoryAdapter;

	private ArrayList<HashMap<String, String>> categoryList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iktoken);
		etText = ResHelper.forceCast(findViewById(R.id.etText));
		tvResultTitle = ResHelper.forceCast(findViewById(R.id.tvResultTitle));
		tvResult = ResHelper.forceCast(findViewById(R.id.tvResult));

		GridView gvCategory = ResHelper.forceCast(findViewById(R.id.gvCategory));
		gvCategory.setOnItemClickListener(this);

		categoryList = new ArrayList<HashMap<String, String>>();
		categoryAdapter = new SimpleAdapter(this, categoryList, android.R.layout.simple_list_item_1, new String[]{"desc"},
				new int[]{android.R.id.text1});
		gvCategory.setAdapter(categoryAdapter);

		//获取分词库
		((IKToken) MobAPI.getAPI(IKToken.NAME)).queryWordCategory(IKTokenAPIActivity.this);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		if (isQuerying) {
			return;
		}
		isQuerying = true;
		tvResult.setText("");
		HashMap<String, String> item = ResHelper.forceCast(categoryAdapter.getItem(position));
		tvResultTitle.setText(getString(R.string.iktoken_api_title_result, "" + item.get("desc")));
		//开始分词
		((IKToken) MobAPI.getAPI(IKToken.NAME)).analyzeWord(item.get("type"), etText.getText().toString(), IKTokenAPIActivity.this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case IKToken.ACTION_CATEGORY: {
				ArrayList<HashMap<String, String>> tmpList = ResHelper.forceCast(result.get("result"));
				if (tmpList == null || tmpList.size() < 1) {
					return;
				}
				categoryList.clear();
				categoryList.addAll(tmpList);
				categoryAdapter.notifyDataSetChanged();
			} break;
			case IKToken.ACTION_ANALYZE: {
				tvResult.setText(result.get("result").toString());
				isQuerying = false;
			} break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		if (action == IKToken.ACTION_ANALYZE) {
			isQuerying = false;
		}
	}
}
