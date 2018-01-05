/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.idiom;

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
import com.mob.mobapi.apis.Idiom;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class IdiomAPIActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etIdiom;
	private TextView tvName;
	private TextView tvPinyin;
	private TextView tvPretation;
	private TextView tvSource;
	private TextView tvSample;
	private TextView tvSampleFrom;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idiom);
		etIdiom = ResHelper.forceCast(findViewById(R.id.etIdiom));
		tvName = ResHelper.forceCast(findViewById(R.id.tvName));
		tvPinyin = ResHelper.forceCast(findViewById(R.id.tvPinyin));
		tvPretation = ResHelper.forceCast(findViewById(R.id.tvPretation));
		tvSource = ResHelper.forceCast(findViewById(R.id.tvSource));
		tvSample = ResHelper.forceCast(findViewById(R.id.tvSample));
		tvSampleFrom = ResHelper.forceCast(findViewById(R.id.tvSampleFrom));
		etIdiom.setText(R.string.test_word_hujiahuwei);
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		Idiom api = (Idiom) MobAPI.getAPI(Idiom.NAME);
		api.queryIdiom(etIdiom.getText().toString().trim(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		tvName.setText(com.mob.tools.utils.ResHelper.toString(res.get("name")));
		tvPinyin.setText(com.mob.tools.utils.ResHelper.toString(res.get("pinyin")));
		tvPretation.setText(com.mob.tools.utils.ResHelper.toString(res.get("pretation")));
		tvSource.setText(com.mob.tools.utils.ResHelper.toString(res.get("source")));
		tvSample.setText(com.mob.tools.utils.ResHelper.toString(res.get("sample")));
		tvSampleFrom.setText(com.mob.tools.utils.ResHelper.toString(res.get("sampleFrom")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
