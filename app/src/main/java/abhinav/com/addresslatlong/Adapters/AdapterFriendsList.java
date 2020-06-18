package abhinav.com.addresslatlong.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import abhinav.com.addresslatlong.Bean.Friend;
import abhinav.com.addresslatlong.R;

public class AdapterFriendsList extends BaseAdapter
{
    private Activity activity;
    private ArrayList<Friend> al_friends;

    public AdapterFriendsList(Activity activity, ArrayList<Friend> al_friends)
    {
        this.activity = activity;
        this.al_friends = al_friends;
    }

    @Override
    public int getCount()
    {
        return al_friends.size();
    }

    @Override
    public Object getItem(int i)
    {
        return al_friends.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_friends_dtls,
                    parent, false);
        }

        TextView txtv_sr_no =(TextView)convertView.findViewById(R.id.txtv_sr_no);
        TextView txtv_name =(TextView)convertView.findViewById(R.id.txtv_name);
        TextView txtv_age =(TextView)convertView.findViewById(R.id.txtv_age);
        TextView txtv_dob =(TextView)convertView.findViewById(R.id.txtv_dob);

        txtv_sr_no.setText(al_friends.get(position).getId());
        txtv_name.setText(al_friends.get(position).getName());
        txtv_age.setText(al_friends.get(position).getAge());
        txtv_dob.setText(al_friends.get(position).getDob());

        return convertView;
    }
}
