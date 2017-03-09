package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import Adapter.Achivement_Adap;
import Adapter.ContactActivityListAdapter;
import Adapter.Current_Goals_adap;
import Adapter.Current_challanges;
import Adapter.Past_challanges;
import model.AllActivity;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Challenges extends Fragment
{
    View view;
    TextView toolbar_new;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Firebase mref;
    JSONArray array;
    RecyclerView recyclerView;
    String rvp_solutionnumber,uplinesolution_no;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      view=inflater.inflate(R.layout.challanges,container,false);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("");

        setHasOptionsMenu(true);

        pref=getActivity().getSharedPreferences("userpref",0);

        rvp_solutionnumber=pref.getString("rvp_solution_number","");
        uplinesolution_no=pref.getString("upline_solution_number","");

        Log.e("upline_solution_number",uplinesolution_no+" up");
        Log.e("rvp_solution_number",rvp_solutionnumber+" rvp");

        Firebase.setAndroidContext(getActivity());
        mref=new Firebase(Constants.URL);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        array=new JSONArray();

        initViews();

        return view;
    }

    private void initViews(){

        recyclerView = (RecyclerView)view.findViewById(R.id.current_recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView recyclerView1 = (RecyclerView)view.findViewById(R.id.past_recyle);
        recyclerView1.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);
        // recyclerView1.setNestedScrollingEnabled(false);

        Past_challanges adapter1 = new Past_challanges(getActivity());
        recyclerView1.setAdapter(adapter1);

        getchallangefromfirebase();

    }

    public void getchallangefromfirebase()
    {
        mref.child("Solution Numbers")
                .child(pref.getString("solution_number","")).child("contests")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server",dataSnapshot.getValue()+" data");

                        try {

                            for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                                String key=messageSnapshot.getValue()+"";
                                JSONObject participants_obj=new JSONObject();
                                JSONObject activitycount_obj=new JSONObject();
                                JSONObject includedactivity_obj=new JSONObject();
                                JSONObject obj=new JSONObject();

                                obj.put("created",messageSnapshot.child("created").getValue());
                                obj.put("currentLeader",messageSnapshot.child("currentLeader").getValue());
                                obj.put("endDate",messageSnapshot.child("endDate").getValue());
                                obj.put("finished",messageSnapshot.child("finished").getValue());
                                obj.put("finishedDate",messageSnapshot.child("finishedDate").getValue());
                                obj.put("hasTimeLimit",messageSnapshot.child("hasTimeLimit").getValue());

                                obj.put("ref",messageSnapshot.child("ref").getValue());
                                obj.put("reward",messageSnapshot.child("reward").getValue());
                                obj.put("startDate",messageSnapshot.child("startDate").getValue());
                                obj.put("title",messageSnapshot.child("title").getValue());
                                obj.put("winner",messageSnapshot.child("winner").getValue());

                                Map<String, Object> activitycount = (Map<String, Object>) messageSnapshot.child("activityCount").getValue();
                                activitycount_obj.put("Appointment Set",activitycount.get("Appointments Set").toString());
                                activitycount_obj.put("Closed IBA",activitycount.get("Closed IBA").toString());
                                activitycount_obj.put("Closed Life",activitycount.get("Closed Life").toString());
                                activitycount_obj.put("Closed Other Business",activitycount.get("Closed Other Business").toString());
                                activitycount_obj.put("Contacts Added",activitycount.get("Contacts Added").toString());
                                activitycount_obj.put("Total Premium",activitycount.get("Total Premium").toString());
                                activitycount_obj.put("Went on KT",activitycount.get("Went on KT").toString());

                                Map<String, Object> included_activity = (Map<String, Object>) messageSnapshot.child("includedActivity").getValue();
                                includedactivity_obj.put("Appointment Set",included_activity.get("Appointments Set").toString());
                                includedactivity_obj.put("Closed IBA",included_activity.get("Closed IBA").toString());
                                includedactivity_obj.put("Closed Life",included_activity.get("Closed Life").toString());
                                includedactivity_obj.put("Closed Other Business",included_activity.get("Closed Other Business").toString());
                                includedactivity_obj.put("Contacts Added",included_activity.get("Contacts Added").toString());
                                includedactivity_obj.put("Total Premium",included_activity.get("Total Premium").toString());
                                includedactivity_obj.put("Went on KT",included_activity.get("Went on KT").toString());

                                obj.put("activityCount",activitycount_obj);
                                obj.put("includedActivity",includedactivity_obj);

                                Log.e("value",obj+"");
                                array.put(obj);
                                Log.e("arrayinclass",array+"");
                            }

                            Current_challanges adapter = new Current_challanges(getActivity(),array);
                            recyclerView.setAdapter(adapter);


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
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if(rvp_solutionnumber.equalsIgnoreCase("")&uplinesolution_no.equalsIgnoreCase("")) {
            menu.findItem(R.id.menu).setIcon(null);
            menu.findItem(R.id.menu).setTitle("NEW");
        }
        else
            menu.findItem(R.id.menu).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId())
          {
              case R.id.menu:
      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                  break;
              case android.R.id.home:
                  getActivity().getSupportFragmentManager().popBackStack();
                  break;
          }
        return super.onOptionsItemSelected(item);

    }
}
