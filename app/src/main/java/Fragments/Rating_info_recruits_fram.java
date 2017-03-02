package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Rating_info_recruits_fram extends Fragment {

    View v;
    TextView tv_rating,tv_married,tv_haskids,tv_aged,tv_homeowner,tv_income;
    ImageView im_marriedcheck,im_marriedcross,im_haskidscheck,im_haskidscross,im_agecheck,im_agecross,im_homecheck,im_homecross,im_incomecheck,im_incomecross;
    int a=0;
    boolean b,c,d,e,f;
    String competitive,created,credible,familyName,givenName,hasKids,homeowner,hungry,incomeOver40k,married,motivated,ofProperAge,peopleSkills,phoneNumber,ref;
    int rating,recruitRating;
    Firebase mref;
    SharedPreferences pref;
    String uid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.rating_clients_frag,container,false);
        setHasOptionsMenu(true);

        Firebase.setAndroidContext(getActivity());
//        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);
        uid=pref.getString("uid","");

        tv_rating=(TextView)v.findViewById(R.id.tv_rating);
        tv_married=(TextView)v.findViewById(R.id.tv_married);
        tv_haskids=(TextView)v.findViewById(R.id.tv_haskids);
        tv_aged=(TextView)v.findViewById(R.id.tv_aged);
        tv_homeowner=(TextView)v.findViewById(R.id.tv_homeowner);
        tv_income=(TextView)v.findViewById(R.id.tv_income);

        im_marriedcheck=(ImageView)v.findViewById(R.id.marriedcheck);
        im_marriedcross=(ImageView)v.findViewById(R.id.marriedcross);
        im_haskidscheck=(ImageView)v.findViewById(R.id.haskidscheck);
        im_haskidscross=(ImageView)v.findViewById(R.id.haskidscross);
        im_agecheck=(ImageView)v.findViewById(R.id.agecheck);
        im_agecross=(ImageView)v.findViewById(R.id.agecross);
        im_homecheck=(ImageView)v.findViewById(R.id.homeownercheck);
        im_homecross=(ImageView)v.findViewById(R.id.homeownercross);
        im_incomecheck=(ImageView)v.findViewById(R.id.incomecheck);
        im_incomecross=(ImageView)v.findViewById(R.id.incomecross);



        competitive = getArguments().getString("competitive");
        created = getArguments().getString("created");
        credible = getArguments().getString("credible");
        familyName = getArguments().getString("familyName");
        givenName = getArguments().getString("givenName");
        hasKids = getArguments().getString("hasKids");
        homeowner = getArguments().getString("homeowner");
        hungry = getArguments().getString("hungry");
        incomeOver40k = getArguments().getString("incomeOver40k");
        married = getArguments().getString("married");
        motivated = getArguments().getString("motivated");
        ofProperAge = getArguments().getString("ofProperAge");
        peopleSkills = getArguments().getString("peopleSkills");
        phoneNumber = getArguments().getString("phoneNumber");
        ref = getArguments().getString("ref");
        rating = Integer.parseInt(getArguments().getString("rating"));
        recruitRating =Integer.parseInt( getArguments().getString("recruitRating"));

        if(recruitRating>0)
        {
            a=recruitRating;
            tv_rating.setText("Recruit Rating: " + a);
            if(married.equalsIgnoreCase("false"))
            {
                im_marriedcheck.setImageResource(R.drawable.checkgray32);
                im_marriedcross.setImageResource(R.drawable.closered32);
                b=false;

            }
            else
            {
                im_marriedcheck.setImageResource(R.drawable.checkgreen32);
                im_marriedcross.setImageResource(R.drawable.closegray32);
                b=true;
            }
            if(hasKids.equalsIgnoreCase("false"))
            {
                im_haskidscheck.setImageResource(R.drawable.checkgray32);
                im_haskidscross.setImageResource(R.drawable.closered32);
                c=false;
            }
            else
            {
                im_haskidscheck.setImageResource(R.drawable.checkgreen32);
                im_haskidscross.setImageResource(R.drawable.closegray32);
                c=true;
            }
            if(ofProperAge.equalsIgnoreCase("false"))
            {
                im_agecheck.setImageResource(R.drawable.checkgray32);
                im_agecross.setImageResource(R.drawable.closered32);
                d=false;
            }
            else
            {
                im_agecheck.setImageResource(R.drawable.checkgreen32);
                im_agecross.setImageResource(R.drawable.closegray32);
                d=true;
            }
            if(homeowner.equalsIgnoreCase("false"))
            {
                im_homecheck.setImageResource(R.drawable.checkgray32);
                im_homecross.setImageResource(R.drawable.closered32);
                e=false;
            }
            else
            {
                im_homecheck.setImageResource(R.drawable.checkgreen32);
                im_homecross.setImageResource(R.drawable.closegray32);
                e=true;
            }
            if(incomeOver40k.equalsIgnoreCase("false"))
            {
                im_incomecheck.setImageResource(R.drawable.checkgray32);
                im_incomecross.setImageResource(R.drawable.closered32);
                f=false;
            }
            else
            {
                im_incomecheck.setImageResource(R.drawable.checkgreen32);
                im_incomecross.setImageResource(R.drawable.closegray32);
                f=true;
            }
        }







        im_marriedcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b)
                {}
                else {
                    im_marriedcheck.setImageResource(R.drawable.checkgreen32);
                    im_marriedcross.setImageResource(R.drawable.closegray32);
                    a = a + 1;
                    tv_rating.setText("Recruit Rating: " + a);
                    b=true;

                    Log.e("check",uid+" "+givenName+" abc");
                    Map newData = new HashMap();
                    newData.put("married","true");
                    newData.put("rating",a);
                    newData.put("recruitRating",a);
                    mref.child("contacts").child(uid).child(givenName).updateChildren(newData);


                }
            }
        });
        im_marriedcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b)
                    im_marriedcheck.setImageResource(R.drawable.checkgray32);
                im_marriedcross.setImageResource(R.drawable.closered32);
                if(a>0) {
                    a = a - 1;
                    tv_rating.setText("Recruit Rating: " + a);
                    b=false;

                    Map newData = new HashMap();
                    newData.put("married","false");
                    newData.put("rating",a);
                    newData.put("recruitRating",a);
                    mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                }
            }
        });
        im_haskidscheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c)
                {}
                else {
                    im_haskidscheck.setImageResource(R.drawable.checkgreen32);
                    im_haskidscross.setImageResource(R.drawable.closegray32);
                    a = a + 1;
                    tv_rating.setText("Recruit Rating: " + a);
                    c=true;

                    Map newData = new HashMap();
                    newData.put("hasKids","true");
                    newData.put("rating",a);
                    newData.put("recruitRating",a);
                    mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                }
            }
        });
        im_haskidscross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c) {
                    im_haskidscheck.setImageResource(R.drawable.checkgray32);
                    im_haskidscross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        c=false;

                        Map newData = new HashMap();
                        newData.put("hasKids","false");
                        newData.put("rating",a);
                        newData.put("recruitRating",a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);

                    }
                }
            }
        });
        im_agecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d)
                {}
                else {
                    im_agecheck.setImageResource(R.drawable.checkgreen32);
                    im_agecross.setImageResource(R.drawable.closegray32);
                    a = a + 1;
                    tv_rating.setText("Recruit Rating: " + a);
                    d=true;


                    Map newData = new HashMap();
                    newData.put("ofProperAge","true");
                    newData.put("rating",a);
                    newData.put("recruitRating",a);
                    mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                }
            }
        });
        im_agecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d) {
                    im_agecheck.setImageResource(R.drawable.checkgray32);
                    im_agecross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        d=false;

                        Map newData = new HashMap();
                        newData.put("ofProperAge","false");
                        newData.put("rating",a);
                        newData.put("recruitRating",a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);

                    }
                }
            }
        });
        im_homecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e) {
                } else {
                    im_homecheck.setImageResource(R.drawable.checkgreen32);
                    im_homecross.setImageResource(R.drawable.closegray32);
                    a = a + 1;
                    tv_rating.setText("Recruit Rating: " + a);
                    e=true;

                    Map newData = new HashMap();
                    newData.put("homeowner","true");
                    newData.put("rating",a);
                    newData.put("recruitRating",a);
                    mref.child("contacts").child(uid).child(givenName).updateChildren(newData);

                }
            }
        });
        im_homecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e) {
                    im_homecheck.setImageResource(R.drawable.checkgray32);
                    im_homecross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        e=false;

                        Map newData = new HashMap();
                        newData.put("homeowner","false");
                        newData.put("rating",a);
                        newData.put("recruitRating",a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                    }
                }
            }
        });
        im_incomecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f)
                {}
                else {
                    im_incomecheck.setImageResource(R.drawable.checkgreen32);
                    im_incomecross.setImageResource(R.drawable.closegray32);
                    a = a + 1;
                    tv_rating.setText("Recruit Rating: " + a);
                    f=true;


                    Map newData = new HashMap();
                    newData.put("incomeOver40k","true");
                    newData.put("rating",a);
                    newData.put("recruitRating",a);
                    mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                }
            }
        });
        im_incomecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f) {
                    im_incomecheck.setImageResource(R.drawable.checkgray32);
                    im_incomecross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        f=false;

                        Map newData = new HashMap();
                        newData.put("incomeOver40k","false");
                        newData.put("rating",a);
                        newData.put("recruitRating",a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                    }
                }
            }
        });


        return v;
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
}
