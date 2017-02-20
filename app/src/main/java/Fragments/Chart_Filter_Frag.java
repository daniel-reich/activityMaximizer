package Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;

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
    CheckBox cb_team;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.chart_filter,container,false);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Chart Filters");

        cb_team=(CheckBox)view.findViewById(R.id.cb_team);
        lay_team=(LinearLayout)view.findViewById(R.id.lay_team);

//        lay_team.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().
//                        replace(R.id.frame_layout,new Filter_Frag()).addToBackStack(null).commit();
//            }
//        });


        return view;
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
