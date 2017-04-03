package Fragments;

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
import android.text.TextUtils;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import Adapter.ActivitiesAdapter;
import model.Activities;
import model.AllDownlines;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ActivityFragments  extends Fragment
{
    RecyclerView activities;
    LinearLayoutManager linearLayoutManager;
    ActivitiesAdapter adapter;
    Dialog helpdialog;
    View view;
    List<Activities> list;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private Firebase mref;
    JSONObject obj;
    String uid;
    int count=0;
    JSONObject checkfilter;
    ArrayList<AllDownlines> data,datateam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.posts,container,false);
        setHasOptionsMenu(true);
        activities=(RecyclerView)view.findViewById(R.id.rview);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        activities.setLayoutManager(linearLayoutManager);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("Activity");

        pref=getActivity().getSharedPreferences("userpref",0);
        uid=pref.getString("uid","");

       // Toast.makeText(getActivity(), f+" acccc", Toast.LENGTH_SHORT).show();
      /*  DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 15);
        Date  date1 = calendar.getTime();

        String e_date=pref.getString("filter_enddate",targetFormat.format(date1));
        String s_date=pref.getString("filter_startdate",targetFormat.format(new Date()));*/
        DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 15);
        Date  date1 = calendar.getTime();

        String e_date=pref.getString("filter_enddate",targetFormat.format(date1));
        String s_date=pref.getString("filter_startdate",targetFormat.format(new Date()));






        Date start_date;





        Log.e("filter",e_date+","+s_date);

        list = getDates(s_date, e_date);
       // getdatafromfirebase(uid);
        String f=pref.getString("filter","");
        Log.e("filter_pref",f+"value");
        try {
            if (!TextUtils.isEmpty("f")) {
                checkfilter = new JSONObject(f);
                if (checkfilter.getString("personal").equalsIgnoreCase("true")) {
                    getdatafromfirebase(uid);
                }
                if (checkfilter.getString("team").equalsIgnoreCase("true")) {
                    getalldownlinesuidfromfirebase();
                    getalldownlinesbaseuidfromfirebase();
                }
                if (checkfilter.getString("trainees").equalsIgnoreCase("true")) {

                    getalltrdownlinesuidfromfirebase();
                } else {
                    adapter = new ActivitiesAdapter(getActivity(), list);
                    activities.setAdapter(adapter);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            adapter = new ActivitiesAdapter(getActivity(), list);
            activities.setAdapter(adapter);

        }


        return view;
    }


    public JSONArray getdatafromfirebase(String uid)
    {

         Log.e("uid",uid+"");
        pref.getString("filter","");
        obj=new JSONObject();

        try {
            obj=new JSONObject(pref.getString("filter",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        final JSONArray array=new JSONArray();
//        pref=getActivity().getSharedPreferences("userpref",0);

        mref.child("events").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {




                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        JSONObject json=new JSONObject();

                         String key=messageSnapshot.getKey();

                        Log.e("key",key);

                        Log.e("message",messageSnapshot.toString());


                        Date date=new Date(Long.parseLong(key));

                        boolean result = false;

                       switch(messageSnapshot.child("type").getValue().toString())

                       {

                           case "Set Appointment":

                               if(obj.has("appointmentset")) {
                                   result = obj.getBoolean("appointmentset");
                               }
                               else
                               result=true;

                               break;

                           case "Went on KT":

                               if(obj.has("went_on_kt")) {
                                   result = obj.getBoolean("went_on_kt");
                               }
                               else
                                   result=true;

                               break;

                           case "Closed Life":

                               if(obj.has("closelife")) {
                                   result = obj.getBoolean("closelife");
                               }
                               else
                                   result=true;
                               break;


                           case "Closed IBA":


                               if(obj.has("closeiba")) {
                                   result = obj.getBoolean("closeiba");
                               }
                               else
                                   result=true;
                               break;


                           case "Closed Other Business":

                               if(obj.has("closeotherbusiness")) {
                                   result = obj.getBoolean("closeotherbusiness");
                               }
                               else
                                   result=true;
                               break;

                           case "Appt Set To Closed Life":

                               if(obj.has("appt_closelife")) {
                                   result = obj.getBoolean("appt_closelife");
                               }
                               else
                                   result=true;
                               break;


                           case "Appt Set To Closed IBA":

                               if(obj.has("appt_closeiba")) {
                                   result = obj.getBoolean("appt_closeiba");
                               }
                               else
                                   result=true;
                               break;

                           case "Call Back":

                               if(obj.has("callback")) {
                                   result = obj.getBoolean("callback");
                               }
                               else
                                   result=true;
                               break;


                           case "Invited to Opportunity Meeting":

                               if(obj.has("invite_oppt_meeting")) {
                                   result = obj.getBoolean("invite_oppt_meeting");
                               }
                               else
                                   result=true;
                               break;

                           case "Went To Opportunity Meeting":

                               if(obj.has("oppt_meeting")) {
                                   result = obj.getBoolean("oppt_meeting");
                               }
                               else
                                   result=true;
                               break;


                           case "Dark House":

                               if(obj.has("darkhouse")) {
                                   result = obj.getBoolean("darkhouse");
                               }
                               else
                                   result=true;
                               break;

                           case "Not Intrested":

                               if(obj.has("notinterested")) {
                                   result = obj.getBoolean("notinterested");
                               }
                               else
                                   result=true;
                               break;

                       }




                        if(result)
                        for(int i=0;i<list.size();i++)

                        {
                            if(isSameDay(date,new Date(list.get(i).getTime())))

                            {
                                if(list.get(i).getJson().length()<=0)
                                {

                                    JSONArray json_data=new JSONArray();

                                    JSONObject json_obj=new JSONObject();

                                    json_obj.put("userName",messageSnapshot.child("userName").getValue().toString());
                                    json_obj.put("contactName",messageSnapshot.child("contactName").getValue().toString());
                                    json_obj.put("type",messageSnapshot.child("type").getValue().toString());
                                    json_obj.put("date",messageSnapshot.child("date").getValue().toString());

                                    Log.e("json",json_obj+"");
                                    json_data.put(json_obj);



                                    list.get(i).setJson(json_data.toString());

                                }

                                else

                                {
                                    JSONArray json_data=new JSONArray(list.get(i).getJson());

                                    JSONObject json_obj=new JSONObject();


                                    json_obj.put("userName",messageSnapshot.child("userName").getValue().toString());
                                    json_obj.put("contactName",messageSnapshot.child("contactName").getValue().toString());
                                    json_obj.put("type",messageSnapshot.child("type").getValue().toString());
                                    json_obj.put("date",messageSnapshot.child("date").getValue().toString());

                                    json_data.put(json_obj);

                                    Log.e("json",json_obj+"");

                                    list.get(i).setJson(json_data.toString());
                                }

                            }

                        }


                        //array.put(json);

                    }












                       adapter = new ActivitiesAdapter(getActivity(), list);
                      activities.setAdapter(adapter);






                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("array_exception","e",e);
                }

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });

        return array;

    }


    private boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        boolean sameYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
        boolean sameMonth = calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        boolean sameDay = calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
        return (sameDay && sameMonth && sameYear);
    }





    private static List<Activities> getDates(String dateString1, String dateString2)
    {
        ArrayList<Activities> dates = new ArrayList<Activities>();
        DateFormat df1 = new SimpleDateFormat("MMM dd,yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);//
            date2 = df1 .parse(dateString2);//dateString2
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("dateparse","e",e);
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            int  year = cal1.get(Calendar.YEAR);
            int  month = cal1.get(Calendar.MONTH)+1;
            int  day = cal1.get(Calendar.DAY_OF_MONTH);

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
                dates.add(new Activities(date.getTime(),""));

            }catch (Exception e){

            }
//            dates.add((cal1.get(Calendar.MONTH)+1)+"-"+cal1.get(Calendar.DAY_OF_MONTH)+"-"+cal1.get(Calendar.YEAR));
            cal1.add(Calendar.DATE, 1);
        }



        return dates;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.mipmap.activity_detail);
       // menu.findItem(R.id.menu).setTitle("Add List");
        menu.findItem(R.id.list).setVisible(true);
        menu.findItem(R.id.list).setIcon(R.mipmap.filter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                // getActivity().getSupportFragmentManager().popBackStack();
                showHelpDialog();
                break;

            case R.id.menu:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new ActivityDetails()).addToBackStack(null).commit();
                break;

            case R.id.list:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new Filter_Frag()).addToBackStack(null).commit();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        helpdialog=new Dialog(getActivity());
        helpdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpdialog.setContentView(R.layout.help_dialog);
        Window window = helpdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpdialog.show();



    }


    public void getalldownlinesuidfromfirebase()
    {
        Log.e("testing1",pref.getString("solution_number","")+" abc");
        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("downlines").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server1",dataSnapshot.getValue()+" data");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("testing1",child.getKey()+" abc");
                   // if(dataSnapshot.child("trainer_solution_number").getValue().toString().equals(""))
                   getallteamdownlinesfromfirebase(child.getKey());
                    count++;

                }


            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void getalldownlinesbaseuidfromfirebase()
    {
        Log.e("testing1",pref.getString("solution_number","")+" abc");
        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("Base").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server2",dataSnapshot.getValue()+" data");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("testing2",child.getKey()+" abc");
                   getdatafromfirebase(child.getKey());
                   count++;
                }


            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


    public void getalltrdownlinesuidfromfirebase()
    {
        Log.e("testing1",pref.getString("solution_number","")+" abc");
        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("downlines").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("testing",child.getKey()+" abc");

                    getalldownlinesfromfirebase(child.getKey());


                }


            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


    public void getalldownlinesfromfirebase(final String uid)
    {
        mref.child("users").child(uid).addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                 data=new ArrayList<AllDownlines>();
                if(!dataSnapshot.child("trainer_solution_number").getValue().toString().equals(""))

                {

                    String s="";

                    if(dataSnapshot.child("givenName").getValue()!=null)

                        s=dataSnapshot.child("givenName").getValue().toString();

                    else
                        s= dataSnapshot.child("givename").getValue().toString();

                    data.add(new AllDownlines(uid,String.valueOf(s),String.valueOf(dataSnapshot.child("fivePointClients").getValue().toString()),String.valueOf(dataSnapshot.child("fivePointRecruits").getValue().toString())));


                }




                if(data.size()>0)
                for(int i=0;i<data.size();i++)
                {
                    getdatafromfirebase(data.get(i).getUid());
                }






            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }




    public void getallteamdownlinesfromfirebase(final String uid)
    {
        mref.child("users").child(uid).addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");

                datateam=new ArrayList<AllDownlines>();
                if(dataSnapshot.child("trainer_solution_number").getValue().toString().equals("")) {
                    datateam.add(new AllDownlines(uid, dataSnapshot.child("givenName").getValue().toString(), dataSnapshot.child("fivePointClients").getValue().toString(), dataSnapshot.child("fivePointRecruits").getValue().toString()));

                }
                if(datateam.size()>0)
                for(int i=0;i<datateam.size();i++)
                {
                    getdatafromfirebase(datateam.get(i).getUid());
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }





}
