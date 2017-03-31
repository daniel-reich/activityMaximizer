package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import Adapter.ContactActivityListAdapter;
import Adapter.SelectNewActivityListAdapter;
import interfaces.ISelectNewActivity;
import u.activitymanager.APPListener;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Activity_list_frag extends Fragment implements ISelectNewActivity {

    View v;
    TextView tv_newactivity;
   public static Dialog dialog;
    RecyclerView rview;
    SelectNewActivityListAdapter adapter;

    Firebase mref;
    SharedPreferences pref;

  public static ContactActivityListAdapter listadapter;
    LinearLayoutManager lManager;
    String uid="",name="",uidd="";
    JSONArray js;
    public static  JSONArray jsonArray;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        APPListener.getInstance().SetIAAPermissionListener(this);
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.activity_list_frag,container,false);

        lManager=new LinearLayoutManager(getActivity());

        rview=(RecyclerView)v.findViewById(R.id.rview);

        js=new JSONArray();


        name= getArguments().getString("givenName");

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("All Activity");

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

        rview.setLayoutManager(lManager);
        listadapter=new ContactActivityListAdapter(getActivity(),js,"simple",name);
        rview.setAdapter(listadapter);
        tv_newactivity=(TextView)v.findViewById(R.id.tv_newactivity);
        tv_newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.select_new_activity_dialog);
                rview=(RecyclerView)dialog.findViewById(R.id.r_view);
                lManager=new LinearLayoutManager(getActivity());
                rview.setLayoutManager(lManager);
                adapter=new SelectNewActivityListAdapter(getActivity());
                rview.setAdapter(adapter);
                dialog.show();
            }
        });
//        try {
//            uidd = getArguments().getString("uid");
//            Log.e("uidd",uidd+" abv");
//            if (uidd.length() > 1) {
//                uid = uidd;
//                tv_newactivity.setVisibility(View.INVISIBLE);
//            } else {
                uid = pref.getString("uid", "");
//            }
//        }catch (Exception e)
//        {
//            Log.e("Exception",e.getMessage());
//        }

        getnotefromfirebase();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        APPListener.getInstance().RemoveIAAPermissionRefreshListener(this);
    }

    public void getnotefromfirebase()
    {


        mref.child("events")
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("get data from server",dataSnapshot.getValue()+" data");

                jsonArray =  new JSONArray();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    JSONObject jGroup = new JSONObject();
                    Log.e("childddd",child.getKey()+" abc");
                    if(name.equalsIgnoreCase(String.valueOf(child.child("contactName").getValue())))
                    {
                        //data.add(new AllActivity(String.valueOf(child.child("contactName").getValue()), String.valueOf(child.child("contactRef").getValue()), String.valueOf(child.child("created").getValue()), String.valueOf(child.child("date").getValue()), String.valueOf(child.child("eventKitID").getValue()), String.valueOf(child.child("ref").getValue()), String.valueOf(child.child("type").getValue()), String.valueOf(child.child("userName").getValue()), String.valueOf(child.child("userRef").getValue())));
                        try {
                            jGroup.put("keyid", String.valueOf(child.getKey() + ""));
                            jGroup.put("contactName", String.valueOf(child.child("contactName").getValue()));
                            jGroup.put("contactRef", String.valueOf(child.child("contactRef").getValue()));
                            jGroup.put("created", String.valueOf(child.child("created").getValue()));
                            jGroup.put("date", String.valueOf(child.child("date").getValue()));
                            jGroup.put("eventKitID", String.valueOf(child.child("eventKitID").getValue()));
                            jGroup.put("ref", String.valueOf(child.child("ref").getValue()));
                            jGroup.put("type", String.valueOf(child.child("type").getValue()));
                            jGroup.put("userName", String.valueOf(child.child("userName").getValue()));
                            jGroup.put("userRef", String.valueOf(child.child("userRef").getValue()));
                            jsonArray.put(jGroup);
                            Log.e("child", String.valueOf(child.child("contactName").getValue()) + " abc");
                        } catch (Exception e) {
                            Log.e("Exception", e + "");
                        }
                    }
                }

                Log.e("jsonarray",jsonArray+" abc");
                listadapter=new ContactActivityListAdapter(getActivity(),jsonArray,"all",name);
                rview.setLayoutManager(lManager);
                rview.setAdapter(listadapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }





    @Override
    public void OnRefreshListener(JSONObject object) {
        jsonArray.put(object);


        adapter.notifyDataSetChanged();
    }
}
