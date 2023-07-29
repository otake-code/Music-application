package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.app.Activity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton button_play;
    private SeekBar speedSeekBar;
    private SeekBar pitchSeekBar;
    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private ToggleButton toggleButton3;
    private ToggleButton toggleButton4;
    private Button timerButton;
    private TextView timerTextView;
    private CountDownTimer timer; // timerを宣言
    private static final int REQUEST_CODE_TIMER_SETTING = 1;
    private long timerDuration = 0; // デフォルトのタイマー時間（1分）
    private long timeLeftInMillis = 0;
    private View graphView; // 棒グラフを表示するView
    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton1 = findViewById(R.id.togglebutton1);
        toggleButton2 = findViewById(R.id.togglebutton2);
        toggleButton3 = findViewById(R.id.togglebutton3);
        toggleButton4 = findViewById(R.id.togglebutton4);
        button_play = findViewById(R.id.play_button);
        speedSeekBar = findViewById(R.id.speedSeekBar);
        pitchSeekBar = findViewById(R.id.pitchSeekBar);
        // リセットボタンを取得
        Button resetButton = findViewById(R.id.reset_button);

        timerButton =findViewById(R.id.btn_open_timer_setting);

        //トグルボタンのテキスト
        toggleButton1.setTextOn("1");
        toggleButton1.setTextOff("1");
        toggleButton2.setTextOn("2");
        toggleButton2.setTextOff("2");
        toggleButton3.setTextOn("3");
        toggleButton3.setTextOff("3");
        toggleButton4.setTextOn("4");
        toggleButton4.setTextOff("4");

        //トグルボタンの設定
        toggleButton1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
                toggleButton2.setChecked(false);
                toggleButton3.setChecked(false);
                toggleButton4.setChecked(false);
            } else {}
        });
        toggleButton2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music2);
                toggleButton1.setChecked(false);
                toggleButton3.setChecked(false);
                toggleButton4.setChecked(false);
            } else {}
        });
        toggleButton3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music3);
                toggleButton1.setChecked(false);
                toggleButton2.setChecked(false);
                toggleButton4.setChecked(false);
            } else {}
        });
        toggleButton4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music4);
                toggleButton1.setChecked(false);
                toggleButton2.setChecked(false);
                toggleButton3.setChecked(false);
            } else {}
        });

        // リセットボタンのクリックリスナーを設定
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // タイマーをキャンセル
                if (timer != null) {
                    timer.cancel();
                }

                // SeekBarの初期値を設定
                speedSeekBar.setProgress(100);
                pitchSeekBar.setProgress(100);

                // タイマー表示を更新
                updateTimerText(timerDuration);
            }
        });

        //　再生ボタンの設定
        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        //button_play.setText("PLAY");
                        // ボタンの背景画像を変更
                        button_play.setBackgroundResource(R.drawable.start); // stop.jpegの画像を設定
                        if(timer != null){
                            timer.cancel();
                        }
                        //hideGraph(); // 再生を一時停止したら棒グラフも非表示にする
                    } else {
                        float speed = speedSeekBar.getProgress() / 100f;
                        float pitch = pitchSeekBar.getProgress() / 100f;

                        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setPitch(pitch));

                        if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {
                            mediaPlayer.seekTo(0);
                        }
                        mediaPlayer.start();
                        // ボタンの背景画像を変更
                        button_play.setBackgroundResource(R.drawable.stop); // play.jpegの画像を設定
                        // タイマーを設定して開始
                        if (timer != null) {
                            timer.cancel();
                        }
                        //timerDuration = 60000; // タイマーの時間をミリ秒単位で設定（ここでは1分）
                        if (timerDuration > 0) {
                            startTimer(timerDuration);
                        }
                    }
                }
            }
        });

        // TextViewを取得
        timerTextView = findViewById(R.id.timerTextView);

        // タイマーの時間を表示するメソッドを呼び出す
        updateTimerText(timeLeftInMillis);

        // タイマー設定画面を開くボタンの設定
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerSettingActivity();
            }
        });
    }
    // タイマーの時間を表示する
    private void updateTimerText(long milliseconds) {
        int hours = (int) (milliseconds/ 3600000);
        int minutes = (int) ((milliseconds % 3600000) / 60000);
        int seconds = (int) ((milliseconds % 60000) / 1000);
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeString);
    }

    //タイマーの動作設定
    private void startTimer(long durationInMillis) {
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }

        timerRunnable = new Runnable() {
            long millisUntilFinished = durationInMillis;

            @Override
            public void run() {
                // 残り時間を表示
                updateTimerText(millisUntilFinished);

                // タイマーが終了したときの処理
                if (millisUntilFinished <= 0) {
                    updateTimerText(0);
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        button_play.setBackgroundResource(R.drawable.start);
                    }
                    return;
                }

                // 1秒後に再度Runnableを実行
                millisUntilFinished -= 1000;
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(timerRunnable);
    }

    //タイマー設定画面を開く
    private void openTimerSettingActivity() {
        Intent intent = new Intent(this, TimeSettingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TIMER_SETTING);
    }

    // タイマー設定画面から戻ってきたとき
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TIMER_SETTING && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                // 設定されたタイマー時間を取得
                long selectedDuration = data.getLongExtra("timer_duration", 0);
                if (selectedDuration > 0) {
                    timerDuration = selectedDuration;
                }
            }
        }
    }

    //再生停止
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
