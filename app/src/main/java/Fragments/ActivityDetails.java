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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.ActivityDetailAdapter;
import Adapter.ContactActivityListAdapter;
import model.AllActivity;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 18-02-2017.
 */
public class ActivityDetails extends Fragment
{
    ActivityDetailAdapter adapter;
    RecyclerView rview;
    View view;
    private Firebase mref;
    private SharedPreferences pref;

    String uid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_detail,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);


        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        uid=pref.getString("uid","");
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        rview=(RecyclerView)view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(layoutManager);
        adapter=new ActivityDetailAdapter();
        rview.setAdapter(adapter);
        HomeActivity.title.setText("Activity");

        getnotefromfirebase();
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.drawable.calendar);
        menu.findItem(R.id.list).setVisible(true);
        menu.findItem(R.id.list).setIcon(R.mipmap.filter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                // getActivity().getSupportFragmentManager().popBackStack();
//                showHelpDialog();
                break;

            case R.id.menu:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new ActivityFragments()).addToBackStack(null).commit();
                break;

            case R.id.list:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new Chart_Filter_Frag()).addToBackStack(null).commit();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    public void getnotefromfirebase()
    {
        mref.child("events")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server",dataSnapshot.getValue()+" data");

                        JSONArray jsonArray =  new JSONArray();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            JSONObject jGroup = new JSONObject();
                            Log.e("childddd",child.child("contactName").getKey()+" abc");
                            //alue().toString(),child.child("created").getValue().toString(),child.child("date").getValue().toString(),child.child("eventKitID").getValue().toString(),child.child("ref").getValue().toString(),child.child("type").getValue().toString(),child.child("userName").getValue().toString(),child.child("userRef").getValue().toString()));
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








                    }
                    @Override
                    public void onCancelled(FirebaseError error) {
                        Log.e("get data error",error.getMessage()+" data");
                    }
                });
    }
}




