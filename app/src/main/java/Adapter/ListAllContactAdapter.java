package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Fragments.ListContact;
import model.AllContact;
import model.AllList;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Rohan on 3/3/2017.
 */
public class ListAllContactAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity c;
    Firebase mref;
    SharedPreferences pref;
    ArrayList<AllContact> data;
    String uid="",name="";
    FragmentManager fm;

    public ListAllContactAdapter(Activity c, ArrayList<AllContact> data, String name, FragmentManager fm) {
        this.c = c;
        this.data=data;
        this.name=name;
        this.fm=fm;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listadapter, parent, false);
            pref=c.getSharedPreferences("userpref",0);
            Firebase.setAndroidContext(c);
            mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
            uid=pref.getString("uid","");
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(data.get(position).getGivenName());

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reflink= Constants.URL+"contacts/"+uid+"/"+data.get(position).getGivenName();
                Map newlist = new HashMap();
                newlist.put("ref",reflink);
                mref.child("lists").child(uid).child(name).child("contacts")
                        .child(data.get(position).getGivenName())
                        .setValue(newlist);
                fm.popBackStack();
            }
        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}
