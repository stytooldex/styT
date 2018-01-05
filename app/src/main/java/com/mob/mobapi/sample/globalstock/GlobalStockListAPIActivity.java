/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.globalstock;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import com.mob.mobapi.apis.GlobalStock;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GlobalStockListAPIActivity extends ListActivity implements APICallback, AdapterView.OnItemClickListener {
	public static final int TYPE_CONTINENT = 0;
	public static final int TYPE_COUNTRY = 1;
	public static final int TYPE_CODES = 2;

	private List<HashMap<String, Object>> listData;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.globalstock_api);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);
		getListView().setOnItemClickListener(this);

		//查询全球股指信息
		GlobalStock api = ResHelper.forceCast(MobAPI.getAPI(GlobalStock.NAME));
		api.queryGlobalStock(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		position -= 1;
		if (listData == null || position < 0 || position >= listData.size()) {
			return;
		}
		Intent intent = new Intent(this, GlobalStockDetailActivity.class);
		intent.putExtra("item", listData.get(position));
		startActivity(intent);
	}

	@Override
	public void onSuccess(API api, int action, Map<String, Object> result) {
		new LoadTask().execute((LinkedHashMap<String, Object>) result.get("result"));
	}

	@Override
	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return listData == null ? 0 : listData.size();
		}

		@Override
		public HashMap<String, Object> getItem(int position) {
			if (listData == null || listData.isEmpty() || position >= listData.size()) {
				return null;
			}
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			HashMap<String, Object> item = getItem(position);
			if (item == null || item.isEmpty()) {
				return TYPE_CODES;
			}
			return (Integer) item.get("type");
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			final ViewHolder viewHolder;
			HashMap<String, Object> itemData = getItem(position);
			if (itemData != null && !itemData.isEmpty()) {
				if (convertView == null) {
					if (((Integer) itemData.get("type")) == TYPE_CONTINENT) {
						convertView = LayoutInflater.from(GlobalStockListAPIActivity.this).inflate(R.layout.view_globalstock_item_continent,
								viewGroup, false);
					} else if (((Integer) itemData.get("type")) == TYPE_COUNTRY) {
						convertView = LayoutInflater.from(GlobalStockListAPIActivity.this).inflate(R.layout.view_globalstock_item_country,
								viewGroup, false);
					} else if (((Integer) itemData.get("type")) == TYPE_CODES) {
						convertView = LayoutInflater.from(GlobalStockListAPIActivity.this).inflate(R.layout.view_globalstock_item_code,
								viewGroup, false);
					}
					viewHolder = new ViewHolder();
					viewHolder.tvContinent = ResHelper.forceCast(convertView.findViewById(R.id.tvContinent));
					viewHolder.tvCountry = ResHelper.forceCast(convertView.findViewById(R.id.tvCountry));
					viewHolder.tvName = ResHelper.forceCast(convertView.findViewById(R.id.tvName));
					viewHolder.tvCode = ResHelper.forceCast(convertView.findViewById(R.id.tvCode));
					convertView.setTag(viewHolder);
				} else {
					viewHolder = ResHelper.forceCast(convertView.getTag());
				}
				if (viewHolder.tvContinent != null) {
					viewHolder.tvContinent.setText(itemData.get("continent").toString());
				}
				if (viewHolder.tvCountry != null) {
					viewHolder.tvCountry.setText(itemData.get("country").toString());
				}
				if (viewHolder.tvName != null) {
					viewHolder.tvName.setText(itemData.get("name").toString());
				}
				if (viewHolder.tvCode != null) {
					viewHolder.tvCode.setText(itemData.get("code").toString());
				}
			}
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView tvContinent;
		TextView tvCountry;
		TextView tvName;
		TextView tvCode;
	}

	class LoadTask extends AsyncTask<LinkedHashMap<String, Object>, Integer, List<HashMap<String, Object>>> {

		@Override
		protected List<HashMap<String, Object>> doInBackground(LinkedHashMap<String, Object>... objects) {
			if (objects == null || objects.length < 1) {
				return null;
			}
			if (objects[0] == null || objects[0].isEmpty()) {
				return null;
			}
			return parseContinent(objects[0]);
		}

		@Override
		protected void onPostExecute(List<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			if (result == null || result.isEmpty()) {
				return;
			}
			listData = new ArrayList<HashMap<String, Object>>();
			listData.addAll(result);

			getListView().setAdapter(new MyAdapter());
			TextView headView = new TextView(getBaseContext());
			headView.setTextSize(14);
			headView.setTextColor(0xFF999999);
			headView.setGravity(Gravity.CENTER);
			headView.setPadding(0, 10, 0, 10);
			headView.setText(R.string.globalstock_api_hint);
			getListView().addHeaderView(headView);
		}
	}

	public static List<HashMap<String, Object>> parseContinent(LinkedHashMap<String, Object> continents) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		if (continents != null) {
			HashMap<String, Object> tmpMap;
			for (Map.Entry<String, Object> mapContinent : continents.entrySet()) {
				tmpMap = new HashMap<String, Object>();
				tmpMap.put("type", TYPE_CONTINENT);
				tmpMap.put("continent", mapContinent.getKey());
				result.add(tmpMap);
				result.addAll(parseCountry((LinkedHashMap<String, Object>) mapContinent.getValue()));
			}
		}
		return result;
	}

	public static List<HashMap<String, Object>> parseCountry(LinkedHashMap<String, Object> mapCountry) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		if (mapCountry != null) {
			HashMap<String, Object> tmpMap;
			for (Map.Entry<String, Object> country : mapCountry.entrySet()) {
				tmpMap = new HashMap<String, Object>();
				tmpMap.put("type", TYPE_COUNTRY);
				tmpMap.put("country", country.getKey());
				result.add(tmpMap);
				result.addAll(parseCodes((ArrayList<LinkedHashMap<String, Object>>) country.getValue()));
			}
		}
		return result;
	}

	public static List<HashMap<String, Object>> parseCodes(ArrayList<LinkedHashMap<String, Object>> countryCodes) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		if (countryCodes != null) {
			for (LinkedHashMap<String, Object> map : countryCodes) {
				map.put("type", TYPE_CODES);
				result.add(map);
			}
		}
		return result;
	}

}
