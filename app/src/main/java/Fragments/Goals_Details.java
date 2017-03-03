package Fragments;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import u.activitymanager.R;
import utils.Constants;

/**
 * Created by surender on 2/17/2017.
 */

public class Goals_Details extends Fragment {

    View v;
    TextView tv_appointset,tv_closelife,tv_wentonkt,tv_total_prem,tv_close_bus,tv_contact_add,tv_closeiba;
    TextView tv_startdate,tv_enddate;
    ProgressBar bar_appointset,bar_closelife,bar_wentonkt,bar_total_prem,bar_close_bus,bar_contact_add,bar_closeiba;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.goals_details,container,false);
        setHasOptionsMenu(true);

        tv_appointset=(TextView)v.findViewById(R.id.tv_appointmentset_value);
        tv_closelife=(TextView)v.findViewById(R.id.tv_closedlife_value);
        tv_wentonkt=(TextView)v.findViewById(R.id.tv_went_on_kt_value);
        tv_total_prem=(TextView)v.findViewById(R.id.tv_total_premium_value);
        tv_close_bus=(TextView)v.findViewById(R.id.tv_close_bus_value);
        tv_contact_add=(TextView)v.findViewById(R.id.tv_contact_added_value);
        tv_closeiba=(TextView)v.findViewById(R.id.tv_close_iba_value);
        tv_startdate=(TextView)v.findViewById(R.id.tv_startdate);
        tv_enddate=(TextView)v.findViewById(R.id.tv_enddate);

        bar_appointset=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_closelife=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_wentonkt=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_total_prem=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_close_bus=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_contact_add=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_closeiba=(ProgressBar)v.findViewById(R.id.bar_appointment_set);

        try {
            JSONObject obj= Constants.jsonObject.getJSONObject("activityCount");

            tv_startdate.setText(Constants.jsonObject.getString("startDate"));
            tv_enddate.setText(Constants.jsonObject.getString("endDate"));

            tv_appointset.setText("0/"+obj.getString("Appointment Set"));
            tv_closelife.setText("0/"+obj.getString("Closed Life"));
            tv_wentonkt.setText("0/"+obj.getString("Went on KT"));
            tv_total_prem.setText("0/"+obj.getString("Total Premium"));
            tv_close_bus.setText("0/"+obj.getString("Closed Other Business"));
            tv_contact_add.setText("0/"+obj.getString("Contacts Added"));
            tv_closeiba.setText("0/"+obj.getString("Closed IBA"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
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
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
