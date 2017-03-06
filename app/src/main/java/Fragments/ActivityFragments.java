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
import Adapter.PostsAdapter;
import u.activitymanager.HomeActivity;
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
    List<String> list;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private Firebase mref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.posts,container,false);
        setHasOptionsMenu(true);
        activities=(RecyclerView)view.findViewById(R.id.rview);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        activities.setLayoutManager(linearLayoutManager);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Activity");

        pref=getActivity().getSharedPreferences("userpref",0);

        String e_date=pref.getString("filter_enddate","null");
        String s_date=pref.getString("filter_startdate","null");

        if(e_date.equals("null")|s_date.equals("null")) {
        }
        else {
            list = getDates(s_date, e_date);

            adapter = new ActivitiesAdapter(getActivity(), list);
            activities.setAdapter(adapter);
        }


        getdatafromfirebase();
        return view;
    }


    public JSONArray getdatafromfirebase()
    {

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        final JSONArray array=new JSONArray();
        pref=getActivity().getSharedPreferences("userpref",0);

        mref.child("events").child(pref.getString("uid","")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {


                    String e_date=pref.getString("filter_enddate","null");
                    String s_date=pref.getString("filter_startdate","null");


                    DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");



                    Date start_date;





                    Log.e("filter",e_date+","+s_date);

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        JSONObject json=new JSONObject();

                         String key=messageSnapshot.getKey();

                        Log.e("key",key);

                        Log.e("message",messageSnapshot.toString());


                        Date date=new Date(Long.parseLong(key));





                        array.put(json);

                    }





                    Log.e("json",array.toString());


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





    private static List<String> getDates(String dateString1, String dateString2)
    {
        ArrayList<String> dates = new ArrayList<String>();
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
                dates.add(formattedDate);

            }catch (Exception e){

            }
//            dates.add((cal1.get(Calendar.MONTH)+1)+"-"+cal1.get(Calendar.DAY_OF_MONTH)+"-"+cal1.get(Calendar.YEAR));
            cal1.add(Calendar.DATE, 1);
        }

        for(int i=0;i<dates.size();i++)
            Log.e("dates",dates.get(i));

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
}
