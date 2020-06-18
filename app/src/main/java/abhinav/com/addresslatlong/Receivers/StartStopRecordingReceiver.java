package abhinav.com.addresslatlong.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import abhinav.com.addresslatlong.InvisibleActivity;
import abhinav.com.addresslatlong.R;
import abhinav.com.addresslatlong.Utilities;

public class StartStopRecordingReceiver  extends BroadcastReceiver
{

    public static final String TAG_NOTIFICATION = "NOTIFICATION_MESSAGE";
    public static final String CHANNEL_ID = "channel_1111";
    public static final int NOTIFICATION_ID = 111111;
    private static final String TAG = "Receiver";
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("abhinav.com.addresslatlong.START_RECORDING"))
        {
            try
            {
                /*Intent start_recording = new Intent(context, InvisibleActivity.class);
                context.startActivity(start_recording);*/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {

                    startActivityNotification(context,NOTIFICATION_ID,context.getResources().getString(R.string.start_recording),
                            context.getResources().getString(R.string.app_name));
//                    Intent activity = new Intent(context, InvisibleActivity.class);
//                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(activity);
                }
                else
                {
                    // If lower than Android 10, we use the normal method ever.
                    Intent activity = new Intent(context, InvisibleActivity.class);
                    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(activity);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


        if(intent.getAction().equals("abhinav.com.addresslatlong.STOP_RECORDING"))
        {
            try
            {
                Utilities.mMediaRecorder.release();
                Utilities.mMediaRecorder.stop();
                Utilities.mMediaRecorder.reset();
                Utilities.mVirtualDisplay.release();

                /*Intent start_recording = new Intent(context, InvisibleActivity.class);
                start_recording.putExtra("START_REC", false);
                context.startActivity(start_recording);*/

            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

//            Toast.makeText(context, "recording stopped", Toast.LENGTH_SHORT).show();
            // stop recording code here
        }
    }

    public static void startActivityNotification(Context context, int notificationID,
                                                 String title, String message) {

        NotificationManager mNotificationManager =
                (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Create GPSNotification builder
        NotificationCompat.Builder mBuilder;

        //Initialise ContentIntent
        Intent ContentIntent = new Intent(context, InvisibleActivity.class);
        ContentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent ContentPendingIntent = PendingIntent.getActivity(context,
                0,
                ContentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_close_black)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .setAutoCancel(true)
                .setContentIntent(ContentPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,
                    "Activity Opening Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setDescription("Activity opening notification");

            mBuilder.setChannelId(CHANNEL_ID);

            Objects.requireNonNull(mNotificationManager).createNotificationChannel(mChannel);
        }

        Objects.requireNonNull(mNotificationManager).notify(TAG_NOTIFICATION,notificationID,
                mBuilder.build());
    }

}