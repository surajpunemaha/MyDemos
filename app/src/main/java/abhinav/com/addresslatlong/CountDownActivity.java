package abhinav.com.addresslatlong;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CountDownActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout_pickDate, layout_pickTime;
    TextView txtv_date, txtv_time;
    TextView txtv_days, txtv_hours, txtv_minutes, txtv_seconds;
    Button btn_start_cnt_down;

    String MY_PREF = "my_preferences";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        layout_pickDate = (LinearLayout) findViewById(R.id.layout_pickDate);
        layout_pickDate.setOnClickListener(this);
        layout_pickTime = (LinearLayout) findViewById(R.id.layout_pickTime);
        layout_pickTime.setOnClickListener(this);

        txtv_date = (TextView) findViewById(R.id.txtv_date);
        txtv_time = (TextView) findViewById(R.id.txtv_time);

        txtv_days = (TextView) findViewById(R.id.txtv_days);
        txtv_hours = (TextView) findViewById(R.id.txtv_hours);
        txtv_minutes = (TextView) findViewById(R.id.txtv_minutes);
        txtv_seconds = (TextView) findViewById(R.id.txtv_seconds);

        btn_start_cnt_down = (Button) findViewById(R.id.btn_start_cnt_down);
        btn_start_cnt_down.setOnClickListener(this);

        pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        String end_date = pref.getString("END_DATE", "");
        String end_time = pref.getString("END_TIME", "");

        txtv_date.setText(end_date);
        txtv_time.setText(end_time);

        if(validate())
        {
            performCountDown();
        }
    }

    public boolean validate()
    {
        boolean valid = true;

        if(txtv_date.getText().toString().equals(""))
        {
            Toast.makeText(CountDownActivity.this,
                    "Select Date", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if(txtv_time.getText().toString().equals(""))
        {
            Toast.makeText(CountDownActivity.this,
                    "Select Time", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.layout_pickDate)
        {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(CountDownActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month,
                                              int day)
                        {
                            txtv_date.setText(day+"/"+(month+1)+"/"+year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        }

        if(view.getId()==R.id.layout_pickTime)
        {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(CountDownActivity.this,
                    new TimePickerDialog.OnTimeSetListener()
                    {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minutes)
                        {
                            txtv_time.setText(hour+":"+minutes+":"+"00");
                        }
                    }, mHour, mMinute, false);

            timePickerDialog.show();
        }

        if(view.getId()==R.id.btn_start_cnt_down)
        {
            if(validate())
            {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("END_DATE", txtv_date.getText().toString().trim());
                editor.putString("END_TIME", txtv_time.getText().toString().trim());
                editor.apply();

                performCountDown();
            }
        }
    }

    public void performCountDown()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                getStartAndEndDates();
            }
        }, 0, 1000);
    }

    public void getStartAndEndDates()
    {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

        try
        {
            String startDateTime = mDay+"/"+(mMonth+1)+"/"+mYear+" "+hours+":"+min+":"+sec;
            Date startDate = simpleDateFormat.parse(startDateTime);

            String end_date = pref.getString("END_DATE", "");
            String end_time = pref.getString("END_TIME", "");

            String endDateTime = end_date+" "+end_time;
            Date endDate = simpleDateFormat.parse(endDateTime);

            calculateDifferenceBetweenDates(startDate, endDate);

        }catch (Exception e)
        {
        }
    }

    public void calculateDifferenceBetweenDates(Date startDate, Date endDate)
    {
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        txtv_days.setText(""+elapsedDays);
        txtv_hours.setText(""+elapsedHours);
        txtv_minutes.setText(""+elapsedMinutes);
        txtv_seconds.setText(""+elapsedSeconds);
    }
}
