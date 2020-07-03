package abhinav.com.addresslatlong;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import abhinav.com.addresslatlong.Serrvice.ForegroundService;

public class ServiceDemo extends AppCompatActivity implements View.OnClickListener
{
    Button btn_start;
    Button btn_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.btn_start)
        {
            startService();
        }

        if(v.getId()==R.id.btn_stop)
        {
            stopService();
        }
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}
