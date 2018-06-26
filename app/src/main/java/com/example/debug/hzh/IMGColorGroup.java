package com.example.debug.hzh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/*
 * @author xy
 * @emil 384813636@qq.com
 * create at 2018/6/26
 * description:
 */
public class IMGColorGroup extends RadioGroup {

    public IMGColorGroup(Context context) {
        super(context);
    }

    public IMGColorGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getCheckColor() {
        int checkedId = getCheckedRadioButtonId();
        IMGColorRadio radio = findViewById(checkedId);
        if (radio != null) {
            return radio.getColor();
        }
        return Color.WHITE;
    }

    public void setCheckColor(int color) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            IMGColorRadio radio = (IMGColorRadio) getChildAt(i);
            if (radio.getColor() == color) {
                radio.setChecked(true);
                break;
            }
        }
    }
}
