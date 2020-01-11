package abhinav.com.addresslatlong;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ChangeLangActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_change_ph_lang, btn_eng, btn_marathi;
    TextView txtv_app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_lang);

        btn_change_ph_lang = (Button) findViewById(R.id.btn_change_ph_lang);
        btn_change_ph_lang.setOnClickListener(this);

        btn_eng = (Button) findViewById(R.id.btn_eng);
        btn_eng.setOnClickListener(this);

        btn_marathi = (Button) findViewById(R.id.btn_marathi);
        btn_marathi.setOnClickListener(this);

        txtv_app_name = (TextView) findViewById(R.id.txtv_app_name);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_change_ph_lang)
        {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }

        if(view.getId()==R.id.btn_eng)
        {
            updateResources(ChangeLangActivity.this, "eng");
        }

        if(view.getId()==R.id.btn_marathi)
        {
            updateResources(ChangeLangActivity.this, "mr");
        }
    }

    private static void updateResources(Context context, String language)
    {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        //return true;
    }
}
