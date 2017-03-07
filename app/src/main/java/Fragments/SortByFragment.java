package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class SortByFragment extends Fragment
 {
     View view;
     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.sortby,container,false);
         setHasOptionsMenu(true);
         // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();

         ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
         ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
         ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         HomeActivity.title.setText("Sort By");
//         AllContacts=(TextView)view.findViewById(R.id.tv_all_contacts);
//         AllContacts.setOnClickListener(this);
         return view;

     }


     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         super.onCreateOptionsMenu(menu, inflater);
         menu.findItem(R.id.menu).setVisible(false);
         // menu.findItem(R.id.menu).setTitle("Add List");
         menu.findItem(R.id.list).setVisible(false);
      //   menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId())
         {
             case android.R.id.home:
                 getActivity().getSupportFragmentManager().popBackStack();
                 break;
         }
         return super.onOptionsItemSelected(item);
     }
 }
