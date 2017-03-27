package Fragments;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    TextView tv_startdate,tv_enddate,tv_percentage;
    ProgressBar bar_appointset,bar_closelife,bar_wentonkt,bar_total_prem,bar_close_bus,bar_contact_add,bar_closeiba;

    int percentage=0;

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
        tv_percentage=(TextView)v.findViewById(R.id.tv_percentage);

        bar_appointset=(ProgressBar)v.findViewById(R.id.bar_appointment_set);
        bar_closelife=(ProgressBar)v.findViewById(R.id.bar_close_life);
        bar_wentonkt=(ProgressBar)v.findViewById(R.id.bar_went_on_kt);
        bar_total_prem=(ProgressBar)v.findViewById(R.id.bar_totoal_premium);
        bar_close_bus=(ProgressBar)v.findViewById(R.id.bar_close_other_bus);
        bar_contact_add=(ProgressBar)v.findViewById(R.id.bar_contact_added);
        bar_closeiba=(ProgressBar)v.findViewById(R.id.bar_close_iba);

        percentage=getArguments().getInt("percentage");

        tv_percentage.setText(percentage+"%");

        try {
            JSONObject obj= Constants.jsonObject.getJSONObject("activityCount");
            JSONObject obj2= Constants.jsonObject.getJSONObject("currentCount");

            Log.e("obj",obj+"");
            Log.e("obj1",obj2+"");

            tv_startdate.setText(Constants.jsonObject.getString("startDate"));
            tv_enddate.setText(Constants.jsonObject.getString("endDate"));

            if(obj2.has("Appointment Set")) {
                int val=Integer.valueOf(obj2.getString("Appointment Set"));
                int val1=Integer.valueOf(obj.getString("Appointment Set"));
                tv_appointset.setText(val+"/" + obj.getString("Appointment Set"));

                bar_appointset.setProgress(percentage(val,val1));
            }
            else
                tv_appointset.setText("0/" + obj.getString("Appointment Set"));

            if(obj2.has("Closed Life")) {
                int val=Integer.valueOf(obj2.getString("Closed Life"));
                int val1=Integer.valueOf(obj.getString("Closed Life"));
                tv_closelife.setText(val+"/" + obj.getString("Closed Life"));

                bar_closelife.setProgress(percentage(val,val1));
            }
            else
            tv_closelife.setText("0/"+obj.getString("Closed Life"));

            if(obj2.has("Went on KT")) {
                int val=Integer.valueOf(obj2.getString("Went on KT"));
                int val1=Integer.valueOf(obj.getString("Went on KT"));
                tv_wentonkt.setText(val+"/" + obj.getString("Went on KT"));

                bar_wentonkt.setProgress(percentage(val,val1));
            }
            else
            tv_wentonkt.setText("0/"+obj.getString("Went on KT"));

            if(obj2.has("Total Premium")) {
                int val=Integer.valueOf(obj2.getString("Total Premium"));
                int val1=Integer.valueOf(obj.getString("Total Premium"));
                tv_total_prem.setText(val+"/" + obj.getString("Total Premium"));
                bar_total_prem.setProgress(percentage(val,val1));
            }
            else
            tv_total_prem.setText("0/"+obj.getString("Total Premium"));

            if(obj2.has("Closed Other Business")) {
                int val=Integer.valueOf(obj2.getString("Closed Other Business"));
                int val1=Integer.valueOf(obj.getString("Closed Other Business"));
                tv_close_bus.setText(val+"/" + obj.getString("Closed Other Business"));

                bar_close_bus.setProgress(percentage(val,val1));
            }
            else
            tv_close_bus.setText("0/"+obj.getString("Closed Other Business"));

            if(obj2.has("Contacts Added")) {
                int val=Integer.valueOf(obj2.getString("Contacts Added"));
                int val1=Integer.valueOf(obj.getString("Contacts Added"));
                tv_contact_add.setText(val+"/" + obj.getString("Contacts Added"));
//                percentage(val,val1)
                bar_contact_add.setProgress(percentage(val,val1));
            }
            else
            tv_contact_add.setText("0/"+obj.getString("Contacts Added"));

            if(obj2.has("Closed IBA")) {
                int val=Integer.valueOf(obj2.getString("Closed IBA"));
                int val1=Integer.valueOf(obj.getString("Closed IBA"));
                tv_closeiba.setText(val+"/" + obj.getString("CClosed IBA"));
//                percentage(val,val1)
                bar_closeiba.setProgress(percentage(val,val1));
            }
            else
            tv_closeiba.setText("0/"+obj.getString("Closed IBA"));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("exception json","e",e);
        }

        return v;
    }

    public int percentage(int tot_achive,int tot){
        int per=tot_achive*100;
        int pe=(Integer)per/tot;

        Log.e("percentage value",tot_achive+" "+tot+" "+pe);

        return pe;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
//        menu.findItem(R.id.menu).setIcon(null);
//        menu.findItem(R.id.menu).setTitle("Save");
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
