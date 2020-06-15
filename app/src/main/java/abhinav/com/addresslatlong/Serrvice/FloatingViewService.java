package abhinav.com.addresslatlong.Serrvice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;

import abhinav.com.addresslatlong.R;


public class FloatingViewService extends Service implements View.OnClickListener
{
    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;
    private View imgv_stop;
    private View imgv_start;
    private MediaRecorder mMediaRecorder;

    public FloatingViewService()
    {

    }


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mMediaRecorder = new MediaRecorder();
        //getting the widget layout from xml using layout inflater
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        //setting the layout parameters
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        //getting windows services and adding the floating view to it


        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);


        //getting the collapsed and expanded view from the floating view
        collapsedView = mFloatingView.findViewById(R.id.layoutCollapsed);
        expandedView = mFloatingView.findViewById(R.id.layoutExpanded);
        imgv_stop = mFloatingView.findViewById(R.id.imgv_stop);
        imgv_start = mFloatingView.findViewById(R.id.imgv_start);

        //adding click listener to close button and expanded view
        mFloatingView.findViewById(R.id.buttonClose).setOnClickListener(this);
        expandedView.setOnClickListener(this);
        imgv_stop.setOnClickListener(this);
        imgv_start.setOnClickListener(this);

        //adding an touchlistener to make drag movement of the floating widget
        mFloatingView.findViewById(R.id.relativeLayoutParent).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        //when the drag is ended switching the state of the widget
                        collapsedView.setVisibility(View.GONE);
                        expandedView.setVisibility(View.VISIBLE);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        //this code is helping the widget to move around the screen with fingers
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.layoutExpanded)
        {
            collapsedView.setVisibility(View.VISIBLE);
            expandedView.setVisibility(View.GONE);
        }

        if(v.getId()==R.id.imgv_start)
        {
            Intent intent = new Intent();
            intent.setAction("abhinav.com.addresslatlong.START_RECORDING");
            sendBroadcast(intent);
            imgv_start.setVisibility(View.GONE);
            imgv_stop.setVisibility(View.VISIBLE);
        }

        if(v.getId()==R.id.imgv_stop)
        {
            Intent intent = new Intent();
            intent.setAction("abhinav.com.addresslatlong.STOP_RECORDING");
            sendBroadcast(intent);
            imgv_start.setVisibility(View.VISIBLE);
            imgv_stop.setVisibility(View.GONE);
        }
    }
}