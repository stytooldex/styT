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
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;
import nico.styTool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UcacheGetAllTableActivity extends dump.z.BaseActivity_ implements APICallback {
	private ListView lvResult;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.ucache_api_getall_table);
		setContentView(R.layout.activity_ucache_getall_table);
		lvResult = (ListView) findViewById(R.id.lvResult);
		Ucache api = (Ucache) MobAPI.getAPI(Ucache.NAME);
		api.queryUcacheGetAllTable(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		Map<String, Object> res = (Map<String, Object>) result.get("result");
		if (res == null || res.isEmpty()) {
			return;
		}
		Map<String, Object> tables = (Map<String, Object>) res.get("tables");
		if (tables == null || tables.isEmpty()) {
			return;
		}
		List<Map<String, Object>> listInfo = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp;
		for (Map.Entry<String, Object> item : tables.entrySet()) {
			if (item == null) {
				continue;
			}
			tmp = new HashMap<String, Object>();
			tmp.put("name", item.getKey());
			tmp.put("count", item.getValue());
			listInfo.add(tmp);
		}

		lvResult.setAdapter(new MyAdapter(this, listInfo));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private void showDatas(List<HashMap<String, Object>> listInfo) {

	}

	private static class MyAdapter extends BaseAdapter {
		private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		private Context context;

		public MyAdapter(Context context, List<Map<String, Object>> listInfo) {
			this.context = context;
			if (listInfo != null) {
				list.addAll(listInfo);
			}
		}

		public int getCount() {
			return list.size();
		}

		public Map<String, Object> getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.view_ucache_getall_tables_list_item, parent, false);
			}
			TextView tvTableName = (TextView) convertView.findViewById(R.id.tvTableName);
			TextView tvTableItemCounts = (TextView) convertView.findViewById(R.id.tvTableItemCounts);
			Map<String, Object> item = getItem(position);
			if (item != null) {
				tvTableName.setText(String.valueOf(item.get("name")));
				tvTableItemCounts.setText(String.valueOf(item.get("count")));
			}
			return convertView;
		}
	}
}
