package abhinav.com.addresslatlong;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btn_demoLatLong, btn_compression, btn_table_layout;
    Button btn_crash_report, btn_bottomsheet, btn_scanQr;
    Button btn_calculateDistance, btn_sharedPrefs;
    Button btn_fileUpload, btn_video_recording, btn_expr_evaluator, btn_abar_count;
    Button btn_expandalble_view, btn_count_down, btn_scan_aadhar, btn_language_demo;
    Button btn_input_restrict, btn_crop_image, btn_aws_sns;
    Button btn_drawer_demo, btn_sms_retriver, btn_drawText, btn_recv_demo, btn_custom_progress;
    Button btn_pdf_demo, btn_job_schedular, btn_read_excel, btn_google_signIn;
    Button btn_screen_recorder, btn_foregroundService, btn_zoomAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    public void initViews()
    {
        btn_zoomAPI= (Button) findViewById(R.id.btn_zoomAPI);
        btn_zoomAPI.setOnClickListener(this);

        btn_foregroundService= (Button) findViewById(R.id.btn_foregroundService);
        btn_foregroundService.setOnClickListener(this);

        btn_screen_recorder= (Button) findViewById(R.id.btn_screen_recorder);
        btn_screen_recorder.setOnClickListener(this);

        btn_google_signIn= (Button) findViewById(R.id.btn_google_signIn);
        btn_google_signIn.setOnClickListener(this);

        btn_read_excel= (Button) findViewById(R.id.btn_read_excel);
        btn_read_excel.setOnClickListener(this);

        btn_job_schedular= (Button) findViewById(R.id.btn_job_schedular);
        btn_job_schedular.setOnClickListener(this);

        btn_pdf_demo= (Button) findViewById(R.id.btn_pdf_demo);
        btn_pdf_demo.setOnClickListener(this);

        btn_custom_progress= (Button) findViewById(R.id.btn_custom_progress);
        btn_custom_progress.setOnClickListener(this);

        btn_recv_demo= (Button) findViewById(R.id.btn_recv_demo);
        btn_recv_demo.setOnClickListener(this);

        btn_drawText= (Button) findViewById(R.id.btn_drawText);
        btn_drawText.setOnClickListener(this);

        btn_sms_retriver= (Button) findViewById(R.id.btn_sms_retriver);
        btn_sms_retriver.setOnClickListener(this);

        btn_drawer_demo= (Button) findViewById(R.id.btn_drawer_demo);
        btn_drawer_demo.setOnClickListener(this);

        btn_aws_sns= (Button) findViewById(R.id.btn_aws_sns);
        btn_aws_sns.setOnClickListener(this);

        btn_crop_image= (Button) findViewById(R.id.btn_crop_image);
        btn_crop_image.setOnClickListener(this);

        btn_input_restrict= (Button) findViewById(R.id.btn_input_restrict);
        btn_input_restrict.setOnClickListener(this);

        btn_language_demo= (Button) findViewById(R.id.btn_language_demo);
        btn_language_demo.setOnClickListener(this);

        btn_scan_aadhar= (Button) findViewById(R.id.btn_scan_aadhar);
        btn_scan_aadhar.setOnClickListener(this);

        btn_count_down= (Button) findViewById(R.id.btn_count_down);
        btn_count_down.setOnClickListener(this);

        btn_expandalble_view= (Button) findViewById(R.id.btn_expandalble_view);
        btn_expandalble_view.setOnClickListener(this);

        btn_abar_count= (Button) findViewById(R.id.btn_abar_count);
        btn_abar_count.setOnClickListener(this);

        btn_expr_evaluator= (Button) findViewById(R.id.btn_expr_evaluator);
        btn_expr_evaluator.setOnClickListener(this);

        btn_video_recording= (Button) findViewById(R.id.btn_video_recording);
        btn_video_recording.setOnClickListener(this);

        btn_fileUpload= (Button) findViewById(R.id.btn_fileUpload);
        btn_fileUpload.setOnClickListener(this);

        btn_sharedPrefs= (Button) findViewById(R.id.btn_sharedPrefs);
        btn_sharedPrefs.setOnClickListener(this);

        btn_calculateDistance= (Button) findViewById(R.id.btn_calculateDistance);
        btn_calculateDistance.setOnClickListener(this);

        btn_scanQr = (Button) findViewById(R.id.btn_scanQr);
        btn_scanQr.setOnClickListener(this);

        btn_bottomsheet = (Button) findViewById(R.id.btn_bottomsheet);
        btn_bottomsheet.setOnClickListener(this);

        btn_crash_report = (Button) findViewById(R.id.btn_crash_report);
        btn_crash_report.setOnClickListener(this);

        btn_table_layout = (Button) findViewById(R.id.btn_table_layout);
        btn_table_layout.setOnClickListener(this);

        btn_compression = (Button) findViewById(R.id.btn_compression);
        btn_compression.setOnClickListener(this);

        btn_demoLatLong = (Button) findViewById(R.id.btn_demoLatLong);
        btn_demoLatLong.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_zoomAPI)
        {
            startActivity(new Intent(HomeActivity.this, ZoomAPIDemo.class));
        }

        if(view.getId()==R.id.btn_foregroundService)
        {
            startActivity(new Intent(HomeActivity.this, ServiceDemo.class));
        }

        if(view.getId()==R.id.btn_screen_recorder)
        {
            startActivity(new Intent(HomeActivity.this, ScreenRecorderActivity.class));
        }

        if(view.getId()==R.id.btn_google_signIn)
        {
            startActivity(new Intent(HomeActivity.this, GoogleSignInDemo.class));
        }

        if(view.getId()==R.id.btn_read_excel)
        {
            ArrayList<String> arrPerm = new ArrayList<>();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    arrPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                else
                {
                    startActivity(new Intent(HomeActivity.this, ReadExcelActivity.class));
                }

                if(!arrPerm.isEmpty())
                {
                    String[] permissions = new String[arrPerm.size()];
                    permissions = arrPerm.toArray(permissions);
                    ActivityCompat.requestPermissions(this, permissions, 1234);
                }
            }
        }

        if(view.getId()==R.id.btn_job_schedular)
        {
            startActivity(new Intent(HomeActivity.this, JobSchedularActivity.class));
        }

        if(view.getId()==R.id.btn_pdf_demo)
        {
            startActivity(new Intent(HomeActivity.this, PDFGenerationActivity.class));
        }

        if(view.getId()==R.id.btn_custom_progress)
        {
            startActivity(new Intent(HomeActivity.this, CustomProgressBarActivity.class));
        }

        if(view.getId()==R.id.btn_recv_demo)
        {
            startActivity(new Intent(HomeActivity.this, RecyclerWithCheckboxActivity.class));
        }

        if(view.getId()==R.id.btn_drawText)
        {
            startActivity(new Intent(HomeActivity.this, TextOnImageActivity.class));
        }

        if(view.getId()==R.id.btn_sms_retriver)
        {
            startActivity(new Intent(HomeActivity.this, SMSRetrieverActivity.class));
        }

        if(view.getId()==R.id.btn_drawer_demo)
        {
            startActivity(new Intent(HomeActivity.this, DrawerDemoActivity.class));
        }

        if(view.getId()==R.id.btn_aws_sns)
        {
            startActivity(new Intent(HomeActivity.this, ImageCropActivity.class));
        }

        if(view.getId()==R.id.btn_crop_image)
        {
            startActivity(new Intent(HomeActivity.this, ImageCropActivity.class));
        }

        if(view.getId()==R.id.btn_input_restrict)
        {
            startActivity(new Intent(HomeActivity.this, InputRestrictionActivity.class));
        }

        if(view.getId()==R.id.btn_language_demo)
        {
            startActivity(new Intent(HomeActivity.this, ChangeLangActivity.class));
        }

        if(view.getId()==R.id.btn_scan_aadhar)
        {
            startActivity(new Intent(HomeActivity.this, ScanAadhar.class));
        }

        if(view.getId()==R.id.btn_count_down)
        {
            startActivity(new Intent(HomeActivity.this, CountDownActivity.class));
        }

        if(view.getId()==R.id.btn_expandalble_view)
        {
            startActivity(new Intent(HomeActivity.this, ExpandableViewActivity.class));
        }

        if(view.getId()==R.id.btn_abar_count)
        {
            startActivity(new Intent(HomeActivity.this, ActionBarIconDemoActivity.class));
        }

        if(view.getId()==R.id.btn_expr_evaluator)
        {
            startActivity(new Intent(HomeActivity.this, ExpressionEvaluate.class));
        }

        if(view.getId()==R.id.btn_video_recording)
        {
            startActivity(new Intent(HomeActivity.this, VideoRecording.class));
        }

        if(view.getId()==R.id.btn_fileUpload)
        {
            startActivity(new Intent(HomeActivity.this, FileUploadActivity.class));
        }

        if(view.getId()==R.id.btn_sharedPrefs)
        {
            startActivity(new Intent(HomeActivity.this, PreferencesActivity.class));
        }

        if(view.getId()==R.id.btn_calculateDistance)
        {
            startActivity(new Intent(HomeActivity.this, CalculateDistance.class));
        }

        if(view.getId()==R.id.btn_scanQr)
        {
            startActivity(new Intent(HomeActivity.this, ScanQr.class));
        }

        if(view.getId()==R.id.btn_bottomsheet)
        {
            startActivity(new Intent(HomeActivity.this, BottomSheet.class));
        }

        if(view.getId()==R.id.btn_table_layout)
        {
            startActivity(new Intent(HomeActivity.this, TableDemo.class));
        }

        if(view.getId()==R.id.btn_compression)
        {
            startActivity(new Intent(HomeActivity.this, CompressionActivity.class));
        }

        if(view.getId()==R.id.btn_demoLatLong)
        {
            startActivity(new Intent(HomeActivity.this, LatLongActivity.class));
        }
    }
}