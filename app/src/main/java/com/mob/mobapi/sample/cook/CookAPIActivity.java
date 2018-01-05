/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.cook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Cook;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CookAPIActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener, OnClickListener {
	private Spinner spCategory1;
	private Spinner spCategory2;
	private Spinner spCategory3;

	private ArrayList<HashMap<String, Object>> category1List;
	private ArrayList<String> categoryIds;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cook);
		spCategory1 = ResHelper.forceCast(findViewById(R.id.spCategory1));
		spCategory1.setOnItemSelectedListener(this);
		spCategory2 = ResHelper.forceCast(findViewById(R.id.spCategory2));
		spCategory2.setOnItemSelectedListener(this);
		spCategory3 = ResHelper.forceCast(findViewById(R.id.spCategory3));
		findViewById(R.id.btnSearch).setOnClickListener(this);

		category1List = new ArrayList<HashMap<String,Object>>();
		categoryIds = new ArrayList<String>();

		// 获取API实例，请求菜单列表
		Cook api = ResHelper.forceCast(MobAPI.getAPI(Cook.NAME));
		api.queryCategory(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		// 解析数据，显示列表
		result = ResHelper.forceCast(result.get("result"));
		HashMap<String, Object> categoryInfo = ResHelper.forceCast(result.get("categoryInfo"));
		category1List.clear();
		ArrayList<HashMap<String,Object>> list = ResHelper.forceCast(result.get("childs"));
		if (list != null) {
			category1List.addAll(list);
		}

		ArrayList<String> category1 = new ArrayList<String>();
		category1.add(com.mob.tools.utils.ResHelper.toString(categoryInfo.get("name")));
		spCategory1.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, category1));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("unchecked")
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.equals(spCategory1)) {
			ArrayList<String> category2 = new ArrayList<String>();
			for (HashMap<String,Object> cat : category1List) {
				HashMap<String, Object> categoryInfo = (HashMap<String,Object>) cat.get("categoryInfo");
				category2.add(com.mob.tools.utils.ResHelper.toString(categoryInfo.get("name")));
			}
			spCategory2.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, category2));
		} else if (parent.equals(spCategory2)) {
			ArrayList<HashMap<String, Object>> category2List = ResHelper.forceCast(category1List.get(position).get("childs"));
			ArrayList<String> category3 = new ArrayList<String>();
			categoryIds = new ArrayList<String>();
			for (HashMap<String,Object> cat : category2List) {
				HashMap<String, Object> categoryInfo = ResHelper.forceCast(cat.get("categoryInfo"));
				category3.add(com.mob.tools.utils.ResHelper.toString(categoryInfo.get("name")));
				categoryIds.add(com.mob.tools.utils.ResHelper.toString(categoryInfo.get("ctgId")));
			}
			spCategory3.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, category3));
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void onClick(View v) {
		// 打开详情页面
		if (categoryIds.size() > 0) {
			String title = (String) spCategory3.getSelectedItem();
			int ind = spCategory3.getSelectedItemPosition();
			String ctgId = categoryIds.get(ind < 0 ? 0 : (ind >= categoryIds.size() ? categoryIds.size() : ind));
			Intent i = new Intent(this, MenuListActivity.class);
			i.putExtra("title", title);
			i.putExtra("ctgId", ctgId);
			startActivity(i);
		}
	}
}
