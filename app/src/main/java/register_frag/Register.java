package register_frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import u.activitymanager.R;
import u.activitymanager.SplashActivity;
import u.activitymanager.utils;

/**
 * Created by Surbhi on 15-02-2017.
 */

public class Register extends Fragment
{
    Firebase mref;
    Toolbar toolbar;
    TextView Next;
    String st_name,st_email,st_lname,st_pass,st_conf_pass,st_phone;
    EditText et_name,et_lname,et_email,et_password,et_conf_pass,et_phone;
    FirebaseAuth firebaseAuth;
    FirebaseUser fuser;
//    private FirebaseAuth.AuthStateListener mAuthListener;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private DatabaseReference mDatabase;
    boolean b1=false;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.activity_register,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SplashActivity.title.setText("Registration");
        setHasOptionsMenu(true);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);

        Next=(TextView)v.findViewById(R.id.tv_register);
        et_name=(EditText)v.findViewById(R.id.et_fname);
        et_lname=(EditText)v.findViewById(R.id.et_lname);
        et_email=(EditText)v.findViewById(R.id.et_email);
        et_phone=(EditText)v.findViewById(R.id.et_phone);
        et_password=(EditText)v.findViewById(R.id.et_password);
        et_conf_pass=(EditText)v.findViewById(R.id.et_confirm_password);

        Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                st_email=et_email.getText().toString();

                st_name=et_name.getText().toString();
                st_lname=et_lname.getText().toString();
                st_phone=et_phone.getText().toString();
                st_pass=et_password.getText().toString();
                st_conf_pass=et_conf_pass.getText().toString();

                if(!utils.isValidEmail(st_email)){
                    Toast.makeText(getActivity(),"Please Enter Valid Emil Address",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_pass.length()<8){
                    Toast.makeText(getActivity(),"you need to enter at least 8 characters in password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_pass.length()>15){
                    Toast.makeText(getActivity(),"maximum 15 characters in password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_name.length()>15|st_name.length()<4){
                    Toast.makeText(getActivity(),"name length should be between 4 and 15 charaters ",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_phone.length()!=10){
                    Toast.makeText(getActivity(),"Please enter valid phone no.",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!st_pass.equals(st_conf_pass)){
                    Toast.makeText(getActivity(),"Confirm password does't match",Toast.LENGTH_LONG).show();
                    return;
                }

//                register();

                emailIsRegistered(st_email);

            }
        });

        return v;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_register);
//
//        toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//
//        Firebase.setAndroidContext(this);
//        firebaseAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
//
//        pref=getSharedPreferences("userpref",0);
//
//        if(pref.getBoolean("register",false)) {
//            Intent in = new Intent(Register.this,RegisterationDetail.class);
//            startActivity(in);
//            finish();
//        }
//
//       // authstatechangelistner();
//
////      Update userinfo = new Update("surender", "kumar","9999999999","surender@gamil.com",pref.getString("uid",""));
////      Log.e("userid",pref.getString("uid",""));
////      mref.child("users").child(pref.getString("uid","")).push().setValue(userinfo);
//
//        Next=(TextView)findViewById(R.id.tv_register);
//        et_name=(EditText)findViewById(R.id.et_fname);
//        et_lname=(EditText)findViewById(R.id.et_lname);
//        et_email=(EditText)findViewById(R.id.et_email);
//        et_phone=(EditText)findViewById(R.id.et_phone);
//        et_password=(EditText)findViewById(R.id.et_password);
//        et_conf_pass=(EditText)findViewById(R.id.et_confirm_password);
//
//        Next.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                st_email=et_email.getText().toString();
//
//                st_name=et_name.getText().toString();
//                st_lname=et_lname.getText().toString();
//                st_phone=et_phone.getText().toString();
//                st_pass=et_password.getText().toString();
//                st_conf_pass=et_conf_pass.getText().toString();
//
//                if(!utils.isValidEmail(st_email)){
//                    Toast.makeText(Register.this,"Please Enter Valid Emil Address",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (st_pass.length()<8){
//                    Toast.makeText(Register.this,"you need to enter at least 8 characters in password",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (st_pass.length()>15){
//                    Toast.makeText(Register.this,"maximum 15 characters in password",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (st_name.length()>15|st_name.length()<4){
//                    Toast.makeText(Register.this,"name length should be between 4 and 15 charaters ",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (st_phone.length()!=10){
//                    Toast.makeText(Register.this,"Please enter valid phone no.",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (!st_pass.equals(st_conf_pass)){
//                    Toast.makeText(Register.this,"Confirm password does't match",Toast.LENGTH_LONG).show();
//                    return;
//                }
//
////                register();
//
//                emailIsRegistered(st_email);
//
//            }
//        });
//    }


//    public void signInanonymously()
//    {
//        firebaseAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.e("signin anonymously", "signInAnonymously:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//
//                        if (!task.isSuccessful()) {
//                            Log.e("signin anonymously if", "signInAnonymously", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Log.e("signin anonymously else", "signInAnonymously", task.getException());
////                            writeNewUser(task.getResult().getUser());
//
//                            edit=pref.edit();
//                            edit.putString("uid",task.getResult().getUser().getUid().toString());
//                            edit.commit();
//
//                            Intent in =new Intent(Register.this,RegisterationDetail.class);
//                            startActivity(in);
//
//                        }
//                        // ...
//                    }
//                });
//    }

//    public void authstatechangelistner()
//    {
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.e("anonymous if", "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.e("anonymous else", "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//    }

    public void emailIsRegistered(String email) {

    firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if (task.getResult().getProviders().size() > 0) {
                    Log.e("Message if",task.getResult().getProviders()+" result");
                    Toast.makeText(getActivity(),"Email Already Exist",Toast.LENGTH_LONG).show();
                }
                else {

                    edit=pref.edit();
                    edit.putString("email",st_email);
                    edit.putString("firstname",st_name);
                    edit.putString("password",st_pass);
                    edit.putString("lastname",st_lname);
                    edit.putString("phone",st_phone);
                    edit.commit();
                    Toast.makeText(getActivity(),"Successfully registered",Toast.LENGTH_LONG).show();

                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.splash_layout,new RegisterationDetail()).addToBackStack(null).commit();
//                    Intent in =new Intent(getActivity(),RegisterationDetail.class);
//                    startActivity(in);

                    Log.e("Message else",task.getResult().getProviders()+" result");
//                    register();
                }
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            firebaseAuth.removeAuthStateListener(mAuthListener);
//        }
//    }


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
