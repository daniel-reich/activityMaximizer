package Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import Adapter.SelectNewActivityListAdapter;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Activity_list_frag extends Fragment {

    View v;
    TextView tv_newactivity;
   public static Dialog dialog;
    RecyclerView rview;
    SelectNewActivityListAdapter adapter;
    LinearLayoutManager lManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.activity_list_frag,container,false);
        tv_newactivity=(TextView)v.findViewById(R.id.tv_newactivity);
        tv_newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.select_new_activity_dialog);
                rview=(RecyclerView)dialog.findViewById(R.id.r_view);
                lManager=new LinearLayoutManager(getActivity());
                rview.setLayoutManager(lManager);
                adapter=new SelectNewActivityListAdapter(getActivity());
                rview.setAdapter(adapter);
                dialog.show();


            }
        });

        return v;
    }
}
