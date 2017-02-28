package Fragments;

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

import Adapter.Current_Goals_adap;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Goals_Tracker extends Fragment {

    RecyclerView rv_currentgoal,rv_metgoal,rv_unmetgoal;
    Current_Goals_adap cur_goal_adap;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.goal_tracker,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);

        rv_currentgoal=(RecyclerView)v.findViewById(R.id.rv_currentgoal);
        rv_metgoal=(RecyclerView)v.findViewById(R.id.rv_metgoals);
        rv_unmetgoal=(RecyclerView)v.findViewById(R.id.rv_unmetgoals);

        rv_currentgoal.setNestedScrollingEnabled(true);
        rv_metgoal.setNestedScrollingEnabled(true);
        rv_unmetgoal.setNestedScrollingEnabled(true);

        rv_currentgoal.setLayoutManager(new LinearLayoutManager(getActivity()));
        cur_goal_adap=new Current_Goals_adap(getActivity());
        rv_currentgoal.setAdapter(cur_goal_adap);

        rv_unmetgoal.setLayoutManager(new LinearLayoutManager(getActivity()));
        cur_goal_adap=new Current_Goals_adap(getActivity());
        rv_unmetgoal.setAdapter(cur_goal_adap);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("NEW");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new Add_New_Goal()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
