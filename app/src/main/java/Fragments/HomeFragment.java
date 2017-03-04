package Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.DoubleNode;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView team,personal;
    Dialog helpdialog;
    LinearLayout lay_day;
    Firebase mref;
    FirebaseAuth firebaseAuth;
    SharedPreferences pref;
    String time="";
    Date date,d;
    TextView tvDay,tvHour,tvMinute,tvSecond;
    String start_date,end_date;
    SharedPreferences.Editor edit;
    private Handler handler;
    private Runnable runnable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();
        team=(TextView)view.findViewById(R.id.team_button);
        personal=(TextView)view.findViewById(R.id.personal_button);
        lay_day=(LinearLayout)view.findViewById(R.id.days_lyt);
        team.setOnClickListener(this);
        personal.setOnClickListener(this);
        team.setSelected(false);
        personal.setSelected(true);
        tvDay=(TextView)view.findViewById(R.id.days);
        tvHour=(TextView)view.findViewById(R.id.hours);
        tvMinute=(TextView)view.findViewById(R.id.min);
        tvSecond=(TextView)view.findViewById(R.id.sec);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Home");
        Firebase.setAndroidContext(getActivity());
        pref=getActivity().getSharedPreferences("userpref",0);
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();
        getTime();
        lay_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

    private void getTime()
    {

        mref.child("Month End").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("timestamp", dataSnapshot.getValue() + "");
                Map map = dataSnapshot.getValue(Map.class);

                Log.e("date", map.get("date").toString());

                time = map.get("date").toString();
                Double dbl= Double.parseDouble(time);
                try {
                    date = new Date();
                    date.setTime(dbl.longValue() * 1000);
                    Log.e("date_time", date + "");
//                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                    date= df.parse(date+"");
//                    end_date=date+"";
//                    Calendar calobj = Calendar.getInstance();
//                    start_date=df.format(calobj.getTime());

//                    Date now = new Date();
//                    now.setTime(System.currentTimeMillis()/1000);
//                    Log.e("now",now+"");

                 countDownStart();
//                    try{
//                      //  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:sss");
//
//
//                        if (d.compareTo(date)<0)
//                        {
//                            Log.e("greater","date is Greater");
//                            calculateDifference(d,date);
//
//                        }
//                        else
//                        {
//                            Log.e("greater","d is Greater");
//
//
//                        }

                    }catch (Exception e1){
                        e1.printStackTrace();
                    }


                }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });
    }







    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    d=new Date();
                    Long lng= System.currentTimeMillis()/1000;
                   // Log.e("lng",lng+"");
                    d.setTime(lng*1000);
                   // Log.e("now",d+"");
                    if (!d.after(date)) {
                        Log.e("start","start");
                        long diff = date.getTime()
                                - d.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        Log.e("timer",seconds+"");
                        tvDay.setText(days+"");
                        tvHour.setText(hours+"");
                        tvMinute.setText(minutes+"");
                        tvSecond.setText(seconds+"");
                    } else {
                           Log.e("stop","stop");
                        handler.removeCallbacks(runnable);
                        // handler.removeMessages(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("e","excptn",e);
                }
            }
        };
        handler.postDelayed(runnable, 0);
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
                showFloatingMenus();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void showFloatingMenus() {

        Fragments.FloatingMenusDialog dialog=new FloatingMenusDialog();
        dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), "");
        dialog.setCancelable(true);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.team_button:
                personal.setSelected(false);
                team.setSelected(true);

                break;
            case R.id.personal_button:
                team.setSelected(false);
                personal.setSelected(true);
                break;
        }

    }
    public void calculateDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Log.e("days", elapsedDays+","+ elapsedHours+","+ elapsedMinutes+","+ elapsedSeconds);

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

        TextView tv_cancel=(TextView)helpdialog.findViewById(R.id.tv_cancel);
        TextView tv_report=(TextView)helpdialog.findViewById(R.id.tv_report);
        TextView tv_tutorial=(TextView)helpdialog.findViewById(R.id.tv_tutorial);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpdialog.dismiss();
            }
        });

        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URI="mailto:surender9466073570@gmail.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse(URI);
                intent.setData(data);
                startActivity(intent);
//                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                intent.setType("text/html");
//                intent.setData(Uri.parse("mailto:" + "recipient@example.com"));
//                List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
//
//                if (!resInfo.isEmpty()) {
//                    for (ResolveInfo info : resInfo) {
//                        if (info.activityInfo.packageName.toLowerCase().contains("gmail") || info.activityInfo.name.toLowerCase().contains("gmail"))
//                        {
//                            intent.putExtra(android.content.Intent.EXTRA_TEXT, "extra text");
//
//                            intent.setPackage(info.activityInfo.packageName);
//                            startActivity(Intent.createChooser(intent, "Send email.."));
//                        }
//                    }
//                }
            }
        });

        helpdialog.show();
    }

}
