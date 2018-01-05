/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.flight;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Flight;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.Map;

public class FlightCityThreeCodesAPIActivity extends ListActivity implements APICallback {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.flight_api_title_city_three_code);
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);

		//查询所有城市机场三字码信息
		((Flight) MobAPI.getAPI(Flight.NAME)).queryCityAirportThreeCodes(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case Flight.ACTION_QUERY_THREE_CODE: {
				ArrayList<Map<String, Object>> res = ResHelper.forceCast(result.get("result"));
				if (res == null) {
					return;
				}
				SimpleAdapter adapter = new SimpleAdapter(this, res, R.layout.view_flight_three_code_list_item,
						new String[]{"airPortName", "cityCode", "cityName", "threeCode"},
						new int[]{R.id.tvAirPortName, R.id.tvCityCode, R.id.tvCityName, R.id.tvThreeCode});
				getListView().setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
