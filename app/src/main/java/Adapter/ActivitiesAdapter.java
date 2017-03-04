package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public ViewHolder(View itemView) {
            super(itemView);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
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

        holder1.tv_date.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return list.size();
    }
}
