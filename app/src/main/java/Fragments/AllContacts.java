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
import android.text.TextUtils;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    String role="client",uid="",uidd="",Ten_new_contacts_added="false",Thirty_new_contacts_added="false",Twenty_new_contacts_added="false";
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
//        try {
//            uidd = getArguments().getString("uid");
//            Log.e("beforeuidd",uidd+" abv");
//            if (uidd.length() > 1) {
//                uid = uidd;
//            } else {
                uid = pref.getString("uid", "");
//                Log.e("after",uidd+" abv");
//            }
//        }catch (Exception e)
//        {
//            Log.e("Exception",e.getMessage());
//        }
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
        mref.child("contacts").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("getdatauidd",uid+" abv");
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllContact>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                   Log.e("child",child+" abc");
                    try {
                        data.add(new AllContact(ConvertParseString(child.child("competitive").getValue()), ConvertParseString(child.child("created").getValue()), ConvertParseString(child.child("credible").getValue()), ConvertParseString(child.child("familyName").getValue()), ConvertParseString(child.child("givenName").getValue()), ConvertParseString(child.child("hasKids").getValue()),
                                ConvertParseString(child.child("homeowner").getValue()), ConvertParseString(child.child("hungry").getValue()), ConvertParseString(child.child("incomeOver40k").getValue()), ConvertParseString(child.child("married").getValue()), ConvertParseString(child.child("motivated").getValue()), ConvertParseString(child.child("ofProperAge").getValue()), ConvertParseString(child.child("peopleSkills").getValue()),
                                ConvertParseString(child.child("phoneNumber").getValue()), String.valueOf(ConvertParseInteger(child.child("rating").getValue())), String.valueOf(ConvertParseInteger(child.child("recruitRating").getValue())), ConvertParseString(child.child("ref").getValue())));

                        Log.e("child", child.child("familyName").getValue() + " abc");
                    }
                    catch(Exception e)
                    {
                        Log.e("Exception",e.getMessage());
                    }
                }
                int size=data.size();
                    if(size==10)
                    {
                        putcontactcountinfirebase("10");
                    }
                    else if(size==20)
                    {
                        putcontactcountinfirebase("20");
                    }
                    else if(size==30)
                    {
                        putcontactcountinfirebase("30");
                    }



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
            String lastSeen= (String) obj;
            if (lastSeen != null && !TextUtils.isEmpty(lastSeen) && !lastSeen.equalsIgnoreCase("null"))
                return lastSeen;
            else
                return "";
        }

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

    public void putcontactcountinfirebase(final String str)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (str.equals("10"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Ten_new_contacts_added").getValue() + " abc");
                    Ten_new_contacts_added = dataSnapshot.child("Ten_new_contacts_added").getValue().toString();
                    if (Ten_new_contacts_added.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Ten_new_contacts_added", "true");
                        newcontact.put("Ten_new_contacts_added_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                        setAdapter();
                    }
                }
                else if (str.equals("20"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Twenty_new_contacts_added").getValue() + " abc");
                    Twenty_new_contacts_added = dataSnapshot.child("Twenty_new_contacts_added").getValue().toString();
                    if (Twenty_new_contacts_added.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Twenty_new_contacts_added", "true");
                        newcontact.put("Twenty_new_contacts_added_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);

                        setAdapter();
                    }
                }
                else if (str.equals("30"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Thirty_new_contacts_added").getValue() + " abc");
                    Thirty_new_contacts_added = dataSnapshot.child("Thirty_new_contacts_added").getValue().toString();
                    if (Thirty_new_contacts_added.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Thirty_new_contacts_added", "true");
                        newcontact.put("Thirty_New_contacts_added_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);

                        setAdapter();
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }





    public void setAdapter()
    {
        adapter=new ClientAdapter(getActivity(),data,role);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
    }


}
