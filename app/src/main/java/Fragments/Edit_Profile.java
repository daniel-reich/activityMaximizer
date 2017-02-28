package Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import Adapter.Achivement_Adap;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import u.activitymanager.SplashActivity;
import utils.NetworkConnection;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class Edit_Profile extends Fragment implements View.OnClickListener {

    TextView tv_fname,tv_lname,tv_phone;
    RelativeLayout lay_fname,lay_lname,lay_phone,lay_password;
    View v;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Firebase mref;
    NetworkConnection net;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.edit_profile,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Edit Profile");
//        HomeActivity.tv_back.setVisibility(View.VISIBLE);
//        HomeActivity.tv_back.setText("Back");
        setHasOptionsMenu(true);

        Firebase.setAndroidContext(getActivity());
//        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        pref=getActivity().getSharedPreferences("userpref",0);

        init();

        return v;
    }

    public void updatefield(String url,String val){

        edit=pref.edit();
        edit.putString(url,val);
        edit.commit();

        Log.e("update success","update success "+url+" "+val);

        Map newUserData = new HashMap();
//      newUserData.put("solution_number","qwe");
        newUserData.put(url, val);
        mref.child("users").child(pref.getString("uid","")).updateChildren(newUserData);

        if(v.equals("givenName")){
        tv_fname.setText(val);
        }
        else if(v.equals("familyName")){
           tv_lname.setText(val);
        }
        else if(v.equals("phoneNumber")){
           tv_phone.setText(val);
        }
    }

    public void init(){
        tv_fname=(TextView)v.findViewById(R.id.tv_firstname);
        tv_lname=(TextView)v.findViewById(R.id.tv_lastname);
        tv_phone=(TextView)v.findViewById(R.id.tv_phone);

        lay_fname=(RelativeLayout)v.findViewById(R.id.lay_fname);
        lay_lname=(RelativeLayout)v.findViewById(R.id.lay_lname);
        lay_phone=(RelativeLayout)v.findViewById(R.id.lay_phone);
        lay_password=(RelativeLayout)v.findViewById(R.id.lay_password);

        tv_fname.setText(pref.getString("givenName",""));
        tv_lname.setText(pref.getString("familyName",""));
        tv_phone.setText(pref.getString("phoneNumber",""));

        lay_phone.setOnClickListener(this);
        lay_lname.setOnClickListener(this);
        lay_fname.setOnClickListener(this);
        lay_password.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.lay_fname){
          setValue("givenName");
        }
        else if(v.getId()==R.id.lay_lname){
            setValue("familyName");
        }
        else if(v.getId()==R.id.lay_phone){
            setValue("phoneNumber");
        }
        else if(v.getId()==R.id.lay_password){
            net=new NetworkConnection(getActivity());
            if(net.isNetworkAvailable())
                sendEmail(pref.getString("email",""));
            else
                net.NetworkAlert();
        }
    }

    private void setValue(final String v) {

       final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_contact_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView tv_title=(TextView)dialog.findViewById(R.id.tv_title);
        TextView tv_cancel=(TextView)dialog.findViewById(R.id.cancel);
        TextView tv_save=(TextView)dialog.findViewById(R.id.tv_save);
        final EditText et_text=(EditText)dialog.findViewById(R.id.et_name);

        if(v.equals("givenName")){
            tv_title.setText("Change first name?");
            et_text.setHint("New first name");
        }
        else if(v.equals("familyName")){
            tv_title.setText("Change last name?");
            et_text.setHint("New last name");
        }
        else if(v.equals("phoneNumber")){
            tv_title.setText("Change phone number?");
            et_text.setHint("New phone name");
        }

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                net=new NetworkConnection(getActivity());

                if(net.isNetworkAvailable()) {

                    if (et_text.getText().length() > 0) {
                        dialog.dismiss();
                        updatefield(v, et_text.getText().toString());
                    }
                    else
                        Toast.makeText(getActivity(), "Fill The Text Field", Toast.LENGTH_LONG).show();
                }
                else{
                   net.NetworkAlert();
                }
            }
        });
    }

    private void sendEmail(final String v) {

        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rvp_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView tv_title=(TextView)dialog.findViewById(R.id.tv_title);
        TextView tv_cancel=(TextView)dialog.findViewById(R.id.cancel);
        TextView tv_save=(TextView)dialog.findViewById(R.id.tv_save);
        TextView tv_text=(TextView) dialog.findViewById(R.id.tv_text);

        tv_title.setText("Reset Email");
        tv_text.setText("You want to change password?");
        tv_save.setText("Send");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                net=new NetworkConnection(getActivity());

                if(net.isNetworkAvailable()) {
                    ResetPassword(v);
               }
                else{
                    net.NetworkAlert();
                }
            }
        });
    }

    public void ResetPassword(String em){

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(em)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Log.e("Sent Verification email", "Email sent.");
                            Toast.makeText(getActivity(),"Sent Email on your registered id",Toast.LENGTH_LONG).show();
                            mref.unauth();
                            edit=pref.edit();
                            edit.clear();
                            edit.commit();
                            Intent in = new Intent(getActivity(), SplashActivity.class);
                            startActivity(in);
                            getActivity().finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                   Log.e("Can't Reset Password","Can't Reset Password");
            }
        });
    }
}
