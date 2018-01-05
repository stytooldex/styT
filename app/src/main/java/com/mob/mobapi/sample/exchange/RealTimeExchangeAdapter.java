/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.exchange;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Exchange;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RealTimeExchangeAdapter extends BaseAdapter implements APICallback {
	private static final int PAGE_SIZE = 50;
	private int pageIndex;
	private int totalPage;
	private ArrayList<HashMap<String, Object>> list;
	private boolean isLoading = false;

	public RealTimeExchangeAdapter() {
		list = new ArrayList<HashMap<String,Object>>();
	}

	/** 请求下一个页面的数据 */
	public void requestData() {
		if (isLoading) {
			return;
		}
		isLoading = true;
		Exchange api = ResHelper.forceCast(MobAPI.getAPI(Exchange.NAME));
		api.queryRealTime(pageIndex + 1, PAGE_SIZE, this);
	}

	@SuppressWarnings("unchecked")
	public void onSuccess(API api, int action, Map<String, Object> result) {
		isLoading = false;
		// 解析数据
		result = (Map<String, Object>) result.get("result");
		try {
			int curPage = ResHelper.parseInt(ResHelper.toString(result.get("page")));
			totalPage = ResHelper.parseInt(ResHelper.toString(result.get("totalPage")));
			if (curPage != pageIndex + 1) {
				return;
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}

		// 追加数据
		pageIndex++;
		ArrayList<HashMap<String, Object>> resultList = ResHelper.forceCast(result.get("resultList"));
		list.addAll(resultList);

		// 显示数据
		notifyDataSetChanged();
	}

	public void onError(API api, int action, Throwable details) {
		isLoading = false;
		details.printStackTrace();
		Toast.makeText(MobSDK.getContext(), R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	public int getCount() {
		if (list.size() == 0) {
			return 0;
		} else if (pageIndex == totalPage) {
			return list.size();
		} else {
			return list.size() + 1;
		}
	}

	public HashMap<String, Object> getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
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

	@SuppressWarnings("unchecked")
	private View getView1(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.view_exchange_real_time_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvBuyPic = ResHelper.forceCast(convertView.findViewById(R.id.tvBuyPic));
			viewHolder.tvClosePri = ResHelper.forceCast(convertView.findViewById(R.id.tvClosePri));
			viewHolder.tvCode = ResHelper.forceCast(convertView.findViewById(R.id.tvCode));
			viewHolder.tvCurrency = ResHelper.forceCast(convertView.findViewById(R.id.tvCurrency));
			viewHolder.tvDate = ResHelper.forceCast(convertView.findViewById(R.id.tvDate));
			viewHolder.tvDiffAmo = ResHelper.forceCast(convertView.findViewById(R.id.tvDiffAmo));
			viewHolder.tvDiffPer = ResHelper.forceCast(convertView.findViewById(R.id.tvDiffPer));
			viewHolder.tvHighPic = ResHelper.forceCast(convertView.findViewById(R.id.tvHighPic));
			viewHolder.tvLowPic = ResHelper.forceCast(convertView.findViewById(R.id.tvLowPic));
			viewHolder.tvOpenPri = ResHelper.forceCast(convertView.findViewById(R.id.tvOpenPri));
			viewHolder.tvRange = ResHelper.forceCast(convertView.findViewById(R.id.tvRange));
			viewHolder.tvSellPic = ResHelper.forceCast(convertView.findViewById(R.id.tvSellPic));
			viewHolder.tvYesterdayPic = ResHelper.forceCast(convertView.findViewById(R.id.tvYesterdayPic));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = ResHelper.forceCast(convertView.getTag());
		}

		HashMap<String, Object> data = getItem(position);
		viewHolder.tvBuyPic.setText(String.valueOf(data.get("buyPic")));
		viewHolder.tvClosePri.setText(String.valueOf(data.get("closePri")));
		viewHolder.tvCode.setText(String.valueOf(data.get("code")));
		viewHolder.tvCurrency.setText(String.valueOf(data.get("currency")));
		viewHolder.tvDate.setText(String.valueOf(data.get("date")));
		viewHolder.tvDiffAmo.setText(String.valueOf(data.get("diffAmo")));
		viewHolder.tvDiffPer.setText(String.valueOf(data.get("diffPer")));
		viewHolder.tvHighPic.setText(String.valueOf(data.get("highPic")));
		viewHolder.tvLowPic.setText(String.valueOf(data.get("lowPic")));
		viewHolder.tvOpenPri.setText(String.valueOf(data.get("openPri")));
		viewHolder.tvRange.setText(String.valueOf(data.get("range")));
		viewHolder.tvSellPic.setText(String.valueOf(data.get("sellPic")));
		viewHolder.tvYesterdayPic.setText(String.valueOf(data.get("yesDayPic")));
		return convertView;
	}

	private View getView2(View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ProgressBar(parent.getContext());
		}

		if (pageIndex < totalPage) {
			convertView.setVisibility(View.VISIBLE);
			requestData();
		} else {
			convertView.setVisibility(View.GONE);
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView tvBuyPic;
		TextView tvClosePri;
		TextView tvCode;
		TextView tvCurrency;
		TextView tvDate;
		TextView tvDiffAmo;
		TextView tvDiffPer;
		TextView tvHighPic;
		TextView tvLowPic;
		TextView tvOpenPri;
		TextView tvRange;
		TextView tvSellPic;
		TextView tvYesterdayPic;
	}

}
