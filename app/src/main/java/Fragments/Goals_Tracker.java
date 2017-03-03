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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.goal_tracker,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        rv_currentgoal=(RecyclerView)v.findViewById(R.id.rv_currentgoal);
        rv_metgoal=(RecyclerView)v.findViewById(R.id.rv_metgoals);
        rv_unmetgoal=(RecyclerView)v.findViewById(R.id.rv_unmetgoals);

        rv_currentgoal.setNestedScrollingEnabled(true);
        rv_metgoal.setNestedScrollingEnabled(true);
        rv_unmetgoal.setNestedScrollingEnabled(true);

        rv_currentgoal.setLayoutManager(new LinearLayoutManager(getActivity()));

//        rv_unmetgoal.setLayoutManager(new LinearLayoutManager(getActivity()));
//        cur_goal_adap=new Current_Goals_adap(getActivity(),);
//        rv_unmetgoal.setAdapter(cur_goal_adap);


        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);

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

        final JSONArray array=new JSONArray();
        Log.e("uid",pref.getString("uid",""));
        mref.child("users").child(pref.getString("uid","")).child("Goals").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        String key=messageSnapshot.getValue()+"";
                          JSONObject obj=new JSONObject();
                          JSONObject obj1=new JSONObject();

                        obj.put("completed",messageSnapshot.child("completed").getValue());
                        obj.put("ref",messageSnapshot.child("ref").getValue());
                        obj.put("title",messageSnapshot.child("title").getValue());
                        obj.put("startDate",messageSnapshot.child("startDate").getValue());
                        obj.put("endDate",messageSnapshot.child("endDate").getValue());
                        obj.put("finished",messageSnapshot.child("finished").getValue());
//                        DataSnapshot activitycount= (DataSnapshot) messageSnapshot.child("activityCount").getValue();

//                        Object activity=messageSnapshot.child("activityCount").getValue();
                        Map<String, Object> activitycount = (Map<String, Object>) messageSnapshot.child("activityCount").getValue();
                        obj1.put("Appointment Set",activitycount.get("Appointments Set").toString());
                        obj1.put("Closed Life",activitycount.get("Closed Life").toString());
                        obj1.put("Went on KT",activitycount.get("Went on KT").toString());
                        obj1.put("Total Premium",activitycount.get("Total Premium").toString());
                        obj1.put("Closed Other Business",activitycount.get("Closed Other Business").toString());
                        obj1.put("Contacts Added",activitycount.get("Contacts Added").toString());
                        obj1.put("Closed IBA",activitycount.get("Closed IBA").toString());
                        obj.put("activityCount",obj1);
                        Log.e("value",obj+"");
                        array.put(obj);
                        Log.e("arrayinclass",array+"");
                    }

                    edit=pref.edit();
                    edit.putString("current_goals",array+"");
                    edit.commit();

                    cur_goal_adap=new Current_Goals_adap(getActivity(),array);
                    rv_currentgoal.setAdapter(cur_goal_adap);

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

        return array;

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
