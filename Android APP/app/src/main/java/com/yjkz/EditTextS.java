package com.yjkz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

/*
  https://blog.csdn.net/wangkai1101/article/details/79066046
 */

public class EditTextS extends androidx.appcompat.widget.AppCompatEditText {
    public EditTextS(Context context) {
        super(context);
    }

    public EditTextS(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            super.onKeyDown(keyCode, event);
            onKeyBoardHideListener.onKeyHide(keyCode, event);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            super.onKeyPreIme(keyCode, event);
            onKeyBoardHideListener.onKeyHide(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    OnKeyBoardHideListener onKeyBoardHideListener;

    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
        this.onKeyBoardHideListener = onKeyBoardHideListener;
    }

    public interface OnKeyBoardHideListener {
        void onKeyHide(int keyCode, KeyEvent event);
    }
}
