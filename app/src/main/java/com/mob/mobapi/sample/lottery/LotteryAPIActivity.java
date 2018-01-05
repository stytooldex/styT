/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.lottery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Lottery;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LotteryAPIActivity extends dump.z.BaseActivity_ implements APICallback, AdapterView.OnItemClickListener {
	private boolean isQuerying = false;

	private EditText etPeriod;
	private TextView tvDateTime;
	private TextView tvName;
	private TextView tvSales;
	private TextView tvPool;
	private TextView tvPeriod;
	private TextView tvLotteryNumber;

	private SimpleAdapter lotteryTypeListAdapter;
	private LotteryResultAdapter lotteryResultAdapter;

	private ArrayList<HashMap<String, Object>> lotteryTypeList;
	private ArrayList<HashMap<String, Object>> lotteryResultList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lottery);
		etPeriod = ResHelper.forceCast(findViewById(R.id.etPeriod));
		tvDateTime = ResHelper.forceCast(findViewById(R.id.tvDateTime));
		tvName = ResHelper.forceCast(findViewById(R.id.tvName));
		tvSales = ResHelper.forceCast(findViewById(R.id.tvSales));
		tvPool = ResHelper.forceCast(findViewById(R.id.tvPool));
		tvPeriod = ResHelper.forceCast(findViewById(R.id.tvPeriod));
		tvLotteryNumber = ResHelper.forceCast(findViewById(R.id.tvLotteryNumber));

		GridView gvLotteryList = ResHelper.forceCast(findViewById(R.id.gvLotteryList));
		ListView lvLotteryResult = ResHelper.forceCast(findViewById(R.id.lvLotteryAward));
		gvLotteryList.setOnItemClickListener(this);

		//init data
		updateLotteryInfo(null, null, null, null, null, null);

		lotteryTypeList = new ArrayList<HashMap<String, Object>>();
		lotteryTypeListAdapter = new SimpleAdapter(this, lotteryTypeList, android.R.layout.simple_list_item_1, new String[]{"name"},
				new int[]{android.R.id.text1});
		gvLotteryList.setAdapter(lotteryTypeListAdapter);

		lotteryResultList = new ArrayList<HashMap<String, Object>>();
		lotteryResultAdapter = new LotteryResultAdapter(this, lotteryResultList);
		lvLotteryResult.setAdapter(lotteryResultAdapter);

		//获取彩种列表
		((Lottery) ResHelper.forceCast(MobAPI.getAPI(Lottery.NAME))).queryLotteryList(LotteryAPIActivity.this);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		if (isQuerying) {
			return;
		}
		isQuerying = true;
		//查询开奖结果
		HashMap<String, Object> item = ResHelper.forceCast(lotteryTypeListAdapter.getItem(position));
		String name = ResHelper.forceCast(item.get("name"));
		((Lottery) ResHelper.forceCast(MobAPI.getAPI(Lottery.NAME))).queryLotteryResults(name, etPeriod.getText().toString(),
				LotteryAPIActivity.this);
	}

	private void updateLotteryInfo(String datetime, String name, String sales, String pool, String period, String lotteryNumber) {
		tvDateTime.setText(getString(R.string.lottery_api_title_datetime, datetime == null ? "" : datetime));
		tvName.setText(getString(R.string.lottery_api_title_name, name == null ? "" : name));
		tvSales.setText(getString(R.string.lottery_api_title_sales, sales == null ? "" : sales));
		tvPool.setText(getString(R.string.lottery_api_title_pool, pool == null ? "" : pool));
		tvPeriod.setText(getString(R.string.lottery_api_title_period, period == null ? "" : period));
		tvLotteryNumber.setText(getString(R.string.lottery_api_title_lottery_number, lotteryNumber == null ? "" : lotteryNumber));
	}

	private void updateLotteryTypeList(Map<String, Object> result) {
		//更新彩票各类列表
		ArrayList<String> res = ResHelper.forceCast(result.get("result"));
		if (res == null || res.size() == 0) {
			return;
		}
		ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> tempMap;
		for (String name : res) {
			tempMap = new HashMap<String, Object>();
			tempMap.put("name", name);
			tempList.add(tempMap);
		}
		if (null != tempList && tempList.size() > 0) {
			lotteryTypeList.clear();
			lotteryTypeList.addAll(tempList);
			lotteryTypeListAdapter.notifyDataSetChanged();
		}
	}

	private void updateLotteryResult(Map<String, Object> result) {
		//更新开奖结果
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		if (res == null || res.size() == 0) {
			return;
		}
		updateLotteryInfo(ResHelper.toString(res.get("awardDateTime")), ResHelper.toString(res.get("name")),
				getNumberString(res.get("sales")), getNumberString(res.get("pool")), ResHelper.toString(res.get("period")),
				ResHelper.toString(res.get("lotteryNumber")));

		ArrayList<HashMap<String, Object>> tempList = ResHelper.forceCast(res.get("lotteryDetails"));
		if (null != tempList && tempList.size() > 0) {
			lotteryResultList.clear();
			lotteryResultList.addAll(tempList);
			lotteryResultAdapter.notifyDataSetChanged();
		}
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case Lottery.ACTION_LIST: {
				updateLotteryTypeList(result);
			} break;
			case Lottery.ACTION_QUERY: {
				updateLotteryResult(result);
				etPeriod.setText("");
				isQuerying = false;
			} break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		if (action == Lottery.ACTION_QUERY) {
			isQuerying = false;
		}
	}

	private String getNumberString(Object obj) {
		if (obj instanceof Double) {
			return ResHelper.toString(Math.round((Double) obj));
		}
		if (obj instanceof Float) {
			return ResHelper.toString(Math.round((Float) obj));
		}
		return ResHelper.toString(obj);
	}

	private class LotteryResultAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<HashMap<String, Object>> list;

		LotteryResultAdapter(Context context, ArrayList<HashMap<String, Object>> res) {
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
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.view_lottery_item, null);
			}
			TextView tvAwards = ResHelper.forceCast(convertView.findViewById(R.id.tvAwards));
			TextView tvAwardNumber = ResHelper.forceCast(convertView.findViewById(R.id.tvAwardNumber));
			TextView tvAwardPrice = ResHelper.forceCast(convertView.findViewById(R.id.tvAwardPrice));
			TextView tvType = ResHelper.forceCast(convertView.findViewById(R.id.tvType));
			HashMap<String, Object> res = getItem(position);
			if (res != null) {
				tvAwards.setText(ResHelper.toString(res.get("awards")));
				tvAwardNumber.setText(getNumberString(res.get("awardNumber")));
				tvAwardPrice.setText(getNumberString(res.get("awardPrice")));
				String type = ResHelper.toString(res.get("type"));
				tvType.setText(type == null ? "" : type);
			}
			return convertView;
		}
	}
}
