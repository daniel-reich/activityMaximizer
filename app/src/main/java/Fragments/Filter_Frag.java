package Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.IWheelPicker;
import com.aigestudio.wheelpicker.WheelPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.ActivitiesAdapter;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class Filter_Frag extends Fragment {
    View view;
    LinearLayout lay_team,lay_startdate,lay_enddate;
    CheckBox cb_team,cb_default,cb_trainees,cb_personal,cb_went_kt,cb_closelife,cb_closeiba,cb_closeotherbus;
    CheckBox cb_appt_closelife,cb_appt_closeiba,cb_callback,cb_darkhouse,cb_notinterested;
    ImageView iv_team,iv_default,iv_trainees,iv_personal,iv_went_kt,iv_closelife,iv_closeiba,iv_closeotherbus;
    ImageView iv_appt_closelife,iv_appt_closeiba,iv_callback,iv_darkhouse,iv_notinterested;
    IWheelPicker wheel_day,wheel_month,wheel_year;
    ArrayList<String> datelist,monthlist,yearlist;
    long millsec;
    TextView tv_startdate,tv_enddate;
    Dialog dialog;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    JSONObject obj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.filter_page,container,false);
        setHasOptionsMenu(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Filters");

        pref=getActivity().getSharedPreferences("userpref",0);

        cb_default=(CheckBox)view.findViewById(R.id.cb_default);
        cb_team=(CheckBox)view.findViewById(R.id.cb_team);
        cb_trainees=(CheckBox)view.findViewById(R.id.cb_trainees);
        cb_personal=(CheckBox)view.findViewById(R.id.cb_personal);
        cb_went_kt=(CheckBox)view.findViewById(R.id.cb_wenton);
        cb_closelife=(CheckBox)view.findViewById(R.id.cb_closelife);
        cb_closeiba=(CheckBox)view.findViewById(R.id.cb_closeiba);
        cb_closeotherbus=(CheckBox)view.findViewById(R.id.cb_closeotherbusiness);
        cb_appt_closelife=(CheckBox)view.findViewById(R.id.cb_apptsettocloselife);
        cb_appt_closeiba=(CheckBox)view.findViewById(R.id.cb_apptsettocloseiba);
        cb_callback=(CheckBox)view.findViewById(R.id.cb_callback);
        cb_darkhouse=(CheckBox)view.findViewById(R.id.cb_darkhouse);
        cb_notinterested=(CheckBox)view.findViewById(R.id.cb_notinterested);

        iv_default=(ImageView) view.findViewById(R.id.iv_default);
        iv_team=(ImageView) view.findViewById(R.id.iv_team);
        iv_trainees=(ImageView) view.findViewById(R.id.iv_trainees);
        iv_personal=(ImageView) view.findViewById(R.id.iv_personal);
        iv_went_kt=(ImageView) view.findViewById(R.id.iv_wenton);
        iv_closelife=(ImageView) view.findViewById(R.id.iv_closelife);
        iv_closeiba=(ImageView)view.findViewById(R.id.iv_closeiba);
        iv_closeotherbus=(ImageView)view.findViewById(R.id.iv_closeotherbusiness);
        iv_appt_closelife=(ImageView)view.findViewById(R.id.iv_apptsettocloselife);
        iv_appt_closeiba=(ImageView)view.findViewById(R.id.iv_apptsettocloseiba);
        iv_callback=(ImageView)view.findViewById(R.id.iv_callback);
        iv_darkhouse=(ImageView)view.findViewById(R.id.iv_darkhouse);
        iv_notinterested=(ImageView)view.findViewById(R.id.iv_notinterested);

        lay_startdate=(LinearLayout)view.findViewById(R.id.lay_startdate);
        lay_enddate=(LinearLayout)view.findViewById(R.id.lay_enddate);
        tv_startdate=(TextView)view.findViewById(R.id.tv_startdate);
        tv_enddate=(TextView)view.findViewById(R.id.tv_enddate);

        setdata(pref.getString("filter",""));

        obj=new JSONObject();

        try {
            obj=new JSONObject(pref.getString("filter",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        getDates(" "," ");

        String e_date=pref.getString("filter_enddate","null");
        String s_date=pref.getString("filter_startdate","null");

        if(e_date.equals("null")|s_date.equals("null")){

            final Calendar cal=Calendar.getInstance();

           int  year = cal.get(Calendar.YEAR);
           int  month = cal.get(Calendar.MONTH)+1;
           int  day = cal.get(Calendar.DAY_OF_MONTH);

            String mon,dy;
                    mon=month+"";
                    dy=day+"";
                    if(month < 10){
                        mon = "0" + month;
                    }
                    if(day < 10){
                        dy  = "0" + day;
                    }

                    String org_dt=mon+"-"+dy+"-"+year;

                    DateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
                    DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");
                    Date date = null;

                    try {

                        date = originalFormat.parse(org_dt);
                        String formattedDate = targetFormat.format(date);
                        Log.e("formatted date", formattedDate);

                        tv_startdate.setText(formattedDate);
                        tv_enddate.setText(formattedDate);

                    }catch (Exception e){

                    }

                }
                else
                {
                    tv_startdate.setText(s_date);
                    tv_enddate.setText(e_date);
                }

        lay_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedilog(true,tv_startdate);
            }
        });

        lay_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedilog(false,tv_enddate);
            }
        });

