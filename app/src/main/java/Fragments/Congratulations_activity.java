package Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import u.activitymanager.R;

/**
 * Created by Rohan on 3/8/2017.
 */
public class Congratulations_activity extends Fragment
{
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.congratulations_activity,container,false);
        return v;
    }
}
