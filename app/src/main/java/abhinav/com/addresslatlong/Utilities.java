package abhinav.com.addresslatlong;

import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

public class Utilities
{
    public static MediaProjection mMediaProjection;
    public static MediaProjectionManager mProjectionManager;
    public static MediaRecorder mMediaRecorder;
    public static boolean recording_started = false;
    public static VirtualDisplay mVirtualDisplay;

    public static final int DISPLAY_WIDTH = 480;
    public static final int DISPLAY_HEIGHT = 640;
}
