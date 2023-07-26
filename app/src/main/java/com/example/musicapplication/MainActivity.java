package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private long timerDuration = 60000; // デフォルトのタイマー時間（1分）
    private long timeLeftInMillis = 0;
    private View graphView; // 棒グラフを表示するView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);*/
        toggleButton1 = findViewById(R.id.togglebutton1);
        toggleButton2 = findViewById(R.id.togglebutton2);
        toggleButton3 = findViewById(R.id.togglebutton3);
        toggleButton4 = findViewById(R.id.togglebutton4);
        button_play = findViewById(R.id.play_button);
        speedSeekBar = findViewById(R.id.speedSeekBar);
        pitchSeekBar = findViewById(R.id.pitchSeekBar);

        timerButton =findViewById(R.id.btn_open_timer_setting);
        //graphView = findViewById(R.id.graph_view);

        toggleButton1.setTextOn("1");
        toggleButton1.setTextOff("1");
        toggleButton2.setTextOn("2");
        toggleButton2.setTextOff("2");
        toggleButton3.setTextOn("3");
        toggleButton3.setTextOff("3");
        toggleButton4.setTextOn("4");
        toggleButton4.setTextOff("4");



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
                        //button_play.setText("STOP");
                        // ボタンの背景画像を変更
                        button_play.setBackgroundResource(R.drawable.stop); // play.jpegの画像を設定
                        // タイマーを設定して開始
                        if (timer != null) {
                            timer.cancel();
                        }
                        //timerDuration = 60000; // タイマーの時間をミリ秒単位で設定（ここでは1分）
                        startTimer(timerDuration);

                        //showGraph(); // 再生開始時に棒グラフを表示する
                    }
                }
            }
        });

        // TextViewを取得
        timerTextView = findViewById(R.id.timerTextView);

        // タイマーの時間を表示するメソッドを呼び出す
        updateTimerText(timeLeftInMillis);

        // タイマー設定画面を開くボタンのクリックリスナーを設定
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimerSettingActivity();
            }
        });
    }
    // タイマーの時間を表示するメソッド
    private void updateTimerText(long milliseconds) {
        int hours = (int) (milliseconds / 3600000);
        int minutes = (int) ((milliseconds % 3600000) / 60000);
        int seconds = (int) ((milliseconds % 60000) / 1000);
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeString);
    }

    /*private void startTimer(long duration) {
        timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // タイマーの残り時間が更新されたときの処理
            }

            @Override
            public void onFinish() {
                // タイマーが終了したときの処理
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    button_play.setBackgroundResource(R.drawable.start);
                }
            }
        }.start();
    }*/
    // タイマーを開始するメソッド
    private void startTimer(long durationInMillis) {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 残り時間を表示
                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // タイマーが終了したときの処理
                updateTimerText(0);
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    button_play.setBackgroundResource(R.drawable.start);
                }
            }
        }.start();
    }


    private void openTimerSettingActivity() {
        Intent intent = new Intent(this, TimeSettingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TIMER_SETTING);
    }

    // タイマー設定画面から戻ってきたときに呼ばれるメソッド
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

 /*btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ボタン1がクリックされたときの処理
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ボタン1がクリックされたときの処理
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ボタン1がクリックされたときの処理
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ボタン1がクリックされたときの処理
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music4);
            }
        });*/
