package Adapter;

/**
 * Created by Champ on 2/11/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Fragments.Challange_details;
import u.activitymanager.R;


public class Past_challanges extends RecyclerView.Adapter<Past_challanges.ViewHolder> {
    // private ArrayList<AndroidVersion> android;
    private Context context;
    JSONArray array;

    public Past_challanges(Context context, JSONArray array) {

        this.context = context;
        this.array=array;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_challanges_recylerview, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ViewHolder holder1= (ViewHolder) viewHolder;

        try {
            Log.e("array",array+"");
            final JSONObject obj=array.getJSONObject(i);
            Log.e("obj",obj+"");

            holder1.title.setText(obj.getString("title"));
            holder1.current_leader.setText(obj.getString("currentLeader"));
            holder1.reward.setText(obj.getString("reward"));

            holder1.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("data", obj+"");
                    Challange_details frag=new Challange_details();
                    frag.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, frag).
                            addToBackStack(null).commit();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("exception","e",e);
        }

    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reward,title;
        private TextView current_leader;
        public LinearLayout layout;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.tv_title);
            reward = (TextView)view.findViewById(R.id.textview_reward);
            current_leader = (TextView) view.findViewById(R.id.textview_currentleader);
            layout=(LinearLayout)view.findViewById(R.id.layout);
        }
    }


}
