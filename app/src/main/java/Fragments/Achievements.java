package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class Achievements extends Fragment
{   RecyclerView rview;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.achievement_fragment,container,false);

        return view;
    }
}
