package Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;


public class AddChallange extends Fragment implements View.OnClickListener {

    TextView title,tv_setreward;
    CheckBox cb_totalprem,cb_contactsadded,cb_appoint_set,cb_went_on_kt,cb_close_life,cb_close_iba;
    CheckBox cb_close_other_bus;

    ImageView iv_totalprem,iv_contactsadded,iv_appoint_set,iv_went_on_kt,iv_close_life,iv_close_iba;
    ImageView iv_close_other_bus;
View view;
    Map map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.challanges_new,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        map=new HashMap();
//        HomeActivity.tv_back.setVisibility(View.VISIBLE);

//        HomeActivity.tv_back.setText("Back");
//        HomeActivity.title.setText("");

        setHasOptionsMenu(true);

        title = (TextView)view.findViewById(R.id.tv_settitle);
        tv_setreward = (TextView)view.findViewById(R.id.tv_setreward);

        cb_totalprem=(CheckBox) view.findViewById(R.id.cb_totalpremium);
        cb_contactsadded=(CheckBox)view.findViewById(R.id.cb_contactadded);
        cb_appoint_set=(CheckBox)view.findViewById(R.id.cb_appointmentset);
        cb_went_on_kt=(CheckBox)view.findViewById(R.id.cb_wenton);
        cb_close_life=(CheckBox)view.findViewById(R.id.cb_closelife);
        cb_close_iba=(CheckBox)view.findViewById(R.id.cb_closeiba);
        cb_close_other_bus=(CheckBox)view.findViewById(R.id.cb_closeotherbusiness);

        iv_totalprem=(ImageView) view.findViewById(R.id.iv_totalpremium);
        iv_contactsadded=(ImageView)view.findViewById(R.id.iv_contactadded);
        iv_appoint_set=(ImageView)view.findViewById(R.id.iv_appointmentset);
        iv_went_on_kt=(ImageView)view.findViewById(R.id.iv_wenton);
        iv_close_life=(ImageView)view.findViewById(R.id.iv_closelife);
        iv_close_iba=(ImageView)view.findViewById(R.id.iv_closeiba);
        iv_close_other_bus=(ImageView)view.findViewById(R.id.iv_closeotherbusiness);


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("New Name",title);
            }
        });

        tv_setreward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Set Reward",tv_setreward);
            }
        });


        title.setOnClickListener(this);
        tv_setreward.setOnClickListener(this);

        map.put("Appointments Set",true);
        map.put("Closed IBA",true);
        map.put("Closed Life",true);
        map.put("Closed Other Business",true);
        map.put("Contacts Added",true);
        map.put("Total Premium",true);
        map.put("Went on KT",true);

        cb_totalprem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Appointments Set",true);
                else
                    map.put("Appointments Set",false);
            }
        });

        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Closed IBA",true);
                else
                    map.put("Closed IBA",false);
            }
        });
        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Closed Life",true);
                else
                    map.put("Closed Life",false);
            }
        });
        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Closed Other Business",true);
                else
                    map.put("Closed Other Business",false);
            }
        });
        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Contacts Added",true);
                else
                    map.put("Contacts Added",false);
            }
        });
        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Total Premium",true);
                else
                    map.put("Total Premium",false);
            }
        });
        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    map.put("Went on KT",true);
                else
                    map.put("Went on KT",false);
            }
        });

        return view;
    }

    private void showDialog(final String v, final TextView tv) {
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

        if(v.equalsIgnoreCase("New Name")|v.equalsIgnoreCase("Set Reward")){
            et_text.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else {
            et_text.setInputType(InputType.TYPE_CLASS_NUMBER);
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

                if (et_text.getText().length() > 0) {
                    tv.setText(et_text.getText().toString());
                    dialog.dismiss();
                }
                else
                    Toast.makeText(getActivity(), "Fill The Text Field", Toast.LENGTH_LONG).show();
            }
        });
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

            //    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void updatefield(){

        if(title.getText().toString().length()<1){
            Alert("Challenge must have a title");
            return;
        }

        String tms= String.valueOf(System.currentTimeMillis());
        Log.e("update success","update success");

        Map m1 = new HashMap();
        m1.put("Appointments Set",0);
        m1.put("Closed IBA",0);
        m1.put("Closed Life",0);
        m1.put("Closed Other Business",0);
        m1.put("Contacts Added",0);
        m1.put("Total Premium",0);
        m1.put("Went on KT",0);

        Map newUserData = new HashMap();
        newUserData.put("completed","false");
        newUserData.put("finished:","false");
        newUserData.put("endDate","1491079520");
        newUserData.put("startDate","1491079520");
        newUserData.put("activityCount",m1);
        newUserData.put("includedActivity",map);
        newUserData.put("currentLeader","");
        newUserData.put("created","1488145580.1949");
        newUserData.put("title",title.getText().toString());

        //newUserData.put("ref", Constants.URL+"users/"+pref.getString("uid","")+"Goals/"+tms);

        //mref.child("users").child(pref.getString("uid","")).child("Goals").child(tms).updateChildren(newUserData);
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

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.tv_settitle){
            showDialog("New Name",title);
        }
        else if(v.getId()==R.id.tv_setreward){
            showDialog("SeT Reward",tv_setreward);
        }
//        else if(v.getId()==R.id.lay_totalprem){
//            showDialog("New Amount",tv_totalprem);
//        }
//        else if(v.getId()==R.id.lay_contactadded){
//            showDialog("New Amount",tv_contactsadded);
//        }
//        else if(v.getId()==R.id.lay_appoint_set){
//            showDialog("New Amount",tv_appoint_set);
//        }
//        else if(v.getId()==R.id.lay_went_kt){
//            showDialog("New Amount",tv_went_on_kt);
//        }
//        else if(v.getId()==R.id.lay_closedlife){
//            showDialog("New Amount",tv_close_life);
//        }
//        else if(v.getId()==R.id.lay_closediba){
//            showDialog("New Amount",tv_close_iba);
//        }
//        else if(v.getId()==R.id.lay_closedbuisness){
//            showDialog("New Amount",tv_close_other_bus);
//        }

    }
}
