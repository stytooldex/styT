/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，
 * 我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.mob.mobapi.sample.usercenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.UserCenter;
import nico.styTool.R;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.ResHelper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserCenterAPIActivity extends dump.z.BaseActivity_ implements APICallback, View.OnClickListener {
	private static final int DIALOG_TYPE_REGISTER = 1;
	private static final int DIALOG_TYPE_LOGIN = 2;
	private static final int DIALOG_TYPE_CHANGE_PASSWORD = 3;
	private static final int DIALOG_TYPE_FORGOT_PASSWORD = 4;
	private static final int DIALOG_TYPE_RESET_PASSWORD = 5;

	private static final int DIALOG_TYPE_PROFILE_INPUT = 1;
	private static final int DIALOG_TYPE_DATA_INPUT = 2;
	private static final int DIALOG_TYPE_PROFILE_QUERY = 3;
	private static final int DIALOG_TYPE_DATA_QUERY = 4;
	private static final int DIALOG_TYPE_PROFILE_DEL = 5;
	private static final int DIALOG_TYPE_DATA_DEL = 6;

	private static final String PWD_SALT = "PUBLIC_LICENCE";

	private int dialogType = DIALOG_TYPE_REGISTER;
	private int dialogTypeProfile = DIALOG_TYPE_PROFILE_INPUT;

	private TextView tvUserInfo;
	private AlertDialog editNameAndPwdDialog;
	private AlertDialog editItemAndValueDialog;
	private EditText etUsername;
	private EditText etPassword;
	private EditText etNewPassword;
	private EditText etItem;
	private EditText etValue;

	private Button btnRegister;
	private Button btnLogin;
	private Button btnChangePassword;
	private Button btnProfilePut;
	private Button btnProfileQuery;
	private Button btnProfileDel;
	private Button btnDataPut;
	private Button btnDataQuery;
	private Button btnDataDel;
	private Button btnForgotPassword;
	private Button btnResetPassword;

	private UserCenter api;

	private String uid = null;
	private String token = null;
	private String username = null;

	private String inputItemStr = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercenter);
		tvUserInfo = ResHelper.forceCast(findViewById(R.id.tvUserInfo));

		btnRegister = ResHelper.forceCast(findViewById(R.id.btnRegister));
		btnLogin = ResHelper.forceCast(findViewById(R.id.btnLogin));
		btnChangePassword = ResHelper.forceCast(findViewById(R.id.btnChangePassword));
		btnForgotPassword = ResHelper.forceCast(findViewById(R.id.btnForgotPassword));
		btnResetPassword = ResHelper.forceCast(findViewById(R.id.btnResetPassword));
		btnProfilePut = ResHelper.forceCast(findViewById(R.id.btnProfilePut));
		btnProfileQuery = ResHelper.forceCast(findViewById(R.id.btnProfileQuery));
		btnProfileDel = ResHelper.forceCast(findViewById(R.id.btnProfileDel));
		btnDataPut = ResHelper.forceCast(findViewById(R.id.btnDataPut));
		btnDataQuery = ResHelper.forceCast(findViewById(R.id.btnDataQuery));
		btnDataDel = ResHelper.forceCast(findViewById(R.id.btnDataDel));
		btnRegister.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnChangePassword.setOnClickListener(this);
		btnProfilePut.setOnClickListener(this);
		btnProfileQuery.setOnClickListener(this);
		btnProfileDel.setOnClickListener(this);
		btnDataPut.setOnClickListener(this);
		btnDataQuery.setOnClickListener(this);
		btnDataDel.setOnClickListener(this);
		btnForgotPassword.setOnClickListener(this);
		btnResetPassword.setOnClickListener(this);

		api = ResHelper.forceCast(MobAPI.getAPI(UserCenter.NAME));
	}

	public void onClick(View view) {
		setBtnEnable(false);
		int vId = view.getId();
		switch (vId) {
			case R.id.btnRegister: {
				createEditNameAndPwdDialog(DIALOG_TYPE_REGISTER);
				editNameAndPwdDialog.setTitle(R.string.usercenter_api_btn_register);
				editNameAndPwdDialog.show();
			} break;
			case R.id.btnLogin: {
				createEditNameAndPwdDialog(DIALOG_TYPE_LOGIN);
				editNameAndPwdDialog.setTitle(R.string.usercenter_api_btn_login);
				editNameAndPwdDialog.show();
			} break;
			case R.id.btnChangePassword: {
				createEditNameAndPwdDialog(DIALOG_TYPE_CHANGE_PASSWORD);
				editNameAndPwdDialog.setTitle(R.string.usercenter_api_btn_change_password);
				editNameAndPwdDialog.show();
			} break;
			case R.id.btnResetPassword: {
				createEditNameAndPwdDialog(DIALOG_TYPE_RESET_PASSWORD);
				editNameAndPwdDialog.setTitle(R.string.usercenter_api_btn_reset_password);
				editNameAndPwdDialog.show();
			} break;
			case R.id.btnForgotPassword: {
				createEditNameAndPwdDialog(DIALOG_TYPE_FORGOT_PASSWORD);
				editNameAndPwdDialog.setTitle(R.string.usercenter_api_btn_forgot_password);
				editNameAndPwdDialog.show();
			} break;
			case R.id.btnProfileQuery: {
				createEditItemAndValueDialog(DIALOG_TYPE_PROFILE_QUERY);
				editItemAndValueDialog.setTitle(R.string.usercenter_api_btn_query_profile);
				editItemAndValueDialog.show();
			} break;
			case R.id.btnProfileDel: {
				createEditItemAndValueDialog(DIALOG_TYPE_PROFILE_DEL);
				editItemAndValueDialog.setTitle(R.string.usercenter_api_btn_del_profile);
				editItemAndValueDialog.show();
			} break;
			case R.id.btnProfilePut: {
				createEditItemAndValueDialog(DIALOG_TYPE_PROFILE_INPUT);
				editItemAndValueDialog.setTitle(R.string.usercenter_api_btn_put_profile);
				editItemAndValueDialog.show();
			} break;
			case R.id.btnDataPut: {
				createEditItemAndValueDialog(DIALOG_TYPE_DATA_INPUT);
				editItemAndValueDialog.setTitle(R.string.usercenter_api_btn_put_data);
				editItemAndValueDialog.show();
			} break;
			case R.id.btnDataQuery: {
				createEditItemAndValueDialog(DIALOG_TYPE_DATA_QUERY);
				editItemAndValueDialog.setTitle(R.string.usercenter_api_btn_query_data);
				editItemAndValueDialog.show();
			} break;
			case R.id.btnDataDel: {
				createEditItemAndValueDialog(DIALOG_TYPE_DATA_DEL);
				editItemAndValueDialog.setTitle(R.string.usercenter_api_btn_del_data);
				editItemAndValueDialog.show();
			} break;
		}
	}

	private void setBtnEnable(boolean enable) {
		if (enable) {
			if (etPassword != null) {
				etPassword.setText("");
			}
			if (etNewPassword != null) {
				etNewPassword.setText("");
			}
			if (etItem != null) {
				etItem.setText("");
			}
			if (etValue != null) {
				etValue.setText("");
			}
		}
		btnRegister.setEnabled(enable);
		btnLogin.setEnabled(enable);
		btnChangePassword.setEnabled(enable);
		btnProfilePut.setEnabled(enable);
		btnProfileQuery.setEnabled(enable);
		btnProfileDel.setEnabled(enable);
		btnDataPut.setEnabled(enable);
		btnDataQuery.setEnabled(enable);
		btnDataDel.setEnabled(enable);
		btnResetPassword.setEnabled(enable);
		btnForgotPassword.setEnabled(enable);
	}

	private void createEditNameAndPwdDialog(int type) {
		dialogType = type;
		if (editNameAndPwdDialog == null) {
			View view = LayoutInflater.from(this).inflate(R.layout.view_usercenter_item_register, null);
			etUsername = ResHelper.forceCast(view.findViewById(R.id.etUsername));
			etPassword = ResHelper.forceCast(view.findViewById(R.id.etPassword));
			etNewPassword = ResHelper.forceCast(view.findViewById(R.id.etNewPassword));
			editNameAndPwdDialog = new AlertDialog.Builder(this)
					.setView(view)
					.setCancelable(false)
					.setPositiveButton(getString(R.string.usercenter_api_btn_confirm), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (dialogType) {
								case DIALOG_TYPE_REGISTER: {
									if (checkInput(false)) {
										String email = etNewPassword.getText().toString();
										api.register(etUsername.getText().toString(), encodePassword(etPassword.getText().toString()),
												email, UserCenterAPIActivity.this);
									}
								} break;
								case DIALOG_TYPE_LOGIN: {
									if (checkInput(false)) {
										api.login(etUsername.getText().toString(), encodePassword(etPassword.getText().toString()),
												UserCenterAPIActivity.this);
									}
								} break;
								case DIALOG_TYPE_CHANGE_PASSWORD: {
									if (checkInput(true)) {
										api.changePassword(etUsername.getText().toString(), encodePassword(etPassword.getText().toString()),
												encodePassword(etNewPassword.getText().toString()), UserCenterAPIActivity.this);
									}
								} break;
								case DIALOG_TYPE_RESET_PASSWORD: {
									if (checkInput(true)) {
										String vcode = etPassword.getText().toString();
										api.changePassword(etUsername.getText().toString(), vcode,
												encodePassword(etNewPassword.getText().toString()), 2, UserCenterAPIActivity.this);
									}
								} break;
								case DIALOG_TYPE_FORGOT_PASSWORD: {
									if (checkInput(false, false)) {
										api.forgotPassword(etUsername.getText().toString(), UserCenterAPIActivity.this);
									}
								} break;
							}
						}
					})
					.setNegativeButton(getString(R.string.usercenter_api_btn_cancel), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							setBtnEnable(true);
						}
					})
					.create();
		}
		switch (dialogType) {
			case DIALOG_TYPE_REGISTER: {
				etNewPassword.setVisibility(View.VISIBLE);
				etNewPassword.setHint(R.string.usercenter_api_hint_input_email);
				etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT);
				etUsername.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setVisibility(View.VISIBLE);
				etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				etPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setHint(R.string.usercenter_api_hint_input_pwd);
			} break;
			case DIALOG_TYPE_LOGIN: {
				etNewPassword.setVisibility(View.GONE);
				etUsername.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setVisibility(View.VISIBLE);
				etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
				etPassword.setHint(R.string.usercenter_api_hint_input_pwd);
			} break;
			case DIALOG_TYPE_CHANGE_PASSWORD: {
				etNewPassword.setVisibility(View.VISIBLE);
				etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				etNewPassword.setHint(R.string.usercenter_api_hint_input_new_pwd);
				etUsername.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setVisibility(View.VISIBLE);
				etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				etPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setHint(R.string.usercenter_api_hint_input_old_pwd);
			} break;
			case DIALOG_TYPE_RESET_PASSWORD: {
				etNewPassword.setVisibility(View.VISIBLE);
				etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				etNewPassword.setHint(R.string.usercenter_api_hint_input_new_pwd);
				etUsername.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setVisibility(View.VISIBLE);
				etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
				etPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etPassword.setHint(R.string.usercenter_api_hint_input_vcode);
			} break;
			case DIALOG_TYPE_FORGOT_PASSWORD: {
				etNewPassword.setVisibility(View.GONE);
				etPassword.setVisibility(View.GONE);
				etUsername.setImeOptions(EditorInfo.IME_ACTION_DONE);
			} break;
		}
	}

	private void createEditItemAndValueDialog(int type) {
		dialogTypeProfile = type;
		if (editItemAndValueDialog == null) {
			View view = LayoutInflater.from(this).inflate(R.layout.view_usercenter_item_input, null);
			etItem = ResHelper.forceCast(view.findViewById(R.id.etItem));
			etValue = ResHelper.forceCast(view.findViewById(R.id.etValue));
			editItemAndValueDialog = new AlertDialog.Builder(this)
					.setView(view)
					.setCancelable(false)
					.setPositiveButton(getString(R.string.usercenter_api_btn_confirm), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							String item = null;
							String value = null;
							try {
								//进行BASE64编码,URL_SAFE/NO_WRAP/NO_PADDING
								item = encodeData(etItem.getText().toString().trim());
								inputItemStr = item;
								value = encodeData(etValue.getText().toString().trim());
							} catch (Exception e) {
								e.printStackTrace();
							}
							switch (dialogTypeProfile) {
								case DIALOG_TYPE_PROFILE_INPUT: {
									api.putProfile(token, uid, item, value, UserCenterAPIActivity.this);
								} break;
								case DIALOG_TYPE_DATA_INPUT: {
									api.putData(token, uid, item, value, UserCenterAPIActivity.this);
								} break;
								case DIALOG_TYPE_PROFILE_DEL: {
									api.delProfile(token, uid, item, UserCenterAPIActivity.this);
								} break;
								case DIALOG_TYPE_PROFILE_QUERY:
								case DIALOG_TYPE_DATA_QUERY: {
									try {
										String inputTxt = etItem.getText().toString().trim();
										String[] tmps = inputTxt.split(",");
										StringBuffer sb = new StringBuffer();
										for (String tmp : tmps) {
											sb.append(encodeData(tmp.trim()));
											sb.append(",");
										}
										if (sb.length() > 0) {
											item = sb.substring(0, sb.length() - 1);
										}
										inputItemStr = item;
										if (dialogTypeProfile == DIALOG_TYPE_DATA_QUERY) {
											api.queryData(token, uid, item, UserCenterAPIActivity.this);
										} else if (dialogTypeProfile == DIALOG_TYPE_PROFILE_QUERY) {
											api.queryProfile(uid, item, UserCenterAPIActivity.this);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								} break;
								case DIALOG_TYPE_DATA_DEL: {
									api.delData(token, uid, item, UserCenterAPIActivity.this);
								} break;
							}
						}
					})
					.setNegativeButton(getString(R.string.usercenter_api_btn_cancel), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							setBtnEnable(true);
						}
					})
					.create();
		}
		switch (dialogTypeProfile) {
			case DIALOG_TYPE_PROFILE_INPUT:
			case DIALOG_TYPE_DATA_INPUT: {
				etItem.setHint(R.string.usercenter_api_hint_input_item);
				etItem.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				etValue.setVisibility(View.VISIBLE);
			} break;
			case DIALOG_TYPE_PROFILE_DEL:
			case DIALOG_TYPE_DATA_DEL: {
				etItem.setHint(R.string.usercenter_api_hint_input_item);
				etItem.setImeOptions(EditorInfo.IME_ACTION_DONE);
				etValue.setVisibility(View.GONE);
			} break;
			case DIALOG_TYPE_PROFILE_QUERY:
			case DIALOG_TYPE_DATA_QUERY: {
				etItem.setHint(R.string.usercenter_api_hint_input_item_more);
				etItem.setImeOptions(EditorInfo.IME_ACTION_DONE);
				etValue.setVisibility(View.GONE);
			} break;
		}
	}

	private boolean checkInput(boolean checkNewPwd) {
		return checkInput(checkNewPwd, true);
	}

	private boolean checkInput(boolean checkNewPwd, boolean checkPwd) {
		username = etUsername.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, R.string.usercenter_api_toast_username_notnull, Toast.LENGTH_SHORT).show();
			setBtnEnable(true);
			return false;
		}
		if (checkPwd && TextUtils.isEmpty(etPassword.getText())) {
			Toast.makeText(this, R.string.usercenter_api_toast_pwd_notnull, Toast.LENGTH_SHORT).show();
			setBtnEnable(true);
			return false;
		}
		if (checkNewPwd && TextUtils.isEmpty(etNewPassword.getText())) {
			Toast.makeText(this, R.string.usercenter_api_toast_pwd_notnull, Toast.LENGTH_SHORT).show();
			setBtnEnable(true);
			return false;
		}
		return true;
	}

	public void onSuccess(API api, int action, Map<String, Object> result) {
		switch (action) {
			case UserCenter.ACTION_REGISTER: {
				uid = ResHelper.forceCast(result.get("result"));
				tvUserInfo.setText(getString(R.string.usercenter_api_toast_register_suc) + "\n"
						+ "uid = " + uid + "\n"
						+ getString(R.string.usercenter_api_user_not_login));
			} break;
			case UserCenter.ACTION_LOGIN: {
				HashMap<String, String> res = ResHelper.forceCast(result.get("result"));
				if (res != null) {
					token = res.get("token");
					uid = res.get("uid");
				}
				tvUserInfo.setText(getString(R.string.usercenter_api_toast_login_suc) + "\n"
						+ "uid = " + uid + "\n"
						+ "token =  " + token);
			} break;
			case UserCenter.ACTION_CHANGE_PASSWORD: {
				tvUserInfo.setText(getString(R.string.usercenter_api_toast_change_suc) + "\n"
						+ "uid = " + uid + "\n"
						+ "token =  " + token);
			} break;
			case UserCenter.ACTION_FORGOT_PASSWORD: {
				tvUserInfo.setText(String.valueOf(result.get("msg")));
			} break;
			case UserCenter.ACTION_PROFILE_PUT:
			case UserCenter.ACTION_DATA_PUT: {
				tvUserInfo.setText(getString(R.string.usercenter_api_toast_put_suc) + "\n"
						+ "uid = " + uid + "\n"
						+ "token =  " + token);
			} break;
			case UserCenter.ACTION_DATA_DEL:
			case UserCenter.ACTION_PROFILE_DEL: {
				tvUserInfo.setText(getString(R.string.usercenter_api_toast_del_suc) + "\n"
						+ "uid = " + uid + "\n"
						+ "token =  " + token);
			} break;
			case UserCenter.ACTION_DATA_QUERY:
			case UserCenter.ACTION_PROFILE_QUERY: {
				try {
					if (TextUtils.isEmpty(inputItemStr) || inputItemStr.contains(",")) {
						//如果输入的为空或者是输入了多项,则查询到的是用户所有或者多项的资料项或者数据
						HashMap<String, String> response = ResHelper.forceCast(result.get("result"));
						StringBuffer sb = new StringBuffer();
						for (Map.Entry<String, String> entry : response.entrySet()) {
							sb.append(decodeData(entry.getKey()));
							sb.append(":");
							sb.append(decodeData(entry.getValue()));
							sb.append("\n");
						}
						tvUserInfo.setText(getString(R.string.usercenter_api_toast_query_suc) + "\n"
								+ sb.toString());
						break;
					}
					String value = decodeData((String) result.get("result"));
					tvUserInfo.setText(getString(R.string.usercenter_api_toast_query_suc) + "\n"
							+ "value = " + value + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} break;
		}
		setBtnEnable(true);
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
		switch (action) {
			case UserCenter.ACTION_REGISTER:
			case UserCenter.ACTION_LOGIN: {
				tvUserInfo.setText(getString(R.string.usercenter_api_user_not_login));
			} break;
			case UserCenter.ACTION_CHANGE_PASSWORD: {
			} break;
		}
		setBtnEnable(true);
	}

	//用户密码加密(此部分用户可以自己实现)
	private String encodePassword(String password) {
		return Data.MD5(password + PWD_SALT);
	}

	//用户资料项和用户数据进行base64编码(此部分必须是base64编码,如有需要,在编码前可自行加密)
	private String encodeData(String data) throws UnsupportedEncodingException {
		//进行BASE64编码,URL_SAFE/NO_WRAP/NO_PADDING
		return new String(Base64.encode(data.getBytes("utf-8"), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING), "utf-8");
	}

	//解析用户资料项和用户数据
	private String decodeData(String data) throws UnsupportedEncodingException {
		//进行BASE64解码,URL_SAFE/NO_WRAP/NO_PADDING
		return new String(Base64.decode(data.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING), "utf-8");
	}
}
