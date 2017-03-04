package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.ContactActivityListAdapter;
import Adapter.Note_Adapter;
import Adapter.SelectNewActivityListAdapter;
import model.AllActivity;
import model.AllNote;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Activity_list_frag extends Fragment {

    View v;
    TextView tv_newactivity;
   public static Dialog dialog;
    RecyclerView rview;
    SelectNewActivityListAdapter adapter;
    ArrayList<AllActivity> data;
    Firebase mref;
    SharedPreferences pref;

  public static   ContactActivityListAdapter listadapter;
    LinearLayoutManager lManager;
    String uid="",name="";
    public static JSONArray js;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.activity_list_frag,container,false);

        lManager=new LinearLayoutManager(getActivity());

        rview=(RecyclerView)v.findViewById(R.id.rview);

        js=new JSONArray();


        name= getArguments().getString("givenName");

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        uid=pref.getString("uid","");


        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        rview.setLayoutManager(lManager);
        listadapter=new ContactActivityListAdapter(getActivity(),js,"simple");
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

        getnotefromfirebase();

        return v;
    }


    public void getnotefromfirebase()
    {
        mref.child("events")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllActivity>();
                JSONArray jsonArray =  new JSONArray();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    JSONObject jGroup = new JSONObject();
                    Log.e("childddd",child.child("contactName").getKey()+" abc");
                    data.add(new AllActivity(child.child("contactName").getValue().toString(),child.child("contactRef").getValue().toString(),child.child("created").getValue().toString(),child.child("date").getValue().toString(),child.child("eventKitID").getValue().toString(),child.child("ref").getValue().toString(),child.child("type").getValue().toString(),child.child("userName").getValue().toString(),child.child("userRef").getValue().toString()));
                    try {
                        jGroup.put("contactName", child.child("contactName").getValue().toString());
                        jGroup.put("contactRef", child.child("contactRef").getValue().toString());
                        jGroup.put("created", child.child("created").getValue().toString());
                        jGroup.put("date", child.child("date").getValue().toString());
                        jGroup.put("eventKitID", child.child("eventKitID").getValue().toString());
                        jGroup.put("ref", child.child("ref").getValue().toString());
                        jGroup.put("type", child.child("type").getValue().toString());
                        jGroup.put("userName", child.child("userName").getValue().toString());
                        jGroup.put("userRef", child.child("userRef").getValue().toString());
                        jsonArray.put(jGroup);
                        Log.e("child", child.child("contactName").getValue() + " abc");
                    }
                    catch (Exception e)
                    {
                        Log.e("Exception",e+"");
                    }
                }

                Log.e("jsonarray",jsonArray+" abc");
                listadapter=new ContactActivityListAdapter(getActivity(),jsonArray,"all");
                rview.setLayoutManager(lManager);
                rview.setAdapter(listadapter);







            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }
}
