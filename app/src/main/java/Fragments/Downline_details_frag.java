package Fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ntt.customgaugeview.library.GaugeView;
import com.soundcloud.android.crop.Crop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.ClientAdapter;
import Adapter.adapter;
import Adapter.personal_list_adapter;
import model.AllContact;
import model.userinfoo;
import u.activitymanager.R;
import utils.AnimateFirstDisplayListener;
import utils.Constants;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class Downline_details_frag extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView rview;
    LinearLayoutManager linearLayoutManager;
    personal_list_adapter adapter;

    CircularImageView Profile_pic;
    StorageReference storageRef;
    FirebaseAuth firebaseAuth;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    ImageView contactimg,activityimg,achivementsimg,goalsimg;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    TextView tv_phone,tv_username,tv_email,tv_solutionnumber;
    String uidd="";
    ArrayList<userinfoo> data;
    int count[]={0,0,0,0,0,0,0,0};
    private GaugeView speedview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.downline_details_page,container,false);
        setHasOptionsMenu(true);

        speedview=(GaugeView) view.findViewById(R.id.meter);

        try {
            uidd = getArguments().getString("uid");
            Log.e("uidd", uidd);
        }catch (Exception e)
        {
            Log.e("Exception",e.getMessage());
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Log.e("check","check");



        rview=(RecyclerView)view.findViewById(R.id.rview);
        Profile_pic=(CircularImageView) view.findViewById(R.id.profile_pic);

        contactimg=(ImageView)view.findViewById(R.id.iv_contact);
        activityimg=(ImageView)view.findViewById(R.id.iv_activity);
        achivementsimg=(ImageView)view.findViewById(R.id.iv_achivement);
        goalsimg=(ImageView)view.findViewById(R.id.iv_goals);

        tv_username=(TextView) view.findViewById(R.id.tv_username);
        tv_phone=(TextView)view.findViewById(R.id.tv_phone);
        tv_solutionnumber=(TextView) view.findViewById(R.id.tv_solutionnumber);
        tv_email=(TextView)view.findViewById(R.id.tv_email);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(linearLayoutManager);
       // adapter=new personal_list_adapter(getActivity());
//        rview.setAdapter(adapter);
        Profile_pic.setOnClickListener(this);
        contactimg.setOnClickListener(this);
        activityimg.setOnClickListener(this);
        achivementsimg.setOnClickListener(this);
        goalsimg.setOnClickListener(this);


        pref=getActivity().getSharedPreferences("userpref",0);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase(Constants.URL);




        storageRef= FirebaseStorage.getInstance().getReference();

        getdatafromfirebase();

        getnotefromfirebase();
        getinfirebasedailipoint();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_contact:
                AllContactsOthers basic_frag = new AllContactsOthers();
                Bundle args = new Bundle();
                args.putString("uid", uidd);
                basic_frag.setArguments(args);

               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, basic_frag).
                        addToBackStack(null).commit();
                break;

            case R.id.iv_activity:
                Activity_list_other_frag basic_frag1 = new Activity_list_other_frag();
                Bundle args1 = new Bundle();
                args1.putString("givenName", data.get(0).getGivenName());
                args1.putString("uid", uidd);
                basic_frag1.setArguments(args1);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,basic_frag1).addToBackStack(null).commit();
                break;

            case R.id.iv_achivement:
                Achievements_Others basic_frag2 = new Achievements_Others();
                Bundle args2 = new Bundle();
                args2.putString("uid", uidd);
                basic_frag2.setArguments(args2);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,basic_frag2).addToBackStack(null).commit();
                break;

            case R.id.iv_goals:
                Goals_tracker_Other basic_frag3 = new Goals_tracker_Other();
                Bundle args3 = new Bundle();
                args3.putString("uid", uidd);
                basic_frag3.setArguments(args3);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,basic_frag3).addToBackStack(null).commit();
                break;

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false).setEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
              //  getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getActivity().getSupportFragmentManager().popBackStack();
                //getActivity().finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void getdatafromfirebase()
    {
        mref.child("users").child(uidd).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<userinfoo>();

//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    Log.e("child",child+" abc");

                String name="";
                if(dataSnapshot.child("givenName").getValue()!=null)

                {

                    name=dataSnapshot.child("givenName").getValue().toString();

                }

                else
                name=name=dataSnapshot.child("givename").getValue().toString();

                    data.add(new userinfoo(name,dataSnapshot.child("familyName").getValue().toString(),dataSnapshot.child("phoneNumber").getValue().toString(),dataSnapshot.child("email").getValue().toString(),dataSnapshot.child("uid").getValue().toString(),dataSnapshot.child("contactsAdded").getValue().toString(),
                            dataSnapshot.child("created").getValue().toString(),dataSnapshot.child("dailyPointAverages").getValue().toString(),dataSnapshot.child("fivePointClients").getValue().toString(),dataSnapshot.child("fivePointRecruits").getValue().toString(),dataSnapshot.child("partner_solution_number").getValue().toString(),dataSnapshot.child("partnerUID").getValue().toString(),dataSnapshot.child("profilePictureURL").getValue().toString(),
                            dataSnapshot.child("ref").getValue().toString(),dataSnapshot.child("rvp_solution_number").getValue().toString(),dataSnapshot.child("solution_number").getValue().toString(),dataSnapshot.child("state").toString(),dataSnapshot.child("trainer_solution_number").getValue().toString(),dataSnapshot.child("upline_solution_number").getValue().toString()));

                    Log.e("child",dataSnapshot.child("familyName").getValue()+" abc");
//                }

                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.userprofile)
                        .showImageOnFail(R.drawable.userprofile)
                        .showImageForEmptyUri(R.drawable.userprofile)
                        .cacheInMemory(false)
                        .cacheOnDisk(true)
                        .build();




                if(getActivity()==null)
                    return;
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));


                imageLoader.getInstance().displayImage(String.valueOf(data.get(0).getProfilePictureURL()), Profile_pic, options, animateFirstListener);

                tv_username.setText(data.get(0).getGivenName());
                tv_phone.setText(data.get(0).getPhoneNumber());
                tv_email.setText(data.get(0).getEmail());
                tv_solutionnumber.setText(data.get(0).getSolution_number());


            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


    public void getinfirebasedailipoint()
    {
        mref.child("users").child(uidd).child("dailyPointAverages").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get achievements1111", dataSnapshot.getValue() + " data");
                Log.e("child11111111111", dataSnapshot.getValue() + " abc");

                for(com.firebase.client.DataSnapshot child:dataSnapshot.getChildren()){

//                    String key=child.getKey();

                    //  int value= Integer.parseInt(child.getValue());
                    long value=((Long)child.getValue());
                    Log.e("achievementToShow",value+"");
                    speedview.setTargetValue(value);

                }

                // achieve_detail = String.valueOf(dataSnapshot.getValue());
                // setAch();

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }
    public void getnotefromfirebase()
    {
        mref.child("events")
                .child(uidd)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server",dataSnapshot.getValue()+" data");

                        JSONArray jsonArray =  new JSONArray();
                        for (com.firebase.client.DataSnapshot child : dataSnapshot.getChildren()) {
                            JSONObject jGroup = new JSONObject();
                            Log.e("childddd",child.child("contactName").getKey()+" abc");
                            //alue().toString(),child.child("created").getValue().toString(),child.child("date").getValue().toString(),child.child("eventKitID").getValue().toString(),child.child("ref").getValue().toString(),child.child("type").getValue().toString(),child.child("userName").getValue().toString(),child.child("userRef").getValue().toString()));
                            try {

                                Log.e ("oo",child.child("type").getValue().toString());

                                String activity_list[]={"Set Appointment","Went on KT","Closed Life","closed IBA","Closed Other Business","Appt Set To Closed Life",
                                        "Appt Set To Closed IBA","Invite to Opportunity Meeting","Went To Opportunity Meeting","Call Back","Dark House","Not Interested"};


                                switch(child.child("type").getValue().toString())

                                {
                                    case "Invited to Opportunity Meeting":

                                        count[7]++;

                                        break;


                                    case "Went to Opportunity Meeting":

                                        count[8]++;

                                        break;

                                    case "Set Appointment":

                                        count[0]++;
                                        break;

                                    case "Went on KT":

                                        count[1]++;
                                        break;

                                    case "Closed IBA":

                                        count[3]++;
                                        break;

                                }

                            }
                            catch (Exception e)
                            {
                                Log.e("Exception",e+"");
                            }
                        }



                        adapter=new personal_list_adapter(getActivity(),count);
                        rview.setAdapter(adapter);
                        Log.e("jsonarray",jsonArray+" abc");








                    }
                    @Override
                    public void onCancelled(FirebaseError error) {
                        Log.e("get data error",error.getMessage()+" data");
                    }
                });
    }


}




//String givenName, String familyName, String phoneNumber, String email, String uid, String contactsAdded, String created, String dailyPointAverages, String fivePointClients, String fivePointRecruits, String partner_solution_number, String partnerUID, String profilePictureURL, String ref, String rvp_solution_number, String solution_number, String state, String trainer_solution_number, String upline_solution_number