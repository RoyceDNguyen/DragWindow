package com.example.dragwindow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;

public class MainActivity extends AppCompatActivity {

    private View dragChild;
    private float originalX, originalY;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set parent window to be 80% of the screen size
        setWindowSize();

        // Declare child window
        final ViewGroup parentWindow = (ViewGroup) findViewById(R.id.parentWindow);
        dragChild = findViewById(R.id.childWindow);
        setChildActionListener(parentWindow); // Set listener
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setWindowSize() {
        WindowManager windowManager = getWindowManager();
        WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();

        // Capture current screen size
        int screenWidth = windowMetrics.getBounds().width();
        int screenHeight = windowMetrics.getBounds().height();

        //Calculate the window size to be 80% of screen size
        int windowWidth = (int) (0.8 * screenWidth);
        int windowHeight = (int) (0.8 * screenHeight);

        // Set window size
        getWindow().setLayout(windowWidth, windowHeight);
        getWindow().setGravity(Gravity.CENTER);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setChildActionListener(ViewGroup parentWindow) {
        dragChild.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: // Record the initial location
                    originalX = view.getX() - motionEvent.getRawX();
                    originalY = view.getY() - motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE: // Record the update location
                    float moveX = motionEvent.getRawX() + originalX;
                    float moveY = motionEvent.getRawY() + originalY;

                    if (moveX >= 0 && moveX + view.getWidth() <= parentWindow.getWidth())
                        view.setX(moveX);

                    if (moveY >= 0 && moveY + view.getHeight() <= parentWindow.getHeight())
                        view.setY(moveY);
                    break;
                default:
                    return false;
            }
            return true;
        });
    }
}