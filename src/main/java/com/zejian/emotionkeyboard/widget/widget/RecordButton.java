package com.zejian.emotionkeyboard.widget.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zejian.emotionkeyboard.R;
import com.zejian.emotionkeyboard.widget.record.OnRecordChangeListener;
import com.zejian.emotionkeyboard.widget.record.RecordManager;
import com.zejian.emotionkeyboard.widget.widget.Event.MotionEventActionType;
import com.zejian.emotionkeyboard.widget.widget.status.RecordStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;

public class RecordButton extends AppCompatButton {


    private final int MAX_RECORD_TIME = 60 * 1000;
    private Dialog mRecordDialog;
    private RecordManager mManager;
    private Dialog mVoiceTooShortDialog;
    private ImageView mIvRecVolume;
    private ImageView recrod_dialog_mic_content;
    private RelativeLayout mIvCancelLayout;
    private TextView mTvRecordDialogTxt;
    private OnSendMessageListener mSendMessageListener;

    public RecordButton(Context context) {
        this(context, null);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initA();
    }

    private void initA() {

        mRecordDialog = new Dialog(getContext(), R.style.DialogStyle);
        mRecordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRecordDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mRecordDialog.setContentView(R.layout.voice_rcd_hint_window);
        mVoiceTooShortDialog = new Dialog(getContext(), R.style.DialogStyle);
        mVoiceTooShortDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mVoiceTooShortDialog.setCancelable(false);
        mVoiceTooShortDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mVoiceTooShortDialog.setContentView(R.layout.too_short_dialog);

        mIvRecVolume = mRecordDialog.findViewById(R.id.voice_rcd_hint_anim);
        recrod_dialog_mic_content = mRecordDialog.findViewById(R.id.imageView1 );
        mIvCancelLayout = mRecordDialog.findViewById(R.id.record_dialog_cancel_layout);
        mTvRecordDialogTxt = mRecordDialog.findViewById(R.id.rcding_tv);
        mManager = RecordManager.getInstance(getContext());
        mManager.setOnRecordChangeListener(new OnRecordChangeListener() {
            @Override
            public void onVolumChanged(int voiceValue) {

                setValue(voiceValue);
            }

            @Override
            public void onRecordTimeChanged(int timeValue, String localPath) {

                if (timeValue > MAX_RECORD_TIME) {
                    mManager.stopRecording();
                    mSendMessageListener.onSendMessage(localPath, timeValue);
                }

            }

            @Override
            public void onRecordTimeShort() {

            }
        });
    }

    private boolean isCancel;
    private float mDownY;

    public void setOnSendMessageListener(@NonNull OnSendMessageListener sendMessageListener) {

        mSendMessageListener = sendMessageListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        @MotionEventActionType
        int action = event.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mManager.startRecording();
                mDownY = event.getY();
                showVoiceDialog(RecordStatus.Send);
                setText(R.string.message_btn_4);
                setBackground(getResources().getDrawable(R.drawable.btn_recorder_press));
                performClick();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                if (mDownY - moveY > 400) {
                    isCancel = true;
                    showVoiceDialog(RecordStatus.Cancel);
                } else if (mDownY - moveY < 120) {
                    isCancel = false;
                    showVoiceDialog(RecordStatus.Send);
                }
                break;
            case MotionEvent.ACTION_UP:
                setText(R.string.message_btn_3);
                setBackground(getResources().getDrawable(R.drawable.btn_recorder));
                mRecordDialog.dismiss();
                if (isCancel) {

                    mManager.cancelRecording();
                } else {

                    setEnabled(false);
                    postDelayed(() -> {
                        int longTime = mManager.stopRecording();
                        // TODO: 2018/8/2 根据时间来判断是否发送
                        if (longTime < 1){
                            mVoiceTooShortDialog.show();
                            setEnabled(false);
                            postDelayed(() -> {
                                mVoiceTooShortDialog.dismiss();
                                setEnabled(true);
                            },400);

                        }else {
                            if (mSendMessageListener != null) {
                                mSendMessageListener.onSendMessage(mManager.getRecordPath(), longTime);
                            }
                        }
                        setEnabled(true);
                    },300);


                }
                break;
            case MotionEvent.ACTION_CANCEL:
                setEnabled(false);
                postDelayed(() -> {
                    int longTime = mManager.stopRecording();
                    // TODO: 2018/8/2 根据时间来判断是否发送
                    if (longTime < 1){
                        mVoiceTooShortDialog.show();
                        setEnabled(false);
                        postDelayed(() -> {
                            mVoiceTooShortDialog.dismiss();
                            setEnabled(true);
                        },400);

                    }else {
                        if (mSendMessageListener != null) {
                            mSendMessageListener.onSendMessage(mManager.getRecordPath(), longTime);
                        }
                    }
                    setEnabled(true);
                },300);

                setBackground(getResources().getDrawable(R.drawable.btn_recorder));
                mRecordDialog.dismiss();
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    private void setValue(int sound_dB) {
        if (sound_dB < 3) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp1);
        } else if (sound_dB > 30 && sound_dB < 40) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp2);
        } else if (sound_dB > 40 && sound_dB < 50) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp3);
        } else if (sound_dB > 50 && sound_dB < 55) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp4);
        } else if (sound_dB > 55 && sound_dB < 60) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp5);
        } else if (sound_dB > 65 && sound_dB < 70) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp6);
        } else if (sound_dB > 75) {
            mIvRecVolume
                    .setBackgroundResource(R.drawable.amp7);
        }

//        switch (value) {
//            case 1:
//
//                mIvRecVolume.setImageResource(R.drawable.amp1);
//                break;
//            case 2:
//                mIvRecVolume.setImageResource(R.drawable.amp2);
//                break;
//            case 3:
//                mIvRecVolume.setImageResource(R.drawable.amp3);
//                break;
//            case 4:
//                mIvRecVolume.setImageResource(R.drawable.amp4);
//
//                break;
//            case 5:
//                mIvRecVolume.setImageResource(R.drawable.amp5);
//                break;
//            case 6:
//                mIvRecVolume.setImageResource(R.drawable.amp6);
//                break;
//            case 7:
//                mIvRecVolume.setImageResource(R.drawable.amp7);
//                break;
////            case 8:
////                mIvRecVolume.setImageResource(R.drawable.amp8);
////                break;
////            case 9:
////                mIvRecVolume.setImageResource(R.drawable.recordingsignal009);
////                break;
//            default:
//                mIvRecVolume.setImageResource(R.drawable.recordingsignal001);
//                break;
        }

    /**
     * 0显示发送  1 显示取消
     *
     * @param flag 显示dialog状态
     */
    private void showVoiceDialog(@RecordStatus int flag) {
        switch (flag) {
            case RecordStatus.Cancel:
                recrod_dialog_mic_content.setVisibility(View.GONE);
                mIvRecVolume.setVisibility(View.GONE);
                mIvCancelLayout.setVisibility(View.VISIBLE);
                mTvRecordDialogTxt.setText(R.string.message_title_18);
                break;
            case RecordStatus.Send:
                recrod_dialog_mic_content.setVisibility(View.VISIBLE);
                mIvRecVolume.setVisibility(View.VISIBLE);
                mIvCancelLayout.setVisibility(View.GONE);
                mTvRecordDialogTxt.setText(R.string.message_title_16);
                break;
        }
        mRecordDialog.show();
    }

    public interface OnSendMessageListener {

        void onSendMessage(String path, int time);
    }
}
