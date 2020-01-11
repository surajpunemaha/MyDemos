package abhinav.com.addresslatlong;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by abhinav on 16/8/18.
 */

public class TestApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable)
            {
                handleUncaughtException(thread, throwable);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e)
    {
        String stackTrace = "\nStackTrace :"+Log.getStackTraceString(e);
        String message = "Message: "+e.getMessage();

        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        String manufacturer = "Mobile :"+Build.MANUFACTURER;
        String model = "Model: "+Build.MODEL;

        String mobile_details = manufacturer+"\n"+model;
        String os = "SDK version: "+sdkVersion + " (" + release +")";

        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra (Intent.EXTRA_EMAIL, new String[]{"surajbang26@gmail.com"});
        intent.putExtra (Intent.EXTRA_SUBJECT, "DemoApp Crash log file");
        intent.putExtra (Intent.EXTRA_TEXT, message+"\n"+os+"\n"+mobile_details+"\n"+stackTrace);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}