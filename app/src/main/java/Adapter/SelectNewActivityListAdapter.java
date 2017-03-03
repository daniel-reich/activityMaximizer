package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Fragments.Activity_list_frag;
import u.activitymanager.R;

/**
 * Created by Surbhi on 03-03-2017.
 */
public class SelectNewActivityListAdapter extends RecyclerView.Adapter<SelectNewActivityListAdapter.ViewHolder> {

    private Context context;
  String activity_list[]={"set Appointment","Went on KT","Closed Life","closed IBA","Closed Other Business","Appt Set To Closed Life",
          "Appt Set To Closed Life","Invite to Opportunity Meeting","Went To Opportunity Meeting","Call Back","Dark House","Not Interested"};

    public SelectNewActivityListAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_new_activity_list_adapter, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SelectNewActivityListAdapter.ViewHolder viewHolder, int i) {

       /* viewHolder.reward.setText(android.get(i).getCurrent_reward());

        viewHolder.current_leader.setText(android.get(i).getCurrent_leader());*/
        viewHolder.tv_activity_list.setText(activity_list[i]);


    }

    @Override
    public int getItemCount() {
        return activity_list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity_list;

        public ViewHolder(View view) {
            super(view);
            tv_activity_list=(TextView)view.findViewById(R.id.tv_activitylist);
            tv_activity_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity_list_frag.dialog.dismiss();
                }
            });

        }
    }
}
