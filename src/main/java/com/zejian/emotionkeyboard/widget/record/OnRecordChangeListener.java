package com.zejian.emotionkeyboard.widget.record;

public interface OnRecordChangeListener {

    void onVolumChanged(int voiceValue);

    void onRecordTimeChanged(int timeValue, String localPath);

    void onRecordTimeShort();
}
