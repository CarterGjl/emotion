package com.zejian.emotionkeyboard.widget.record.messagetype;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

import com.zejian.emotionkeyboard.widget.record.OnRecordChangeListener;
import com.zejian.emotionkeyboard.widget.record.RecordControlListener;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;

import static com.zejian.emotionkeyboard.widget.record.RecordManager.getdB;

public class RecordAudioManager implements RecordControlListener {




    //指定音频源 这个和MediaRecorder是相同的 MediaRecorder.AudioSource.MIC指的是麦克风
    private static final int mAudioSource = MediaRecorder.AudioSource.MIC;
    //指定采样率 （MediaRecoder 的采样率通常是8000Hz AAC的通常是44100Hz。 设置采样率为44100，目前为常用的采样率，官方文档表示这个值可以兼容所有的设置）
    private static final int mSampleRateInHz = 44100;
    //指定捕获音频的声道数目。在AudioFormat类中指定用于此的常量，单声道
    private static final int mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
    //指定音频量化位数 ,在AudioFormaat类中指定了以下各种可能的常量。通常我们选择ENCODING_PCM_16BIT和ENCODING_PCM_8BIT PCM代表的是脉冲编码调制，它实际上是原始音频样本。
//因此可以设置每个样本的分辨率为16位或者8位，16位将占用更多的空间和处理能力,表示的音频也更加接近真实。
    private static final int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
    //指定缓冲区大小。调用AudioRecord类的getMinBufferSize方法可以获得。
    private int mBufferSizeInBytes;

