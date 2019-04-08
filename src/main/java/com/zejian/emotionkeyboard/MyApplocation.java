package com.zejian.emotionkeyboard;

import android.app.Application;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

public class MyApplocation extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        BundledEmojiCompatConfig config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
    }
}
