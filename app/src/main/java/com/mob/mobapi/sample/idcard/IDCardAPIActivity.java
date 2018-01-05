/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.idcard;

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
import com.mob.mobapi.apis.IDCard;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class IDCardAPIActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etCardNumber;
	private TextView tvArea;
	private TextView tvBirthday;
	private TextView tvSex;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idcard);
		etCardNumber = ResHelper.forceCast(findViewById(R.id.etCardNumber));
		tvArea       = ResHelper.forceCast(findViewById(R.id.tvArea));
		tvBirthday   = ResHelper.forceCast(findViewById(R.id.tvBirthday));
		tvSex        = ResHelper.forceCast(findViewById(R.id.tvSex));
		etCardNumber.setText("45102519800411512X"); // for example
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	public void onClick(View v) {
		// 获取API实例，查询身份证信息
		IDCard api = ResHelper.forceCast(MobAPI.getAPI(IDCard.NAME));
		api.queryIDCard(etCardNumber.getText().toString().trim(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> address = ResHelper.forceCast(result.get("result"));
		tvArea.setText(com.mob.tools.utils.ResHelper.toString(address.get("area")));
		tvBirthday.setText(com.mob.tools.utils.ResHelper.toString(address.get("birthday")));
		tvSex.setText(com.mob.tools.utils.ResHelper.toString(address.get("sex")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
