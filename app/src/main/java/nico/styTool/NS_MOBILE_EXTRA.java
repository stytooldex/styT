package nico.styTool;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

//QQ
public class NS_MOBILE_EXTRA extends DialogPreference {
    private SeekBar seekBar;
    private TextView textView;
    private String hintText = "拆开红包", prefKind;

    public NS_MOBILE_EXTRA(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.preference_seekbar);

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attr = attrs.getAttributeName(i);
            if (attr.equalsIgnoreCase("pref_kind")) {
                prefKind = attrs.getAttributeValue(i);
                break;
            }
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        SharedPreferences pref = getSharedPreferences();

        int delay = pref.getInt(prefKind, 0);
        this.seekBar = view.findViewById(R.id.delay_seekBar);
        this.seekBar.setProgress(delay);

        this.textView = view.findViewById(R.id.pref_seekbar_textview);
        if (delay == 0) {
            this.textView.setText("立即" + hintText);
        } else {
            this.textView.setText("延迟" + delay + "毫秒" + hintText);
        }

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    textView.setText("立即" + hintText);
                } else {
                    textView.setText("延迟" + i + "毫秒" + hintText);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            SharedPreferences.Editor editor = getEditor();
            editor.putInt(prefKind, this.seekBar.getProgress());
            editor.commit();
        }
        super.onDialogClosed(positiveResult);
    }
}
