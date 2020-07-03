package abhinav.com.addresslatlong.Serrvice;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import abhinav.com.addresslatlong.R;

public class ForegroundService extends Service
{
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    NotificationManager manager;
    int counter = 30;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.profile_icon)
                .build();

        startForeground(1, notification);

        myTask();

        return START_NOT_STICKY;
    }

    public void updateNotification(int i)
    {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(""+i)
                .setSmallIcon(R.drawable.profile_icon)
                .build();
        manager.notify(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void myTask()
    {
        new CountDownTimer(30*1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                counter = counter - 1;
                updateNotification(counter);
            }

            @Override
            public void onFinish()
            {
                stopSelf();
            }

        }.start();
    }
}