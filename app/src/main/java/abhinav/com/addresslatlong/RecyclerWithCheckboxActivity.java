package abhinav.com.addresslatlong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import abhinav.com.addresslatlong.Adapters.AdapterNames;
import abhinav.com.addresslatlong.Bean.NameBean;
import abhinav.com.addresslatlong.Bean.StatusHolder;
import abhinav.com.addresslatlong.Other.Utility;

public class RecyclerWithCheckboxActivity extends AppCompatActivity
{
    RecyclerView recv_names;
    public ArrayList<NameBean> al_names;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_with_checkbox);

        recv_names = (RecyclerView) findViewById(R.id.recv_names);
        al_names = new ArrayList<>();

        getNames();
        setAdapter();
    }

    public void getNames()
    {
        al_names.clear();

        al_names.add(new NameBean("Suraj B.", false));
        al_names.add(new NameBean("Sneha", false));
        al_names.add(new NameBean("Anjali", false));
        al_names.add(new NameBean("Prakash", false));
        al_names.add(new NameBean("Atish", false));
        al_names.add(new NameBean("Ragini", false));
        al_names.add(new NameBean("Manoj", false));
        al_names.add(new NameBean("Ratnadeep", false));
        al_names.add(new NameBean("Priyam", false));
        al_names.add(new NameBean("Ankita", false));
        al_names.add(new NameBean("Nitish", false));
        al_names.add(new NameBean("Vrushali", false));
        al_names.add(new NameBean("Pravin", false));
        al_names.add(new NameBean("Prajyot", false));
        al_names.add(new NameBean("Suraj P.", false));
        al_names.add(new NameBean("Archana", false));
        al_names.add(new NameBean("Shivani", false));
        al_names.add(new NameBean("Kirti", false));
        al_names.add(new NameBean("Rututja", false));
        al_names.add(new NameBean("Surabhi", false));
        al_names.add(new NameBean("Parvati", false));
        al_names.add(new NameBean("Raksha", false));
        al_names.add(new NameBean("Mayur", false));
        al_names.add(new NameBean("Niraj", false));
        al_names.add(new NameBean("Dhiraj", false));
        al_names.add(new NameBean("Mayur", false));
        al_names.add(new NameBean("Saurabh", false));
        al_names.add(new NameBean("Sanjay", false));
        al_names.add(new NameBean("Pintu", false));
        al_names.add(new NameBean("Sachin", false));
        al_names.add(new NameBean("Dharmesh", false));
        al_names.add(new NameBean("Sarvesh", false));
        al_names.add(new NameBean("Aniket", false));
        al_names.add(new NameBean("Ashish", false));

        Utility.al_statusHolder.clear();

        for(int i=0; i<al_names.size(); i++)
        {
            StatusHolder holder = new StatusHolder();
            holder.setChecked(false);
            Utility.al_statusHolder.add(holder);
        }
    }

    public void setAdapter()
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        AdapterNames adapterNames = new AdapterNames(RecyclerWithCheckboxActivity.this, al_names);
        recv_names.setLayoutManager(mLayoutManager);
        recv_names.setAdapter(adapterNames);
    }
}
