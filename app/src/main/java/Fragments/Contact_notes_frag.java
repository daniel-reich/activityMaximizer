package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Contact_notes_frag extends Fragment {

    View v;
    TextView tv_newactivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.activity_list_frag,container,false);

        tv_newactivity=(TextView)v.findViewById(R.id.tv_newactivity);
        tv_newactivity.setText("+ New Note");

        return v;
    }
}
