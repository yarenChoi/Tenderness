package com.yarenchoi.tenderness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.db.entity.Voice;
import com.yarenchoi.tenderness.presenter.IPlayVoicePresenterImpl;
import com.yarenchoi.tenderness.presenter.ipresenter.IPlayVoicePresenter;
import com.yarenchoi.tenderness.ui.activity.iactivity.IPlayVoiceActivity;
import com.yarenchoi.tenderness.utils.MediaPlayerUtils;

public class PlayVoiceActivity extends BaseActivity implements IPlayVoiceActivity {

    private static final String VOICE_ID = "voiceId";

    TextView voiceTitleView;
    TextView voiceDateView;
    TextView voiceLenView;
    ImageView playBtn;

    private IPlayVoicePresenter presenter;
    private Long voiceId;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_voice);
        voiceTitleView = (TextView) this.findViewById(R.id.tv_voice_title);
        voiceDateView = (TextView) this.findViewById(R.id.tv_voice_date);
        voiceLenView = (TextView) this.findViewById(R.id.tv_voice_len);
        playBtn = (ImageView) this.findViewById(R.id.iv_play_voice);
    }

    @Override
    protected void setUpView() {
        this.voiceId = getIntent().getLongExtra(VOICE_ID, -1);

        presenter = new IPlayVoicePresenterImpl(PlayVoiceActivity.this);
        presenter.loadVoice(voiceId);
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public static void startActivity(Context context, Long voiceId) {
        Intent intent = new Intent(context, PlayVoiceActivity.class);
        intent.putExtra(VOICE_ID, voiceId);
        context.startActivity(intent);
    }

    @Override
    public void initVoice(final Voice voice) {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayerUtils.playSound(voice.getVoiceUrl(), new MediaPlayerUtils.OnMediaListener() {
                    @Override
                    public void onComplete() {
                        MediaPlayerUtils.release();
                    }

                    @Override
                    public void onPause() {
                    }

                    @Override
                    public void onResume() {
                    }
                });
            }
        });
    }

    @Override
    public void showVoiceTitle(String title) {
        voiceTitleView.setText(title);
    }

    @Override
    public void showVoiceDate(String date) {
        voiceDateView.setText(date);
    }

    @Override
    public void showVoiceLen(String len) {
        voiceLenView.setText(len);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerUtils.release();
    }
}
