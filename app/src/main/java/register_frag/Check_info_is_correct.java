package register_frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import model.UserInfo;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import u.activitymanager.SplashActivity;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Check_info_is_correct extends Fragment implements View.OnClickListener {
    View v;
    FirebaseAuth firebaseAuth;
    Firebase mref;
    String st_email,st_pass;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    TextView tv_done,tv_name,tv_email,tv_state,tv_phone,tv_solutionnumber,tv_rvpnumber,tv_uplinenumber,tv_trainernumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.check_info_is_correct,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SplashActivity.title.setText("Registration");
        setHasOptionsMenu(true);

        tv_name=(TextView)v.findViewById(R.id.tv_name);
        tv_email=(TextView)v.findViewById(R.id.tv_email);
        tv_state=(TextView)v.findViewById(R.id.tv_state);
        tv_phone=(TextView)v.findViewById(R.id.tv_phone);
        tv_solutionnumber=(TextView)v.findViewById(R.id.tv_solutionnumber);
        tv_rvpnumber=(TextView)v.findViewById(R.id.tv_rvpsolutionnumber);
        tv_uplinenumber=(TextView)v.findViewById(R.id.tv_uplinesolutionnumber);
        tv_trainernumber=(TextView)v.findViewById(R.id.tv_trainersolutionnumber);

        tv_done=(TextView)v.findViewById(R.id.tv_done);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        pref=getActivity().getSharedPreferences("userpref",0);

        tv_name.setText(pref.getString("givenName",""));
        tv_email.setText(pref.getString("email",""));
        tv_phone.setText(pref.getString("phone",""));
        tv_state.setText(pref.getString("state",""));
        tv_rvpnumber.setText(pref.getString("rvpsolutionnumber",""));
        tv_solutionnumber.setText(pref.getString("solutionnumber",""));
        tv_uplinenumber.setText(pref.getString("uplinesolutionnumber",""));
        tv_trainernumber.setText(pref.getString("trainersolutionnumber",""));

        st_email=pref.getString("email","");
        st_pass=pref.getString("password","def");

        tv_done.setOnClickListener(this);

        return v;

    }

    public void register(){

        firebaseAuth.createUserWithEmailAndPassword(st_email, st_pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            writeNewUser(task.getResult().getUser());
                        }else{
                            //display some message here
                            Log.e("error_message",task.getException()+" ex");
                            Toast.makeText(getActivity(),"Registration Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void writeNewUser(FirebaseUser user) {

        final String uid = user.getUid();
        Log.e("writeuserid",uid);
        UserInfo userinfo = new UserInfo(pref.getString("givenName"," "), pref.getString("familyName"," "),
                pref.getString("phone"," "),pref.getString("email"," "), uid,
                " "," "," "," "," "," "," "," "," "," ", pref.getString("rvpsolutionnumber",""),
                pref.getString("solutionnumber",""),pref.getString("state",""),pref.getString("trainersolutionnumber",""),
                pref.getString("uplinesolutionnumber",""));

        mref.child("users")
                .child(uid)
                .setValue(userinfo, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError != null) {
                            Log.e("Data could not be" ,firebaseError.getMessage().toString());
                        } else {
                            Log.e("Data saved successfull",firebase.getRef()+" ");
                            edit=pref.edit();
                            edit.putString("uid",uid);
                            edit.putBoolean("signup",true);
                            edit.commit();

                            String uid=pref.getString("uid","");
                            Log.e("writeuserid",uid);

                            Map newUserData = new HashMap();
//                    newUserData.put("solution_number","qwe");
                            newUserData.put("users", uid);
                            mref.child("Solution Numbers").child(pref.getString("solutionnumber","")).updateChildren(newUserData);

                            Toast.makeText(getActivity(),"Successfully registered",Toast.LENGTH_LONG).show();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.splash_layout,new Login()).commit();

//                            Intent in =new Intent(getActivity(),HomeActivity.class);
//                            startActivity(in);
                        }
                    }
                });

//        mref.child("users").child(uid).setValue(userinfo);

//        Intent i=new Intent(Register.this,HomeActivity.class);
//        startActivity(i);
//        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_done:
                register();
//                getActivity().getSupportFragmentManager().beginTransaction().
//                        replace(R.id.frame_layout,new Register()).addToBackStack(null).commit();
//                break;
        }
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
