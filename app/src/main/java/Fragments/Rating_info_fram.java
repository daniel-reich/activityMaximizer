package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Rating_info_fram extends Fragment {

    View v;
    TextView tv_clients,tv_recruits;
    FrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.rating_info,container,false);

        frameLayout=(FrameLayout)v.findViewById(R.id.fram_rating);

        tv_clients=(TextView)v.findViewById(R.id.tv_clients);
        tv_recruits=(TextView)v.findViewById(R.id.tv_recruits);

        tv_recruits.setSelected(false);
        tv_clients.setSelected(true);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fram_rating,new Rating_info_clients_fram()).addToBackStack(null).commit();

        tv_clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fram_rating,new Rating_info_clients_fram()).addToBackStack(null).commit();
                tv_recruits.setSelected(false);
                tv_clients.setSelected(true);
            }
        });
        tv_recruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fram_rating,new Rating_info_recruits_fram()).addToBackStack(null).commit();
                tv_recruits.setSelected(true);
                tv_clients.setSelected(false);
            }
        });

        return v;
    }
}
