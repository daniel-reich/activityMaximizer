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

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
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
public class SelectNewActivityListAdapter extends RecyclerView.Adapter<SelectNewActivityListAdapter.ViewHolder> {

    private Context context;
  String activity_list[]={"Set Appointment","Went on KT","Closed Life","closed IBA","Closed Other Business","Appt Set To Closed Life",
          "Appt Set To Closed Life","Invite to Opportunity Meeting","Went To Opportunity Meeting","Call Back","Dark House","Not Interested"};
    private SharedPreferences pref;
    private Firebase mref;

    public SelectNewActivityListAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_new_activity_list_adapter, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SelectNewActivityListAdapter.ViewHolder viewHolder, int i) {

       /* viewHolder.reward.setText(android.get(i).getCurrent_reward());

        viewHolder.current_leader.setText(android.get(i).getCurrent_leader());*/
        viewHolder.tv_activity_list.setText(activity_list[i]);


    }

    @Override
    public int getItemCount() {
        return activity_list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity_list;




        public ViewHolder(View view) {
            super(view);
            tv_activity_list=(TextView)view.findViewById(R.id.tv_activitylist);
            tv_activity_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int position=getPosition();

                    Log.e("position",position+"");






                    Activity_list_frag.dialog.dismiss();
                    addNewContact(position);
                }
            });

        }
    }



    private void addNewContact(int position) {
        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
        Log.e("Today is ", timeStampDate.getTime()+"");
        String timestamp=String.valueOf(timeStampDate.getTime());

        JSONObject json=new JSONObject();
        try {
            json.put("name",activity_list[position]);
            json.put("time",timestamp+"");

            Activity_list_frag.js.put(json);
            Activity_list_frag.listadapter.notifyDataSetChanged();
        } catch (Exception e) {

            Log.e("y","e",e);
            e.printStackTrace();
        }

        pref=context.getSharedPreferences("userpref",0);
     String   uid=pref.getString("uid","");
        String noteref= Constants.URL+"events/"+uid+"/"+timestamp;







//        mref.child("contacts").child(uid)
//                .child(st_fname).child("notes").child(timestamp)
//                .setValue(newnote);


        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        Map newcontact = new HashMap();
        newcontact.put("contactName", Need_to_Quality.givenName);
        newcontact.put("contactRef",Need_to_Quality.ref);
        newcontact.put("created",timestamp);
        newcontact.put("date",timestamp);
        newcontact.put("eventKitID","");
        newcontact.put("ref",noteref);
        newcontact.put("type",activity_list[position]);
        newcontact.put("userName",pref.getString("givenName","")+" "+pref.getString("familyName",""));
        newcontact.put("userRef",pref.getString("ref",""));

        Log.e("Aa",newcontact.toString());

        mref.child("events")
                .child(uid)
                .child(timestamp)
                .setValue(newcontact);



        
    }

}
