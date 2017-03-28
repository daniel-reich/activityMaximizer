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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

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
    EditText search_contact;
    String role="client",uid="",uidd="",Ten_new_contacts_added="false",Thirty_new_contacts_added="false",Twenty_new_contacts_added="false";
    private JSONObject obj;

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
        search_contact=(EditText)view.findViewById(R.id.edt_searchcontact);
       // rView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(getActivity());

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
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



        search_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                if(String.valueOf(cs).length()>0) {
                    Log.e("Hello", "Hello");
//                        adapter = new Listing_Adapter(data);
                    adapter.filter(cs.toString().toLowerCase());
//
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
       // menu.findItem(R.id.menu).setIcon(R.mipmap.sort);
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
                        data.add(new AllContact(String.valueOf(child.child("competitive").getValue()), String.valueOf(child.child("created").getValue()), String.valueOf(child.child("credible").getValue()), String.valueOf(child.child("familyName").getValue()), String.valueOf(child.child("givenName").getValue()), String.valueOf(child.child("hasKids").getValue()),
                                String.valueOf(child.child("homeowner").getValue()), String.valueOf(child.child("hungry").getValue()), String.valueOf(child.child("incomeOver40k").getValue()), String.valueOf(child.child("married").getValue()), String.valueOf(child.child("motivated").getValue()), String.valueOf(child.child("ofProperAge").getValue()), String.valueOf(child.child("peopleSkills").getValue()),
                                String.valueOf(child.child("phoneNumber").getValue()), String.valueOf(ConvertParseInteger(child.child("rating").getValue())), String.valueOf(ConvertParseInteger(child.child("recruitRating").getValue())), String.valueOf(child.child("ref").getValue())));

                        Log.e("child", child.child("familyName").getValue() + " abc");
                    }
                    catch(Exception e)
                    {
                        Log.e("Exception",e.getMessage());
                    }
                }
                int size=data.size();
                Log.e("sizee",size+" abcc");
                    if(size>=10 && size<20)
                    {
                        putcontactcountinfirebase("10",role);
                    }
                    else if(size>=20 && size<30)
                    {
                        putcontactcountinfirebase("20",role);
                    }
                    else if(size>=30)
                    {
                        putcontactcountinfirebase("30",role);
                    }
                    else
                    {
                        setAdapter(role);
                    }



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

    public void putcontactcountinfirebase(final String str, final String role)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                try {
                    obj = new JSONObject(dataSnapshot.getValue() + "");
                }
                catch (Exception e)
                {

                }
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
                        try {
                            obj.put("Ten_new_contacts_added", "true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                        setAdapter(role);

                        Achivement_Details frag = new Achivement_Details();
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 12);
                        newcontact.put("Ten_new_contacts_added_date", timestamp);
                        bundle.putString("data", obj + "");
                        frag.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_layout, frag).addToBackStack(null).commit();
                        
                    }
                    else
                    {
                        setAdapter(role);
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


                        Achivement_Details frag = new Achivement_Details();
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 15);

                        try {
                            obj.put("Twenty_new_contacts_added", "true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bundle.putString("data", obj + "");
                        frag.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_layout, frag).addToBackStack(null).commit();

                        setAdapter(role);
                    }
                    else
                    {
                        setAdapter(role);
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

                        try {
                            obj.put("Thirty_new_contacts_added", "true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Achivement_Details frag = new Achivement_Details();
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 13);
                        newcontact.put("Thirty_new_contacts_added_date", timestamp);
                        bundle.putString("data", obj + "");
                        frag.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_layout, frag).addToBackStack(null).commit();

                        setAdapter(role);
                    }
                    else
                    {
                        setAdapter(role);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }





    public void setAdapter(String role)
    {
        adapter=new ClientAdapter(getActivity(),data,role);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
    }


}
