/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.wxarticle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.WxArticle;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nico.styTool.R;

public class WxArticleAPIActivity extends dump.z.BaseActivity_ implements APICallback, AdapterView.OnItemClickListener {
	private SimpleAdapter categoryAdapter;
	private ArrayList<HashMap<String, Object>> categoryList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wxarticle);
		ListView lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));
		lvResult.setOnItemClickListener(this);

		//init data
		categoryList = new ArrayList<HashMap<String, Object>>();
		categoryAdapter = new SimpleAdapter(this, categoryList, R.layout.view_wxarticle_category_item, new String[]{"cid", "name"},
				new int[]{R.id.tvCid, R.id.tvName});
		lvResult.setAdapter(categoryAdapter);

		//查询分类信息
		((WxArticle) ResHelper.forceCast(MobAPI.getAPI(WxArticle.NAME))).queryCategory(this);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		HashMap<String, Object> item = ResHelper.forceCast(categoryAdapter.getItem(position));
		//goto wx articles
		Intent intent = new Intent(this, WxArticleListAPIActivity.class);
		intent.putExtra("name", (String) item.get("name"));
		intent.putExtra("cid", (String) item.get("cid"));
		startActivity(intent);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		ArrayList<HashMap<String, Object>> res = ResHelper.forceCast(result.get("result"));
		if (null != res && res.size() > 0) {
			categoryList.clear();
			categoryList.addAll(res);
			categoryAdapter.notifyDataSetChanged();
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
