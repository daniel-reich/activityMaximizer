package register_frag;

import android.content.Intent;
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
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import u.activitymanager.SplashActivity;
import u.activitymanager.utils;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Login extends Fragment implements View.OnClickListener {

    Toolbar toolbar;
    TextView Login;
    EditText et_email,et_password;
    String st_email,st_pass;
    FirebaseUser user;
    Firebase mref;
    FirebaseAuth firebaseAuth;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.activity_login,container,false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SplashActivity.title.setText("Login");
        setHasOptionsMenu(true);
//
        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

         mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        pref=getActivity().getSharedPreferences("userpref",0);

        Login=(TextView)v.findViewById(R.id.tv_login);
        et_email=(EditText)v.findViewById(R.id.et_email);
        et_password=(EditText)v.findViewById(R.id.et_password);

        Login.setOnClickListener(this);

        return v;
    }

    public void getdatafromfirebase()
    {
        mref.child("users").child(pref.getString("uid","")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");

                edit=pref.edit();
                edit.putString("email",dataSnapshot.child("email").getValue()+"");
                edit.putString("givenName",dataSnapshot.child("givenName").getValue()+"");
                edit.putString("familyName",dataSnapshot.child("familyName").getValue()+"");
                edit.putString("upline_solution_number",dataSnapshot.child("upline_solution_number").getValue()+"");
                edit.putString("partner_solution_number",dataSnapshot.child("partner_solution_number").getValue()+"");
                edit.putString("trainer_solution_number",dataSnapshot.child("trainer_solution_number").getValue()+"");
                edit.putString("rvp_solution_number",dataSnapshot.child("rvp_solution_number").getValue()+"");
                edit.putString("state",dataSnapshot.child("state").getValue()+"");
                edit.putString("solution_number",dataSnapshot.child("solution_number").getValue()+"");
                edit.putString("phoneNumber",dataSnapshot.child("phoneNumber").getValue()+"");
                edit.putString("fivePointClients",dataSnapshot.child("fivePointClients").getValue()+"");
                edit.putString("contactsAdded",dataSnapshot.child("contactsAdded").getValue()+"");
                edit.putString("partnerUID",dataSnapshot.child("partnerUID").getValue()+"");
                edit.putString("fivePointRecruits",dataSnapshot.child("fivePointRecruits").getValue()+"");
                edit.putString("ref",dataSnapshot.child("ref").getValue()+"");
                edit.putString("achievements",dataSnapshot.child("achievements").getValue()+"");
                edit.putString("profilePictureURL",dataSnapshot.child("profilePictureURL").getValue()+"");
                edit.putBoolean("signup",true);
                edit.commit();

                try {
                    Intent i = new Intent(getActivity(), HomeActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
                catch (Exception e)
                {
                    Log.e("Exception",e.getMessage());
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//
//        Firebase.setAndroidContext(this);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//       // mref=new Firebase("https://testingdatabase-62bfb.firebaseio.com/");
//
//        pref=getSharedPreferences("userpref",0);
//
//        Login=(TextView)findViewById(R.id.tv_login);
//        et_email=(EditText)findViewById(R.id.et_email);
//        et_password=(EditText)findViewById(R.id.et_password);
//
//        Login.setOnClickListener(this);
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_login:
                validation();
//                Intent i=new Intent(Login.this,HomeActivity.class);
//                startActivity(i);
//                finish();
                break;
        }
    }

    public void validation(){
        st_email=et_email.getText().toString();
        st_pass=et_password.getText().toString();

        boolean b= utils.isValidEmail(st_email);
        if(!b){
            Toast.makeText(getActivity(),"Please Enter Valid Email Address",Toast.LENGTH_LONG).show();
            return;
        }
        if(st_pass.length()<8){
            Toast.makeText(getActivity(),"Password Does't Match",Toast.LENGTH_LONG).show();
            return;
        }

        signIn();
    }

    private void signIn() {

        //showProgressDialog();
        firebaseAuth.signInWithEmailAndPassword(st_email, st_pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("login", "signIn:onComplete:" + task.isSuccessful());
                        // hideProgressDialog();
                        if (task.isSuccessful()) {
                            user = task.getResult().getUser();

                            edit=pref.edit();
                            edit.putString("uid",user.getUid().toString());
                            edit.putBoolean("signup",true);
                            edit.commit();
                            Toast.makeText(getActivity(), "Sign In Success",Toast.LENGTH_SHORT).show();
                            getdatafromfirebase();

                        } else {
                            Toast.makeText(getActivity(), "Enter Valid Email or Password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
