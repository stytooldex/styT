/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.tiku;

import android.app.ListActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.TiKu;
import nico.styTool.R;

public class TiKuListAPIActivity extends ListActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final String title = getIntent().getStringExtra("title");
		final String cid = getIntent().getStringExtra("cid");
		final int type = getIntent().getIntExtra("type", 0);//0 kemu1, 1kemu4, 2shitiku
		getListView().setBackgroundColor(0xffffffff);
		getListView().setDivider(new ColorDrawable(0xff7f7f7f));
		getListView().setDividerHeight(1);
		TiKuAdapter adapter = null;
		final TiKu api = (TiKu) MobAPI.getAPI(TiKu.NAME);
		//0 kemu1, 1kemu4, 2shitiku
		switch (type) {
			case 0: {
				setTitle(R.string.tiku_api_kemu1);
				adapter = new TiKuAdapter() {
					public void requestData() {
						api.queryKeMu1(pageIndex + 1, PAGE_SIZE, this);
					}
				};
			} break;
			case 1: {
				setTitle(R.string.tiku_api_kemu4);
				adapter = new TiKuAdapter() {
					public void requestData() {
						api.queryKeMu4(pageIndex + 1, PAGE_SIZE, this);
					}
				};
			} break;
			case 2: {
				setTitle(title);
				adapter = new TiKuAdapter() {
					public void requestData() {
						api.queryShiTiKu(cid, pageIndex + 1, PAGE_SIZE, this);
					}
				};
			} break;
		}

		if (adapter != null) {
			setListAdapter(adapter);
			adapter.request();
		}
	}

}
