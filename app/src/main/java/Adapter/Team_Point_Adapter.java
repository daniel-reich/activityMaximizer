package Adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.AllContact;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Rohan on 3/10/2017.
 */
public class Team_Point_Adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity c;
    Firebase mref;
    SharedPreferences pref;

    String uid="",name="";
    FragmentManager fm;

    JSONArray data;

    public Team_Point_Adapter(JSONArray data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        TextView username,count;
        ImageView tick;



        public ViewHolder(View itemView) {
            super(itemView);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            count=(TextView)itemView.findViewById(R.id.count);
            tick=(ImageView)itemView.findViewById(R.id.next);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listadapter1, parent, false);
//            pref=c.getSharedPreferences("userpref",0);
//            Firebase.setAndroidContext(c);
//            mref=new Firebase("https://activitymaximizer.firebaseio.com/");
//            uid=pref.getString("uid","");
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
//        holder1.username.setText(data.get(position).getGivenName());


        try {
            holder1.username.setText(data.getJSONObject(position).getString("name"));

            holder1.count.setText(data.getJSONObject(position).getLong("count") + "");


            if (data.getJSONObject(position).getBoolean("selected"))

                holder1.tick.setVisibility(View.VISIBLE);

             else

                holder1.tick.setVisibility(View.INVISIBLE);

                holder1.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            if (data.getJSONObject(position).getBoolean("selected"))

                                data.getJSONObject(position).put("selected",false);

                            else

                                data.getJSONObject(position).put("selected",true);

                            notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


        }
        catch (Exception e)
        {

        }

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.length();
    }
}

