package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import interfaces.ISelectNewActivity;
import model.AllActivity;
import model.AllNote;
import u.activitymanager.APPListener;
import u.activitymanager.HomeActivity;
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
    ArrayList<AllActivity> data;
    Firebase mref;
    SharedPreferences pref;

  public static   ContactActivityListAdapter listadapter;
    LinearLayoutManager lManager;
    String uid="",name="",uidd="";
    public static JSONArray js;
    JSONArray jsonArray;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("All Activity");



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
                .child(uid)
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllActivity>();
                jsonArray =  new JSONArray();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    JSONObject jGroup = new JSONObject();
                    Log.e("childddd",child.child("contactName").getKey()+" abc");
                    data.add(new AllActivity(ConvertParseString(child.child("contactName").getValue()),ConvertParseString(child.child("contactRef").getValue()),ConvertParseString(child.child("created").getValue()),ConvertParseString(child.child("date").getValue()),ConvertParseString(child.child("eventKitID").getValue()),ConvertParseString(child.child("ref").getValue()),ConvertParseString(child.child("type").getValue()),ConvertParseString(child.child("userName").getValue()),ConvertParseString(child.child("userRef").getValue())));
                    try {
                        jGroup.put("contactName", ConvertParseString(child.child("contactName").getValue()));
                        jGroup.put("contactRef", ConvertParseString(child.child("contactRef").getValue()));
                        jGroup.put("created", ConvertParseString(child.child("created").getValue()));
                        jGroup.put("date", ConvertParseString(child.child("date").getValue()));
                        jGroup.put("eventKitID", ConvertParseString(child.child("eventKitID").getValue()));
                        jGroup.put("ref", ConvertParseString(child.child("ref").getValue()));
                        jGroup.put("type", ConvertParseString(child.child("type").getValue()));
                        jGroup.put("userName", ConvertParseString(child.child("userName").getValue()));
                        jGroup.put("userRef", ConvertParseString(child.child("userRef").getValue()));
                        jsonArray.put(jGroup);
                        Log.e("child", ConvertParseString(child.child("contactName").getValue()) + " abc");
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



    public static String ConvertParseString(Object obj ) {
        if(obj==null)
        {
            return "";
        }
        else {
            String lastSeen= String.valueOf(obj);
            if (lastSeen != null && !TextUtils.isEmpty(lastSeen) && !lastSeen.equalsIgnoreCase("null"))
                return lastSeen;
            else
                return "";
        }

    }

    @Override
    public void OnRefreshListener(JSONObject object) {
        jsonArray.put(object);


        adapter.notifyDataSetChanged();
        Log.e("chal","ja bhai");
    }
}
