/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.tiku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.TiKu;
import nico.styTool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiKuAPIActivity extends dump.z.BaseActivity_ implements View.OnClickListener, AdapterView.OnItemClickListener, APICallback {
	private View btnKeMu1;
	private View btnKeMu4;
	private View btnShiTiKuCategory;
	private ListView lvResult;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tiku);
		btnKeMu1 = findViewById(R.id.btnKeMu1);
		btnKeMu4 = findViewById(R.id.btnKeMu4);
		btnShiTiKuCategory = findViewById(R.id.btnShiTiKuCategory);
		btnKeMu1.setOnClickListener(this);
		btnKeMu4.setOnClickListener(this);
		btnShiTiKuCategory.setOnClickListener(this);

		TextView tvTittle = (TextView) findViewById(R.id.tvTittle);
		tvTittle.setText(R.string.tiku_api_shitiku_category_info);
		lvResult = (ListView) findViewById(R.id.lvResult);

		lvResult.setOnItemClickListener(this);

		btnShiTiKuCategory.performClick();
	}


	public void onClick(View v) {
		int vId = v.getId();
		switch (vId) {
			case R.id.btnKeMu1:
			case R.id.btnKeMu4: {
				Intent intent = new Intent(this, TiKuListAPIActivity.class);
				intent.putExtra("type", vId == R.id.btnKeMu1 ? 0 : 1);
				startActivity(intent);
			} break;
			case R.id.btnShiTiKuCategory: {
				btnShiTiKuCategory.setEnabled(false);
				((TiKu) MobAPI.getAPI(TiKu.NAME)).queryShiTiKuCategory(this);
			} break;
		}

	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try {
			Map<String, Object> item = (Map<String, Object>) parent.getAdapter().getItem(position);
			Intent intent = new Intent(this, TiKuListAPIActivity.class);
			intent.putExtra("type", 2);
			intent.putExtra("cid", String.valueOf(item.get("cid")));
			intent.putExtra("title", String.valueOf(item.get("title")));
			startActivity(intent);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		try {
			Map<String, List<Map<String, Object>>> res = (Map<String, List<Map<String, Object>>>) result.get("result");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (res != null && !res.isEmpty()) {
				Map<String, Object> map;
				for (Map.Entry<String, List<Map<String, Object>>> tmp : res.entrySet()) {
					if (tmp.getValue() == null) {
						continue;
					}
					for (Map<String, Object> item : tmp.getValue()) {
						map = new HashMap<String, Object>();
						map.put("key", tmp.getKey());
						map.putAll(item);
						list.add(map);
					}
				}
			}
			lvResult.setAdapter(new CategoryAdapter(this, list));
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		btnShiTiKuCategory.setEnabled(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		btnShiTiKuCategory.setEnabled(true);
	}

	private static class CategoryAdapter extends BaseAdapter {
		private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		private Context context;

		public CategoryAdapter(Context context, List<Map<String, Object>> list) {
			this.context = context;
			if (list != null) {
				this.list.clear();
				this.list.addAll(list);
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Map<String, Object> getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.view_tiku_category_list_item, parent, false);
			}
			TextView tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
			Map<String, Object> item = getItem(position);
			if (showCategoryName(position)) {
				tvCategoryName.setVisibility(View.VISIBLE);
				tvCategoryName.setText(String.valueOf(item.get("key")));
			} else {
				tvCategoryName.setVisibility(View.GONE);
			}
			TextView tvCid = (TextView) convertView.findViewById(R.id.tvCid);
			tvCid.setText(String.valueOf(item.get("cid")));
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			tvTitle.setText(String.valueOf(item.get("title")));
			return convertView;
		}

		private boolean showCategoryName(int position) {
			if (position > 0 && String.valueOf(getItem(position).get("key")).equals(
					String.valueOf(getItem(position - 1).get("key")))) {
				return false;
			}
			return true;
		}
	}
}
