package abhinav.com.addresslatlong;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculateDistance extends AppCompatActivity implements View.OnClickListener {
    EditText edit_start_lat, edit_start_long, edit_end_lat, edit_end_long;
    Button btn_calculate;
    TextView txtv_distance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_distance);

        edit_start_lat = (EditText) findViewById(R.id.edit_start_lat);
        edit_start_long = (EditText) findViewById(R.id.edit_start_long);
        edit_end_lat = (EditText) findViewById(R.id.edit_end_lat);
        edit_end_long = (EditText) findViewById(R.id.edit_end_long);

        btn_calculate = (Button) findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(this);

        txtv_distance = (TextView) findViewById(R.id.txtv_distance);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_calculate)
        {
            Double start_lot = Double.parseDouble(edit_start_lat.getText().toString().trim());
            Double start_long = Double.parseDouble(edit_start_long.getText().toString().trim());

            Double end_lat = Double.parseDouble(edit_end_lat.getText().toString().trim());
            Double end_long = Double.parseDouble(edit_end_long.getText().toString().trim());

            float[] result = new float[1];

            Location.distanceBetween(start_lot, start_long, end_lat, end_long, result);

            txtv_distance.setText(result[0]+" Meter's");
        }
    }
}
