package abhinav.com.addresslatlong.Receivers;

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
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import abhinav.com.addresslatlong.InvisibleActivity;
import abhinav.com.addresslatlong.Utilities;

public class StartStopRecordingReceiver  extends BroadcastReceiver
{

    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("abhinav.com.addresslatlong.START_RECORDING"))
        {
            try
            {
                /*Intent start_recording = new Intent(context, InvisibleActivity.class);
                context.startActivity(start_recording);*/

                Intent i = new Intent();
                i.setClassName("abhinav.com.addresslatlong", "abhinav.com.addresslatlong.InvisibleActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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

}