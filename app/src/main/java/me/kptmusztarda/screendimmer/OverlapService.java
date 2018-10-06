package me.kptmusztarda.screendimmer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class OverlapService extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    private static boolean isRunning;
    private Timer timer;

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setBackgroundColor(Color.BLACK);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;

        windowManager.addView(chatHead, params);

        isRunning = true;

//        timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                final Handler UIHandler = new Handler(Looper.getMainLooper());
//                UIHandler .post(new Runnable() {
//                    @Override
//                    public void run() {
//                        windowManager.removeViewImmediate(chatHead);
//                        windowManager.addView(chatHead, params);
//                        System.out.println("reDraw");
//                    }
//                });
//            }
//        };
//        timer.scheduleAtFixedRate(task, 0, 10000);
    }

    protected static boolean isRunning() {
        return isRunning;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(chatHead != null) windowManager.removeView(chatHead);
        if(timer != null) {
            timer.cancel();
            timer.purge();
        }
        isRunning = false;
    }
}
