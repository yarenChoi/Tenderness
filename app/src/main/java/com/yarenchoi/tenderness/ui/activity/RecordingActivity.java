package com.yarenchoi.tenderness.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.presenter.IRecordingPresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.IRecordingPresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IRecordingActivity;
import com.yarenchoi.tenderness.ui.fragment.BaseFragment;
import com.yarenchoi.tenderness.utils.AudioRecorderUtils;
import com.yarenchoi.tenderness.widget.VoiceLineView;

public class RecordingActivity extends BaseActivity implements IRecordingActivity {

    private static final int MSG_AUDIO_PREPARED = 0x110;
    private static final int MSG_VOICE_CHANGED = 0x111;
    private static final int MSG_VOICE_FINISH = 0x112;
    private static final int SHOW_PROGRESS_DIALOG = 0x113;
    private static final int HIDE_PROGRESS_DIALOG = 0x114;

    VoiceLineView voiceLine;
    TextView timeView;
    ImageView recorder;
    AlertDialog selectDialog;
    AlertDialog progressDialog;
    EditText voiceTitleEt;

    AudioRecorderUtils mAudioRecorder;

    private boolean isRecording = false;
    private float mTime;

    private IRecordingPresenter presenter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recording);
        voiceLine = (VoiceLineView) this.findViewById(R.id.voice_line);
        timeView = (TextView) this.findViewById(R.id.tv_recorder_time);
        recorder = (ImageView) this.findViewById(R.id.iv_recorder);
    }

    @Override
    protected void setUpView() {

        presenter = new IRecordingPresenterImpl(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dlg_record_voice, null);
        voiceTitleEt = (EditText) dialogView.findViewById(R.id.et_voice_title);
        builder.setView(dialogView);
        builder.setCancelable(false);
        selectDialog = builder.create();

        progressDialog = new AlertDialog.Builder(this)
                .setView(LayoutInflater.from(this).inflate(R.layout.dlg_progress, null))
                .setCancelable(false)
                .create();

        mAudioRecorder = AudioRecorderUtils.getInstance();
        mAudioRecorder.setOnAudioStateListener(new AudioRecorderUtils.AudioStateListener() {

            public void wellPrepared() {
                handler.sendEmptyMessage(MSG_AUDIO_PREPARED);
            }
        });

        recorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecording) {
                    mAudioRecorder.prepareAudio();
                } else {
                    handler.sendEmptyMessage(MSG_VOICE_FINISH);
                }
            }
        });
    }

    @Override
    protected void handler(Message msg) {
        switch (msg.what) {
            case MSG_AUDIO_PREPARED:
                isRecording = true;
                recorder.setImageResource(R.drawable.video_recorder_stop_btn);
                voiceLine.start();
                // 开启一个线程获取音量
                new Thread(mGetVoiceLevelRunnable).start();
                break;
            case MSG_VOICE_CHANGED:
                timeView.setText(String.valueOf(Math.round(mTime) + "\""));
                updateVoiceLevel(mAudioRecorder.getVoiceLevel(200));
                break;
            case MSG_VOICE_FINISH:
                isRecording = false;
                mAudioRecorder.release();
                voiceLine.pause();
                selectDialog.show();
                break;
            case SHOW_PROGRESS_DIALOG:
                progressDialog.show();
                break;
            case HIDE_PROGRESS_DIALOG:
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                finishAndRefresh();
                break;
            default:
                break;
        }

//        super.handleMessage(msg);
    }

    private void updateVoiceLevel(int voiceLevel) {
        voiceLine.setVolume(voiceLevel);
    }

    /**
     * 获取音量大小的线程
     */
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
//                    if (mTime > 29.9f){
//                        dimissDialog();
//                        mAudioManager.release();
//                        if (audioFinishRecorderListener != null) {
//                            audioFinishRecorderListener.onFinish(mTime,mAudioManager.getCurrentFilePath());
//                        }
//                        reset();
//                    }
                    handler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void showProgress() {
        handler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
    }

    @Override
    public void hideProgress() {
        handler.sendEmptyMessageDelayed(HIDE_PROGRESS_DIALOG, 1000);
    }

    @Override
    public void showResult(String result) {
        Log.d(TAG, result);
    }

    private void finishAndRefresh() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public String getVoiceTitle() {
        return voiceTitleEt.getText().toString();
    }

    @Override
    public float getVoiceLen() {
        return mTime;
    }

    @Override
    public String getVoiceUrl() {
        return mAudioRecorder.getCurrentFilePath();
    }

    public void cancelRecording(View view) {
        selectDialog.dismiss();
        mTime = 0;
        timeView.setText(R.string._00_00);
        recorder.setImageResource(R.drawable.video_recorder_start_btn);
        mAudioRecorder.cancel();
    }

    public void finishRecording(View view) {
        selectDialog.dismiss();
        presenter.saveRecording();
    }

    public static void startActivity(BaseFragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), RecordingActivity.class), requestCode);
        fragment.getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAudioRecorder != null) {
            mAudioRecorder.release();
        }
        Log.d(TAG, "onDestroy");
    }
}
