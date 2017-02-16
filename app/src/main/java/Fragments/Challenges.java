package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Adapter.Current_challanges;
import Adapter.Past_challanges;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Challenges extends Fragment
{
    View view;
    TextView toolbar_new;

    private final String current_reward[] = {
            "Hello Yatin",
            "Hello Payal"
    };

    private final String current_leader[] = {
            "Yatin Leader",
            "Payal Leader"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.challanges,container,false);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("");
        setHasOptionsMenu(true);
        initViews();
        return view;
    }
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.current_recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        Current_challanges adapter = new Current_challanges(getActivity());
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView1 = (RecyclerView)view.findViewById(R.id.past_recyle);
        recyclerView1.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);
        // recyclerView1.setNestedScrollingEnabled(false);

        Past_challanges adapter1 = new Past_challanges(getActivity());
        recyclerView1.setAdapter(adapter1);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("NEW");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId())
          {
              case R.id.menu:
      //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AddChallange()).addToBackStack(null).commit();
                  break;
              case android.R.id.home:
                  getActivity().getSupportFragmentManager().popBackStack();
                  break;
          }
        return super.onOptionsItemSelected(item);

    }
}
