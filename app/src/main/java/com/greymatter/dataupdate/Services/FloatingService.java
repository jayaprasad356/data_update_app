package com.greymatter.dataupdate.Services;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.greymatter.dataupdate.HomeActivity;
import com.greymatter.dataupdate.R;

public class FloatingService extends Service{


    private WindowManager wm;
    private ImageView imgLogo;
    private WindowManager.LayoutParams params;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        imgLogo = new ImageView(this);
        imgLogo.setImageResource(R.drawable.ic_launcher_foreground);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.TOP | Gravity.START ;
        params.x = 0;
        params.y = 100;


        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
            }
        });



        imgLogo.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    initialY = params.y;
                    initialX = params.x;
                    initialTouchX =  event.getRawX();
                    initialTouchY =  event.getRawY();
                    return true;
                    case MotionEvent.EDGE_TOP:
                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        wm.updateViewLayout(imgLogo, params);
                        return true;
                }
                 return false;
            }
        });
        wm.addView(imgLogo,params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (imgLogo != null)
            wm.removeView(imgLogo);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
