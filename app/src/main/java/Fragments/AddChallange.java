package Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;


public class AddChallange extends Fragment {

    TextView title,tv_setreward;
View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.challanges_new,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        HomeActivity.tv_back.setVisibility(View.VISIBLE);

//        HomeActivity.tv_back.setText("Back");
//        HomeActivity.title.setText("");

        setHasOptionsMenu(true);

        title = (TextView)view.findViewById(R.id.tv_settitle);
        tv_setreward = (TextView)view.findViewById(R.id.tv_setreward);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        tv_setreward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        return view;
    }

    private void showDialog() {
       final Dialog RvpDialog=new Dialog(getActivity());
        RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RvpDialog.setContentView(R.layout.add_contact_dialog);
        Window window = RvpDialog.getWindow();
        TextView tv_save=(TextView)RvpDialog.findViewById(R.id.tv_save);
        TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
        TextView tv_title=(TextView)RvpDialog.findViewById(R.id.tv_title);
        tv_title.setText("Name");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RvpDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("Save");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
            //    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                break;
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
