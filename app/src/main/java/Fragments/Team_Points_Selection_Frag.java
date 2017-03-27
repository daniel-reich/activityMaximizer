package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Adapter.ListAllContactAdapter;
import Adapter.Team_Point_Adapter;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/10/2017.
 */
public class Team_Points_Selection_Frag extends Fragment
{
    View v;
    SharedPreferences pref;
    RecyclerView rview;
    LinearLayoutManager linearManager;
    Team_Point_Adapter adapter;

    JSONArray json;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.team_points_selection_frag,container,false);
        setHasOptionsMenu(true);
        rview=(RecyclerView)v.findViewById(R.id.rview);
        linearManager=new LinearLayoutManager(getActivity());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("");




        pref=getActivity().getSharedPreferences("userpref",0);

        try {


            ;

            if(pref.getString("team","").length()>0)
            json=new JSONArray(pref.getString("team",""));
            else
                json=new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            adapter=new Team_Point_Adapter(json);
        } catch (Exception e) {


        }
        rview.setLayoutManager(linearManager);
        rview.setAdapter(adapter);



        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(true);
        menu.findItem(R.id.menu).setTitle("Save");
//        menu.findItem(R.id.list).setVisible(false);
        //   menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:

                SharedPreferences.Editor edit=pref.edit();

                edit.putString("team",json.toString());
                edit.commit();
                getFragmentManager().popBackStack();

                break;

            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
