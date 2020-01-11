package abhinav.com.addresslatlong;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ActionBarIconDemoActivity extends AppCompatActivity
{
    Menu defaultMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_icon_demo);
        addActionBar();

        Button clickme = (Button) findViewById(R.id.clickme);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCount(ActionBarIconDemoActivity.this, "3");
            }
        });
    }

    public void addActionBar()
    {
        try {
            getSupportActionBar().setTitle("Title");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Log.i("Error=>",""+e);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setCount(this, "9");
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.icon_menu, menu);
        defaultMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public void setCount(Context context, String count) {

        MenuItem menuItem = defaultMenu.findItem(R.id.ic_group);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

}
