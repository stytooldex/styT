/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.oilprice;

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
import com.mob.mobapi.apis.OilPrice;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OilPriceAPIActivity extends dump.z.BaseActivity_ implements APICallback {
	private OilPriceAdapter oilPriceAdapter;
	private ArrayList<HashMap<String, Object>> oilPriceList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oilprice);
		ListView lvResult = ResHelper.forceCast(findViewById(R.id.lvResult));

		//init data
		oilPriceList = new ArrayList<HashMap<String, Object>>();
		oilPriceAdapter = new OilPriceAdapter(this, oilPriceList);
		lvResult.setAdapter(oilPriceAdapter);

		//查询今日油价
		((OilPrice) ResHelper.forceCast(MobAPI.getAPI(OilPrice.NAME))).queryOilPrice(OilPriceAPIActivity.this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		if (res != null && res.size() > 0) {
			ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
			for (Map.Entry<String, Object> entry : res.entrySet()) {
				HashMap<String, Object> value = ResHelper.forceCast(entry.getValue());
				tempList.add(value);
			}
			if (tempList.size() > 0) {
				oilPriceList.clear();
				oilPriceList.addAll(tempList);
				oilPriceAdapter.notifyDataSetChanged();
			}
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	private static class ViewHolder {
		TextView tvProvince;
		TextView tvDieselOil0;
		TextView tvGasoline90;
		TextView tvGasoline93;
		TextView tvGasoline97;
	}

	private class OilPriceAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<HashMap<String, Object>> list;

		OilPriceAdapter(Context context, ArrayList<HashMap<String, Object>> res) {
			inflater = LayoutInflater.from(context);
			list = res;
		}

		public int getCount() {
			if (null != list) {
				return list.size();
			}
			return 0;
		}

		public HashMap<String, Object> getItem(int position) {
			if (position < list.size()) {
				return list.get(position);
			}
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.view_oilprice_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tvProvince = ResHelper.forceCast(convertView.findViewById(R.id.tvProvince));
				viewHolder.tvDieselOil0 = ResHelper.forceCast(convertView.findViewById(R.id.tvDieselOil0));
				viewHolder.tvGasoline90 = ResHelper.forceCast(convertView.findViewById(R.id.tvGasoline90));
				viewHolder.tvGasoline93 = ResHelper.forceCast(convertView.findViewById(R.id.tvGasoline93));
				viewHolder.tvGasoline97 = ResHelper.forceCast(convertView.findViewById(R.id.tvGasoline97));
				convertView.setTag(viewHolder);
			} else {
				viewHolder = ResHelper.forceCast(convertView.getTag());
			}
			HashMap<String, Object> res = getItem(position);
			if (res != null) {
				viewHolder.tvProvince.setText(com.mob.tools.utils.ResHelper.toString(res.get("province")));
				viewHolder.tvDieselOil0.setText(com.mob.tools.utils.ResHelper.toString(res.get("dieselOil0")));
				viewHolder.tvGasoline90.setText(com.mob.tools.utils.ResHelper.toString(res.get("gasoline90")));
				viewHolder.tvGasoline93.setText(com.mob.tools.utils.ResHelper.toString(res.get("gasoline93")));
				viewHolder.tvGasoline97.setText(com.mob.tools.utils.ResHelper.toString(res.get("gasoline97")));
			}
			return convertView;
		}
	}
}
