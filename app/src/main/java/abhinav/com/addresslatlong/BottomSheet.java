package abhinav.com.addresslatlong;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomSheet extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener,
        View.OnClickListener
{
    private TextView mTextView;

    private static final String TAG = BottomSheet.class.getSimpleName();
    BottomSheetBehavior sheetBehavior;
    LinearLayout layout_persistent_bottom_sheet;
    Button btn_proceed_payment, button_open_persitent_sheet;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        mTextView = findViewById(R.id.text_view_button_clicked);

        layout_persistent_bottom_sheet = findViewById(R.id.layout_persistent_bottom_sheet);

        btn_proceed_payment = findViewById(R.id.btn_proceed_payment);
        btn_proceed_payment.setOnClickListener(this);

        button_open_persitent_sheet = findViewById(R.id.button_open_persitent_sheet);
        button_open_persitent_sheet.setOnClickListener(this);

        sheetBehavior = BottomSheetBehavior.from(layout_persistent_bottom_sheet);

        /* Bottom sheet state change listener. We are changing button text when sheet changed state */

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
                switch (newState)
                {
                    case BottomSheetBehavior.STATE_HIDDEN :
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED :
                        button_open_persitent_sheet.setText("Expanded");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED :
                        button_open_persitent_sheet.setText("Collapsed");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING :
                        break;
                    case BottomSheetBehavior.STATE_SETTLING :
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset)
            {

            }
        });



        /* ---------------------- Model Bottom Sheet ---------------------- */

        Button button_open_bottom_sheet = findViewById(R.id.button_open_bottom_sheet);
        button_open_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });
    }

    @Override
    public void onButtonClicked(String text) {
        mTextView.setText(text);
    }


    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.button_open_persitent_sheet)
        {
            togglePersistentBottomSheet();
        }

        if(view.getId()==R.id.btn_proceed_payment)
        {
            mTextView.setText("Proceed Payment Clicked");
        }
    }

    public void togglePersistentBottomSheet()
    {
        if(sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED)
        {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        else
        {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}
