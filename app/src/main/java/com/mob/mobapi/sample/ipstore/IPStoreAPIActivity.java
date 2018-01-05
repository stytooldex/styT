/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.ipstore;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
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
import com.mob.mobapi.apis.IPStore;
import nico.styTool.R;
import com.mob.tools.utils.ResHelper;

public class IPStoreAPIActivity extends dump.z.BaseActivity_ implements OnClickListener, APICallback {
	private EditText etIPAddrNumber;
	private TextView tvIpaddr;
	private TextView tvCountry;
	private TextView tvProvince;
	private TextView tvCity;
	private TextView tvDistrict;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipstore);
		etIPAddrNumber = ResHelper.forceCast(findViewById(R.id.etIPAddr));
		tvIpaddr   = ResHelper.forceCast(findViewById(R.id.tvIPAddr));
		tvCountry  = ResHelper.forceCast(findViewById(R.id.tvCountry));
		tvProvince = ResHelper.forceCast(findViewById(R.id.tvProvince));
		tvCity     = ResHelper.forceCast(findViewById(R.id.tvCity));
		tvDistrict = ResHelper.forceCast(findViewById(R.id.tvDistrict));
		etIPAddrNumber.setText(getCurrentIpAddr());
		findViewById(R.id.btnSearch).setOnClickListener(this);
	}

	private String getCurrentIpAddr() {
		String ipaddr = "120.132.154.120"; // http://www.mob.com
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
						.hasMoreElements();) {
					InetAddress inetAddress = ipAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddr = inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		return ipaddr;
	}

	public void onClick(View v) {
		IPStore api = ResHelper.forceCast(MobAPI.getAPI(IPStore.NAME));
		api.queryIPStore(etIPAddrNumber.getText().toString().trim(), this);
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
		tvIpaddr.setText(com.mob.tools.utils.ResHelper.toString(res.get("ip")));
		tvCountry.setText(com.mob.tools.utils.ResHelper.toString(res.get("country")));
		tvProvince.setText(com.mob.tools.utils.ResHelper.toString(res.get("province")));
		tvCity.setText(com.mob.tools.utils.ResHelper.toString(res.get("city")));
		tvDistrict.setText(com.mob.tools.utils.ResHelper.toString(res.get("district")));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}
}
