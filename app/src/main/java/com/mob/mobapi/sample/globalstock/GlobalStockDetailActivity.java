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
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GlobalStockDetailActivity extends ListActivity implements APICallback {
	private List<HashMap<String, Object>> listData;
	private MyAdapter adapter;
	private int type = GlobalStockListAPIActivity.TYPE_CODES;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.globalstock_api_title_detail);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);

		HashMap<String, Object> itemData = ResHelper.forceCast(getIntent().getSerializableExtra("item"));
		if (itemData == null) {
			return;
		}
		type = (Integer) itemData.get("type");
		GlobalStock api = ResHelper.forceCast(MobAPI.getAPI(GlobalStock.NAME));
		if (type == GlobalStockListAPIActivity.TYPE_CONTINENT) {
			//根据洲际名查询股指明细
			api.queryDetail(null, null, (String) itemData.get("continent"), this);
		} else if (type == GlobalStockListAPIActivity.TYPE_COUNTRY) {
			//根据国家查询股指明细
			api.queryDetail(null, (String) itemData.get("country"), null, this);
		} else if (type == GlobalStockListAPIActivity.TYPE_CODES) {
			//根据股指代码查询股指明细
			api.queryDetail((String) itemData.get("code"), null, null, this);
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		new LoadTask().execute((LinkedHashMap<String, Object>) result.get("result"));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	class MyAdapter extends BaseAdapter {
		public int getCount() {
			return listData == null ? 0 : listData.size();
		}

		public HashMap<String, Object> getItem(int position) {
			if (listData == null || listData.isEmpty() || position >= listData.size()) {
				return null;
			}
			return listData.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup viewGroup) {
			final ViewHolder viewHolder;
			HashMap<String, Object> itemData = getItem(position);
			if (itemData != null && !itemData.isEmpty()) {
				if (convertView == null) {
					convertView = LayoutInflater.from(GlobalStockDetailActivity.this).inflate(R.layout.view_globalstock_detail_item,
							viewGroup, false);
					viewHolder = new ViewHolder();
					viewHolder.vContinent = convertView.findViewById(R.id.vContinent);
					viewHolder.vCountry = convertView.findViewById(R.id.vCountry);
					viewHolder.tvContinent = ResHelper.forceCast(convertView.findViewById(R.id.tvContinent));
					viewHolder.tvCountry = ResHelper.forceCast(convertView.findViewById(R.id.tvCountry));
					viewHolder.tvName = ResHelper.forceCast(convertView.findViewById(R.id.tvName));
					viewHolder.tvCode = ResHelper.forceCast(convertView.findViewById(R.id.tvCode));
					viewHolder.tvChange = ResHelper.forceCast(convertView.findViewById(R.id.tvChange));
					viewHolder.tvChangeRate = ResHelper.forceCast(convertView.findViewById(R.id.tvChangeRate));
					viewHolder.tvPrice = ResHelper.forceCast(convertView.findViewById(R.id.tvPrice));
					viewHolder.tvTime = ResHelper.forceCast(convertView.findViewById(R.id.tvTime));
					convertView.setTag(viewHolder);
				} else {
					viewHolder = ResHelper.forceCast(convertView.getTag());
				}

				viewHolder.vContinent.setVisibility(View.GONE);
				viewHolder.vCountry.setVisibility(View.GONE);
				viewHolder.tvName.setVisibility(View.GONE);
				viewHolder.tvCode.setVisibility(View.GONE);
				viewHolder.tvChange.setVisibility(View.GONE);
				viewHolder.tvChangeRate.setVisibility(View.GONE);
				viewHolder.tvPrice.setVisibility(View.GONE);
				viewHolder.tvTime.setVisibility(View.GONE);
				if (type == GlobalStockListAPIActivity.TYPE_CONTINENT) {
					if (itemData.get("continent") != null) {
						viewHolder.vContinent.setVisibility(View.VISIBLE);
						viewHolder.tvContinent.setText(itemData.get("continent").toString());
					}
					if (itemData.get("country") != null) {
						viewHolder.vCountry.setVisibility(View.VISIBLE);
						viewHolder.tvCountry.setText(itemData.get("country").toString());
					}
				} else if (type == GlobalStockListAPIActivity.TYPE_COUNTRY) {
					if (itemData.get("country") != null) {
						viewHolder.vCountry.setVisibility(View.VISIBLE);
						viewHolder.tvCountry.setText(itemData.get("country").toString());
					}
				}
				if (itemData.get("name") != null) {
					viewHolder.tvName.setVisibility(View.VISIBLE);
					viewHolder.tvName.setText(getString(R.string.globalstock_api_item_name) + " " + itemData.get("name").toString());
				}
				if (itemData.get("code") != null) {
					viewHolder.tvCode.setVisibility(View.VISIBLE);
					viewHolder.tvCode.setText(getString(R.string.globalstock_api_item_code) + " " + itemData.get("code").toString());
				}
				if (itemData.get("change") != null) {
					viewHolder.tvChange.setVisibility(View.VISIBLE);
					viewHolder.tvChange.setText(getString(R.string.globalstock_api_item_change) + " " + itemData.get("change").toString());
				}
				if (itemData.get("changeRate") != null) {
					viewHolder.tvChangeRate.setVisibility(View.VISIBLE);
					viewHolder.tvChangeRate.setText(getString(R.string.globalstock_api_item_change_rate) + " "
							+ itemData.get("changeRate").toString());
				}
				if (itemData.get("price") != null) {
					viewHolder.tvPrice.setVisibility(View.VISIBLE);
					viewHolder.tvPrice.setText(getString(R.string.globalstock_api_item_price) + " " + itemData.get("price").toString());
				}
				if (itemData.get("time") != null) {
					viewHolder.tvTime.setVisibility(View.VISIBLE);
					viewHolder.tvTime.setText(getString(R.string.globalstock_api_item_time) + " " + itemData.get("time").toString());
				}
			}
			return convertView;
		}
	}

	private static class ViewHolder {
		View vContinent;
		View vCountry;
		TextView tvContinent;
		TextView tvCountry;
		TextView tvName;
		TextView tvCode;
		TextView tvChange;
		TextView tvChangeRate;
		TextView tvPrice;
		TextView tvTime;
	}

	class LoadTask extends AsyncTask<LinkedHashMap<String, Object>, Integer, List<HashMap<String, Object>>> {

		protected List<HashMap<String, Object>> doInBackground(LinkedHashMap<String, Object>... objects) {
			if (objects == null || objects.length < 1) {
				return null;
			}
			if (objects[0] == null || objects[0].isEmpty()) {
				return null;
			}

			if (type == GlobalStockListAPIActivity.TYPE_CONTINENT) {
				return GlobalStockListAPIActivity.parseContinent(objects[0]);
			} else if (type == GlobalStockListAPIActivity.TYPE_COUNTRY) {
				return GlobalStockListAPIActivity.parseCountry(objects[0]);
			} else if (type == GlobalStockListAPIActivity.TYPE_CODES) {
				List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
				result.add(objects[0]);
				return result;
			}
			return null;
		}

		protected void onPostExecute(List<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			if (result == null || result.isEmpty()) {
				return;
			}

			if (listData == null) {
				listData = new ArrayList<HashMap<String, Object>>();
			} else {
				listData.clear();
			}
			listData.addAll(result);

			if (adapter == null) {
				adapter = new MyAdapter();
				getListView().setAdapter(adapter);
				return;
			}
			adapter.notifyDataSetChanged();
		}
	}

}
