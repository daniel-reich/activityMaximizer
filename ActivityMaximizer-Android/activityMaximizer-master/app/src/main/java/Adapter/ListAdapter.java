package Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Fragments.ListContact;
import Fragments.Need_to_Quality;
import Fragments.Rating_info_fram;
import model.AllContact;
import model.AllList;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/2/2017.
 */
public class ListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<AllList> data;
    public ListAdapter(Context c, ArrayList<AllList> data) {
        this.c = c;
        this.data=data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        TextView username;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listadapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(data.get(position).getName());

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("givennemee",data.get(position).getName()+" avc");
                ListContact basic_frag = new ListContact();
                Bundle args = new Bundle();
                args.putString("givenName", data.get(position).getName());
                basic_frag.setArguments(args);

                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, basic_frag).
                        addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}