    // 声明 AudioRecord 对象
    private AudioRecord mAudioRecord = null;




    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            @HandlerMessageType
            int what = msg.what;
            switch (what) {
                case HandlerMessageType.UPDATE_VOICE_CHANGE:
                    int volume = msg.arg1;
                    int time = msg.arg2;
                    if (mListener != null) {

                        mListener.onVolumChanged(volume);
                        if (time % 10 == 0) {

                            mListener.onRecordTimeChanged((time / 10), getRecordPath());
                        }
                    }
                    return true;
            }
            return false;
        }
    });
    @SuppressLint("StaticFieldLeak")
    private static RecordAudioManager mRecordManager = null;
    private MediaRecorder mMediaRecorder;
    private int SAMPLE_RATE_IN_HZ = 16000; // 采样率
    private String recordPath;
    private File mFile;
    private ExecutorService mThreadPool;

    private String resourceId;
    private Vibrator mVibrator;
    private int mBufferSize;
    private DataOutputStream mDataOutputStream;

    public String getRecordPath() {


        return recordPath == null ? "" : recordPath;
    }

    private AtomicBoolean mIsRecording = new AtomicBoolean(false);
    private long mStartTime;
    private OnRecordChangeListener mListener;
    private Context mContext;

    private RecordAudioManager() {

    }

    public static RecordAudioManager getInstance(Context context) {
        synchronized (RecordAudioManager.class) {
            if (mRecordManager == null) {
                mRecordManager = new RecordAudioManager();
            }
            mRecordManager.init(context);
        }
        return mRecordManager;
    }

    private void init(Context context) {

        mContext = context;
        mThreadPool = Executors.newCachedThreadPool();
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

    }


    @Override
    public void startRecording() {

        //初始化数据 计算最小缓冲区

        mBufferSizeInBytes = AudioRecord.getMinBufferSize(mSampleRateInHz, mChannelConfig, mAudioFormat);

        mAudioRecord = new AudioRecord(mAudioSource, mSampleRateInHz, mChannelConfig, mAudioFormat,
                mBufferSizeInBytes);


//        if (mMediaRecorder == null) {
//            mMediaRecorder = new MediaRecorder();
//            //设置输出格式
//            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 设置输出编码格式
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 设置音频编码器可用于录制
//            mMediaRecorder.setAudioChannels(1);// 设置录制的音频通道数-单通道
//            mMediaRecorder.setAudioSamplingRate(44100); //64000
//            mMediaRecorder.setAudioEncodingBitRate(96000);// 设置音频编码录音比特率
//            mMediaRecorder.setOnErrorListener(new RecorderErrorListener());// 设置录音错误监听
//        } else {
//
//            try {
//                mMediaRecorder.stop();
//                mMediaRecorder.reset();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        //
//        String path = mMediaRecorder.toString();
//        this.recordPath = getRecordFilePath(path);
//        this.mFile = new File(recordPath);
//        mMediaRecorder.setOutputFile(mFile.getAbsolutePath());
//
//        try {
//            mMediaRecorder.prepare();
//            mMediaRecorder.start();
//
//            //设置正在录音
//            mIsRecording.set(true);
//            mStartTime = new Date().getTime();
//            mThreadPool.execute(new RecordingChangeUpDater());
//
//            if (mVibrator != null) {
//                if (mVibrator.hasVibrator()) {
//                    mVibrator.vibrate(100);
//                }
//            }
//
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            mIsRecording.set(false);
//            mMediaRecorder.release();
//            mMediaRecorder = null;
//        }

    }


    @Override
    public String getRecordFileName() {
        return null;
    }

    @Override
    public void cancelRecording() {

        try {
            if (mMediaRecorder == null) {
                return;
            }
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;

            if (mFile != null && mFile.exists() && !mFile.isDirectory()) {

                boolean delete = mFile.delete();
            }
            mIsRecording.set(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int stopRecording() {

        if (mMediaRecorder != null) {

            try {
                mIsRecording.set(false);
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;

                if ((new Date().getTime() - mStartTime < 200)) {
                    mListener.onRecordTimeShort();
                }
                //获取当前的录音长度
                return (int) ((new Date().getTime() - mStartTime) / 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public boolean isRecording() {
        return mIsRecording.get();
    }

    @Override
    public MediaRecorder getMediaRecorder() {
        return mMediaRecorder;
    }

    /**
     * @param record 录音文件名字
     * @return 录音文件完整路径
     */
    @Override
    public String getRecordFilePath(@NonNull String record) {

//        this.resourceId = record;
//        Logger.d(DataUtil.getAudioPath(Utils.getSDAbsPath())+File.pathSeparator+ UUID.randomUUID().toString()+AcuConstant.SUFFIX_AUDIO);
//        return DataUtil.getAudioPath(Utils.getSDAbsPath())+File.pathSeparator+ UUID.randomUUID().toString()+AcuConstant.SUFFIX_AUDIO;
        return "";
    }

    @NonNull
    public String getResourceId() {


        return resourceId == null ? "" : resourceId;
    }

    private boolean isStartToRecorder;
    @Override
    public void setOnRecordChangeListener(@NonNull OnRecordChangeListener listener) {
        mListener = listener;
    }

    private final class RecorderThread implements Runnable{
        @Override
        public void run() {


            mIsRecording.set(true);

            createFile();

            try {
                //判断audiorecorder 是否初始化  停止录音时候释放了 状态为
                if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
                    initData();
                }
                //最小缓冲区
                byte[] buffer = new byte[mBufferSizeInBytes];
                mDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(getRecordFilePath(UUID.randomUUID().toString())))));

                //开始录音

                mAudioRecord.startRecording();

                while (isRecording()&&mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING){
                    int bufferReadResult = mAudioRecord.read(buffer, 0, mBufferSizeInBytes);
                    int v = 0;
                    for (int i = 0; i < bufferReadResult; i++) {
                        mDataOutputStream.write(buffer[i]);
                        if (!isStartToRecorder){
                            v = buffer[i] * buffer[i];

                        }
                    }
                    double mean = v / (double) bufferReadResult;
                    double volume = 10 * Math.log10(mean);

                }

            }catch (Exception e){
                stopRecording();
            }
        }
    }

    private void initData() {

    }

    private void createFile() {

    }

    private final class RecordingChangeUpDater implements Runnable {
        @Override
        public void run() {


            int currentRecordCounter = 0;
            while (mIsRecording.get()) {

                int volume = 0;
                try {
                    volume = mMediaRecorder.getMaxAmplitude();
                } catch (Exception e) {
//                    Logger.e(e, "");
                }
                if (volume!=0){
                    double value = getdB(volume);
                    Message msg = Message.obtain();
                    msg.arg1 = (int) value;
                    msg.arg2 = currentRecordCounter;
                    msg.what = HandlerMessageType.UPDATE_VOICE_CHANGE;
                    mHandler.sendMessage(msg);
                }
//                32768 调节灵敏度
//                int value = 10 * volume / 2222;
//                if (value > 9)
//                    value = 9;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }
                currentRecordCounter++;
            }

        }
    }

    public class RecorderErrorListener implements MediaRecorder.OnErrorListener {

        @Override
        public void onError(MediaRecorder mp, int what, int extra) {
            String whatDescription;
            switch (what) {
                case MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN:
                    whatDescription = "MEDIA_RECORDER_ERROR_UNKNOWN";
                    break;
                default:
                    whatDescription = Integer.toString(what);
                    break;
            }
//            Logger.i("voice", String.format("MediaRecorder error occured: %s,%d", whatDescription,
//                    extra));
        }
    }

}
