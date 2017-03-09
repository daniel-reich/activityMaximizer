package Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class FloatingMenusDialog extends DialogFragment implements View.OnClickListener {
    View view;
    RelativeLayout Pending_Request_Layout;
    SharedPreferences  pref;
    FloatingActionButton challenges,achievements,profile,posts,pendingrequest,tools;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.floatingmenudialog,container,false);
        pref=getActivity().getSharedPreferences("userpref",0);
        challenges=(FloatingActionButton)view.findViewById(R.id.menu_item);
        profile=(FloatingActionButton)view.findViewById(R.id.menu_item4);
        posts=(FloatingActionButton)view.findViewById(R.id.menu_item5);
        achievements=(FloatingActionButton)view.findViewById(R.id.menu_item2);
        tools=(FloatingActionButton)view.findViewById(R.id.menu_item3);
        pendingrequest=(FloatingActionButton)view.findViewById(R.id.menu_item6);
        Pending_Request_Layout=(RelativeLayout)view.findViewById(R.id.pending_request_layout);
        pendingrequest.setOnClickListener(this);
        challenges.setOnClickListener(this);
        profile.setOnClickListener(this);
        achievements.setOnClickListener(this);
        posts.setOnClickListener(this);
        tools.setOnClickListener(this);
        Log.e("rvp soltn no",pref.getString("rvp_solution_number",""));
        if(pref.getString("rvp_solution_number","").length()==0  && pref.getString("upline_solution_number","").length()==0)
        {
            Pending_Request_Layout.setVisibility(View.VISIBLE);
        }
        else
        {
            Pending_Request_Layout.setVisibility(View.GONE);
        }


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        getDialog().setCancelable(true);
        WindowManager.LayoutParams attributes = window.getAttributes();
        //must setBackgroundDrawable(TRANSPARENT) in onActivityCreated()
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        if (needFullScreen)
//        {
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //}
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.menu_item:
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Challenges()).addToBackStack(null).commit();
                break;
            case R.id.menu_item2:
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Achievements()).addToBackStack(null).commit();
                break;
            case R.id.menu_item3:
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Tools()).addToBackStack(null).commit();
                break;
            case R.id.menu_item4:
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Profile()).addToBackStack(null).commit();
                break;
            case R.id.menu_item5:
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new PostsFragment()).addToBackStack(null).commit();
                break;
            case R.id.menu_item6:
                this.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new RVPRequestFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
