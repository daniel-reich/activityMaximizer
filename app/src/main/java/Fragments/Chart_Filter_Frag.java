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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import Adapter.ActivitiesAdapter;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class Chart_Filter_Frag extends Fragment
{
    RecyclerView activities;
    LinearLayoutManager linearLayoutManager;
    ActivitiesAdapter adapter;
    Dialog helpdialog;
    View view;
    LinearLayout lay_team;
    CheckBox cb_team,cb_trainees,cb_personal;
    ImageView iv_team,iv_trainees,iv_personal;
    JSONObject obj;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.chart_filter,container,false);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Chart Filters");

        pref=getActivity().getSharedPreferences("userpref",0);

        cb_team=(CheckBox)view.findViewById(R.id.cb_team);
        cb_trainees=(CheckBox)view.findViewById(R.id.cb_trainees);
        cb_personal=(CheckBox)view.findViewById(R.id.cb_personal);

        iv_team=(ImageView)view.findViewById(R.id.iv_team);
        iv_trainees=(ImageView)view.findViewById(R.id.iv_trainees);
        iv_personal=(ImageView)view.findViewById(R.id.iv_personal);

        lay_team=(LinearLayout)view.findViewById(R.id.lay_team);

        obj=new JSONObject();

        try {
            obj=new JSONObject(pref.getString("chart_filter",""));
            setdata(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                else{
                    iv_team.setImageBitmap(null);
                    try {
                        obj.put("team",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                edit=pref.edit();
                edit.putString("chart_filter",obj+"");
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
                else{
                    iv_trainees.setImageBitmap(null);
                    try {
                        obj.put("trainees",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                edit=pref.edit();
                edit.putString("chart_filter",obj+"");
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
                else{
                    iv_personal.setImageBitmap(null);
                    try {
                        obj.put("personal",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                edit=pref.edit();
                edit.putString("chart_filter",obj+"");
                edit.commit();
            }
        });

        return view;
    }

    public void setdata(JSONObject object) {

        try {
            Log.e("value", object + "");

            if (object.has("team")) {
                if (object.getBoolean("team")) {
                    cb_team.setChecked(true);
                    iv_team.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_team.setChecked(false);
                    iv_team.setImageBitmap(null);
                }
            }
            if (object.has("trainees")) {
                if (object.getBoolean("trainees")) {
                    cb_trainees.setChecked(true);
                    iv_trainees.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_trainees.setChecked(false);
                    iv_trainees.setImageBitmap(null);
                }
            }
            if (object.has("personal")) {
                if (object.getBoolean("personal")) {
                    cb_personal.setChecked(true);
                    iv_personal.setImageResource(R.drawable.checkmark_bl24);
                } else {
                    cb_personal.setChecked(false);
                    iv_personal.setImageBitmap(null);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.mipmap.activity_detail);
       // menu.findItem(R.id.menu).setTitle("Add List");
        menu.findItem(R.id.list).setVisible(false);
        menu.findItem(R.id.menu).setVisible(false);
        menu.findItem(R.id.list).setIcon(R.mipmap.filter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
               // showHelpDialog();
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
