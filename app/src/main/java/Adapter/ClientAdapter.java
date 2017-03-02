package Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;

import Fragments.Need_to_Quality;
import Fragments.SortByFragment;
import model.AllContact;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ClientAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<AllContact> data;
    String role;
    public ClientAdapter(Context c, ArrayList<AllContact> data, String role) {
        this.c = c;
        this.data=data;
        this.role=role;
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
        holder1.username.setText(data.get(position).getGivenName());
        if(role.equalsIgnoreCase("client")) {
            holder1.rating.setText("Rating : " + data.get(position).getRating());
        }
        else
        {
            holder1.rating.setText("Recruit Rating : " + data.get(position).getRecruitRating());
        }
        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Need_to_Quality basic_frag = new Need_to_Quality();
                Bundle args = new Bundle();
                args.putString("givenName", data.get(position).getGivenName().toString());
                args.putString("phoneNumber", data.get(position).getPhoneNumber().toString());
                args.putString("competitive", data.get(position).getCompetitive().toString());
                args.putString("created", data.get(position).getCreated().toString());

                args.putString("credible", data.get(position).getCredible().toString());
                args.putString("familyName", data.get(position).getFamilyName().toString());
                args.putString("hasKids", data.get(position).getHasKids().toString());
                args.putString("homeowner", data.get(position).getHomeowner().toString());

                args.putString("hungry", data.get(position).getHungry().toString());
                args.putString("incomeOver40k", data.get(position).getIncomeOver40k().toString());
                args.putString("married", data.get(position).getMarried().toString());
                args.putString("motivated", data.get(position).getMotivated().toString());

                args.putString("ofProperAge", data.get(position).getOfProperAge().toString());
                args.putString("peopleSkills", data.get(position).getPeopleSkills().toString());
                args.putString("rating", String.valueOf(data.get(position).getRating()));
                args.putString("recruitRating", String.valueOf(data.get(position).getRecruitRating()));
                args.putString("ref", data.get(position).getRef().toString());
                basic_frag.setArguments(args);

;



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
