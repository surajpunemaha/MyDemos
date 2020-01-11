package abhinav.com.addresslatlong;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferencesActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText edit_name;
    Button btn_savePref;

    String MY_PREF = "my_preferences";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        String str = pref.getString("NAME", "NA");

        edit_name = (EditText) findViewById(R.id.edit_name);

        btn_savePref = (Button) findViewById(R.id.btn_savePref);
        btn_savePref.setOnClickListener(this);

        edit_name.setText(str);

    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_savePref)
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("NAME", edit_name.getText().toString());
            editor.apply();
        }
    }
}
