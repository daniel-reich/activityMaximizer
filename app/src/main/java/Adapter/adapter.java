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

import u.activitymanager.R;

/**
 * Created by Dell on 14-02-2017.
 */

public class adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
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
        return 10;
    }
}


