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
import android.graphics.drawable.ColorDrawable;
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
import com.mob.mobapi.apis.Car;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarSeriesDetailsActivity extends ListActivity implements APICallback {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);

		String cid = getIntent().getExtras().getString("cid");
		setTitle(getString(R.string.car_api_title_series_name, cid));

		//查询车型详细信息 cid和name必须填一个，默认使用cid
		Car api = ResHelper.forceCast(MobAPI.getAPI(Car.NAME));
		api.querySeries(cid, this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		try {
			List<Map<String, Object>> resList = (List<Map<String, Object>>) result.get("result");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			StringBuffer sb = new StringBuffer();
			if (resList != null && resList.size() > 0) {
				Map<String, Object> res = resList.get(0);
				if (res != null && !res.isEmpty()) {
					Object brand = res.get("brand");
					if (brand != null) {
						sb.append(getString(R.string.car_api_item_series_brand_name));
						sb.append(": ");
						sb.append(brand);
					}
					Object brandName = res.get("brandName");
					if (brandName != null) {
						sb.append(getString(R.string.car_api_item_car_type));
						sb.append(": ");
						sb.append(brandName);
					}
					Object carImage = res.get("carImage");
					if (carImage != null) {
						sb.append("\n");
						sb.append(getString(R.string.car_api_item_car_image));
						sb.append(": ");
						sb.append(carImage);
					}
					Object seriesName = res.get("seriesName");
					if (seriesName != null) {
						sb.append("\n");
						sb.append(getString(R.string.car_api_item_series_name));
						sb.append(": ");
						sb.append(seriesName);
					}
					Object sonBrand = res.get("sonBrand");
					if (seriesName != null) {
						sb.append("\n");
						sb.append(getString(R.string.car_api_item_brand_car));
						sb.append(": ");
						sb.append(sonBrand);
					}
					Map<String, Object> item;
					List<Map<String, Object>> info = (List<Map<String, Object>>) res.get("baseInfo");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_base_info));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("airConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_air_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("carbody");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_car_body));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("chassis");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_chassis));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("controlConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_control_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("engine");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_engine));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("exterConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_exter_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("glassConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_glass_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("interConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_inter_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("lightConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_light_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("mediaConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_media_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("safetyDevice");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_safety_device));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("seatConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_seat_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("techConfig");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_tech_config));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("transmission");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_transmission));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("wheelInfo");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_wheel_info));
						item.put("info", getInfo(info));
						list.add(item);
					}

					info = (List<Map<String, Object>>) res.get("motorList");
					if (info != null && !info.isEmpty()) {
						item = new HashMap<String, Object>();
						item.put("name", getString(R.string.car_api_item_motor_list));
						item.put("info", getInfo(info));
						list.add(item);
					}
				}

				getListView().setAdapter(new MyAdapter(this, list));
				TextView headView = new TextView(getBaseContext());
				headView.setTextSize(16);
				headView.setSingleLine(false);
				headView.setTextColor(0xFF666666);
				headView.setPadding(10, 10, 10, 10);
				headView.setText(sb.toString());
				getListView().addHeaderView(headView);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private String getInfo(List<Map<String, Object>> info) {
		StringBuffer sb = new StringBuffer();
		for (Map<String, Object> tmp : info) {
			sb.append(tmp.get("name"));
			sb.append(": ");
			sb.append(tmp.get("value"));
			sb.append("\n");
		}
		return sb.toString();
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
				convertView = LayoutInflater.from(context).inflate(R.layout.view_car_series_details_list_item, parent, false);
			}
			Map<String, Object> item = getItem(position);
			TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
			tvName.setText(String.valueOf(item.get("name")));
			TextView tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
			tvInfo.setText(String.valueOf(item.get("info")));
			return convertView;
		}
	}
}
