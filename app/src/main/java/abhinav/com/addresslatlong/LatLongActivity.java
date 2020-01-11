package abhinav.com.addresslatlong;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LatLongActivity extends AppCompatActivity
{
    EditText edit_longitude,edit_latitude;
    Button btn_get;
    TextView txtv_city;
    LinearLayout layout_form;
    Toolbar toolbar;
    Spinner spinner_alphabates,spinner_result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latlong);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner_alphabates = (Spinner) findViewById(R.id.spinner_alphabates);
        spinner_result = (Spinner) findViewById(R.id.spinner_result);


        edit_latitude = (EditText) findViewById(R.id.edit_latitude);
        edit_longitude = (EditText) findViewById(R.id.edit_longitude);
        txtv_city = (TextView) findViewById(R.id.txtv_city);
        layout_form = (LinearLayout) findViewById(R.id.layout_form);

        btn_get = (Button) findViewById(R.id.btn_get);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lat = Double.parseDouble(edit_latitude.getText().toString());
                double longi = Double.parseDouble(edit_longitude.getText().toString());

                /*Snackbar.make(edit_latitude, "Showing", Snackbar.LENGTH_SHORT).
                        setAction("OK", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();*/

                getCity(lat, longi);
            }
        });

        spinner_alphabates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {

                String arrayName = spinner_alphabates.getSelectedItem().toString();
                int resId = getResources().getIdentifier(arrayName,"array",getPackageName());

                setResultArray(resId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setResultArray(int resID)
    {
        String [] result_array = getResources().getStringArray(resID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, result_array);
        spinner_result.setAdapter(adapter);
    }

    public void getCity(double lat, double longi)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
            String cityName = addresses.get(0).getLocality();
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

            txtv_city.setText(cityName+" | "+stateName+"|"+countryName);
        } catch (IOException e) {
            txtv_city.setText(""+e);
        }
    }
}
