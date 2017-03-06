package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import u.activitymanager.R;

/**
 * Created by Surbhi on 18-02-2017.
 */
public class ActivityDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    String activity_list[]={"Contact Added","Appointments Set","Went to KT","Closed Life","closed IBA","Closed Other Business","Appt Set To Closed Life",
            "Appt Set To Closed IBA","Call Back","Confirmed Invites","New Shows","Dark House","Not Interested"};

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView act_name,today,tw,tq,tm,ty;

        public ViewHolder(View itemView) {
            super(itemView);

            act_name=(TextView)itemView.findViewById(R.id.actname);
            today=(TextView)itemView.findViewById(R.id.today);
            tw=(TextView)itemView.findViewById(R.id.tw);
            tm=(TextView)itemView.findViewById(R.id.tm);
            tq=(TextView)itemView.findViewById(R.id.tq);
            ty=(TextView)itemView.findViewById(R.id.ty);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            final Context c = parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail_adapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.act_name.setText(activity_list[position]);

    }

    @Override
    public int getItemCount() {


        return activity_list.length;
    }
}

