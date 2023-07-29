package com.example.musicapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatToggleButton;

public class CustomToggleButton extends AppCompatToggleButton {
    private int textColorOn;
    private int textColorOff;

    public CustomToggleButton(Context context) {
        super(context);
        init();
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //形を決める
        setBackgroundResource(R.drawable.shape_sample); // セレクターを設定
        init();
    }

    private void init() {
        // スタイルを設定
        textColorOn = getResources().getColor(R.color.black);
        textColorOff = getResources().getColor(R.color.black);
        setTextOn("1");
        setTextOff("1");
        setTextSize(16); // 適切なテキストサイズを設定してください
        setTextStyle(isChecked()); // 初期状態によってテキストスタイルを設定

        setTextColor(isChecked() ? textColorOn : textColorOff); // 初期状態によってテキスト色を設定
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        setTextStyle(checked);
        setTextColor(checked ? textColorOn : textColorOff);
    }

    private void setTextStyle(boolean checked) {
        if (checked) {
            // 太字
            setTypeface(getTypeface(), Typeface.NORMAL);
            //背景を作る
            setBackgroundResource(R.drawable.gradation_black); // セレクターを設定
        } else {
            // 通常のテキストスタイル
            setTypeface(getTypeface(), Typeface.NORMAL);
            //背景を作る
            setBackgroundResource(R.drawable.gradation_white); // セレクターを設定
        }
    }
}
