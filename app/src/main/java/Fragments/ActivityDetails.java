package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Adapter.ActivityDetailAdapter;
import Adapter.ContactActivityListAdapter;
import model.AllActivity;
import model.Table;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 18-02-2017.
 */
public class ActivityDetails extends Fragment {
    ActivityDetailAdapter adapter;
    RecyclerView rview;
    View view;
    private Firebase mref;
    private SharedPreferences pref;

    String uid;


    ArrayList<Table> data=new ArrayList<>();
    private int quarter;
    private int myquarter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_detail, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);


        pref = getActivity().getSharedPreferences("userpref", 0);
        Firebase.setAndroidContext(getActivity());
        uid = pref.getString("uid", "");
        mref = new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        for(int i=0;i<=13;i++)
        {

          data.add(new Table());

        }


        rview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rview.setLayoutManager(layoutManager);

        rview.setAdapter(adapter);
        HomeActivity.title.setText("Activity");

        getnotefromfirebase();
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.drawable.calendar);
        menu.findItem(R.id.list).setVisible(true);
        menu.findItem(R.id.list).setIcon(R.mipmap.filter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // getActivity().getSupportFragmentManager().popBackStack();
//                showHelpDialog();
                break;

            case R.id.menu:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout, new ActivityFragments()).addToBackStack(null).commit();
                break;

            case R.id.list:
                // showFloatingMenus();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout, new Chart_Filter_Frag()).addToBackStack(null).commit();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    public void getnotefromfirebase() {
        mref.child("events")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server", dataSnapshot.getValue() + " data");

                        JSONArray jsonArray = new JSONArray();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            JSONObject jGroup = new JSONObject();
                            Log.e("childddd", child.child("contactName").getKey() + " abc");
                            //alue().toString(),child.child("created").getValue().toString(),child.child("date").getValue().toString(),child.child("eventKitID").getValue().toString(),child.child("ref").getValue().toString(),child.child("type").getValue().toString(),child.child("userName").getValue().toString(),child.child("userRef").getValue().toString()));
                            try {

                                jGroup.put("created", child.child("created").getValue().toString());

                                jGroup.put("ref", child.child("ref").getValue().toString());

                                String activity_list[]={"Set Appointment","Went on KT","Closed Life","Closed IBA","Closed Other Business","Appt Set To Closed Life",
                                        "Appt Set To Closed IBA","Invited to Opportunity Meeting","Went To Opportunity Meeting","Call Back","Dark House","Not Interested"};
                                Date date = new Date(Long.parseLong(child.child("created").getValue().toString()));

                                 quarter = (date.getMonth() / 3) + 1;
                                 myquarter=(new Date().getMonth()/3)+1;
                                switch (child.child("type").getValue().toString())

                                {



                                    case "Set Appointment":


                                        dosomething(date,1);






                                        break;

                                    case "Went on KT":


                                        dosomething(date,2);

                                        break;

                                    case "Closed Life":


                                        dosomething(date,3);

                                        break;


                                    case "Closed IBA":


                                        dosomething(date,4);

                                        break;


                                    case "Closed Other Business":

                                        dosomething(date,5);
                                        break;

                                    case "Appt Set To Closed Life":

                                        dosomething(date,6);

                                        break;


                                    case "Appt Set To Closed IBA":

                                        dosomething(date,7);

                                        break;

                                    case "Call Back":

                                        dosomething(date,8);

                                        break;


                                    case "Invited to Opportunity Meeting":

                                        dosomething(date,9);
                                        break;

                                    case "Went To Opportunity Meeting":

                                        dosomething(date,10);
                                        break;


                                    case "Dark House":

                                        dosomething(date,11);


                                        break;


                                    case "Not Intrested":

                                        dosomething(date,12);


                                        break;


















                                }


                            } catch (Exception e) {
                                Log.e("Exception", e + "");
                            }
                        }


                        adapter = new ActivityDetailAdapter(getActivity().getApplicationContext(),data);

                        rview.setAdapter(adapter);
                        Log.e("jsonarray", jsonArray + " abc");


                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                        Log.e("get data error", error.getMessage() + " data");
                    }
                });
    }


    public void dosomething(Date date,int i)
    {
        if(DateUtils.isToday(date.getTime()))

            data.get(i).today++;


        if(isDateInCurrentWeek(date))

            data.get(i).tw++;


        if(isDateCurrentMonth(date))

            data.get(i).tm++;


        if(isDateCurrentYear(date))

            data.get(i).ty++;








        if(quarter==myquarter)

            data.get(i).tq++;
    }


    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }


    public boolean isDateCurrentMonth(Date date) {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

//set the given date in one of the instance and current date in another
        cal1.setTime(date);
        cal2.setTime(new Date());

//now compare the dates using functions
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {

                return true;
            }


        }

        return false;
    }


    public boolean isDateCurrentYear(Date date) {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

//set the given date in one of the instance and current date in another
        cal1.setTime(date);
        cal2.setTime(new Date());

//now compare the dates using functions
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {

            return true;


        }

        return false;
    }


}




