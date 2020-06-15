package abhinav.com.addresslatlong;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import abhinav.com.addresslatlong.Receivers.StartStopRecordingReceiver;
import abhinav.com.addresslatlong.Serrvice.FloatingViewService;

public class ScreenRecorderActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btn_startRecording, btn_stopRecording;
    public static int DRAW_OVER_OTHER_APP_PERMISSION = 4534;
    StartStopRecordingReceiver receiver = new StartStopRecordingReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_recorder);

        btn_startRecording = (Button) findViewById(R.id.btn_startRecording);
        btn_startRecording.setOnClickListener(this);

        btn_stopRecording = (Button) findViewById(R.id.btn_stopRecording);
        btn_stopRecording.setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction("abhinav.com.addresslatlong.START_RECORDING");
        filter.addAction("abhinav.com.addresslatlong.STOP_RECORDING");

        registerReceiver(receiver, filter);
        askForSystemOverlayPermission();
        grantPermission();

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_startRecording)
        {
            try
            {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    startService(new Intent(ScreenRecorderActivity.this, FloatingViewService.class));
                    //finish();
                } else if (Settings.canDrawOverlays(ScreenRecorderActivity.this))
                {
                    startService(new Intent(ScreenRecorderActivity.this, FloatingViewService.class));
                    //finish();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(v.getId()==R.id.btn_stopRecording)
        {
            /*Intent intent = new Intent();
            intent.setAction("abhinav.com.addresslatlong.STOP_RECORDING");
            sendBroadcast(intent);*/
        }
    }

    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this))
        {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION);
        }
    }

    public void grantPermission()
    {
        ArrayList<String> arrPerm = new ArrayList<>();


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            arrPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED)
        {
            arrPerm.add(Manifest.permission.RECORD_AUDIO);
        }

        if(!arrPerm.isEmpty())
        {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, 7867);
        }
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
