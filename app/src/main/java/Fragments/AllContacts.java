package Fragments;

import android.app.Dialog;
import android.content.Intent;
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
import android.webkit.ClientCertRequest;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import Adapter.ClientAdapter;
import model.AllContact;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class AllContacts extends Fragment implements View.OnClickListener {

    Dialog helpdialog;
    RecyclerView rView;
    TextView Clients,Recruits;
    View view;
    Firebase mref;
    SharedPreferences pref;
    LinearLayoutManager layoutManager;
    ClientAdapter adapter;
    ArrayList<AllContact> data;
    String role="client";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.all_contacts,container,false);
        setHasOptionsMenu(true);
        Clients=(TextView)view.findViewById(R.id.tv_clients);
        Recruits=(TextView)view.findViewById(R.id.tv_recruits);
        Clients.setOnClickListener(this);
        Recruits.setOnClickListener(this);
        Clients.setSelected(true);
        Recruits.setSelected(false);
        rView=(RecyclerView)view.findViewById(R.id.rview);
       // rView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(getActivity());

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        getdatafromfirebase("client");

        Log.e("AllContacts","Allcontacts");

                ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("All Contacts");


        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(true);
        menu.findItem(R.id.menu).setIcon(R.mipmap.sort);
        menu.findItem(R.id.list).setVisible(false);
        //   menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new SortByFragment()).addToBackStack(null).commit();
                break;

            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_clients:
                Recruits.setSelected(false);
                Clients.setSelected(true);
                getdatafromfirebase("client");
                break;
            case R.id.tv_recruits:
                Clients.setSelected(false);
                Recruits.setSelected(true);
                getdatafromfirebase("recruits");
                break;
        }
    }

    public void getdatafromfirebase(final String role)
    {
        mref.child("contacts").child(pref.getString("uid","")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllContact>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                   Log.e("child",child+" abc");
                    data.add(new AllContact(child.child("competitive").getValue().toString(),child.child("created").getValue().toString(),child.child("credible").getValue().toString(),child.child("familyName").getValue().toString(),child.child("givenName").getValue().toString(),child.child("hasKids").getValue().toString(),
                            child.child("homeowner").getValue().toString(),child.child("hungry").getValue().toString(),child.child("incomeOver40k").getValue().toString(),child.child("married").getValue().toString(),child.child("motivated").getValue().toString(),child.child("ofProperAge").getValue().toString(),child.child("peopleSkills").getValue().toString(),
                            child.child("phoneNumber").getValue().toString(),child.child("rating").getValue().toString(),child.child("recruitRating").getValue().toString(),child.child("ref").getValue().toString()));

                    Log.e("child",child.child("familyName").getValue()+" abc");
                }
                adapter=new ClientAdapter(getActivity(),data,role);
                rView.setLayoutManager(layoutManager);
                rView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


}
