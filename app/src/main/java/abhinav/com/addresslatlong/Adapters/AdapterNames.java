package abhinav.com.addresslatlong.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import abhinav.com.addresslatlong.Bean.NameBean;
import abhinav.com.addresslatlong.Other.Utility;
import abhinav.com.addresslatlong.R;

public class AdapterNames extends RecyclerView.Adapter<AdapterNames.DataHolder>
{

    private Activity context;
    private ArrayList<NameBean> al_names;

    public AdapterNames(Activity context, ArrayList<NameBean> al_names)
    {
        this.context = context;
        this.al_names = al_names;
    }

    @NonNull
    @Override
    public AdapterNames.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recv_chkbx, parent, false);
        return new AdapterNames.DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, final int position)
    {
        holder.checkbx_name.setText(al_names.get(position).getName());

        boolean is_checked = Utility.al_statusHolder.get(position).isChecked();

        holder.checkbx_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    Utility.al_statusHolder.get(position).setChecked(true);
                }
                else
                {
                    Utility.al_statusHolder.get(position).setChecked(false);
                }
            }
        });

        if(is_checked)
        {
            holder.checkbx_name.setChecked(true);
        }
        else
        {
            holder.checkbx_name.setChecked(false);
        }

    }

    @Override
    public int getItemCount()
    {
        return al_names.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class DataHolder extends RecyclerView.ViewHolder
    {
        CheckBox checkbx_name;

        public DataHolder(View itemView)
        {
            super(itemView);

            checkbx_name = (CheckBox) itemView.findViewById(R.id.checkbx_name);
        }
    }
}
