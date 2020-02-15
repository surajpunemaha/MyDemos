package abhinav.com.addresslatlong;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class CustomProgressBarActivity extends AppCompatActivity
{
    public int counter;
    public static final int OTP_RESEND_DURATION = 100000; // in milliseconds
    ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        startCountDown();
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
}
