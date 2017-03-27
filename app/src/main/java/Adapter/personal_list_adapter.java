package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class personal_list_adapter extends RecyclerView.Adapter<personal_list_adapter.ViewHolder> {
  //  private ArrayList<AndroidVersion> android;
    private Context context;

    int count[];

    String activity_list[]={"Kitchen Table Set","Kitchen Tables Done","Apps Closed","Recruits","Total Premium","Upcoming Appointments",
            "Confirmed Invites","New Shows"};

    public personal_list_adapter(Context context,int count[]) {
        this.context = context;
        this.count=count;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.personal_list_adapter, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(personal_list_adapter.ViewHolder viewHolder, int position) {
//
       viewHolder.reward.setText(activity_list[position]);

        viewHolder.current_leader.setText(count[position]+"");

    }

    @Override
    public int getItemCount()
    {
        Log.e("count","count");
        return activity_list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reward;
        private TextView current_leader;
        public ViewHolder(View view) {
            super(view);

            reward = (TextView)view.findViewById(R.id.name);
            current_leader = (TextView) view.findViewById(R.id.value);
        }
    }
}
