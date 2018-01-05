package dump.s;

import android.text.Editable;
import android.text.TextWatcher;

public class SmsWatcher implements TextWatcher {

  private TextChangingListener mChangingListener;

  public SmsWatcher(TextChangingListener changingListener) {
	mChangingListener = changingListener;
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
	mChangingListener.onChange(s);
  }

  public interface TextChangingListener {
	void onChange(Editable s);
  }
}

