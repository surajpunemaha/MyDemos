package abhinav.com.addresslatlong;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VideoRecording extends AppCompatActivity implements View.OnClickListener
{
    private static final int REQUEST_TAKE_VIDEO = 1234;
    Button btn_start_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recording);

        btn_start_rec = (Button) findViewById(R.id.btn_start_rec);
        btn_start_rec.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_start_rec)
        {
            try
            {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                //intent.putExtra("android.intent.extra.durationLimit", 30000);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 120);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10485760L);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);
                startActivityForResult(intent, REQUEST_TAKE_VIDEO);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
