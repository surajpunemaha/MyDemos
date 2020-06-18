package abhinav.com.addresslatlong;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CameraDemoActivity extends AppCompatActivity
{
    private final static String DEBUG_TAG = "CameraDemoActivity";
    private Camera camera;
    private int cameraId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_demo);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
        }
        else
        {
            cameraId = findFrontFacingCamera();
            if(cameraId<0)
            {
                Toast.makeText(this, "No fron facing camera", Toast.LENGTH_LONG).show();
            }
            else
            {
                camera = Camera.open(cameraId);
                camera.startPreview();
            }
        }
    }

    public int findFrontFacingCamera()
    {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
}
