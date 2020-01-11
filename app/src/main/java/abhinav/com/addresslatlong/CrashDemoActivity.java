package abhinav.com.addresslatlong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrashDemoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_clickHere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_demo);

        btn_clickHere = (Button) findViewById(R.id.btn_clickHere);
        btn_clickHere.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_clickHere)
        {
            int diviser = 0/0;
        }
    }
}
