package com.wksc.counting.widegit;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.wksc.counting.popwindows.TitleDescribewindow;

/**
 * Created by puhua on 2016/6/28.
 *
 * @
 */
public class TableTitleLayout extends LinearLayout {

    public TableTitleLayout(Context con) {
        super(con);
    }

    public TableTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableTitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initView(String titles) {
        MarqueeText textView = new MarqueeText(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(5, 10, 5, 10);
        textView.setTextSize(12f);
        textView.setText(titles);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setHorizontallyScrolling(true);
        textView.setMarqueeRepeatLimit(1);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        this.addView(textView);
        this.invalidate();

    }

    public void initView(String[] titles, final String[] titleDesc) {
        for (int i = 0; i < titles.length; i++) {
            MarqueeText textView = new MarqueeText(this.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5, 10, 5, 10);
            textView.setTextSize(12f);
            textView.setText(titles[i]);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setHorizontallyScrolling(true);
            textView.setMarqueeRepeatLimit(1);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
            final int finalI = i;
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TitleDescribewindow titleSelectPopupWindow = new TitleDescribewindow((Activity) TableTitleLayout.this.getContext());
                    titleSelectPopupWindow.showPopupwindow(v, titleDesc[finalI]);
                    return false;
                }
            });
            this.addView(textView);
        }
        this.invalidate();

    }
}
