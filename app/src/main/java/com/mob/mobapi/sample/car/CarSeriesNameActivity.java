/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.car;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Car;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarSeriesNameActivity extends ListActivity implements AdapterView.OnItemClickListener, APICallback {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);
		getListView().setOnItemClickListener(this);

		final String name = getIntent().getExtras().getString("name");
		setTitle(getString(R.string.car_api_title_series_name, name));
		// 根据车系名称查询车型
		Car api = ResHelper.forceCast(MobAPI.getAPI(Car.NAME));
		api.querySeriesName(name, this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try {
			Map<String, Object> item = (Map<String, Object>) parent.getAdapter().getItem(position);
			Intent intent = new Intent(this, CarSeriesDetailsActivity.class);
			intent.putExtra("cid", String.valueOf(item.get("carId")));
			intent.putExtra("name", String.valueOf(item.get("seriesName")));
			startActivity(intent);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		try {
			List<Map<String, Object>> res = (List<Map<String, Object>>) result.get("result");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (res != null && !res.isEmpty()) {
				list.addAll(res);
			}
			getListView().setAdapter(new MyAdapter(this, list));
			TextView headView = new TextView(getBaseContext());
			headView.setTextSize(14);
			headView.setTextColor(0xFF999999);
			headView.setGravity(Gravity.CENTER);
			headView.setPadding(0, 10, 0, 10);
			headView.setText(R.string.car_api_hint_desc);
			getListView().addHeaderView(headView);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private static class MyAdapter extends BaseAdapter {
		private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		private Context context;

		public MyAdapter(Context context, List<Map<String, Object>> list) {
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
				convertView = LayoutInflater.from(context).inflate(R.layout.view_car_series_name_list_item, parent, false);
			}
			Map<String, Object> item = getItem(position);
			TextView tvBrandName = (TextView) convertView.findViewById(R.id.tvBrandName);
			tvBrandName.setText(String.valueOf(item.get("brandName")));
			TextView tvCarId = (TextView) convertView.findViewById(R.id.tvCarId);
			tvCarId.setText(String.valueOf(item.get("carId")));
			TextView tvGuidePrice = (TextView) convertView.findViewById(R.id.tvGuidePrice);
			tvGuidePrice.setText(String.valueOf(item.get("guidePrice")));
			TextView tvSeriesName = (TextView) convertView.findViewById(R.id.tvSeriesName);
			tvSeriesName.setText(String.valueOf(item.get("seriesName")));
			return convertView;
		}
	}
}
