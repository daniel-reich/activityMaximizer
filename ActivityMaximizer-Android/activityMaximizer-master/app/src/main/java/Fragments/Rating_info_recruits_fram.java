package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.AllRatingContact;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Rating_info_recruits_fram extends Fragment
{
    ArrayList<AllRatingContact> data;
    View v;
    TextView tv_rating,tv_married,tv_haskids,tv_aged,tv_homeowner,tv_income;
    ImageView im_hungrycheck,im_hungrycross,im_crediblecheck,im_crediblecross,im_skillscheck,im_skillscross,im_competitivecheck,im_competitvecross,im_motivatecheck,im_motivatecross;
    int a=0,rate=0,rr=0;
    boolean b,c,d,e,f;
    String competitive,created,credible,familyName,givenName,hasKids,homeowner,hungry,incomeOver40k,married,motivated,ofProperAge,peopleSkills,phoneNumber,ref;
    int rating,recruitRating;
    Firebase mref;
    SharedPreferences pref;
    String uid="",Ten_five_point_recruits="";;
    private JSONObject obj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.rating_recurits_frag,container,false);
        setHasOptionsMenu(true);

        Firebase.setAndroidContext(getActivity());
//        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);
        uid=pref.getString("uid","");

        tv_rating=(TextView)v.findViewById(R.id.tv_rating);
        tv_married=(TextView)v.findViewById(R.id.tv_married);
        tv_haskids=(TextView)v.findViewById(R.id.tv_haskids);
        tv_aged=(TextView)v.findViewById(R.id.tv_aged);
        tv_homeowner=(TextView)v.findViewById(R.id.tv_homeowner);
        tv_income=(TextView)v.findViewById(R.id.tv_income);

        im_hungrycheck=(ImageView)v.findViewById(R.id.hungrycheck);
        im_hungrycross=(ImageView)v.findViewById(R.id.hungrycross);
        im_crediblecheck=(ImageView)v.findViewById(R.id.crediblecheck);
        im_crediblecross=(ImageView)v.findViewById(R.id.crediblecross);
        im_skillscheck=(ImageView)v.findViewById(R.id.skillscheck);
        im_skillscross=(ImageView)v.findViewById(R.id.skillscross);
        im_competitivecheck=(ImageView)v.findViewById(R.id.competitivecheck);
        im_competitvecross=(ImageView)v.findViewById(R.id.competitvecross);
        im_motivatecheck=(ImageView)v.findViewById(R.id.motivatecheck);
        im_motivatecross=(ImageView)v.findViewById(R.id.motivatecross);

        givenName = getArguments().getString("givenName");
        ref = getArguments().getString("ref");
        Log.e("uiddddd",uid+",,"+givenName);
        getcontactdatafromfirebase(givenName);

//        competitive = getArguments().getString("competitive");
//        created = getArguments().getString("created");
//        credible = getArguments().getString("credible");
//        familyName = getArguments().getString("familyName");
//        givenName = getArguments().getString("givenName");
//        hasKids = getArguments().getString("hasKids");
//        homeowner = getArguments().getString("homeowner");
//        hungry = getArguments().getString("hungry");
//        incomeOver40k = getArguments().getString("incomeOver40k");
//        married = getArguments().getString("married");
//        motivated = getArguments().getString("motivated");
//        ofProperAge = getArguments().getString("ofProperAge");
//        peopleSkills = getArguments().getString("peopleSkills");
//        phoneNumber = getArguments().getString("phoneNumber");
//        ref = getArguments().getString("ref");
//        rating = Integer.parseInt(getArguments().getString("rating"));
//        recruitRating =Integer.parseInt( getArguments().getString("recruitRating"));

