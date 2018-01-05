package nico.styTool;

public class jni_string {
    /*
    CompoundButton mCompoundButton;
    android.support.v7.widget.Toolbar toolbar;

    @TargetApi(21)
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked && compoundButton == mCompoundButton
                && getResources().getBoolean(R.bool.use_accessibility_service)
                && WatchingAccessibilityService.getInstance() == null) {
            new Builder(this).setMessage(R.string.dialog_enable_accessibility_msg)
                    .setPositiveButton(R.string.dialog_enable_accessibility_positive_btn, new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.ACCESSIBILITY_SETTINGS");
                            startActivity(intent);

                        }
                    }).setNegativeButton(R.string.dialog_enable_accessibility_Nagetive_btn, this).setOnCancelListener(this).create().show();
            DefaultSharedPreferences.save(this, true);
        } else if (compoundButton == mCompoundButton) {
            DefaultSharedPreferences.save(this, isChecked);
            if (isChecked) {
                ViewWindow.showView(this, getPackageName() + "\n" + getClass().getName());
            } else {
                ViewWindow.removeView();
            }
        }
    }

    public void ok(View view) {
        Intent intent = new Intent(jni_string.this, FxService.class);
        startService(intent);
        finish();
    }

    public void no(View view) {
        Intent intent = new Intent(jni_string.this, FxService.class);
        stopService(intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.jni_main);


        ViewWindow.showView(this, "");
        mCompoundButton = (CompoundButton) findViewById(R.id.sw_window);
        mCompoundButton.setOnCheckedChangeListener(this);
        if (getResources().getBoolean(R.bool.use_watching_service)) {
            startService(new Intent(this, WatchingService.class));
        }
    }

    protected void onPause() {
        super.onPause();
        if (!DefaultSharedPreferences.read(this)) {
            return;
        }
        if (!getResources().getBoolean(R.bool.use_accessibility_service) || WatchingAccessibilityService.getInstance() != null) {
            NotificationActionReceiver.showNotification(this, false);
        }
    }

    protected void onResume() {
        super.onResume();
        status();
        NotificationActionReceiver.initNotification(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        status();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        status();
    }

    private void status() {
        mCompoundButton.setChecked(DefaultSharedPreferences.read(this));
        if (getResources().getBoolean(R.bool.use_accessibility_service) && WatchingAccessibilityService.getInstance() == null) {
            mCompoundButton.setChecked(false);
        }
    }*/
}
