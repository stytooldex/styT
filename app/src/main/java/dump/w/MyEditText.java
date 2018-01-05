package dump.w;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    private Paint line;

    public MyEditText(Context context, AttributeSet As) {
        super(context, As);
        setFocusable(true);
        line = new Paint();
        //线条颜色
        line.setColor(Color.BLUE);
        //线条宽
        line.setStrokeWidth(2);
        //第一个参数是光标距离
        setPadding(35, 0, 0, 0);
        setGravity(Gravity.TOP);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (getText().toString().length() != 0) {
            float y = 0;
            Paint p = new Paint();
            p.setColor(Color.GRAY);
            //行号文字大小
            p.setTextSize(20);
            for (int l = 0; l < getLineCount(); l++) {
                y = ((l + 1) * getLineHeight()) - (getLineHeight() / 4);
                canvas.drawText(String.valueOf(l + 1), 0, y, p);
                canvas.save();
            }
        }
        int k = getLineHeight();
        int i = getLineCount();
        /*第一个参数为竖线条上端距离,
		 第三个参数为竖线条下端距离*/
        canvas.drawLine(31, 0, 31, getHeight() + (i * k), line);
        int y = (getLayout().getLineForOffset(getSelectionStart()) + 1) * k;
        canvas.drawLine(0, y, getWidth(), y, line);
        canvas.save();
        canvas.restore();
        super.onDraw(canvas);
    }
}
