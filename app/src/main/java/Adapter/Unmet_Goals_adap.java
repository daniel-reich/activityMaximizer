package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import u.activitymanager.R;

/**
 * Created by surender on 12/17/2016.
 */

public class Unmet_Goals_adap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;

    public Unmet_Goals_adap(Context c) {
        this.c = c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // Log.e("oncreateviewholder","oncreateviewholder");

        View view=null;
        final Context c=parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_goals_adap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int p) {
       // Log.e("onbindviewholder","onbindviewholder");
        ViewHolder holder1= (ViewHolder) holder;
        holder1.name.setText("abc");

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

       public TextView name;

        public ViewHolder(View v) {
            super(v);

            name=(TextView)v.findViewById(R.id.tv_username);

        }
    }
}
