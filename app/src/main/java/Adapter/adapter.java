package Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import model.Activity_breakdown_getset;
import u.activitymanager.R;

/**
 * Created by Dell on 14-02-2017.
 */

public class adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<Activity_breakdown_getset> data;
    Context c;

    public adapter( Context c,ArrayList<Activity_breakdown_getset> data) {
        this.c = c;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_col1,tv_col2,tv_col3,tv_col4;

    public ViewHolder(View view) {
        super(view);

        tv_col1=(TextView)view.findViewById(R.id.tv_column1);
        tv_col2=(TextView)view.findViewById(R.id.tv_column2);
        tv_col3=(TextView)view.findViewById(R.id.tv_column3);
        tv_col4=(TextView)view.findViewById(R.id.tv_column4);

    }
}
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            final Context c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int p) {
        final ViewHolder holder1= (ViewHolder) holder;

        holder1.tv_col1.setText(data.get(p).getActivity_name());
        holder1.tv_col2.setText(data.get(p).getPoints()+"");
        holder1.tv_col3.setText(data.get(p).getHatch_value()+"");
        holder1.tv_col4.setText(data.get(p).getTotal()+"");

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}


