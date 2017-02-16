package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.IWheelPicker;
import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by surender on 2/16/2017.
 */
public class SavingCalculator extends Fragment implements WheelPicker.OnItemSelectedListener {

    IWheelPicker wheel1,wheel2,wheel3;
    TextView tv_yeartosave,tv_annualinterest,tv_interestearned;
    ArrayList<String> list,list1;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v=inflater.inflate(R.layout.saving_calculator,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tools");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // lay_calculator=(LinearLayout)view.findViewById(R.id.ly_calculator);
        setHasOptionsMenu(true);
        HomeActivity.title.setText("Saving Calculator");
        wheel1=(IWheelPicker)v.findViewById(R.id.main_wheel1);
        wheel2=(IWheelPicker)v.findViewById(R.id.main_wheel2);
        wheel3=(IWheelPicker)v.findViewById(R.id.main_wheel3);

        tv_yeartosave=(TextView)v.findViewById(R.id.tv_yeartosave);
        tv_annualinterest=(TextView)v.findViewById(R.id.tv_annualinterest);
        tv_interestearned=(TextView)v.findViewById(R.id.tv_interestearned);

        list=new ArrayList<>();
        list1=new ArrayList<>();

        for(int i=0;i<100;i++)
            list.add(i+"");

        for(int i=0;i<100;i++) {
            if(i<10)
                list1.add(".0"+i + "%");
            else
                list1.add("."+i+ "%");
        }

        wheel1.setData(list);
        wheel2.setData(list);
        wheel3.setData(list1);
        wheel1.setOnItemSelectedListener(this);
        wheel2.setOnItemSelectedListener(this);
        wheel3.setOnItemSelectedListener(this);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {

        if(picker.getId()==R.id.main_wheel1) {
          tv_yeartosave.setText(list.get(position));
        }
        else if(picker.getId()==R.id.main_wheel2) {
            tv_annualinterest.setText(list.get(position)+".00%");
        }
        else if(picker.getId()==R.id.main_wheel3) {
            tv_interestearned.setText("$0"+list1.get(position));
        }

    }
}
