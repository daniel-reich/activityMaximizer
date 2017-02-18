package Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import Adapter.Current_challanges;
import Adapter.Past_challanges;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Achivement_Details extends Fragment
{
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.achivement_details,container,false);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.tv_back.setVisibility(View.VISIBLE);
//        HomeActivity.tv_back.setText("Back");
//        HomeActivity.title.setText("");

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("Show");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId())
          {
              case R.id.menu:
                  showDialog();
               break;
              case android.R.id.home:
                  getActivity().getSupportFragmentManager().popBackStack();
                  break;
          }
        return super.onOptionsItemSelected(item);

    }

    private void showDialog() {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rvp_dialog);
        Window window = dialog.getWindow();
        TextView tv_send=(TextView)dialog.findViewById(R.id.tv_save);
        TextView tv_cancel=(TextView)dialog.findViewById(R.id.cancel);
        TextView tv_title=(TextView)dialog.findViewById(R.id.tv_title);
        TextView tv_text=(TextView)dialog.findViewById(R.id.tv_text);
        tv_title.setText("Show Achievement On\nProfile?");
        tv_text.setVisibility(View.GONE);
        tv_cancel.setText("No");
        tv_send.setText("Yes");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
