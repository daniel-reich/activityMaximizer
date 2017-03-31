package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import Adapter.Current_Goals_adap;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Goals_Tracker extends Fragment {

    RecyclerView rv_currentgoal,rv_metgoal,rv_unmetgoal;
    Current_Goals_adap cur_goal_adap;
    View v;
    FirebaseAuth firebaseAuth;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    JSONObject obj;
    JSONArray current_array,met_array,unmet_array;
    String uid="",uidd="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.goal_tracker,container,false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("Goals");

        rv_currentgoal=(RecyclerView)v.findViewById(R.id.rv_currentgoal);
        rv_metgoal=(RecyclerView)v.findViewById(R.id.rv_metgoals);
        rv_unmetgoal=(RecyclerView)v.findViewById(R.id.rv_unmetgoals);

        rv_currentgoal.setNestedScrollingEnabled(true);
        rv_metgoal.setNestedScrollingEnabled(true);
        rv_unmetgoal.setNestedScrollingEnabled(true);

        rv_currentgoal.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_metgoal.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_unmetgoal.setLayoutManager(new LinearLayoutManager(getActivity()));
//        cur_goal_adap=new Current_Goals_adap(getActivity(),);
//        rv_unmetgoal.setAdapter(cur_goal_adap);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);



//        try {
//            uidd = getArguments().getString("uid");
//            Log.e("uidd",uidd);
//            if (uidd.length() > 1) {
//                uid = uidd;
//            } else {
                uid = pref.getString("uid", "");
//            }
//        }catch (Exception e)
//        {
//            Log.e("Exception",e.getMessage());
//        }

//        obj=new JSONObject();

//        try {
//
//            if(!pref.getString("current_goals"," ").equals(" ")) {
//                obj = new JSONObject(pref.getString("current_goals", ""));
//
//            }
//            else
//                obj=new JSONObject();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//
//        }

      getdatafromfirebase();

        return v;
    }

    public JSONArray getdatafromfirebase()
    {

        Log.e("uid",pref.getString("uid",""));
        mref.child("users").child(uid).child("Goals").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {

                    current_array=new JSONArray();
                    met_array=new JSONArray();
                    unmet_array=new JSONArray();

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        String key=messageSnapshot.getValue()+"";
                          JSONObject obj=new JSONObject();
                          JSONObject obj1=new JSONObject();
                        JSONObject obj2=new JSONObject();

                        String enddate = (String) messageSnapshot.child("endDate").getValue();

                       String check_met= Check_enddate(enddate);


                        obj.put("completed",messageSnapshot.child("completed").getValue());
                        obj.put("ref",messageSnapshot.child("ref").getValue());
                        obj.put("title",messageSnapshot.child("title").getValue());
                        obj.put("startDate",messageSnapshot.child("startDate").getValue());
                        obj.put("endDate",messageSnapshot.child("endDate").getValue());
                        obj.put("finished",messageSnapshot.child("finished").getValue());

                        Map<String, Object> activitycount = (Map<String, Object>) messageSnapshot.child("activityCount").getValue();
                        obj1.put("Appointment Set",activitycount.get("Appointments Set").toString());
                        obj1.put("Closed Life",activitycount.get("Closed Life").toString());
                        obj1.put("Went on KT",activitycount.get("Went on KT").toString());
                        obj1.put("Total Premium",activitycount.get("Total Premium").toString());
                        obj1.put("Closed Other Business",activitycount.get("Closed Other Business").toString());
                        obj1.put("Contacts Added",activitycount.get("Contacts Added").toString());
                        obj1.put("Closed IBA",activitycount.get("Closed IBA").toString());



                        Map<String, Object> currentcount = (Map<String, Object>) messageSnapshot.child("currentCount").getValue();
                        if(currentcount.containsKey("Appointments Set"))
                        obj2.put("Appointment Set",currentcount.get("Appointments Set").toString());
                        if(currentcount.containsKey("Closed Life"))
                        obj2.put("Closed Life",currentcount.get("Closed Life").toString());
                        if(currentcount.containsKey("Went on KT"))
                        obj2.put("Went on KT",currentcount.get("Went on KT").toString());
                        if(currentcount.containsKey("Total Premium"))
                        obj2.put("Total Premium",currentcount.get("Total Premium").toString());
                        if(currentcount.containsKey("Closed Other Business"))
                        obj2.put("Closed Other Business",currentcount.get("Closed Other Business").toString());
                        if(currentcount.containsKey("Contacts Added"))
                        obj2.put("Contacts Added",currentcount.get("Contacts Added").toString());
                        if(currentcount.containsKey("Closed IBA"))
                        obj2.put("Closed IBA",currentcount.get("Closed IBA").toString());



                        obj.put("activityCount",obj1);
                        obj.put("currentCount",obj2);

                        Log.e("value",obj+"");

                        if(check_met.equalsIgnoreCase("current")) {
                            Log.e("add_in_current_array","add_in_current_array");
                            if(messageSnapshot.child("completed").getValue().toString().equalsIgnoreCase("true"))
                                met_array.put(obj);
                            else
                                current_array.put(obj);
                        }
                        else {
                            Log.e("add_in_met_array","add_in_met_array");
                            if(messageSnapshot.child("completed").getValue().toString().equalsIgnoreCase("true"))
                            met_array.put(obj);
                            else
                                unmet_array.put(obj);
                        }

                        Log.e("arrayinclass",current_array+"");
                    }

                    edit=pref.edit();
                    edit.putString("current_goals",current_array+"");
                    edit.commit();

                    cur_goal_adap=new Current_Goals_adap(getActivity(),current_array);
                    rv_currentgoal.setAdapter(cur_goal_adap);

                    cur_goal_adap=new Current_Goals_adap(getActivity(),met_array);
                    rv_metgoal.setAdapter(cur_goal_adap);


                    cur_goal_adap=new Current_Goals_adap(getActivity(),unmet_array);
                    rv_unmetgoal.setAdapter(cur_goal_adap);


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("array_exception","e",e);
                }

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });

        return current_array;
    }

    public String Check_enddate(String enddate){

        String ret_value="";

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm a");

        String currentDate = formatter.format(new Date());

        try {
            Date end_d = (Date)formatter.parse(enddate);
            Date current_d = (Date)formatter.parse(currentDate);
            Log.e("date_start",current_d+"");
            Log.e("date_end",end_d+"");

            int cur_to_end=end_d.compareTo(current_d);

            if(cur_to_end>0){
                ret_value="current";
                Log.e("end_date_is ","greater than cur_date");
            }
            else {
                ret_value="met";
                Log.e("currnet_date_is", "greater than end_date");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret_value;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("NEW");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new Add_New_Goal()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
