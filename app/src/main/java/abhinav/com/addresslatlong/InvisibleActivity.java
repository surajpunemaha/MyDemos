package abhinav.com.addresslatlong;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import abhinav.com.addresslatlong.Receivers.StartStopRecordingReceiver;

public class InvisibleActivity extends AppCompatActivity
{
    private static final int PERMISSION_CODE = 1;
    private int mScreenDensity ;
    MediaProjectionCallback mediaProjectionCallback;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invisible);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        initRecorder();
        prepareRecorder();

        mediaProjectionCallback = new MediaProjectionCallback();
        Utilities.mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        if (Utilities.mMediaProjection == null)
        {
            startActivityForResult(Utilities.mProjectionManager.createScreenCaptureIntent(), PERMISSION_CODE);
            return;
        }
        Utilities.mVirtualDisplay = createVirtualDisplay();
        Utilities.mMediaRecorder.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PERMISSION_CODE)
        {
            Utilities.mMediaProjection = Utilities.mProjectionManager.getMediaProjection(resultCode, data);
            Utilities.mMediaProjection.registerCallback(mediaProjectionCallback, null);
            Utilities.mVirtualDisplay = createVirtualDisplay();
            Utilities.mMediaRecorder.start();
            Utilities.recording_started = true;
            InvisibleActivity.this.finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionCallback extends MediaProjection.Callback
    {
        @Override
        public void onStop()
        {
            if (Utilities.recording_started ==true)
            {
                Utilities.mMediaRecorder.stop();
                Utilities.mMediaRecorder.reset();
            }

            Utilities.mMediaProjection = null;
            stopScreenSharing();
        }
    }

    private void stopScreenSharing()
    {
        if (Utilities.mVirtualDisplay == null) {
            return;
        }
        Utilities.mVirtualDisplay.release();
        //mMediaRecorder.release();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay createVirtualDisplay()
    {
        return Utilities.mMediaProjection.createVirtualDisplay("MainActivity",
                Utilities.DISPLAY_WIDTH, Utilities.DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                Utilities.mMediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
    }

    private void initRecorder() {
        if (Utilities.mMediaRecorder == null) {
            Utilities.mMediaRecorder = new MediaRecorder();
            Utilities.mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            Utilities.mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            Utilities.mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            Utilities.mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            Utilities.mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            Utilities.mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            Utilities.mMediaRecorder.setVideoFrameRate(30);
            Utilities.mMediaRecorder.setVideoSize(Utilities.DISPLAY_WIDTH, Utilities.DISPLAY_HEIGHT);
            Utilities.mMediaRecorder.setOutputFile(getFilePath());
        }
    }

    private void prepareRecorder()
    {
        try {
            Utilities.mMediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    public String getFilePath()
    {
        final String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "Failed to get External Storage", Toast.LENGTH_SHORT).show();
            return null;
        }
        final File folder = new File(directory);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        String filePath;
        if (success) {
            String videoName = ("capture_" + getCurSysDate() + ".mp4");
            filePath = directory + File.separator + videoName;
        } else {
            Toast.makeText(this, "Failed to create Recordings directory", Toast.LENGTH_SHORT).show();
            return null;
        }
        return filePath;
    }

    public String getCurSysDate() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
}
