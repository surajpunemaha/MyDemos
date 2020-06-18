package abhinav.com.addresslatlong;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomProgressBarActivity extends AppCompatActivity
{
    public int counter;
    public static final int OTP_RESEND_DURATION = 100000; // in milliseconds
    ProgressBar progressBar1;

    ArrayList<Integer> al_seconds = new ArrayList<Integer>();
    TextView txtv_counter;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        //startCountDown();
        initSecods();
    }

    public void startCountDown()
    {
        counter = ( OTP_RESEND_DURATION / 1000);

        new CountDownTimer(OTP_RESEND_DURATION, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {
                int minutes_remain = counter/60;
                int seconds_remain = counter%60;

                int progress = 100-counter;
                progressBar1.setProgress(progress);

                counter--;
            }

            @Override
            public void onFinish()
            {

            }

        }.start();
    }


    /*-------------------------------*/
    // can you see this??

    public void initSecods()
    {
        txtv_counter= findViewById(R.id.txtv_counter);
        al_seconds.add(6);
        al_seconds.add(9);
        al_seconds.add(12);

        startOTPCountDown(al_seconds.get(i));
    }

    public void startOTPCountDown(int duration_in_secods)
    {
        counter = 0;

        new CountDownTimer(duration_in_secods*1000, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {
                counter = counter+1;
                txtv_counter.setText(""+counter);
            }

            @Override
            public void onFinish()
            {
                i++;
                if(i<al_seconds.size())
                {
                    startOTPCountDown(al_seconds.get(i));
                }
            }

        }.start();
    }

    // disla ka??
    // ho var ghya code ekda
}
