package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

import java.util.ArrayList;

import Adapter.ListAllContactAdapter;
import model.AllContact;
import model.AllList;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/3/2017.
 */
public class LisiAllContact extends Fragment {

    RecyclerView rView;
    View view;
    Firebase mref;
    SharedPreferences pref;
    LinearLayoutManager layoutManager;
    ListAllContactAdapter adapter;
    ArrayList<AllList> data;
    ArrayList<AllContact> NoClientsArrayList;
    String uid;
    String name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.listallcontacts,container,false);
        setHasOptionsMenu(true);
        rView=(RecyclerView)view.findViewById(R.id.rview);
        // rView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(getActivity());
        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        uid=pref.getString("uid","");
        name= getArguments().getString("givenName");
        NoClientsArrayList= (ArrayList<AllContact>) getArguments().getSerializable("NoClientsArrayList");
        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        Log.e("name",name);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(name);

        adapter=new ListAllContactAdapter(getActivity(),NoClientsArrayList,name,getActivity().getSupportFragmentManager());
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        menu.findItem(R.id.menu).setVisible(true);
//        menu.findItem(R.id.menu).setIcon(R.mipmap.sort);
//        menu.findItem(R.id.list).setVisible(false);
        //   menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
//            case R.id.menu:
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new SortByFragment()).addToBackStack(null).commit();
//                break;

            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }





}
