package com.yarenchoi.tenderness.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class MediaPlayerUtils {
    private static MediaPlayer mMediaPlayer;
    private static boolean isPause;
    private static String filePath;
    private static OnMediaListener listener;

    /**
     * 播放音乐
     *
     * @param newFilePath 播放的音频地址
     * @param onMediaListener 播放监听
     */
    public static void playSound(String newFilePath, OnMediaListener onMediaListener) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            filePath = newFilePath;
            listener = onMediaListener;

            //设置一个error监听器
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    mMediaPlayer.reset();
                    return false;
                }
            });
        } else {
            if (filePath.equals(newFilePath)){
                pauseOrResume();
                return;
            } else {
                mMediaPlayer.reset();
                listener = onMediaListener;
            }
        }

        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    listener.onComplete();
                }
            });
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果正在播放则暂停
     * 如果处于暂停状态则唤醒
     */
    public static void pauseOrResume() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                pause();
            } else {
                resume();
            }
        }
    }
    /**
     * 暂停播放
     */
    public static void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) { //正在播放的时候
            mMediaPlayer.pause();
            listener.onPause();
            isPause = true;
        }
    }

    /**
     * 当前是isPause状态
     */
    public static void resume() {
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            listener.onResume();
            isPause = false;
        }
    }

    /**
     * 释放资源
     */
    public static void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public interface OnMediaListener {
        void onComplete();
        void onPause();
        void onResume();
    }
}
