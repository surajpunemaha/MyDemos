package abhinav.com.addresslatlong;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JobSchedularActivity extends AppCompatActivity
{
    JobScheduler jobScheduler;
    int MYJOBID = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_schedular);

        jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName jobService = new ComponentName(getPackageName(), JobCheckInternet.class.getName());
        JobInfo jobInfo = new JobInfo.Builder(MYJOBID,jobService).
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY). build();
        jobScheduler.schedule(jobInfo);
    }
}
