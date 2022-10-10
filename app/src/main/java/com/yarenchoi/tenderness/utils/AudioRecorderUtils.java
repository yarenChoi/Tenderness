package com.yarenchoi.tenderness.utils;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by YarenChoi on 2016/8/11.
 * 录音工具类
 */
public class AudioRecorderUtils {
    private static MediaRecorder mMediaRecorder;
    private static final String mDir = Environment.getExternalStorageDirectory() + "/Tenderness/" + "sound/";
    private String mCurrentFilePath;

    private static AudioRecorderUtils mInstance;

    private boolean isPrepare;

    private AudioRecorderUtils() {
    }

    public static AudioRecorderUtils getInstance() {
        if (mInstance == null) {
            synchronized (AudioRecorderUtils.class) {
                if (mInstance == null) {
                    mInstance = new AudioRecorderUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 使用接口 用于回调
     */
    public interface AudioStateListener {
        void wellPrepared();
    }

    public AudioStateListener mAudioStateListener;

    /**
     * 回调方法
     */
    public void setOnAudioStateListener(AudioStateListener listener) {
        mAudioStateListener = listener;
    }

    // 去准备
    public void prepareAudio() {
        try {
            isPrepare = false;
            File dir = new File(mDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = generateFileName();
            File file = new File(dir, fileName);

            mCurrentFilePath = file.getAbsolutePath();

            mMediaRecorder = new MediaRecorder();
            // 设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            // 设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            // 设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);

            // 准备录音
            mMediaRecorder.prepare();
            // 开始
            mMediaRecorder.start();
            // 准备结束
            isPrepare = true;
            if (mAudioStateListener != null) {
                mAudioStateListener.wellPrepared();
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 按时间生成文件名
     */
    private String generateFileName() {
//        return UUID.randomUUID().toString() + ".mp3";
        return TimeFormatUtils.getFormatFileName() + ".mp3";
    }


    public int getVoiceLevel(int maxlevel) {
        if (isPrepare) {
            try {
                // mMediaRecorder.getMaxAmplitude() 1~32767
                return (maxlevel * mMediaRecorder.getMaxAmplitude()) / 32768 + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mMediaRecorder != null) {

            //mMediaRecorder.stop();
            mMediaRecorder.reset();
        }
        mMediaRecorder = null;
    }

    /**
     * 取消录音并删除文件
     * public void cancel() {
     * release();
     * if (mCurrentFilePath != null) {
     * File file = new File(mCurrentFilePath);
     * file.delete();
     * mCurrentFilePath = null;
     * }
     * <p/>
     * }
     */

    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }
}
