package Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import Fragments.Goals_Details;
import Fragments.Need_to_Quality;
import u.activitymanager.R;

/**
 * Created by surender on 12/17/2016.
 */

public class Current_Goals_adap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;

    public Current_Goals_adap(Context c) {
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
        //holder1.name.setText("abc");

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout, new Goals_Details()).
                        addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

       public TextView name;
        public LinearLayout layout;

        public ViewHolder(View v) {
            super(v);

            name=(TextView)v.findViewById(R.id.tv_username);
            layout=(LinearLayout)v.findViewById(R.id.layout);

        }
    }
}
