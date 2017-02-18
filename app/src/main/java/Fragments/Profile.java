package Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class Profile extends Fragment implements View.OnClickListener {
    MenuItem menu;
    Toolbar toolbar;
    Dialog settingsdialog,helpdialog;
    RelativeLayout lay_branch_tran;
    View view;
    TextView RVP;
    Dialog RvpDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_profile,container,false);
        setHasOptionsMenu(true);
        HomeActivity.title.setText("My Profile");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RVP=(TextView)view.findViewById(R.id.tv_rvp);
        lay_branch_tran=(RelativeLayout)view.findViewById(R.id.lay_branchtransfer);

        RVP.setOnClickListener(this);
        lay_branch_tran.setOnClickListener(this);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.drawable.settings);
        menu.findItem(R.id.menu).setTitle("Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                showSettingsDialog();
                break;
            case android.R.id.home:
               // showHelpDialog();
               getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }



    private void showSettingsDialog()
    {
      //  AlertDialog dialog=new AlertDialog(getActivity(),)selectPicdialog=new Dialog(getActivity());
        settingsdialog=new Dialog(getActivity());
        settingsdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsdialog.setContentView(R.layout.settings_dialog);
        Window window = settingsdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingsdialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_rvp:
                showDialog();
                break;
            case R.id.lay_branchtransfer:
                showBranch();
        }
    }

    private void showBranch() {
        RvpDialog=new Dialog(getActivity());
        RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RvpDialog.setContentView(R.layout.branch_transfer_dialog);
        Window window = RvpDialog.getWindow();
        TextView tv_send=(TextView)RvpDialog.findViewById(R.id.tv_send);
        TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
        tv_send.setText("Send");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RvpDialog.show();
    }

    private void showDialog() {
        RvpDialog=new Dialog(getActivity());
        RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RvpDialog.setContentView(R.layout.rvp_dialog);
        Window window = RvpDialog.getWindow();
        TextView tv_send=(TextView)RvpDialog.findViewById(R.id.tv_save);
        TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
        tv_send.setText("Send");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RvpDialog.show();
    }
}
