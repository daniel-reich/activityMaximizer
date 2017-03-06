package Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.firebase.client.Firebase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import u.activitymanager.R;
import utils.Constants;

/**
 * Created by WIN 10 on 05-03-2017.
 */
public class NewActivityFrag extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {


    LinearLayout show_calendarlayout;
    Calendar myCalendar;
    TextView tv_date;

    String date;
    private String time;

    TextView activity;
    private Firebase mref;
    private SharedPreferences pref;

    Bundle bundle;
    java.sql.Timestamp selectedtimeStampDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.newactivity,container,false);
        show_calendarlayout=(LinearLayout)v.findViewById(R.id.show_calendarlayout);
        tv_date=(TextView)v.findViewById(R.id.tv_date);

        activity=(TextView)v.findViewById(R.id.activity);

        myCalendar = Calendar.getInstance();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        bundle=getArguments();

        activity.setText(bundle.getString("title"));

        updateLabel();


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Calendar now = Calendar.getInstance();



                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        NewActivityFrag.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                Calendar now1 = Calendar.getInstance();

                dpd.setMinDate(now);


                dpd.show(getActivity().getFragmentManager(), "SelectDate");


            }
        });
        show_calendarlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


            menu.findItem(R.id.menu).setTitle("Add");


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        addNewContact(0);

        return super.onOptionsItemSelected(item);
    }

    private void updateLabel() {




        String myFormat = "MM/dd/yy hh:mm a ";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tv_date.setText(sdf.format(new Date()));

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        date=dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        Calendar now = Calendar.getInstance();

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String minuteString = minute < 10 ? "0" + minute : "" + minute;
                        String secondString = second < 10 ? "0" + second : "" + second;

                        String AM_PM ;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";

                        } else {
                            AM_PM = "PM";

                        }
                        time = hourString + ":" + minuteString;

                        date=date+" "+time;



                            tv_date.setText(date);
                        Log.e("date",date+"");
                        String myFormat = "dd/MM/yyyy hh:mm";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                        Date selecteddate= null;
                        try {
                            selecteddate = sdf.parse(tv_date.getText().toString());
                            selectedtimeStampDate = new Timestamp(selecteddate.getTime());
                            Log.e("selectedtime",selectedtimeStampDate.getTime()+"");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }


                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),now.get(Calendar.SECOND));

        tpd.show(getActivity().getFragmentManager(), "Timepicker");

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    private void addNewContact(int position) {
        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
        Log.e("Today is ", timeStampDate.getTime()+"");
        String timestamp=String.valueOf(timeStampDate.getTime());

        JSONObject json=new JSONObject();
        try {
            json.put("name",bundle.getString("name",""));
            json.put("time",timestamp+"");

            Activity_list_frag.js.put(json);
            Activity_list_frag.listadapter.notifyDataSetChanged();
        } catch (Exception e) {

            Log.e("y","e",e);
            e.printStackTrace();
        }

        pref=getActivity().getSharedPreferences("userpref",0);
        String   uid=pref.getString("uid","");
        String noteref= Constants.URL+"events/"+uid+"/"+timestamp;







//        mref.child("contacts").child(uid)
//                .child(st_fname).child("notes").child(timestamp)
//                .setValue(newnote);


        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        Map newcontact = new HashMap();
        newcontact.put("contactName", Need_to_Quality.givenName);
        newcontact.put("contactRef",Need_to_Quality.ref);
        newcontact.put("created",timestamp);
        newcontact.put("date",timestamp);
        newcontact.put("eventKitID","");
        newcontact.put("ref",noteref);
        newcontact.put("amount",0);
        newcontact.put("type",bundle.getString("name",""));
        newcontact.put("userName",pref.getString("givenName","")+" "+pref.getString("familyName",""));
        newcontact.put("userRef",pref.getString("ref",""));

        Log.e("Aa",newcontact.toString());

        mref.child("events")
                .child(uid)
                .child(String.valueOf(selectedtimeStampDate.getTime()))
                .setValue(newcontact);


        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack();


    }


}
