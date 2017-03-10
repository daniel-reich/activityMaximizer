package Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;


public class AddChallange extends Fragment implements View.OnClickListener {

    TextView title,tv_setreward,tv_timelimit;
    TextView tv_totalprem,tv_contactsadded,tv_appoint_set,tv_went_on_kt,tv_close_life,tv_close_iba;
    TextView tv_close_other_bus,tv_startdate,tv_enddate,tv_endtime,tv_starttime;

    TextView tv_totalprem1,tv_contactsadded1,tv_appoint_set1,tv_went_on_kt1,tv_close_life1,tv_close_iba1;
    TextView tv_close_other_bus1;
    LinearLayout lay_date;
    RelativeLayout ly_startdate,ly_enddate;
    //    ImageView iv_totalprem,iv_contactsadded,iv_appoint_set,iv_went_on_kt,iv_close_life,iv_close_iba;
//    ImageView iv_close_other_bus;
    View view;
    Map map;
    Firebase mref;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

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

        tv_totalprem=(TextView) view.findViewById(R.id.tv_totalpremium);
        tv_contactsadded=(TextView) view.findViewById(R.id.tv_contactadded);
        tv_appoint_set=(TextView)view.findViewById(R.id.tv_appointmentset);
        tv_went_on_kt=(TextView)view.findViewById(R.id.tv_wenton);
        tv_close_life=(TextView)view.findViewById(R.id.tv_closelife);
        tv_close_iba=(TextView)view.findViewById(R.id.tv_closeiba);
        tv_close_other_bus=(TextView)view.findViewById(R.id.tv_closeotherbusiness);

        tv_totalprem1=(TextView) view.findViewById(R.id.tv_totalpremium1);
        tv_contactsadded1=(TextView) view.findViewById(R.id.tv_contactadded1);
        tv_appoint_set1=(TextView)view.findViewById(R.id.tv_appointmentset1);
        tv_went_on_kt1=(TextView)view.findViewById(R.id.tv_wenton1);
        tv_close_life1=(TextView)view.findViewById(R.id.tv_closelife1);
        tv_close_iba1=(TextView)view.findViewById(R.id.tv_closeiba1);
        tv_close_other_bus1=(TextView)view.findViewById(R.id.tv_closeotherbusiness1);

        tv_timelimit=(TextView)view.findViewById(R.id.tv_timelimit);
        tv_enddate=(TextView)view.findViewById(R.id.tv_enddate);
        tv_startdate=(TextView)view.findViewById(R.id.tv_startdate);
        tv_endtime=(TextView)view.findViewById(R.id.tv_endtime);
        tv_starttime=(TextView)view.findViewById(R.id.tv_starttime);
        lay_date=(LinearLayout)view.findViewById(R.id.lay_date);

        ly_startdate=(RelativeLayout)view.findViewById(R.id.lay_startdate);
        ly_enddate=(RelativeLayout)view.findViewById(R.id.lay_enddate);

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        mref=new Firebase(Constants.URL);

        title.setOnClickListener(this);
        tv_setreward.setOnClickListener(this);

        ly_startdate.setOnClickListener(this);
        ly_enddate.setOnClickListener(this);

        tv_totalprem.setOnClickListener(this);
        tv_contactsadded.setOnClickListener(this);
        tv_appoint_set.setOnClickListener(this);
        tv_went_on_kt.setOnClickListener(this);
        tv_close_life.setOnClickListener(this);
        tv_close_iba.setOnClickListener(this);
        tv_close_other_bus.setOnClickListener(this);
        tv_timelimit.setOnClickListener(this);


        map.put("Appointments Set",false);
        map.put("Closed IBA",false);
        map.put("Closed Life",false);
        map.put("Closed Other Business",false);
        map.put("Contacts Added",false);
        map.put("Total Premium",false);
        map.put("Went on KT",false);

//
//        cb_totalprem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Appointments Set",true);
//                else
//                    map.put("Appointments Set",false);
//            }
//        });
//
//        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Closed IBA",true);
//                else
//                    map.put("Closed IBA",false);
//            }
//        });
//        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Closed Life",true);
//                else
//                    map.put("Closed Life",false);
//            }
//        });
//        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Closed Other Business",true);
//                else
//                    map.put("Closed Other Business",false);
//            }
//        });
//        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Contacts Added",true);
//                else
//                    map.put("Contacts Added",false);
//            }
//        });
//        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Total Premium",true);
//                else
//                    map.put("Total Premium",false);
//            }
//        });
//        cb_close_iba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    map.put("Went on KT",true);
//                else
//                    map.put("Went on KT",false);
//            }
//        });
        datecurrent();

