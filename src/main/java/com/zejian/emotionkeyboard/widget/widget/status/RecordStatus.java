package com.zejian.emotionkeyboard.widget.widget.status;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@IntDef({RecordStatus.Send,RecordStatus.Cancel})
@Retention(RetentionPolicy.SOURCE)
public @interface RecordStatus {

    int Send = 0;
    int Cancel = 1;
}
