package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class TimeSettingActivity extends AppCompatActivity {

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private Button btnOk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);

        hourPicker = findViewById(R.id.hour_picker);
        minutePicker = findViewById(R.id.minute_picker);
        secondPicker = findViewById(R.id.second_picker);
        btnOk = findViewById(R.id.btn_ok);
        // 最大、最小を設定
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);

        // 最大、最小を設定
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        // 最大、最小を設定
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);


        //時間を指定してタイマー時間をセットする
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OKボタンが押されたときの処理

                int selectedHours = hourPicker.getValue();
                int selectedMinutes = minutePicker.getValue();
                int selectedSeconds = secondPicker.getValue();
                long timerDuration = (selectedHours * 3600 + selectedMinutes * 60 + selectedSeconds) * 1000; // 時間をミリ秒に変換

                Intent intent = new Intent();
                intent.putExtra("timer_duration", timerDuration);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}