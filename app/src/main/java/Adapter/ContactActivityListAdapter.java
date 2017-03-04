package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Fragments.Activity_list_frag;
import Fragments.Need_to_Quality;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Surbhi on 03-03-2017.
 */
public class ContactActivityListAdapter extends RecyclerView.Adapter<ContactActivityListAdapter.ViewHolder> {

    private Context context;

    private SharedPreferences pref;
    private Firebase mref;

    JSONArray array;
    String str="";

    public ContactActivityListAdapter(Context context, JSONArray array,String str) {
        this.context = context;
        this.array=array;
        this.str=str;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_need_activity_adap, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContactActivityListAdapter.ViewHolder viewHolder, int i) {



       /* viewHolder.reward.setText(android.get(i).getCurrent_reward());

        viewHolder.current_leader.setText(android.get(i).getCurrent_leader());*/
        try {

            if(str.equalsIgnoreCase("simple")) {

                viewHolder.tv_activity_list.setText(array.getJSONObject(i).getString("name"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'hh:mm a");

                long time = Long.parseLong(array.getJSONObject(i).getString("time"));

                viewHolder.timestamp.setText(simpleDateFormat.format(time));


                Log.e("inside", "oo");
            }
            else if(str.equalsIgnoreCase("all"))
            {
                viewHolder.tv_activity_list.setText(array.getJSONObject(i).getString("type"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'hh:mm a");

                long time = Long.parseLong(array.getJSONObject(i).getString("date"));

                viewHolder.timestamp.setText(simpleDateFormat.format(time));


                Log.e("inside", "oo");
            }

        } catch (Exception e) {


            Log.e("i","e",e);




        }


    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity_list;

        private TextView timestamp;







        public ViewHolder(View view) {
            super(view);
            tv_activity_list=(TextView)view.findViewById(R.id.tv_start);
            timestamp=(TextView)view.findViewById(R.id.tv_date);

            tv_activity_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int position=getPosition();

                    Log.e("position",position+"");






                }
            });

        }
    }





}
