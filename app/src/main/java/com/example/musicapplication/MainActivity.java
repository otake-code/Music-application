package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button button_play;
    private SeekBar speedSeekBar;
    private SeekBar pitchSeekBar;
    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private ToggleButton toggleButton3;
    private ToggleButton toggleButton4;
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

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        button_play.setText("PLAY");
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
                        button_play.setText("STOP");
                        //showGraph(); // 再生開始時に棒グラフを表示する
                    }
                }
            }
        });

    }

    //======================グラフの描画===============
    /*private void showGraph() {
        if (!isGraphShowing) {
            isGraphShowing = true;
            graphView.setVisibility(View.VISIBLE);
            startGraphAnimation();
        }
    }

    private void hideGraph() {
        if (isGraphShowing) {
            isGraphShowing = false;
            graphView.setVisibility(View.INVISIBLE);
            stopGraphAnimation();
        }
    }

    private void startGraphAnimation() {
        final int maxGraphHeight = graphView.getHeight(); // グラフの最大の高さをViewの高さとする
        final Random random = new Random();
        final Runnable graphAnimationRunnable = new Runnable() {
            @Override
            public void run() {
                if (isGraphShowing) {
                    int graphHeight = random.nextInt(maxGraphHeight);
                    updateGraphView(graphHeight);
                    graphView.postDelayed(this, 100); // 100msごとに新しいランダムな高さの棒グラフを描画
                }
            }
        };
        graphAnimationRunnable.run();
    }

    private void stopGraphAnimation() {
        graphView.removeCallbacks(null);
    }

    private void updateGraphView(int graphHeight) {
        // ここで実際の棒グラフの描画処理を行う
        // graphHeightを利用して、表示を更新する
        // この例では単純に高さを設定していますが、実際の描画は要件に合わせてカスタマイズしてください
        graphView.getLayoutParams().height = graphHeight;
        graphView.requestLayout();
    }*/
//===== 終わり============================---


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
