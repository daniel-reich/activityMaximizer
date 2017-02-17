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

public class Rating_info_clients_fram extends Fragment {

    View v;
    TextView tv_rating,tv_married,tv_haskids,tv_aged,tv_homeowner,tv_income;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.rating_clients_frag,container,false);

        tv_rating=(TextView)v.findViewById(R.id.tv_rating);
        tv_married=(TextView)v.findViewById(R.id.tv_married);
        tv_haskids=(TextView)v.findViewById(R.id.tv_haskids);
        tv_aged=(TextView)v.findViewById(R.id.tv_aged);
        tv_homeowner=(TextView)v.findViewById(R.id.tv_homeowner);
        tv_income=(TextView)v.findViewById(R.id.tv_income);

        return v;
    }
}
