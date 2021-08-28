package com.example.gostop;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout back;
    TextView positive;
    TextView negative;
    float oldPositive = 0;
    float oldNegative = 0;
    int randomNum1 = 0;
    int randomNum2 = 0;
    int result = 1;
    int max_num_value = 50;
    int min_num_value = 30;
    boolean isRun = false;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = findViewById(R.id.back);
        positive = findViewById(R.id.positive);
        negative = findViewById(R.id.negative);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(result == 1) {
                    back.setBackgroundColor(Color.parseColor("#1E90FF"));
                }else {
                    back.setBackgroundColor(Color.parseColor("#8B0000"));
                }
                RandomThread rt = new RandomThread();
                isRun = true;
                rt.start();
                break;
            case MotionEvent.ACTION_UP:
                result = random.nextInt(2);
                if(result == 1) {
                    back.setBackgroundColor(Color.parseColor("#00BFFF"));
                }else {
                    back.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                Log.d("result", "" + result);
                isRun = false;
                break;
            default:
                break;
        }
        return true;
    }

    class RandomThread extends Thread {
        @Override
        public void run() {
            while(isRun) {
                try {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            oldPositive = randomNum1 != 0 ? randomNum1 : 36;
            oldNegative = randomNum2 != 0 ? randomNum2 : 36;
            randomNum1 = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;
            randomNum2 = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;

            String name = "";
            Log.d("what???????? ", name + oldPositive + " " + " " + randomNum1);
            ValueAnimator animator1 = ObjectAnimator.ofFloat(positive, "textSize", oldPositive, randomNum1);
            ValueAnimator animator2 = ObjectAnimator.ofFloat(negative, "textSize", oldNegative, randomNum2);
            animator1.setDuration(500);
            animator1.start();
            animator2.setDuration(500);
            animator2.start();
        }
    };
}