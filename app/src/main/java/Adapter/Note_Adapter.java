package Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Fragments.Need_to_Quality;
import model.AllContact;
import model.AllNote;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/3/2017.
 */
public class Note_Adapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<AllNote> data;
    public Note_Adapter(Context c, ArrayList<AllNote> data) {
        this.c = c;
        this.data=data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        TextView username,rating;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            rating=(TextView)itemView.findViewById(R.id.tv_rating);
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
//        holder1.username.setText(data.get(position).getGivenName());
//        holder1.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}
