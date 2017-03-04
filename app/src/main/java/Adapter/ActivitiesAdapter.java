package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ActivitiesAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    List<String> list;

    public ActivitiesAdapter(Context c, List<String> list) {
        this.c = c;
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_date;
        LayoutInflater inflater;
        LinearLayout layout,layout1;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            layout=(LinearLayout)itemView.findViewById(R.id.lay_appointment);
            layout1=(LinearLayout)itemView.findViewById(R.id.layout1);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        {
            final Context c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.layout.removeAllViews();
        holder1.tv_date.setText(list.get(position));
        if(position==4) {
            for(int i=0;i<2;i++)
            {
                holder1.layout1.setBackgroundColor(Color.parseColor("#dddddd"));
                holder1.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                holder1.view = holder1.inflater.inflate(R.layout.activity_adapter_item, null);
                holder1.layout.addView(holder1.view);
            }
        }

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return list.size();
    }
}
