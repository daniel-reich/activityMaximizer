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
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import Fragments.Downline_details_frag;
import model.AllBaseDownlines;
import model.AllDownlines;
import u.activitymanager.R;
import utils.GaugeView;

/**
 * Created by Rohan on 3/7/2017.
 */
public class BaseDownlineAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<AllBaseDownlines> data;
    FragmentManager fm;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public BaseDownlineAdapter(Context c, ArrayList<AllBaseDownlines> data, FragmentManager fm) {
        this.c = c;
        this.data=data;
        this.fm=fm;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView username,clientpoint,recruiterpoint;
        SwipeRevealLayout swipeRevealLayout;
        GaugeView SpeedView;
        TextView delete,Addtoteam;
        public ViewHolder(View itemView) {
            super(itemView);
            SpeedView=(GaugeView)itemView.findViewById(R.id.meter);
            layout=(LinearLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            clientpoint=(TextView)itemView.findViewById(R.id.tv_pointclients_count);
            recruiterpoint=(TextView)itemView.findViewById(R.id.tv_pointrecuirters_count);
           // swipeRevealLayout=(SwipeRevealLayout)itemView.findViewById(R.id.swipeview);
            //swipeRevealLayout.setVisibility(View.GONE);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basedownlineitem, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.SpeedView.setTargetValue((float)0.0);
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


                Downline_details_frag basic_frag = new Downline_details_frag();
                Bundle args = new Bundle();
                args.putString("uid", data.get(position).getUid());
                basic_frag.setArguments(args);

                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, basic_frag).
                        addToBackStack(null).commit();
            }
        });


      //  viewBinderHelper.bind(holder1.swipeRevealLayout, String.valueOf(data.get(position)));


    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}