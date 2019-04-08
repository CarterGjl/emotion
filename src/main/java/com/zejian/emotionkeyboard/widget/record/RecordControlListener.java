package com.zejian.emotionkeyboard.widget.record;

import android.media.MediaRecorder;

import androidx.annotation.NonNull;


public interface RecordControlListener {

    void startRecording();

    void cancelRecording();

    int stopRecording();

    boolean isRecording();

    MediaRecorder getMediaRecorder();

    String getRecordFilePath(@NonNull String record);

    String getRecordFileName();
    void setOnRecordChangeListener(@NonNull OnRecordChangeListener listener);

}
