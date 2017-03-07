package Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Fragments.Downline_details_frag;
import Fragments.ListContact;
import model.AllDownlines;
import model.AllList;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/7/2017.
 */
public class DownlineAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<AllDownlines> data;
    FragmentManager fm;
    public DownlineAdapter(Context c, ArrayList<AllDownlines> data, FragmentManager fm) {
        this.c = c;
        this.data=data;
        this.fm=fm;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView username,clientpoint,recruiterpoint;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            clientpoint=(TextView)itemView.findViewById(R.id.tv_pointclients_count);
            recruiterpoint=(TextView)itemView.findViewById(R.id.tv_pointrecuirters_count);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downline_trainees_adap_item, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(data.get(position).getName());
        Log.e("dddd",data.get(position).getFivePointRecruits().toString()+" abc");
        if(data.get(position).getFivePointClients().toString().equalsIgnoreCase(""))
        {
            holder1.clientpoint.setText("0");
        }
        else {
            holder1.clientpoint.setText(data.get(position).getFivePointClients());
        }
        if(data.get(position).getFivePointRecruits().toString().equalsIgnoreCase(""))
        {
            holder1.recruiterpoint.setText("0");
        }
        else {
            holder1.recruiterpoint.setText(data.get(position).getFivePointRecruits());
        }

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                fm.beginTransaction().
//                        replace(R.id.frame_layout,new Downline_details_frag()).addToBackStack(null).commit();
                Downline_details_frag basic_frag = new Downline_details_frag();
                Bundle args = new Bundle();
                args.putString("uid", data.get(position).getUid());
                basic_frag.setArguments(args);

                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, basic_frag).
                        addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}
