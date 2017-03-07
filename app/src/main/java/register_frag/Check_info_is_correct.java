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
    String st_email,st_pass,rvpsolutionnumber="",uplinesolutionnumber="";
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

        rvpsolutionnumber= pref.getString("rvpsolutionnumber","");
        uplinesolutionnumber=pref.getString("uplinesolutionnumber","");

        tv_name.setText(pref.getString("givenName",""));
        tv_email.setText(pref.getString("email",""));
        tv_phone.setText(pref.getString("phoneNumber",""));
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

        Map m1 = new HashMap();
        m1.put("Closed_three_IBAs",false);
        m1.put("Closed_three_IBAs_date", 0);
        m1.put("Closed_three_life",false);
        m1.put("Closed_three_life_date", 0);
        m1.put("Fifty_KTs", false);
        m1.put("Fifty_KTs_date", 0);
        m1.put("First_call_from_app", false);
        m1.put("First_call_from_app_date",0);
        m1.put("First_contact_added", false);
        m1.put("First_contact_added_date", 0);
        m1.put("First_downline", false);
        m1.put("First_downline_date",0);
        m1.put("One_hundred_KTs", false);
        m1.put("One_hundred_KTs_date", 0);
        m1.put("One_week_eight_five_three_one",false);
        m1.put("One_week_eight_five_three_one_date",0);
        m1.put("Perfect_month", false);
        m1.put("Perfect_month_date", 0);
        m1.put("Perfect_week", false);
        m1.put("Perfect_week_date", 0);
        m1.put("Ten_five_point_clients", false);
        m1.put("Ten_five_point_clients_date",0);
        m1.put("Ten_five_point_recruits", false);
        m1.put("Ten_five_point_recruits_date",0);
        m1.put("Ten_new_contacts_added", false);
        m1.put("Ten_new_contacts_added_date", 0);
        m1.put("Thirty_New_contacts_added_date", 0);
        m1.put("Thirty_new_contacts_added", false);
        m1.put("Three_appointments_set",false);
        m1.put("Three_appointments_set_date", 0);
        m1.put("Top_speed", false);
        m1.put("Top_speed_date", 0);
        m1.put("Twenty_new_contacts_added", false);
        m1.put("Twenty_new_contacts_added_date", 0);
        m1.put("Two_hundred_KTs", false);
        m1.put("Two_hundred_KTs_date",0);
        m1.put("Two_week_eight_five_three_one", false);
        m1.put("Two_week_eight_five_three_one_date", 0);
        m1.put("Went_on_three_KTs",false);
        m1.put("Went_on_three_KTs_date", 0);

//        public String givenName,familyName,phoneNumber,email,uid;
//        public String contactsAdded,  created, dailyPointAverages,fivePointClients,fivePointRecruits,
//                partner_solution_number, partnerUID,profilePictureURL,ref,rvp_solution_number,solution_number,
//                state,trainer_solution_number,upline_solution_number;

        Map m2=new HashMap();

        m2.put("givenName",pref.getString("givenName"," "));
        m2.put("familyName",pref.getString("familyName"," "));
        m2.put("phoneNumber",pref.getString("phoneNumber"," "));
        m2.put("email",pref.getString("email"," "));
        m2.put("uid",uid);
        m2.put("achievements",m1);
        m2.put("contactsAdded","");
        m2.put("created","");
        m2.put("dailyPointAverages","");
        m2.put("fivePointClients","");
        m2.put("fivePointRecruits","");
        m2.put("partner_solution_number","");
        m2.put("partnerUID","");
        m2.put("profilePictureURL","");
        m2.put("ref","");
        m2.put("rvp_solution_number","");
        m2.put("solution_number",pref.getString("solutionnumber",""));
        m2.put("state",pref.getString("state",""));
        m2.put("trainer_solution_number",pref.getString("trainersolutionnumber",""));
        m2.put("upline_solution_number",pref.getString("uplinesolutionnumber",""));

//        UserInfo userinfo = new UserInfo(pref.getString("givenName"," "), pref.getString("familyName"," "),
//                pref.getString("phone"," "),pref.getString("email"," "), uid,
//                m1," "," "," "," "," "," "," "," "," ", pref.getString("rvpsolutionnumber",""),
//                pref.getString("solutionnumber",""),pref.getString("state",""),pref.getString("trainersolutionnumber",""),
//                pref.getString("uplinesolutionnumber",""));

        mref.child("users")
                .child(uid)
                .setValue(m2, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError != null) {
                            Log.e("Data could not be" ,firebaseError.getMessage().toString());
                        } else {
                            Log.e("Data saved successfull",firebase.getRef()+" ");
                            edit=pref.edit();
                            edit.putString("uid",uid);
                            edit.commit();

                            String uid=pref.getString("uid","");
                            Log.e("writeuserid",uid);

                            Map newUserData = new HashMap();
//                    newUserData.put("solution_number","qwe");
                            newUserData.put("users", uid);
                            if(uplinesolutionnumber.length()>1) {
                                newUserData.put("upline", uplinesolutionnumber);
                            }
                            mref.child("Solution Numbers").child(pref.getString("solutionnumber","")).updateChildren(newUserData);


                            if(rvpsolutionnumber.length()>1)
                            {
                                Map rv = new HashMap();
                                rv.put(uid, pref.getString("solutionnumber",""));
                                mref.child("Solution Numbers").child(rvpsolutionnumber).child("Base").updateChildren(rv);
                            }

                            if(uplinesolutionnumber.length()>1)
                            {
                                Map dv = new HashMap();
                                dv.put("name", pref.getString("givenName",""));
                                dv.put("solutionNumber", pref.getString("solutionnumber",""));
                                dv.put("uid", uid);
                                mref.child("Solution Numbers").child(uplinesolutionnumber).child("downlines").child(uid).updateChildren(dv);
                            }
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
