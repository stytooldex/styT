package dump.b;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import dump.b.a.ColorPickerView;
import dump.b.a.builder.ColorPickerClickListener;
import dump.b.a.builder.ColorPickerDialogBuilder;
import nico.styTool.R;


public class LEDActivity extends dump.z.BaseActivity_ implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener {


    private TextInputEditText mContentLed;

    private TextView mPreviewLed;
    private AppCompatSeekBar mRollspeedSeekbarLed;
    private AppCompatRadioButton mAdaptiveRadiobtnLed;
    private RadioGroup mShowstyleRadiogroupLed;
    private AppCompatSpinner mCompatSpinner;
    private TextView mLinesTextView;
    private AppCompatSeekBar mlinesSeekbar;

    public int mBgColor;
    public int mFontColor;
    public int mProgress;
    public int mShowStyle;
    public int mMagicStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        mContentLed = (TextInputEditText) findViewById(R.id.content_led);
        Button mFontcolorBtnLed = (Button) findViewById(R.id.fontcolor_btn_led);
        Button mBgcolorBtnLed = (Button) findViewById(R.id.bgcolor_btn_led);
        mPreviewLed = (TextView) findViewById(R.id.preview_led);

        ImageView mReverseColorLed = (ImageView) findViewById(R.id.reverseColor_led);

        mShowstyleRadiogroupLed = (RadioGroup) findViewById(R.id.showstyle_radiogroup_led);

        AppCompatRadioButton mSingleRadiobtnLed = (AppCompatRadioButton) findViewById(R.id.single_radiobtn_led);
        AppCompatRadioButton mSingleTossBtnLed = (AppCompatRadioButton) findViewById(R.id.single_toss_radiobtn_led);

        mRollspeedSeekbarLed = (AppCompatSeekBar) findViewById(R.id.rollspeed_seekbar_led);
        mAdaptiveRadiobtnLed = (AppCompatRadioButton) findViewById(R.id.adaptive_radiobtn_led);
        mLinesTextView = (TextView) findViewById(R.id.tv_lines_led);
        mlinesSeekbar = (AppCompatSeekBar) findViewById(R.id.lines_seekbar_led);
        AppCompatRadioButton mMagicRadiobtnLed = (AppCompatRadioButton) findViewById(R.id.magic_radiobtn_led);
        mCompatSpinner = (AppCompatSpinner) findViewById(R.id.spinner_magicstyle_led);
        Button mStartBtnLed = (AppCompatButton) findViewById(R.id.start_btn_led);

        if (mFontcolorBtnLed != null) {
            mFontcolorBtnLed.setOnClickListener(this);
        }
        if (mStartBtnLed != null) {
            mStartBtnLed.setOnClickListener(this);
        }
        if (mBgcolorBtnLed != null) {
            mBgcolorBtnLed.setOnClickListener(this);
        }

