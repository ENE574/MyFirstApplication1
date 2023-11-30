package com.jnu.student;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class GameViewFragment extends Fragment implements SurfaceHolder.Callback, View.OnClickListener {
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private GameEngine gameEngine;
    private TextView scoreTextView;
    private ImageView targetImageView;
    private Bitmap targetBitmap;
    private int score;
    public GameViewFragment() {
    }
    public static GameViewFragment newInstance() {
        return new GameViewFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_view, container, false);
        surfaceView = view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gameEngine != null) {
                    return gameEngine.onTouchEvent(event);
                }
                return false;
            }
        });
        scoreTextView = view.findViewById(R.id.scoreTextView);
        score = 0;
        Button resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);
        timerTextView = view.findViewById(R.id.timerTextView);
        startTimer();
        targetImageView=view.findViewById(R.id.targetImageView);
        targetImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameEngine != null) {
                    gameEngine.moveTarget();
                }
            }
        });
        targetBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.book_3);
        int targetWidth = targetBitmap.getWidth() / 10; // 缩小为原来的一半宽度
        int targetHeight = targetBitmap.getHeight() / 10; // 缩小为原来的一半高度
        targetBitmap = Bitmap.createScaledBitmap(targetBitmap, targetWidth, targetHeight, false);
        gameEngine = new GameEngine(surfaceHolder, new GameEngine.GameEventListener() {
            @Override
            public void onHit() {
                score++;
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scoreTextView.setText("Score: " + score);
                        targetImageView.setVisibility(View.INVISIBLE);
                    }
                });
                gameEngine.moveTarget();
            }
        },targetBitmap);
        gameEngine.start();
        return view;
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("Time's up!");
                surfaceView.setOnTouchListener(null);
            }
        }.start();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameEngine.updateSurfaceHolder(holder);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        gameEngine.updateSurfaceHolder(holder);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameEngine.stop();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetButton) {
            score = 0;
            scoreTextView.setText("Score: " + score);
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            startTimer();
            surfaceView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (gameEngine != null) {
                        return gameEngine.onTouchEvent(event);
                    }
                    return false;
                }
            });
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}