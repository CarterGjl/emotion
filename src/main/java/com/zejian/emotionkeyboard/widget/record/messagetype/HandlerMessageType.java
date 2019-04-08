package com.zejian.emotionkeyboard.widget.record.messagetype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@IntDef({HandlerMessageType.UPDATE_VOICE_CHANGE})
@Retention(RetentionPolicy.SOURCE)
public @interface HandlerMessageType {

    int UPDATE_VOICE_CHANGE = 10;
}
