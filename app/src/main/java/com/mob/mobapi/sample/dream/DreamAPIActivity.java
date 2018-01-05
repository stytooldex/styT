/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.dream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Dream;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DreamAPIActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private static final int PAGE_SIZE = 20; // max is 20
	private ListView lvDream;
	private EditText etDream;
	private int pageIndex;
	private int total;
	private ArrayList<HashMap<String, Object>> dreamList;
	private DreamSimpleAdapter adapter;
	private boolean isLoading = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dream);
		lvDream = ResHelper.forceCast(findViewById(R.id.lvDream));
		etDream = ResHelper.forceCast(findViewById(R.id.etDream));
		etDream.setText(R.string.dream_api_example);
		findViewById(R.id.btnSearch).setOnClickListener(this);

		dreamList = new ArrayList<HashMap<String,Object>>();
		adapter = new DreamSimpleAdapter(this, dreamList);
		lvDream.setAdapter(adapter);
	}

	public void onClick(View v) {
		pageIndex = 0;
		dreamList.clear();
		adapter.notifyDataSetChanged();
		queryDream();
	}

	private void queryDream() {
		if (isLoading) {
			return;
		}
		isLoading = true;
		Dream api = ResHelper.forceCast(MobAPI.getAPI(Dream.NAME));
		String keyword = etDream.getText().toString().trim();
		api.queryDream(keyword, pageIndex + 1, PAGE_SIZE, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		isLoading = false;
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		total = Integer.parseInt(ResHelper.toString(res.get("total")));
		ArrayList<HashMap<String, Object>> list = ResHelper.forceCast(res.get("list"));
		if (null != list) {
			dreamList.addAll(list);
			adapter.notifyDataSetChanged();
		}
		pageIndex ++;
	}

	public void onError(API api, int action, Throwable details) {
		isLoading = false;
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private class DreamSimpleAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<HashMap<String, Object>> list;

		DreamSimpleAdapter(Context context, ArrayList<HashMap<String, Object>> res) {
			inflater = LayoutInflater.from(context);
			list = res;
		}

		public int getCount() {
			if (list.size() == 0) {
				return 0;
			} else if (list.size() == total) {
				return list.size();
			} else {
				return list.size() + 1;
			}
		}

		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public int getItemViewType(int position) {
			return position < list.size() ? 0 : 1;
		}

		public int getViewTypeCount() {
			return 2;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (position < list.size()) {
				return getView1(position, convertView, parent);
			} else {
				return getView2(convertView, parent);
			}
		}

		private View getView1(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.view_dream_item, null);
			TextView tvName  = ResHelper.forceCast(convertView.findViewById(R.id.tvName));
			TextView tvDetail = ResHelper.forceCast(convertView.findViewById(R.id.tvDetail));

			HashMap<String, Object> res = list.get(position);
			tvName.setText(ResHelper.toString(res.get("name")));
			tvDetail.setText(ResHelper.toString(res.get("detail")));
			return convertView;
		}

		private View getView2(View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ProgressBar(parent.getContext());
			}

			if (list.size() < total) {
				convertView.setVisibility(View.VISIBLE);
				queryDream();
			} else {
				convertView.setVisibility(View.GONE);
			}

			return convertView;
		}
	}
}