//        wheel_day=(IWheelPicker)view.findViewById(R.id.wheel_date);
//        wheel_month=(IWheelPicker)view.findViewById(R.id.wheel_month);
//        wheel_year=(IWheelPicker)view.findViewById(R.id.wheel_year);
//
//        datelist=new ArrayList<>();
//        monthlist=new ArrayList<>();
//        yearlist=new ArrayList<>();
//
//        for(int i=1;i<32;i++)
//            datelist.add(i+"");
//
//        for(int i=2000;i<2500;i++) {
//                monthlist.add(i + "");
//        }
//
//        monthlist.add("January");
//        monthlist.add("February");
//        monthlist.add("March");
//        monthlist.add("April");
//        monthlist.add("May");
//        monthlist.add("June");
//        monthlist.add("July");
//        monthlist.add("August");
//        monthlist.add("September");
//        monthlist.add("October");
//        monthlist.add("November");
//        monthlist.add("December");
//
//        wheel_day.setData(datelist);
//        wheel_month.setData(monthlist);
//        wheel_year.setData(yearlist);
//
//        wheel_day.setOnItemSelectedListener(this);
//        wheel_month.setOnItemSelectedListener(this);
//        wheel_year.setOnItemSelectedListener(this);

//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };

        cb_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_default.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("default",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("default",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_default.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_team.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_team.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("team",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("team",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_team.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_trainees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_trainees.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("trainees",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("trainees",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_trainees.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_personal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_personal.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("personal",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("personal",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_personal.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_went_kt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_went_kt.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("went_on_kt",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("went_on_kt",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_went_kt.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });

        cb_closelife.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_closelife.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("closelife",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("closelife",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_closelife.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_closeiba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_closeiba.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("closeiba",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("closeiba",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_closeiba.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_closeotherbus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_closeotherbus.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("closeotherbusiness",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("closeotherbusiness",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_closeotherbus.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_appt_closelife.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_appt_closelife.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("appt_closelife",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("appt_closelife",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_appt_closelife.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_appt_closeiba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_appt_closeiba.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("appt_closeiba",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("appt_closeiba",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_appt_closeiba.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_callback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_callback.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("callback",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("callback",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_callback.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_darkhouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_darkhouse.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("darkhouse",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("darkhouse",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_darkhouse.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });
        cb_notinterested.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    iv_notinterested.setImageResource(R.drawable.checkmark_bl24);
                    try {
                        obj.put("notinterested",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        obj.put("notinterested",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    iv_notinterested.setImageBitmap(null);
                }
                edit=pref.edit();
                edit.putString("filter",obj+"");
                edit.commit();
            }
        });

        return view;
    }

    public void setdata(String val)
    {
        Log.e("value",val);
        try {

            JSONObject object=new JSONObject(val);
            Log.e("value",object+"");

            if(object.has("default")) {
                if (object.getBoolean("default")) {
                    cb_default.setChecked(true);
                    iv_default.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_default.setChecked(false);
                    iv_default.setImageBitmap(null);
                }
            }

            if(object.has("team")) {
                if (object.getBoolean("team")) {
                    cb_team.setChecked(true);
                    iv_team.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_team.setChecked(false);
                    iv_team.setImageBitmap(null);
                }
            }

            if(object.has("trainees")) {
                if (object.getBoolean("trainees")) {
                    cb_trainees.setChecked(true);
                    iv_trainees.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_trainees.setChecked(false);
                    iv_trainees.setImageBitmap(null);
                }
            }

            if(object.has("personal")) {
                if (object.getBoolean("personal")) {
                    cb_personal.setChecked(true);
                    iv_personal.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_personal.setChecked(false);
                    iv_personal.setImageBitmap(null);
                }
            }

            if(object.has("went_on_kt")) {
                if (object.getBoolean("went_on_kt")) {
                    cb_went_kt.setChecked(true);
                    iv_went_kt.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_went_kt.setChecked(false);
                    iv_went_kt.setImageBitmap(null);
                }
            }

            if(object.has("closelife")) {
                if (object.getBoolean("closelife")) {
                    cb_closelife.setChecked(true);
                    iv_closelife.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_closelife.setChecked(false);
                    iv_closelife.setImageBitmap(null);
                }
            }

            if(object.has("closeiba")) {
                if (object.getBoolean("closeiba")) {
                    cb_closeiba.setChecked(true);
                    iv_closeiba.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_closeiba.setChecked(false);
                    iv_closeiba.setImageBitmap(null);
                }
            }

            if(object.has("closeotherbusiness")) {
                if (object.getBoolean("closeotherbusiness")) {
                    cb_closeotherbus.setChecked(true);
                    iv_closeotherbus.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_closeotherbus.setChecked(false);
                    iv_closeotherbus.setImageBitmap(null);
                }
            }

            if(object.has("appt_closelife")) {
                if (object.getBoolean("appt_closelife")) {
                    cb_appt_closelife.setChecked(true);
                    iv_appt_closelife.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_appt_closelife.setChecked(false);
                    iv_appt_closelife.setImageBitmap(null);
                }
            }

            if(object.has("appt_closeiba")) {
                if (object.getBoolean("appt_closeiba")) {
                    cb_appt_closeiba.setChecked(true);
                    iv_appt_closeiba.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_appt_closeiba.setChecked(false);
                    iv_appt_closeiba.setImageBitmap(null);
                }
            }

            if(object.has("callback")) {
                if (object.getBoolean("callback")) {
                    cb_callback.setChecked(true);
                    iv_callback.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_callback.setChecked(false);
                    iv_callback.setImageBitmap(null);
                }
            }

            if(object.has("darkhouse")) {
                if (object.getBoolean("darkhouse")) {
                    cb_darkhouse.setChecked(true);
                    iv_darkhouse.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_darkhouse.setChecked(false);
                    iv_darkhouse.setImageBitmap(null);
                }
            }

            if(object.has("notinterested")) {
                if (object.getBoolean("notinterested")) {
                    cb_notinterested.setChecked(true);
                    iv_notinterested.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_notinterested.setChecked(false);
                    iv_notinterested.setImageBitmap(null);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("setdata exception","e",e);
        }
    }

    public void datedilog(final boolean b, final TextView tv)
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

        final Calendar cal=Calendar.getInstance();

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());
                cal.set(Calendar.MONTH, dp.getMonth());
                cal.set(Calendar.YEAR, dp.getYear());
                millsec=cal.getTimeInMillis() /1000;
                Log.e("millisecond",millsec+"");

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

                Log.e("date",mon+"-"+dy+"-"+dp.getYear());
                String org_dt=mon+"-"+dy+"-"+dp.getYear();

                DateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
                DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");
                Date date = null;

                try {

                    date = originalFormat.parse(org_dt);
                    String formattedDate = targetFormat.format(date);
                    tv.setText(formattedDate);
                    Log.e("formatted date",formattedDate);

                    edit=pref.edit();
                    if(b) {
                        edit.putString("filter_startdate", tv.getText().toString());
                        edit.putString("filter_enddate", tv_enddate.getText().toString());
                    }
                    else {
                        edit.putString("filter_startdate", tv_startdate.getText().toString());
                        edit.putString("filter_enddate", tv.getText().toString());
                    }

                    edit.commit();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
        // menu.findItem(R.id.menu).setTitle("Add List");
        menu.findItem(R.id.list).setVisible(false);
//        menu.findItem(R.id.list).setIcon(R.mipmap.filter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                 getActivity().getSupportFragmentManager().popBackStack();
                break;

            case R.id.menu:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ActivityDetails()).addToBackStack(null).commit();
                break;

            case R.id.list:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ActivityDetails()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
