package com.jnu.student.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jnu.student.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
public class ClockView extends View {
    private Bitmap dialBitmap;
    private Bitmap hourHandBitmap;
    private Bitmap minuteHandBitmap;
    private Bitmap secondHandBitmap;
    private float hourRotation;
    private float minuteRotation;
    private float secondRotation;
    public ClockView(Context context) {
        super(context);
        init();
        startClockUpdate();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        startClockUpdate();
    }

    private void init() {
        dialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
        hourHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.custom_hour_hand);
        minuteHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.custom_minute_hand);
        secondHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.custom_second_hand);
    }
    private void startClockUpdate() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                postInvalidate(); // 通知 View 进行重绘
                updateClockHands();
            }
        }, 0, 1000); // 每隔一秒更新一次
    }
    private void updateClockHands() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        // 计算时、分、秒针的旋转角度
        hourRotation = (hour + minute / 60.0f) * 30.0f; // 每小时30度
        minuteRotation = minute * 6.0f; // 每分钟6度
        secondRotation = second * 6.0f; // 每秒6度
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20;
        // 绘制表盘
        canvas.drawBitmap(dialBitmap, null, new RectF(0, 0, width, height), null);
        // 绘制时针
        canvas.save();
        canvas.rotate(hourRotation, width / 2, height / 2);
        canvas.drawBitmap(hourHandBitmap, null, new RectF(0, 0, width, height), null);
        canvas.restore();
        // 绘制分针
        canvas.save();
        canvas.rotate(minuteRotation, width / 2, height / 2);
        canvas.drawBitmap(minuteHandBitmap, null, new RectF(0, 0, width, height), null);
        canvas.restore();
        // 绘制秒针
        canvas.save();
        canvas.rotate(secondRotation, width / 2, height / 2);
        canvas.drawBitmap(secondHandBitmap, null, new RectF(0, 0, width, height), null);
        canvas.restore();
    }
}