//        if(recruitRating>0)
//        {
//            a=recruitRating;
//            tv_rating.setText("Recruit Rating: " + a);
//            if(hungry.equalsIgnoreCase("false"))
//            {
//                im_hungrycheck.setImageResource(R.drawable.checkgray32);
//                im_hungrycross.setImageResource(R.drawable.closered32);
//                b=false;
//
//            }
//            else
//            {
//                im_hungrycheck.setImageResource(R.drawable.checkgreen32);
//                im_hungrycross.setImageResource(R.drawable.closegray32);
//                b=true;
//            }
//            if(credible.equalsIgnoreCase("false"))
//            {
//                im_crediblecheck.setImageResource(R.drawable.checkgray32);
//                im_crediblecross.setImageResource(R.drawable.closered32);
//                c=false;
//            }
//            else
//            {
//                im_crediblecheck.setImageResource(R.drawable.checkgreen32);
//                im_crediblecross.setImageResource(R.drawable.closegray32);
//                c=true;
//            }
//            if(peopleSkills.equalsIgnoreCase("false"))
//            {
//                im_skillscheck.setImageResource(R.drawable.checkgray32);
//                im_skillscross.setImageResource(R.drawable.closered32);
//                d=false;
//            }
//            else
//            {
//                im_skillscheck.setImageResource(R.drawable.checkgreen32);
//                im_skillscross.setImageResource(R.drawable.closegray32);
//                d=true;
//            }
//            if(competitive.equalsIgnoreCase("false"))
//            {
//                im_competitivecheck.setImageResource(R.drawable.checkgray32);
//                im_competitvecross.setImageResource(R.drawable.closered32);
//                e=false;
//            }
//            else
//            {
//                im_competitivecheck.setImageResource(R.drawable.checkgreen32);
//                im_competitvecross.setImageResource(R.drawable.closegray32);
//                e=true;
//            }
//            if(motivated.equalsIgnoreCase("false"))
//            {
//                im_motivatecheck.setImageResource(R.drawable.checkgray32);
//                im_motivatecross.setImageResource(R.drawable.closered32);
//                f=false;
//            }
//            else
//            {
//                im_motivatecheck.setImageResource(R.drawable.checkgreen32);
//                im_motivatecross.setImageResource(R.drawable.closegray32);
//                f=true;
//            }
//        }







        im_hungrycheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b)
                {}
                else {
                    im_hungrycheck.setImageResource(R.drawable.checkgreen32);
                    im_hungrycross.setImageResource(R.drawable.closegray32);
                    getdatafromfirebase();
                    a = a + 1;
                    rate=rate+1;
                    if(rate==5)
                    {
                        rr=rr+1;
                        Map newrate = new HashMap();
                        newrate.put("fivePointRecruits",rr);
                        mref.child("users").child(uid).updateChildren(newrate);
                    }

                    tv_rating.setText("Recruit Rating: " + a);
                    b=true;

                    Log.e("check",uid+" "+givenName+" abc");
                    if(a<6) {
                        Map newData = new HashMap();
                        newData.put("hungry", "true");
                        newData.put("recruitRating", a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        putfivePointRecruitsinfirebase();
                    }





                }
            }
        });
        im_hungrycross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    im_hungrycheck.setImageResource(R.drawable.checkgray32);
                    im_hungrycross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
