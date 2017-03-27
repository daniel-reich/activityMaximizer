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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.Challange_Details_adap;
import Adapter.Current_Goals_adap;
import u.activitymanager.R;
import utils.Constants;


public class Challange_details extends Fragment {

    TextView tv_challangename,tv_reward,tv_startdate,tv_enddate;
    View v;
    Firebase mref;

    RecyclerView rview;
    Challange_Details_adap adapter;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String st_currentLeader;
    ArrayList<String> currentleader_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.challanges_details,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        tv_challangename=(TextView)v.findViewById(R.id.tv_challage);
        tv_enddate=(TextView)v.findViewById(R.id.tv_enddate);
        tv_startdate=(TextView)v.findViewById(R.id.tv_startdate);
        tv_reward=(TextView)v.findViewById(R.id.tv_reward);
        rview=(RecyclerView)v.findViewById(R.id.recyclerview);

        currentleader_list=new ArrayList<>();

        rview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rview.setLayoutManager(layoutManager);

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        mref=new Firebase(Constants.URL);

        String strtext = getArguments().getString("data");

        try {

            JSONObject obj=new JSONObject(strtext);
            tv_challangename.setText(obj.getString("title"));
            tv_enddate.setText(obj.getString("endDate"));
            tv_startdate.setText(obj.getString("startDate"));
            tv_reward.setText(obj.getString("reward"));
             currentleader_list.add(obj.getString("currentLeader"));

            adapter=new Challange_Details_adap(getActivity(),currentleader_list);
            rview.setAdapter(adapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("Save").setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
            //    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