        return view;
    }

    public void datecurrent(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Log.e("currenttime",currentDateTimeString);

        DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
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
        if(tv_setreward.getText().toString().length()<1){
            Alert("Challenge must have a reward");
            return;
        }

        if(tv_totalprem1.getText().toString().equals("0")&tv_close_other_bus1.getText().toString().equals("0")&
                tv_contactsadded1.getText().toString().equals("0")&tv_appoint_set1.getText().toString().equals("0")&
                tv_went_on_kt1.getText().toString().equals("0")&tv_close_life1.getText().toString().equals("0")&
                tv_close_iba1.getText().toString().equals("0")){
            Alert("Challage must have at least one included activity type");
            return;
        }

        String tms= String.valueOf(System.currentTimeMillis());
        Log.e("update success","update success");

        Map m1 = new HashMap();
        m1.put("Appointments Set",tv_appoint_set1.getText().toString());
        m1.put("Closed IBA", tv_close_iba1.getText().toString());
        m1.put("Closed Life", tv_close_life1.getText().toString());
        m1.put("Closed Other Business", tv_close_other_bus1.getText().toString());
        m1.put("Contacts Added", tv_contactsadded1.getText().toString());
        m1.put("Total Premium", tv_totalprem1.getText().toString());
        m1.put("Went on KT", tv_went_on_kt1.getText().toString());

        Map newUserData = new HashMap();
        newUserData.put("activityCount",m1);
        newUserData.put("created",System.currentTimeMillis()+"");
        newUserData.put("currentLeader","");
        newUserData.put("endDate",tv_enddate.getText().toString()+" "+tv_endtime.getText().toString());
        newUserData.put("startDate",tv_startdate.getText().toString()+" "+tv_starttime.getText().toString());
        newUserData.put("finished","false");
        newUserData.put("finishedDate",tv_enddate.getText().toString()+" "+tv_endtime.getText().toString());
        newUserData.put("hasTimeLimit",false);
        newUserData.put("includedActivity",map);
        newUserData.put("Participants","");
        //newUserData.put("ref","1491079520");
        newUserData.put("reward",tv_setreward.getText().toString());
//        newUserData.put("startDate","1491079522");
        newUserData.put("title",title.getText().toString());
        newUserData.put("winner","");

        newUserData.put("ref", Constants.URL+"Solution Numbers/"+pref.getString("solution_number","")+"/contests/"+tms);

        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("contests").child(tms).updateChildren(newUserData, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Log.e("Solution Update","Solution Update");
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

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.tv_settitle){
            showDialog("New Name",title);
        }
        else if(v.getId()==R.id.tv_setreward){
            showDialog("SeT Reward",tv_setreward);
        }
        else if(v.getId()==R.id.tv_timelimit){
            if(lay_date.getVisibility()==View.VISIBLE)
                lay_date.setVisibility(View.GONE);
            else
           lay_date.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.tv_totalpremium){
            showDialog("New Amount",tv_totalprem1);
        }
        else if(v.getId()==R.id.tv_contactadded){
            showDialog("New Amount",tv_contactsadded1);
        }
        else if(v.getId()==R.id.tv_appointmentset){
            showDialog("New Amount",tv_appoint_set1);
        }
        else if(v.getId()==R.id.tv_wenton){
            showDialog("New Amount",tv_went_on_kt1);
        }
        else if(v.getId()==R.id.tv_closelife){
            showDialog("New Amount",tv_close_life1);
        }
        else if(v.getId()==R.id.tv_closeiba){
            showDialog("New Amount",tv_close_iba1);
        }
        else if(v.getId()==R.id.tv_closeotherbusiness){
            showDialog("New Amount",tv_close_other_bus1);
        }
        else if(v.getId()==R.id.lay_startdate){
            datedilog(true,tv_startdate,tv_starttime);
        }
        else if(v.getId()==R.id.lay_enddate){
            datedilog(true,tv_enddate,tv_endtime);
        }
    }
}
