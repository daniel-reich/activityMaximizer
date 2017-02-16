package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ActivitiesAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;

    public ActivitiesAdapter(Context c) {
        this.c = c;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View itemView) {
            super(itemView);

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

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return 6;
    }
}
