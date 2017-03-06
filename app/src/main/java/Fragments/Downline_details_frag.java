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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.firebase.client.Firebase;
import com.github.siyamed.shapeimageview.DiamondImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Adapter.adapter;
import Adapter.personal_list_adapter;
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
    ImageView meter;
    DiamondImageView Profile_pic;
    StorageReference storageRef;
    FirebaseAuth firebaseAuth;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    TextView tv_phone,tv_username,tv_email,tv_solutionnumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.downline_details_page,container,false);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Log.e("check","check");
        meter=(ImageView)view.findViewById(R.id.meter);
        rview=(RecyclerView)view.findViewById(R.id.rview);
        Profile_pic=(DiamondImageView)view.findViewById(R.id.profile_pic);

        tv_username=(TextView) view.findViewById(R.id.tv_username);
        tv_phone=(TextView)view.findViewById(R.id.tv_phone);
        tv_solutionnumber=(TextView) view.findViewById(R.id.tv_solutionnumber);
        tv_email=(TextView)view.findViewById(R.id.tv_email);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(linearLayoutManager);
       // adapter=new personal_list_adapter(getActivity());
        rview.setAdapter(adapter);
        Profile_pic.setOnClickListener(this);
        meter.setOnClickListener(this);

        pref=getActivity().getSharedPreferences("userpref",0);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase(Constants.URL);

        storageRef= FirebaseStorage.getInstance().getReference();

//        getdatafromfirebase();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.userprofile)
                .showImageOnFail(R.drawable.userprofile)
                .showImageForEmptyUri(R.drawable.userprofile)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();

        Log.e("profilepic",pref.getString("profilePictureURL","null image path"));

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.getInstance().displayImage(pref.getString("profilePictureURL",""), Profile_pic, options, animateFirstListener);

        tv_username.setText(pref.getString("givenName","")+" "+pref.getString("familyName",""));
        tv_phone.setText(pref.getString("phoneNumber",""));
        tv_email.setText(pref.getString("email",""));
        tv_solutionnumber.setText(pref.getString("solution_number",""));

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
}
