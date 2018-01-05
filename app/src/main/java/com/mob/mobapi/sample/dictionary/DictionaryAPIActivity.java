/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.dictionary;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Dictionary;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class DictionaryAPIActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etDictionary;
	private TextView tvName;
	private TextView tvPinyin;
	private TextView tvWubi;
	private TextView tvBushou;
	private TextView tvBihuaWithBushou;
	private TextView tvBrief;
	private TextView tvDetail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dictionary);
		etDictionary  = ResHelper.forceCast(findViewById(R.id.etDictionary));
		tvName   = ResHelper.forceCast(findViewById(R.id.tvName));
		tvPinyin = ResHelper.forceCast(findViewById(R.id.tvPinyin));
		tvWubi   = ResHelper.forceCast(findViewById(R.id.tvWubi));
		tvBushou = ResHelper.forceCast(findViewById(R.id.tvBushou));
		tvBrief  = ResHelper.forceCast(findViewById(R.id.tvBrief));
		tvDetail = ResHelper.forceCast(findViewById(R.id.tvDetail));
		tvBihuaWithBushou = ResHelper.forceCast(findViewById(R.id.tvBihuaWithBushou));
		etDictionary.setText(R.string.test_word_you);
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		Dictionary api = (Dictionary) MobAPI.getAPI(Dictionary.NAME);
		api.queryDictionary(etDictionary.getText().toString().trim(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		tvName.setText(ResHelper.toString(res.get("name")));
		tvPinyin.setText(ResHelper.toString(res.get("pinyin")));
		tvWubi.setText(ResHelper.toString(res.get("wubi")));
		tvBushou.setText(ResHelper.toString(res.get("bushou")));
		tvBihuaWithBushou.setText(ResHelper.toString(res.get("bihuaWithBushou")));
		tvBrief.setText(ResHelper.toString(res.get("brief")));
		tvDetail.setText(ResHelper.toString(res.get("detail")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