        initViewEvent();
    }


    private void initViewEvent() {


        mShowstyleRadiogroupLed.check(R.id.single_radiobtn_led);
        mShowStyle = R.id.single_radiobtn_led;
        if (!mAdaptiveRadiobtnLed.isChecked()) {
            mlinesSeekbar.setEnabled(false);
        }

        mCompatSpinner.setSelection(0);
        mMagicStyle = 0;
        mRollspeedSeekbarLed.setOnSeekBarChangeListener(this);
        mShowstyleRadiogroupLed.setOnCheckedChangeListener(this);
        mCompatSpinner.setOnItemSelectedListener(this);
        mlinesSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLinesTextView.setText(String.valueOf(progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // @Override
    // public void initPresenter() {

    // }


    //@OnClick({R.id.fontcolor_btn_led, R.id.bgcolor_btn_led, R.id.start_btn_led, R.id.recommendcolor_btn_led, R.id.reverseColor_led})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fontcolor_btn_led:
                showColorPicker(true);
            case R.id.bgcolor_btn_led:
                showColorPicker(false);
                break;
            case R.id.reverseColor_led:
                if (mBgColor != 0 && mFontColor != 0) {
                    int bgColor = mBgColor;
                    changePreviewBgColor(mFontColor);
                    changePreviewFontColor(bgColor);
                }
                break;
            case R.id.start_btn_led:
                startShowLed();
                break;
        }
    }

    private void startShowLed() {
        Editable mContentLedText = mContentLed.getText();
        if (TextUtils.isEmpty(mContentLedText)) {
            // mContentLed.setError("请填写要显示的内容");
            return;
        }
        if (mFontColor == 0) {
            mFontColor = mPreviewLed.getCurrentTextColor();
        }
        if (mBgColor == 0) {
            mBgColor = 0xffffffff;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Const.LED_CONTENT, mContentLedText.toString());
        bundle.putInt(Const.LED_BG_COLOR, mBgColor);
        bundle.putInt(Const.LED_FONT_COLOR, mFontColor);

        switch (mShowStyle) {
            case R.id.single_radiobtn_led:
                bundle.putInt(Const.LED_ROLL_SPEED, mProgress);
                bundle.putBoolean(Const.LED_SINGLE_ISH, true);
                startSingleLED(bundle);
                break;
            case R.id.single_toss_radiobtn_led:
                bundle.putInt(Const.LED_ROLL_SPEED, mProgress);
                bundle.putBoolean(Const.LED_SINGLE_ISH, false);
                startSingleLED(bundle);
                break;
            case R.id.adaptive_radiobtn_led:
                bundle.putString(Const.LED_LINES, mLinesTextView.getText().toString());
                //startAdaptiveLED(bundle);
                break;
            case R.id.magic_radiobtn_led:
                bundle.putString(Const.LED_MAGIC_STYLE,
                        getResources().getStringArray(R.array.arrays_led_magicstyle)[mMagicStyle]
                );
                startMagicLED(bundle);
                break;
            default:
                break;
        }
    }

    private void startMagicLED(Bundle bundle) {
        if (!mContentLed.getText().toString().contains("n")) {
            // mContentLed.setError("至少输入两句话，用'#'分隔");
            return;
        }
        //startActivity(new Intent(this, LEDMagicActivity.class).putExtras(bundle));

    }

    //private void startAdaptiveLED(Bundle bundle) {
    //s//tartActivity(new Intent(this, LEDAutoActivity.class).putExtras(bundle));
    //  }

    private void startSingleLED(Bundle bundle) {
        startActivity(new Intent(this, LED2Activity.class).putExtras(bundle));

    }

    private void showColorPicker(final boolean isFontColor) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("选择颜色")
                .initialColor(0xffff0000)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton(android.R.string.yes, new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        if (isFontColor) {
                            changePreviewFontColor(selectedColor);
                        } else {
                            changePreviewBgColor(selectedColor);
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void changePreviewBgColor(int selectedColor) {
        mPreviewLed.setBackgroundColor(selectedColor);
        mBgColor = selectedColor;
    }

    private void changePreviewFontColor(int selectedColor) {
        mPreviewLed.setTextColor(selectedColor);
        mFontColor = selectedColor;
    }

    /**
     * 对滚动速度进度条 的监听
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mProgress = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * 对 显示样式 的单选按钮的事件监听
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        mShowStyle = checkedId;
        if (checkedId == R.id.single_radiobtn_led || checkedId == R.id.single_toss_radiobtn_led) {
            if (!mRollspeedSeekbarLed.isEnabled())
                mRollspeedSeekbarLed.setEnabled(true);
        } else {
            if (mRollspeedSeekbarLed.isEnabled())
                mRollspeedSeekbarLed.setEnabled(false);
        }

        if (checkedId != R.id.magic_radiobtn_led) {
            if (mCompatSpinner.isEnabled()) {
                mCompatSpinner.setEnabled(false);
            }
        } else {
            if (!mCompatSpinner.isEnabled()) {
                mCompatSpinner.setEnabled(true);
            }
        }

        if (checkedId == R.id.adaptive_radiobtn_led) {
            if (!mlinesSeekbar.isEnabled()) {
                mlinesSeekbar.setEnabled(true);
            }
        } else {
            if (mlinesSeekbar.isEnabled()) {
                mlinesSeekbar.setEnabled(false);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMagicStyle = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        mMagicStyle = 0;
    }
}
