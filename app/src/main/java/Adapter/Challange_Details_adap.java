package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Fragments.Need_to_Quality;
import model.AllContact;
import model.AllList;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class Challange_Details_adap extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<String> data;
    String role;
    SharedPreferences pref;
    public Challange_Details_adap(Context c, ArrayList<String> data) {
        this.c = c;
        this.data=data;
        pref=c.getSharedPreferences("userpref",0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView username,rating;

        public ViewHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout) itemView.findViewById(R.id.dynamic_layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            Log.e("enter","enter");
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challange_detail_adap, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int p) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(pref.getString("givenName","")+" "+pref.getString("familyName",""));

//        ArrayList<AllList> list=new ArrayList<>();
//        for (int i=0;i<2;i++)
//        list.add(new AllList("name:",i+""));
//        setdata(holder1.layout,list);

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }

    public void setdata(LinearLayout layout, ArrayList<AllList> list) {

        for(int i=0;i<list.size();i++) {
            View child = LayoutInflater.from(c).inflate(R.layout.challange_detail_adap_item, null);
            TextView tv_name = (TextView) child.findViewById(R.id.tv_textname);
            TextView tv_value = (TextView) child.findViewById(R.id.tv_textvalue);

            tv_name.setText(list.get(i).getName());
            tv_value.setText(list.get(i).getRef());

            layout.addView(child);
        }
    }

  }


