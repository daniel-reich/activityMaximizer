package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import Fragments.Need_to_Quality;
import Fragments.SortByFragment;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ClientAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;

    public ClientAdapter(Context c) {
        this.c = c;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

 RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clients_adapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Need_to_Quality basic_frag = new Need_to_Quality();
                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Need_to_Quality()).
                        addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return 6;
    }
}
