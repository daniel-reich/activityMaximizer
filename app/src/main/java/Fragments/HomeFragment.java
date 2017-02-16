package Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView team,personal;
    Dialog helpdialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();
        team=(TextView)view.findViewById(R.id.team_button);
        personal=(TextView)view.findViewById(R.id.personal_button);
        team.setOnClickListener(this);
        personal.setOnClickListener(this);
        team.setSelected(false);
        personal.setSelected(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Home");

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

                case android.R.id.home:
                   // getActivity().getSupportFragmentManager().popBackStack();
                    showHelpDialog();
                    break;

            case R.id.menu:
                showFloatingMenus();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void showFloatingMenus() {

        Fragments.FloatingMenusDialog dialog=new FloatingMenusDialog();
        dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), "");
        dialog.setCancelable(true);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.team_button:
                personal.setSelected(false);
                team.setSelected(true);


                break;
            case R.id.personal_button:
                team.setSelected(false);
                personal.setSelected(true);


                break;
        }

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