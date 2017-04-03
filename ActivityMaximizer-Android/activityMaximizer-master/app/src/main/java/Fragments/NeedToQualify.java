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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import Adapter.ClientAdapter;
import model.AllContact;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/3/2017.
 */
public class NeedToQualify extends Fragment implements View.OnClickListener {

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

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

        getdatafromfirebase("client");

        Log.e("AllContacts","Allcontacts");

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("Need To Qualify");

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
        menu.findItem(R.id.menu).setIcon(R.mipmap.sort);
        menu.findItem(R.id.list).setVisible(false);
        //   menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
//            case R.id.menu:
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new SortByFragment()).addToBackStack(null).commit();
//                break;
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllContact>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("child",child+" abc");//String.valueOf(child.child("competitive").getValue())
                    AllContact allContact = new AllContact(String.valueOf(child.child("competitive").getValue()),String.valueOf(child.child("created").getValue()),String.valueOf(child.child("credible").getValue()),String.valueOf(child.child("familyName").getValue()),String.valueOf(child.child("givenName").getValue()),String.valueOf(child.child("hasKids").getValue()),
                    String.valueOf(child.child("homeowner").getValue()),String.valueOf(child.child("hungry").getValue()),String.valueOf(child.child("incomeOver40k").getValue()),String.valueOf(child.child("married").getValue()),String.valueOf(child.child("motivated").getValue()),String.valueOf(child.child("ofProperAge").getValue()),String.valueOf(child.child("peopleSkills").getValue()),
                            String.valueOf(child.child("phoneNumber").getValue()),String.valueOf(ConvertParseInteger(child.child("rating").getValue())),String.valueOf(ConvertParseInteger(child.child("recruitRating").getValue())),String.valueOf(child.child("ref").getValue()));
                    if(allContact.getRating() == 0){
                        data.add(allContact);
                    }

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



    public static int ConvertParseInteger(Object obj) {
        if(obj==null)
        {
            return 0;
        }
        else {
            Long lastSeen = (Long) obj;
            if (lastSeen != null | lastSeen != 0) {
                String str=String.valueOf(lastSeen);
                return Integer.valueOf(str);
            }
            else
                return 0;
        }
    }


}
