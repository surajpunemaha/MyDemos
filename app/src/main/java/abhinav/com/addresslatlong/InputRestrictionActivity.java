package abhinav.com.addresslatlong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InputRestrictionActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    EditText edit_input;
    RadioGroup rdogrp_language;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_restriction);

        edit_input = (EditText) findViewById(R.id.edit_input);

        rdogrp_language = (RadioGroup) findViewById(R.id.rdogrp_language);
        rdogrp_language.setOnCheckedChangeListener(this);

        addTextChangeListener();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        if(radioGroup.getId()==R.id.rdogrp_language)
        {
            int id = rdogrp_language.getCheckedRadioButtonId();
            if(id==R.id.rdobtn_english)
            {

            }

            if(id==R.id.rdobtn_marathi)
            {
                Toast.makeText(this, "Marathi", Toast.LENGTH_SHORT).show();
            }

            if(id==R.id.rdobtn_both)
            {
                Toast.makeText(this, "Both", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addTextChangeListener()
    {
        edit_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                System.out.println("okay");
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }
}