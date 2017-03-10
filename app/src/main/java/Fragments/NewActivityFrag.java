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


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Adapter.SelectNewActivityListAdapter;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by WIN 10 on 05-03-2017.
 */
public class NewActivityFrag extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {


    LinearLayout show_calendarlayout;
    Calendar myCalendar;
    TextView tv_date;

    String date,increement_value;
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

        increement_value=bundle.getString("increementvalue");

        Log.e("increement_value",increement_value);

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

        mref=new Firebase(Constants.URL);

        Map newcontact = new HashMap();
        newcontact.put("contactName", Need_to_Quality.givenName);
        newcontact.put("contactRef",Need_to_Quality.ref);
        newcontact.put("created",timestamp);
        newcontact.put("date",selectedtimeStampDate.getTime());
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
                .setValue(newcontact, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(increement_value.equalsIgnoreCase("Appointments Set")){
                            check_Goals();
                        }
                    }
                });

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack();

    }

    public void check_Goals(){

        Log.e("check_goals_call","check_goals_call");

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/users/"+pref.getString("uid","")+"/Goals/");
        mref.keepSynced(true);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Child_Event_Verse", "onChildChanged:" + dataSnapshot.getKey());
                String key=dataSnapshot.getKey();
//                long key= Long.parseLong(dataSnapshot.getKey());
//                long v=1488570223417l;
//                int vv=v.compareTo(key);
                //if (v<key){
                String startdate = (String) dataSnapshot.child("startDate").getValue();
                String enddate = (String) dataSnapshot.child("endDate").getValue();
                //04/03/2017 12:46 PM
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
                String currentDate = formatter.format(new Date());

                try {
                    Date start_d = (Date)formatter.parse(startdate);
                    Date end_d = (Date)formatter.parse(enddate);
                    Date current_d = (Date)formatter.parse(currentDate);

                    Log.e("date_start",start_d+"");
                    Log.e("date_end",end_d+"");
                    Log.e("date_current",current_d+"");

                    Log.e("increement_value",increement_value+"  ------- ");

                    int cur_to_end=end_d.compareTo(current_d);
                    int cur_to_start=start_d.compareTo(current_d);
//                    Log.e("cur_to_end",cur_to_end+"");
//                    Log.e("cur_to_start",cur_to_start+"");
                    if(cur_to_end>0){
                        Log.e("end_date_is ","greater than cur_date");
                        if(cur_to_start<0) {
                            Log.e("current_date_is", "greater than start date");
                            //update value
                            Map<String, Object> activitycount = (Map<String, Object>) dataSnapshot.child("activityCount").getValue();

                            if(Integer.valueOf(activitycount.get(increement_value).toString())>0){
                                increment_Goals_Counter(mref,key,increement_value);
                            }

                        }
                        else
                            Log.e("start_date_is","greater than current date");
                    }
                    else
                        Log.e("currnet_date_is ","greater than end_date");


                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("date_time_exception","e",e);
                }
                Log.e("start_date","startdate: "+startdate);
                Log.e("end_date","enddate: "+enddate);
                Log.e("currnet_date","Date: "+currentDate);

//                    if (ss!=null){
//                        Log.e("TEST","Changed values: null ");
//                    }
//                }
//                else
//                    Log.e("onChildAdded","Changed values: null ");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged","Changed values: null ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildremoved","Changed values: null ");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildMoved","Changed values: null ");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("onCancelled","Changed values: null ");
            }
        };
        mref.addChildEventListener(childEventListener);
    }

    public void increment_Goals_Counter(Firebase ref,String key,String node) {

        Log.e("uid",pref.getString("uid",""));
        Log.e("increment_Goals_call","increment_Goals_Counter_call");
//        String currentDate = datecurrent();

        Log.e("key_node",key+"/"+node);

        ref.child(key).child("currentCount").child(node).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    Log.e("Firebase goals counter","increement failed");
                } else {
                    Log.e("Firebase goals counter","increment succeeded.");
                }
            }
        });
    }

}
