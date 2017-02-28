package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.NetworkConnection;

/**
 * Created by surender on 2/17/2017.
 */

public class Add_New_Goal extends Fragment implements View.OnClickListener {

    View v;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    TextView tv_goalname,tv_totalprem,tv_contactsadded,tv_appoint_set,tv_went_on_kt,tv_close_life,tv_close_iba;
    TextView tv_close_other_bus,tv_startdate,tv_enddate;
    RelativeLayout ly_goalname,ly_totalprem,ly_contactsadded,ly_appoint_set,ly_went_on_kt,ly_close_life,ly_close_iba;
    RelativeLayout ly_close_other_bus,ly_startdate,ly_enddate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v=inflater.inflate(R.layout.add_new_goals,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("");
        tv_goalname=(TextView)v.findViewById(R.id.tv_goalname);
        tv_totalprem=(TextView)v.findViewById(R.id.tv_totalpremium);
        tv_contactsadded=(TextView)v.findViewById(R.id.tv_contactadded);
        tv_appoint_set=(TextView)v.findViewById(R.id.tv_appointmentset);
        tv_went_on_kt=(TextView)v.findViewById(R.id.tv_wentonkt);
        tv_close_life=(TextView)v.findViewById(R.id.tv_closedlife);
        tv_close_iba=(TextView)v.findViewById(R.id.tv_closediba);
        tv_close_other_bus=(TextView)v.findViewById(R.id.tv_closedbusiness);
        tv_enddate=(TextView)v.findViewById(R.id.tv_enddate);
        tv_startdate=(TextView)v.findViewById(R.id.tv_startdate);

        ly_goalname=(RelativeLayout) v.findViewById(R.id.lay_goalname);
        ly_totalprem=(RelativeLayout)v.findViewById(R.id.lay_totalprem);
        ly_contactsadded=(RelativeLayout)v.findViewById(R.id.lay_contactadded);
        ly_appoint_set=(RelativeLayout)v.findViewById(R.id.lay_appoint_set);
        ly_went_on_kt=(RelativeLayout)v.findViewById(R.id.lay_went_kt);
        ly_close_life=(RelativeLayout)v.findViewById(R.id.lay_closedlife);
        ly_close_iba=(RelativeLayout)v.findViewById(R.id.lay_closediba);
        ly_close_other_bus=(RelativeLayout)v.findViewById(R.id.lay_closedbuisness);
        ly_startdate=(RelativeLayout)v.findViewById(R.id.lay_startdate);
        ly_enddate=(RelativeLayout)v.findViewById(R.id.lay_enddate);

        ly_appoint_set.setOnClickListener(this);
        ly_goalname.setOnClickListener(this);
        ly_totalprem.setOnClickListener(this);
        ly_contactsadded.setOnClickListener(this);
        ly_went_on_kt.setOnClickListener(this);
        ly_close_life.setOnClickListener(this);
        ly_close_iba.setOnClickListener(this);
        ly_close_other_bus.setOnClickListener(this);
        ly_startdate.setOnClickListener(this);
        ly_enddate.setOnClickListener(this);

        setHasOptionsMenu(true);

        Firebase.setAndroidContext(getActivity());
//        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("Save");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                updatefield();
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void updatefield(){

        Log.e("tv_totalprem",tv_totalprem.getText().toString());
        Log.e("tv_close_other_bus",tv_close_other_bus.getText().toString());
        Log.e("tv_contactsadded",tv_contactsadded.getText().toString());
        Log.e("tv_appoint_set",tv_appoint_set.getText().toString());
        Log.e("tv_went_on_it",tv_went_on_kt.getText().toString());
        Log.e("tv_close_life",tv_close_life.getText().toString());
        Log.e("tv_close_iba",tv_close_iba.getText().toString());

        if(tv_goalname.getText().toString().length()<1){
            Alert("Goal must have a title");
            return;
        }
        if(tv_totalprem.getText().toString().equals("0")&tv_close_other_bus.getText().toString().equals("0")&
                tv_contactsadded.getText().toString().equals("0")&tv_appoint_set.getText().toString().equals("0")&
                tv_went_on_kt.getText().toString().equals("0")&tv_close_life.getText().toString().equals("0")&
                tv_close_iba.getText().toString().equals("0")){
            Alert("Goal must have at least one included activity type");
            return;
        }

        String tms= String.valueOf(System.currentTimeMillis());
        Log.e("update success","update success");

        Map m1 = new HashMap();
        m1.put("Appointments Set",tv_appoint_set.getText().toString());
        m1.put("Closed IBA", tv_close_iba.getText().toString());
        m1.put("Closed Life", tv_close_life.getText().toString());
        m1.put("Closed Other Business", tv_close_other_bus.getText().toString());
        m1.put("Contacts Added", tv_contactsadded.getText().toString());
        m1.put("Total Premium", tv_totalprem.getText().toString());
        m1.put("Went on KT", tv_went_on_kt.getText().toString());

        Map newUserData = new HashMap();
        newUserData.put("completed","false");
        newUserData.put("finished:","false");
        newUserData.put("endDate",tv_enddate.getText().toString());
        newUserData.put("startDate",tv_startdate.getText().toString());
        newUserData.put("activityCount",m1);
        newUserData.put("title",tv_goalname.getText().toString());
        newUserData.put("ref","https://activitymaximizer.firebaseio.com/users/WHLfPP6Wv2TlKIfBU48ITnpwJ0C2/Goals/"+tms);
        mref.child("users").child(pref.getString("uid","")).child("Goals").child(tms).updateChildren(newUserData);

    }


    public void Alert(String val) {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView tv_text=(TextView)dialog.findViewById(R.id.tv_name);
        TextView tv_ok=(TextView)dialog.findViewById(R.id.tv_ok);

        tv_text.setText(val);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setValue(final String v, final TextView et) {

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

        tv_title.setText(v);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (et_text.getText().length() > 0) {
                        et.setText(et_text.getText().toString());
                        dialog.dismiss();
                    }
                    else
                        Toast.makeText(getActivity(), "Fill The Text Field", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.lay_goalname){
            setValue("New Name",tv_goalname);
        }
        else if(v.getId()==R.id.lay_totalprem){
            setValue("New Amount",tv_totalprem);
        }
        else if(v.getId()==R.id.lay_contactadded){
            setValue("New Amount",tv_contactsadded);
        }
        else if(v.getId()==R.id.lay_appoint_set){
            setValue("New Amount",tv_appoint_set);
        }
        else if(v.getId()==R.id.lay_went_kt){
            setValue("New Amount",tv_went_on_kt);
        }
        else if(v.getId()==R.id.lay_closedlife){
            setValue("New Amount",tv_close_life);
        }
        else if(v.getId()==R.id.lay_closediba){
            setValue("New Amount",tv_close_iba);
        }
        else if(v.getId()==R.id.lay_closedbuisness){
            setValue("New Amount",tv_close_other_bus);
        }
    }
}
