package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Need_to_Quality extends Fragment {

    View v;
    ImageView tv_ratinginfo,tv_activitylist,tv_contactnotes;
    FrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.need_to_quality,container,false);

        tv_ratinginfo=(ImageView)v.findViewById(R.id.tv_ratinginfo);
        tv_activitylist=(ImageView)v.findViewById(R.id.tv_activitylist);
        tv_contactnotes=(ImageView)v.findViewById(R.id.tv_contactnotes);
        frameLayout=(FrameLayout)v.findViewById(R.id.frame_layout1);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout1,new Rating_info_fram()).addToBackStack(null).commit();

        tv_ratinginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout1,new Rating_info_fram()).addToBackStack(null).commit();
            }
        });

        tv_activitylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout1,new Activity_list_frag()).addToBackStack(null).commit();
            }
        });

        tv_contactnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout1,new Contact_notes_frag()).addToBackStack(null).commit();
            }
        });

        return v;
    }
}
