package abhinav.com.addresslatlong;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/**
 * Created by abhinav on 13/3/20.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobCheckInternet extends JobService
{

    @Override
    public boolean onStartJob(JobParameters params)
    {
        Toast.makeText(this,"Executed",Toast.LENGTH_LONG).show();

        /*
   * True - if your service needs to process
   * the work (on a separate thread).
   * False - if there's no more work to be done for this job.
   */

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Toast.makeText(this,"Stopped",Toast.LENGTH_LONG).show();
        return true;
    }
}