//                        getdatafromfirebase();
//
//                        if (a == 5) {
//                            rr = rr - 1;
//                            Map newrate = new HashMap();
//                            newrate.put("fivePointRecruits", rr);
//                            mref.child("users").child(uid).updateChildren(newrate);
//                        }

                        a = a - 1;
                        rate = rate - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        b = false;

                        if (a > 0) {
                            Map newData = new HashMap();
                            newData.put("hungry", "false");
                            newData.put("recruitRating", a);
                            mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        }
                    }
                }
            }
        });
        im_crediblecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c)
                {}
                else {
                    im_crediblecheck.setImageResource(R.drawable.checkgreen32);
                    im_crediblecross.setImageResource(R.drawable.closegray32);
                    getdatafromfirebase();
                    a = a + 1;
                    rate=rate+1;
                    if(rate==5)
                    {
                        rr=rr+1;
                        Map newrate = new HashMap();
                        newrate.put("fivePointRecruits",rr);
                        mref.child("users").child(uid).updateChildren(newrate);
                    }
                    tv_rating.setText("Recruit Rating: " + a);
                    c=true;
                    if(a<6) {
                        Map newData = new HashMap();
                        newData.put("credible", "true");
                        newData.put("recruitRating", a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        putfivePointRecruitsinfirebase();
                    }
                }
            }
        });
        im_crediblecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c) {
                    im_crediblecheck.setImageResource(R.drawable.checkgray32);
                    im_crediblecross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
//                        getdatafromfirebase();
//
//                        if (a == 5) {
//                            rr = rr - 1;
//                            Map newrate = new HashMap();
//                            newrate.put("fivePointRecruits", rr);
//                            mref.child("users").child(uid).updateChildren(newrate);
//                        }
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        c = false;

                        if (a > 0) {
                            Map newData = new HashMap();
                            newData.put("credible", "false");
                            newData.put("recruitRating", a);
                            mref.child("contacts").child(uid).child(givenName).updateChildren(newData);

                        }
                    }
                }
            }
        });
        im_skillscheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d)
                {}
                else {
                    im_skillscheck.setImageResource(R.drawable.checkgreen32);
                    im_skillscross.setImageResource(R.drawable.closegray32);
                    getdatafromfirebase();
                    a = a + 1;
                    rate=rate+1;
                    if(rate==5)
                    {
                        rr=rr+1;
                        Map newrate = new HashMap();
                        newrate.put("fivePointRecruits",rr);
                        mref.child("users").child(uid).updateChildren(newrate);
                    }
                    tv_rating.setText("Recruit Rating: " + a);
                    d=true;


                    if(a<6) {
                        Map newData = new HashMap();
                        newData.put("peopleSkills", "true");
                        newData.put("recruitRating", a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        putfivePointRecruitsinfirebase();
                    }
                }
            }
        });
        im_skillscross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(d) {
                    im_skillscheck.setImageResource(R.drawable.checkgray32);
                    im_skillscross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
//                        getdatafromfirebase();
//
//                        if (a == 5) {
//                            rr = rr - 1;
//                            Map newrate = new HashMap();
//                            newrate.put("fivePointRecruits", rr);
//                            mref.child("users").child(uid).updateChildren(newrate);
//                        }


                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        d=false;
                        if (a > 0) {
                            Map newData = new HashMap();
                            newData.put("peopleSkills", "false");
                            newData.put("recruitRating", a);
                            mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        }

                    }
                }
            }
        });
        im_competitivecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e) {
                } else {
                    im_competitivecheck.setImageResource(R.drawable.checkgreen32);
                    im_competitvecross.setImageResource(R.drawable.closegray32);
                    getdatafromfirebase();
                    a = a + 1;
                    rate=rate+1;
                    if(rate==5)
                    {
                        rr=rr+1;
                        Map newrate = new HashMap();
                        newrate.put("fivePointRecruits",rr);
                        mref.child("users").child(uid).updateChildren(newrate);
                    }
                    tv_rating.setText("Recruit Rating: " + a);
                    e=true;

                    if(a<6) {
                        Map newData = new HashMap();
                        newData.put("competitive", "true");
                        newData.put("recruitRating", a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        putfivePointRecruitsinfirebase();
                    }
                }
            }
        });
        im_competitvecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e) {
                    im_competitivecheck.setImageResource(R.drawable.checkgray32);
                    im_competitvecross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
//                        getdatafromfirebase();
//
//                        if (a == 5) {
//                            rr = rr - 1;
//                            Map newrate = new HashMap();
//                            newrate.put("fivePointRecruits", rr);
//                            mref.child("users").child(uid).updateChildren(newrate);
//                        }
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        e=false;

                        if(a>0) {
                            Map newData = new HashMap();
                            newData.put("competitive", "false");
                            newData.put("recruitRating", a);
                            mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        }
                    }
                }
            }
        });
        im_motivatecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f)
                {}
                else {
                    im_motivatecheck.setImageResource(R.drawable.checkgreen32);
                    im_motivatecross.setImageResource(R.drawable.closegray32);
                    getdatafromfirebase();
                    a = a + 1;
                    rate=rate+1;
                    if(rate==5)
                    {
                        rr=rr+1;
                        Map newrate = new HashMap();
                        newrate.put("fivePointRecruits",rr);
                        mref.child("users").child(uid).updateChildren(newrate);
                    }
                    tv_rating.setText("Recruit Rating: " + a);
                    f=true;


                    if(a<6) {
                        Map newData = new HashMap();
                        newData.put("motivated", "true");
                        newData.put("recruitRating", a);
                        mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        putfivePointRecruitsinfirebase();
                    }
                }
            }
        });
        im_motivatecross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f) {
                    im_motivatecheck.setImageResource(R.drawable.checkgray32);
                    im_motivatecross.setImageResource(R.drawable.closered32);
                    if (a > 0) {
//                        getdatafromfirebase();
//
//                        if (a == 5) {
//                            rr = rr - 1;
//                            Map newrate = new HashMap();
//                            newrate.put("fivePointRecruits", rr);
//                            mref.child("users").child(uid).updateChildren(newrate);
//                        }
                        a = a - 1;
                        tv_rating.setText("Recruit Rating: " + a);
                        f=false;

                        if(a>0) {
                            Map newData = new HashMap();
                            newData.put("motivated", "false");
                            newData.put("recruitRating", a);
                            mref.child("contacts").child(uid).child(givenName).updateChildren(newData);
                        }
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


    public void getdatafromfirebase()
    {
        mref.child("users").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                rr=ConvertParseInteger(dataSnapshot.child("fivePointRecruits").getValue());
                Log.e("rrrrr",rr+" ann");
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
            Log.e("lastseen",obj+" abc");
            String lastSeen = String.valueOf(obj);
            if (lastSeen != null & !TextUtils.isEmpty(lastSeen)) {
                return Integer.valueOf(lastSeen);
            }
            else
                return 0;
        }
    }


    public void puttenfivepointrecruitsinfirebase(final String str)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (str.equals("10"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Ten_five_point_recruits").getValue() + " abc");
                    Ten_five_point_recruits = dataSnapshot.child("Ten_five_point_recruits").getValue().toString();
                    if (Ten_five_point_recruits.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Ten_five_point_recruits", "true");
                        newcontact.put("Ten_five_point_recruits_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);

                        Achivement_Details frag = new Achivement_Details();
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",8);

                        try {
                            obj.put("Ten_five_point_recruits", "true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        bundle.putString("data", obj + "");
                        frag.setArguments(bundle);

                        if(getActivity()!=null)
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.frame_layout, frag).addToBackStack(null).commit();
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


    public void putfivePointRecruitsinfirebase()
    {
        mref.child("users").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                try {
                    obj = new JSONObject(dataSnapshot.getValue() + "");
                }
                catch (Exception e)
                {

                }
                Log.e("get data from server", dataSnapshot.getValue() + " data");
                Log.e("child", dataSnapshot.child("fivePointRecruits").getValue() + " abc");
                String fivePointClients = String.valueOf(dataSnapshot.child("fivePointClients").getValue());
                if (fivePointClients.equalsIgnoreCase("10"))
                {
                    puttenfivepointrecruitsinfirebase("10");
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }




    public void getcontactdatafromfirebase(String givenname)
    {
        mref.child("contacts").child(uid).child(givenname).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("getdatauidd",uid+" abv");
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllRatingContact>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("child",child+" abc");
                    try {
                        data.add(new AllRatingContact(String.valueOf(dataSnapshot.child("competitive").getValue()), String.valueOf(dataSnapshot.child("created").getValue()), String.valueOf(dataSnapshot.child("credible").getValue()), String.valueOf(dataSnapshot.child("familyName").getValue()), String.valueOf(dataSnapshot.child("givenName").getValue()), String.valueOf(dataSnapshot.child("hasKids").getValue()),
                                String.valueOf(dataSnapshot.child("homeowner").getValue()), String.valueOf(dataSnapshot.child("hungry").getValue()), String.valueOf(dataSnapshot.child("incomeOver40k").getValue()), String.valueOf(dataSnapshot.child("married").getValue()), String.valueOf(dataSnapshot.child("motivated").getValue()), String.valueOf(dataSnapshot.child("ofProperAge").getValue()), String.valueOf(dataSnapshot.child("peopleSkills").getValue()),
                                String.valueOf(dataSnapshot.child("phoneNumber").getValue()), ConvertParseInteger(dataSnapshot.child("rating").getValue()),ConvertParseInteger(dataSnapshot.child("recruitRating").getValue()), String.valueOf(dataSnapshot.child("ref").getValue())));

                        Log.e("child", dataSnapshot.child("rating").getValue() + " abc"+dataSnapshot.child("recruitRating").getValue());
                    }
                    catch(Exception e)
                    {
                        Log.e("Exception",e.getMessage());
                    }
                }


                competitive = data.get(0).getCompetitive();
                created = data.get(0).getCreated();
                credible = data.get(0).getCredible();
                familyName = data.get(0).getFamilyName();
//                givenName = data.get(0).getGivenName();
                hasKids = data.get(0).getHasKids();
                homeowner = data.get(0).getHomeowner();
                hungry = data.get(0).getHungry();
                incomeOver40k = data.get(0).getIncomeOver40k();
                married = data.get(0).getMarried();
                motivated = data.get(0).getMotivated();
                ofProperAge = data.get(0).getOfProperAge();
                peopleSkills = data.get(0).getPeopleSkills();
                phoneNumber = data.get(0).getPhoneNumber();

                rating = data.get(0).getRating();
                recruitRating =data.get(0).getRecruitRating();
                Log.e("ratinggg",competitive+" cccc"+created+married+recruitRating+rating+ref);
                //        Toast.makeText(getActivity(), rating+" anc", Toast.LENGTH_SHORT).show();

                if(recruitRating>0)
                {
                    a=recruitRating;
                    tv_rating.setText("Recruit Rating: " + a);
                    if(hungry.equalsIgnoreCase("false"))
                    {
                        im_hungrycheck.setImageResource(R.drawable.checkgray32);
                        im_hungrycross.setImageResource(R.drawable.closered32);
                        b=false;

                    }
                    else
                    {
                        im_hungrycheck.setImageResource(R.drawable.checkgreen32);
                        im_hungrycross.setImageResource(R.drawable.closegray32);
                        b=true;
                    }
                    if(credible.equalsIgnoreCase("false"))
                    {
                        im_crediblecheck.setImageResource(R.drawable.checkgray32);
                        im_crediblecross.setImageResource(R.drawable.closered32);
                        c=false;
                    }
                    else
                    {
                        im_crediblecheck.setImageResource(R.drawable.checkgreen32);
                        im_crediblecross.setImageResource(R.drawable.closegray32);
                        c=true;
                    }
                    if(peopleSkills.equalsIgnoreCase("false"))
                    {
                        im_skillscheck.setImageResource(R.drawable.checkgray32);
                        im_skillscross.setImageResource(R.drawable.closered32);
                        d=false;
                    }
                    else
                    {
                        im_skillscheck.setImageResource(R.drawable.checkgreen32);
                        im_skillscross.setImageResource(R.drawable.closegray32);
                        d=true;
                    }
                    if(competitive.equalsIgnoreCase("false"))
                    {
                        im_competitivecheck.setImageResource(R.drawable.checkgray32);
                        im_competitvecross.setImageResource(R.drawable.closered32);
                        e=false;
                    }
                    else
                    {
                        im_competitivecheck.setImageResource(R.drawable.checkgreen32);
                        im_competitvecross.setImageResource(R.drawable.closegray32);
                        e=true;
                    }
                    if(motivated.equalsIgnoreCase("false"))
                    {
                        im_motivatecheck.setImageResource(R.drawable.checkgray32);
                        im_motivatecross.setImageResource(R.drawable.closered32);
                        f=false;
                    }
                    else
                    {
                        im_motivatecheck.setImageResource(R.drawable.checkgreen32);
                        im_motivatecross.setImageResource(R.drawable.closegray32);
                        f=true;
                    }
                }


            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }



}




