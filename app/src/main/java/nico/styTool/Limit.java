package nico.styTool;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;


public class Limit extends AppCompatEditText
{

    public Limit(Context context)
    {
        super(context);
    }

    public Limit(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Limit(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 输入法
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        return new mInputConnecttion(super.onCreateInputConnection(outAttrs),
				     false);
    }
}

class mInputConnecttion extends InputConnectionWrapper implements
InputConnection
{

    public mInputConnecttion(InputConnection target, boolean mutable)
    {
        super(target, mutable);
    }

    /**
     * 对输入的内容进行拦截
     *
     * @param text
     * @param newCursorPosition
     * @return
     */

    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        // 只能输入汉字或者英文
        return !(!text.toString().matches("[\u4e00-\u9fa5]+") && !text.toString().matches("[a-zA-Z /]+") && !text.toString().matches("\n*\r")) && super.commitText(text, newCursorPosition);
    }
    @Override
    public boolean sendKeyEvent(KeyEvent event)
    {
        return super.sendKeyEvent(event);
    }

    @Override
    public boolean setSelection(int start, int end)
    {
        return super.setSelection(start, end);
    }

}
