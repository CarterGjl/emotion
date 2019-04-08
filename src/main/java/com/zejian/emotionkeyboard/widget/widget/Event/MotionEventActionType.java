package com.zejian.emotionkeyboard.widget.widget.Event;

import android.view.MotionEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@IntDef({MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE,MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL})
@Retention(RetentionPolicy.SOURCE)
public @interface MotionEventActionType {

}
