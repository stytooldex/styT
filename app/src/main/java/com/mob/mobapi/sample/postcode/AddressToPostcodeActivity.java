/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.postcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Postcode;
import nico.styTool.R;

public class AddressToPostcodeActivity extends dump.z.BaseActivity_ implements APICallback, OnItemSelectedListener {
	private Spinner spProvince;
	private Spinner spCity;
	private Spinner spDistrict;
	private Spinner spAddress;
	private TextView tvPostcode;

	private ArrayList<HashMap<String, Object>> resultList;
	private ArrayList<String> provinceIds;
	private ArrayList<Integer> cityIds;
	private ArrayList<Integer> districtIds;
	private HashMap<String, String> addressToPostcode;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postcode_a2p);
		spProvince = (Spinner) findViewById(R.id.spProvince);
		spProvince.setOnItemSelectedListener(this);
		spCity = (Spinner) findViewById(R.id.spCity);
		spCity.setOnItemSelectedListener(this);
		spDistrict = (Spinner) findViewById(R.id.spDistrict);
		spDistrict.setOnItemSelectedListener(this);
		spAddress = (Spinner) findViewById(R.id.spAddress);
		spAddress.setOnItemSelectedListener(this);
		tvPostcode = (TextView) findViewById(R.id.tvPostcode);

		// 获取API，请求城市列表
		Postcode api = (Postcode) MobAPI.getAPI(Postcode.NAME);
		api.listAddress(this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case Postcode.ACTION_PCD: onDistrictListGot(result); break;
			case Postcode.ACTION_SEARCH: onAddressListGot(result); break;
		}
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	// 显示城市数据
	@SuppressWarnings("unchecked")
	private void onDistrictListGot(Map<String, Object> result) {
		resultList = (ArrayList<HashMap<String, Object>>) result.get("result");
		provinceIds = new ArrayList<String>();
		ArrayList<String> provinces = new ArrayList<String>();
		for (HashMap<String, Object> p : resultList) {
			provinceIds.add(com.mob.tools.utils.ResHelper.toString(p.get("id")));
			provinces.add(com.mob.tools.utils.ResHelper.toString(p.get("province")));
		}
		spProvince.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, provinces));
	}

	// 显示邮编
	@SuppressWarnings("unchecked")
	private void onAddressListGot(Map<String, Object> result) {
		ArrayList<HashMap<String, Object>> addressList = (ArrayList<HashMap<String, Object>>) result.get("result");
		addressToPostcode = new HashMap<String, String>();
		ArrayList<String> addresses = new ArrayList<String>();
		for (HashMap<String, Object> add : addressList) {
			ArrayList<String> addList = (ArrayList<String>) add.get("address");
			if (addList != null) {
				for (String a : addList) {
					addresses.add(a);
					addressToPostcode.put(a, com.mob.tools.utils.ResHelper.toString(add.get("postNumber")));
				}
			}
		}
		Collections.sort(addresses);

		spAddress.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, addresses));
	}

	@SuppressWarnings("unchecked")
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.equals(spProvince)) {
			ArrayList<HashMap<String, Object>> cityList
					= (ArrayList<HashMap<String, Object>>) resultList.get(position).get("city");
			cityIds = new ArrayList<Integer>();
			ArrayList<String> cities = new ArrayList<String>();
			for (HashMap<String, Object> c : cityList) {
				cityIds.add(Integer.parseInt(com.mob.tools.utils.ResHelper.toString(c.get("id"))));
				cities.add(com.mob.tools.utils.ResHelper.toString(c.get("city")));
			}
			spCity.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, cities));
		} else if (parent.equals(spCity)) {
			ArrayList<HashMap<String, Object>> cityList
					= (ArrayList<HashMap<String, Object>>) resultList.get(spProvince.getSelectedItemPosition()).get("city");
			ArrayList<HashMap<String, Object>> districtList
					= (ArrayList<HashMap<String, Object>>) cityList.get(position).get("district");
			districtIds = new ArrayList<Integer>();
			ArrayList<String> districts = new ArrayList<String>();
			for (HashMap<String, Object> d : districtList) {
				districtIds.add(Integer.parseInt(com.mob.tools.utils.ResHelper.toString(d.get("id"))));
				districts.add(com.mob.tools.utils.ResHelper.toString(d.get("district")));
			}
			spDistrict.setAdapter(new ArrayAdapter<String>(this, R.layout.view_weather_district, districts));
		} else if (parent.equals(spDistrict)) {
			String pid = provinceIds.get(spProvince.getSelectedItemPosition());
			int cid = cityIds.get(spCity.getSelectedItemPosition());
			int did = districtIds.get(position);

			// 请求指定城市ID的邮编
			Postcode api = (Postcode) MobAPI.getAPI(Postcode.NAME);
			api.addressToPostcode(pid, cid, did, null, this);
		} else if (parent.equals(spAddress)) {
			String province = (String) spProvince.getSelectedItem();
			String city = (String) spCity.getSelectedItem();
			String district = (String) spDistrict.getSelectedItem();
			String address = (String) spAddress.getSelectedItem();
			String postcode = addressToPostcode.get(address);
			tvPostcode.setText(getString(R.string.postcode_is_x, province, city, district, address, postcode));
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

}
