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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.RVPRequestAdapter;
import model.RVPRequest;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 09-03-2017.
 */
public class PendingRequestFragment extends Fragment
{
    View view;
    RecyclerView rView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<RVPRequest> rvpRequests;
    RVPRequestAdapter adapter;
    StorageReference storageRef;
    ArrayList<JSONObject> data;
    Firebase mref;
    SharedPreferences pref;
    Map map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.rvp_requests,container,false);
        setHasOptionsMenu(true);
        HomeActivity.title.setText("Pending Requests");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rView=(RecyclerView)view.findViewById(R.id.rview);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        rView.setLayoutManager(linearLayoutManager);
        Firebase.setAndroidContext(getActivity());
        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);
        getRVPRequestsfromFirebase();

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
        menu.findItem(R.id.menu).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getRVPRequestsfromFirebase() {
      rvpRequests =new ArrayList<RVPRequest>();

        Log.e("soltn no",pref.getString("solution_number",""));

        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("Pending Requests").child("RVP Requests").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                 map=new HashMap() ;
                   map= new HashMap<String, String>();
                   data=new ArrayList<JSONObject>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        JSONObject obj=new JSONObject();
                        try {
                            obj.put("key",child.getKey().toString());
                            obj.put("ref",child.child("ref").getValue());
                            obj.put("RVPsoltn_number",child.child("userRVPSolutionNumber").getValue());
                            obj.put("solution_number",child.child("userSolutionNumber").getValue());
                            obj.put("uid",child.child("userUID").getValue());
                            obj.put("upline_soltn_number",child.child("userUplineSolutionNumber").getValue());
                            obj.put("username",child.child("username").getValue());
                        data.add(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("excptn","e",e);
                        }
                }


                adapter=new RVPRequestAdapter(getActivity(),data);
                rView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


}
