package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.github.siyamed.shapeimageview.DiamondImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ntt.customgaugeview.library.GaugeView;

import java.util.Date;
import java.util.LinkedHashMap;

import Adapter.personal_list_adapter;
import model.UserData;
import u.activitymanager.R;
import utils.ActivityComputeUtils;
import utils.AnimateFirstDisplayListener;
import utils.Constants;
import utils.UserTask;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class Downline_details_frag extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView rview;
    LinearLayoutManager linearLayoutManager;
    personal_list_adapter adapter;

    DiamondImageView Profile_pic;
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
    GaugeView speedview;

    UserTask userTask;
    UserData mData;

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
        Profile_pic=(DiamondImageView)view.findViewById(R.id.profile_pic);

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

        getUserData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userTask.cancel();
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
                if (mData == null) return;
                Activity_list_other_frag basic_frag1 = new Activity_list_other_frag();
                Bundle args1 = new Bundle();
                args1.putString("givenName", mData.user.givenName);
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

    public void showUser() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.userprofile)
                .showImageOnFail(R.drawable.userprofile)
                .showImageForEmptyUri(R.drawable.userprofile)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(String.valueOf(mData.user.profilePictureURL), Profile_pic, options, animateFirstListener);
        tv_username.setText(mData.user.givenName);
        tv_phone.setText(mData.user.phoneNumber);
        tv_email.setText(mData.user.email);
        tv_solutionnumber.setText(mData.user.solution_number);

        LinkedHashMap<String, Integer> activityCount = ActivityComputeUtils.computeActivityCount(mData, new Date());
        adapter = new personal_list_adapter(getActivity());
        adapter.setData(activityCount);
        rview.setAdapter(adapter);
        speedview.setTargetValue(ActivityComputeUtils.computeWeeklyTotal(activityCount));
    }

    private void getUserData() {
        userTask = new UserTask(mref, uidd)
                .withListener(new UserTask.Listener() {
                    @Override
                    public void onChanged(UserData data) {
                        mData = data;
                        showUser();
                    }

                    @Override
                    public void onError(FirebaseError error) {
                        Log.e("get data error", error.getMessage() + " data");
                    }
                });
        userTask.start();
    }
}