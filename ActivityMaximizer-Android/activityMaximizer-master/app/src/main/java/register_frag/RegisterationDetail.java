package register_frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import u.activitymanager.R;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class RegisterationDetail extends Fragment
{
    Toolbar toolbar;
    String st_sol_num,st__sol_num_conf,st_state;
    TextView Next;
    EditText et_solutionnumber,et_solutionnumber_conf;
    Spinner sp_state;
    Firebase mref;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.registration_detail,container,false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().show();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("Registration");

        setHasOptionsMenu(true);

        Next=(TextView)v.findViewById(R.id.tv_register);
        et_solutionnumber=(EditText)v.findViewById(R.id.et_solution_number);
        et_solutionnumber_conf=(EditText)v.findViewById(R.id.et_solution_number_conf);
        sp_state=(Spinner)v.findViewById(R.id.sp_state);

        String[] state = {"AL - Alabama", "AK - Alaska", "AZ - Arizona", "AR - Arkansas", "CA - California", "CO - Colorado", "CT - Connecticut", "DE - Delaware", "FL - Florida", "GA - Georgia", "HI - Hawaii", "ID - Idaho", "IL - Illinois", "IN - Indiana", "IA - Iowa", "KS - Kansas", "KY - Kentucky", "LA - Louisiana", "ME - Maine", "MD - Maryland", "MA - Massachusetts", "MI - Michigan", "MN - Minnesota", "MS - Mississippi", "MO - Missouri", "MT - Montana", "NE - Nebraska", "NV - Nevada", "NH - New Hampshire", "NJ - New Jersey", "NM - New Mexico", "NY - New York", "NC - North Carolina", "ND - North Dakota", "OH - Ohio", "OK - Oklahoma", "OR - Oregon", "PA - Pennsylvania", "RI - Rhode Island", "SC - South Caroina", "SD - South Dakota", "TN - Tennessee", "TX - Texas", "UT - Utah", "VT - Vermont", "VA - Virginia", "WA - Washington", "WV - West Virginia", "WI - Wisconson", "WY - Wyoming", "-", "AB - Alberta", "BC - British Columbia", "MB - Manitoba", "NB - New Brunswick", "NL - Newfoundland", "NS - Nova Scotia", "NT - Northwest Territories", "NU - Nunavut", "ON - Ontaria", "Prince Edward Island", "QC - Quebec", "SK - Saskatchewan", "YT - Yukon Territories"};


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, state); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(spinnerArrayAdapter);

        Firebase.setAndroidContext(getActivity());
        pref=getActivity().getSharedPreferences("userpref",0);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                st_state=sp_state.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                st_sol_num=et_solutionnumber.getText().toString();
                st__sol_num_conf=et_solutionnumber_conf.getText().toString();

                if(st_sol_num.length()<1){
                    Toast.makeText(getActivity(),"Pleaes enter Solution Number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!st_sol_num.equals(st__sol_num_conf)){
                    Toast.makeText(getActivity(),"Confirmation Solution Number Does't match",Toast.LENGTH_LONG).show();
                    return;
                }

                checksolutionnumber();
            }
        });

        return v;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.registration_detail);
//        toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        Next=(TextView)findViewById(R.id.tv_register);
//        et_solutionnumber=(EditText)findViewById(R.id.et_solution_number);
//        et_solutionnumber_conf=(EditText)findViewById(R.id.et_solution_number_conf);
//        et_state=(EditText)findViewById(R.id.et_state);
//
//        Firebase.setAndroidContext(this);
//        pref=getSharedPreferences("userpref",0);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
//        firebaseAuth = FirebaseAuth.getInstance();
//        Next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                   st_sol_num=et_solutionnumber.getText().toString();
//                   st__sol_num_conf=et_solutionnumber_conf.getText().toString();
//                st_state=et_state.getText().toString();
//                if(st_sol_num.length()<1){
//                    Toast.makeText(RegisterationDetail.this,"Pleaes enter Solution Number",Toast.LENGTH_LONG).show();
//                  return;
//                }
//                if(st__sol_num_conf.length()<1){
//                    Toast.makeText(RegisterationDetail.this,"Confirmation Solution Number Does't match",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if(st_state.length()<1){
//                    Toast.makeText(RegisterationDetail.this,"Pleaes Enter State",Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                checksolutionnumber();
//            }
//        });
//    }

    public void checksolutionnumber(){

        mref.child("Solution Numbers").child(st_sol_num).addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getActivity(),"this solution number already assigned",Toast.LENGTH_LONG).show();
                }
                else{
                    edit=pref.edit();
                    edit.putString("solution_number",st_sol_num);
                    edit.putString("state",st_state);
                    edit.commit();

                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.splash_layout,new Registratio_after_details()).addToBackStack(null).commit();
//

//                    writeNewUser(pref.getString("uid",""));
//                    Toast.makeText(getActivity(),"User not Exist",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                  Log.e("onCancelled",firebaseError.getMessage()+"  ");
            }
        });
    }

    private void writeNewUser(String uid) {

        Log.e("writeuserid",uid);

        Map newUserData = new HashMap();
        newUserData.put("solution_number", st_sol_num);
        newUserData.put("state", "state");
        mref.child("users").child(uid).updateChildren(newUserData);
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
