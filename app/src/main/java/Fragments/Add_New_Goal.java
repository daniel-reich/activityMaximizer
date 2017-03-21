package Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;
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
    TextView tv_close_other_bus,tv_startdate,tv_enddate,tv_endtime,tv_starttime;
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
        tv_endtime=(TextView)v.findViewById(R.id.tv_endtime);
        tv_starttime=(TextView)v.findViewById(R.id.tv_starttime);

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

// textView is the TextView view that should display it
//        textView.setText(currentDateTimeString);
//        03-Mar-2017 13:35:42

        datecurrent();

        return v;
    }

    public void datecurrent(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Log.e("currenttime",currentDateTimeString);

        DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;

        try {
            String formattedDate = targetFormat.format(new Date());

//            date = originalFormat.parse(currentDateTimeString);
//            String formattedDate = targetFormat.format(date);
//            tv.setText(formattedDate);
            Log.e("formatted date",formattedDate);
            String[] ar=formattedDate.split(" ");
            tv_startdate.setText(ar[0]);
            tv_enddate.setText(ar[0]);

            String[] tim=ar[1].split(":");
            String tm=getTime(Integer.valueOf(tim[0]),Integer.valueOf(tim[1]));
            tv_starttime.setText(tm);
            tv_endtime.setText(tm);

        } catch (Exception e) {
            e.printStackTrace();

        }
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

        Map m3 = new HashMap();

        if(Integer.parseInt(tv_appoint_set.getText().toString())>0)
            m3.put("Appointments Set",0);

        if(Integer.parseInt(tv_close_iba.getText().toString())>0)
            m3.put("Closed IBA",0);

        if(Integer.parseInt(tv_close_life.getText().toString())>0)
            m3.put("Closed Life",0);

        if(Integer.parseInt(tv_close_other_bus.getText().toString())>0)
            m3.put("Closed Other Business",0);

        if(Integer.parseInt(tv_contactsadded.getText().toString())>0)
            m3.put("Contacts Added",0);

        if(Integer.parseInt(tv_totalprem.getText().toString())>0)
            m3.put("Total Premium",0);

        if(Integer.parseInt(tv_went_on_kt.getText().toString())>0)
            m3.put("Went on KT",0);

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
        newUserData.put("endDate",tv_enddate.getText().toString()+" "+tv_endtime.getText().toString());
        newUserData.put("startDate",tv_startdate.getText().toString()+" "+tv_starttime.getText().toString());
        newUserData.put("activityCount",m1);
        newUserData.put("currentCount",m3);
        newUserData.put("title",tv_goalname.getText().toString());
        newUserData.put("ref", Constants.URL+"users/"+pref.getString("uid","")+"Goals/"+tms);
        mref.child("users").child(pref.getString("uid","")).child("Goals").child(tms).updateChildren(newUserData, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
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

        if(v.equalsIgnoreCase("New Name")){
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
        else if(v.getId()==R.id.lay_startdate){
            datedilog(true,tv_startdate,tv_starttime);
//            setValue("New Amount",tv_close_iba);
        }
        else if(v.getId()==R.id.lay_enddate){
            datedilog(true,tv_enddate,tv_endtime);
//            setValue("New Amount",tv_close_other_bus);
        }
    }

    public void datedilog(final boolean b,final TextView tv, final TextView tv1)
    {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.datepicker_dialog);
        final DatePicker dp=(DatePicker)dialog.findViewById(R.id.datePicker);

//        if(!b)
//            dp.setMinDate(millsec-1000);

        TextView tv_cancel=(TextView)dialog.findViewById(R.id.tv_cancel);
        TextView tv_save=(TextView)dialog.findViewById(R.id.tv_save);

        dialog.show();

//        final Calendar cal=Calendar.getInstance();

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cal.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());
//                cal.set(Calendar.MONTH, dp.getMonth());
//                cal.set(Calendar.YEAR, dp.getYear());
//                millsec=cal.getTimeInMillis() /1000;
//                Log.e("millisecond",millsec+"");

                int month=dp.getMonth()+1;
                int day=dp.getDayOfMonth();
                String mon,dy;

                mon=month+"";
                dy=day+"";

                if(month < 10){
                    mon = "0" + month;
                }
                if(day < 10){
                    dy  = "0" + day;
                }

                Log.e("date",mon+"/"+dy+"/"+dp.getYear());
                String org_dt=mon+"/"+dy+"/"+dp.getYear();

                tv.setText(org_dt);

                timedilog(b,tv1);

                    if(b) {
//                        edit.putString("filter_startdate", tv.getText().toString());
//                        edit.putString("filter_enddate", tv_enddate.getText().toString());
                    }
                    else {
//                        edit.putString("filter_startdate", tv_startdate.getText().toString());
//                        edit.putString("filter_enddate", tv.getText().toString());
                    }
                dialog.dismiss();
            }
        });
    }

    public void timedilog(final boolean b,final TextView tv)
    {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.timepicker_dialog);
        final TimePicker dp=(TimePicker) dialog.findViewById(R.id.timePicker);

//        dp.setIs24HourView(false);

        TextView tv_cancel=(TextView)dialog.findViewById(R.id.tv_cancel);
        TextView tv_save=(TextView)dialog.findViewById(R.id.tv_save);
        dialog.show();

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int hour=dp.getCurrentHour();
                int minute=dp.getCurrentMinute();

                String ti=getTime(hour,minute);
                Log.e("time",ti);
                tv.setText(ti);

                if(b) {
//                        edit.putString("filter_startdate", tv.getText().toString());
//                        edit.putString("filter_enddate", tv_enddate.getText().toString());
                }
                else {
//                        edit.putString("filter_startdate", tv_startdate.getText().toString());
//                        edit.putString("filter_enddate", tv.getText().toString());
                }
                dialog.dismiss();
            }
        });
    }
    private String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a");

        String tm=formatter.format(tme);
        if(tm.contains("am"))
           tm= tm.replaceFirst("am","AM");
        else if(tm.contains("pm"))
           tm= tm.replaceFirst("pm","PM");

        return tm;
    }
}
