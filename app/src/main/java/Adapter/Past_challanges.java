package Adapter;

/**
 * Created by Champ on 2/11/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import u.activitymanager.R;


public class Past_challanges extends RecyclerView.Adapter<Past_challanges.ViewHolder> {
   // private ArrayList<AndroidVersion> android;
    private Context context;

    public Past_challanges(Context context)
    {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_challanges_recyler, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(Past_challanges.ViewHolder viewHolder, int i) {

       /* viewHolder.reward.setText(android.get(i).getCurrent_reward());
        viewHolder.current_leader.setText(android.get(i).getCurrent_leader());*/

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reward;
        private TextView current_leader;
        public ViewHolder(View view) {
            super(view);

            reward = (TextView)view.findViewById(R.id.textview_reward1);
            current_leader = (TextView) view.findViewById(R.id.textview_contestwinner);
        }
    }

}
