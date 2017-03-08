package Fragments;

/**
 * Created by jattin on 3/7/2017.
 */

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.BarChart;
        import com.github.mikephil.charting.charts.LineChart;
        import com.github.mikephil.charting.data.BarData;
        import com.github.mikephil.charting.data.BarDataSet;
        import com.github.mikephil.charting.data.BarEntry;
        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.data.LineData;
        import com.github.mikephil.charting.data.LineDataSet;
        import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Adapter.personal_list_adapter;
import u.activitymanager.R;

public class ChartFragment extends Fragment {


    View v;
    private SharedPreferences pref;
    private Firebase mref;
    private LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v=inflater.inflate(R.layout.chart,container,false);

         lineChart = (LineChart)v.findViewById(R.id.chart);


        lineChart.setDescription("");

        lineChart.setDrawGridBackground(false);






        getnotefromfirebase();



        return v;
    }

    public void getnotefromfirebase()
    {

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        String uid=pref.getString("uid","");
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        mref.child("users")
                .child(uid).child("dailyPointAverages")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server",dataSnapshot.getValue()+" data");







                        HashMap<String,Long> map = (HashMap<String,Long>) dataSnapshot.getValue();



                        Map<Long,Long> map1 = new TreeMap<Long,Long>();


                        DateFormat dateFormat = new SimpleDateFormat("MMM dd");

                        for (Map.Entry<String,Long> entry : map.entrySet())
                        {


                            try {
                                map1.put(dateFormat.parse(entry.getKey()).getTime(),entry.getValue());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }





                        HashMap<String,Long> map2 = (HashMap<String,Long>) dataSnapshot.getValue();



                        ArrayList<String> labels = new ArrayList<String>();



                        int i=0;
                        ArrayList<Entry> entries = new ArrayList<>();

                        for (Map.Entry<Long,Long> entry : map1.entrySet())
                        {
                            Log.e("aaa",entry.getKey() + "/" + entry.getValue());



                            entries.add(new Entry(entry.getValue(), i));




                            labels.add(dateFormat.format(entry.getKey()));

                            i++;






                        }

                        LineDataSet dataset = new LineDataSet(entries, "");

                        LineData data = new LineData(labels, dataset);
                        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        dataset.setDrawCubic(true);
                        dataset.setDrawFilled(true);

                        lineChart.setData(data);
















                    }
                    @Override
                    public void onCancelled(FirebaseError error) {
                        Log.e("get data error",error.getMessage()+" data");
                    }
                });
    }





}
