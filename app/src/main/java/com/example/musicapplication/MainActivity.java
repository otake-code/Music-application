package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button button_play;
    private SeekBar speedSeekBar;
    private SeekBar pitchSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_play = findViewById(R.id.play_button);
        speedSeekBar = findViewById(R.id.speedSeekBar);
        pitchSeekBar = findViewById(R.id.pitchSeekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    button_play.setText("PLAY");
                } else {
                    float speed = speedSeekBar.getProgress() / 100f;
                    float pitch = pitchSeekBar.getProgress() / 100f;

                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setPitch(pitch));

                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    button_play.setText("STOP");
                }
            }
        });
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
