package Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

    Boolean b=false;
    Boolean a=false;
    IWheelPicker wheel1,wheel2,wheel3;
    LinearLayout initiall,monthlyl;
    Button one,two,three,four,five,six,seven,eight,nine,zero,dot,delete;
    EditText initial,monthly;
    TextView tv_yeartosave,tv_annualinterest,tv_interestearned;
    ArrayList<String> list,list1;
    View v;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        one = (Button)v.findViewById(R.id.btn_one);
        two = (Button)v.findViewById(R.id.btn_two);
        three = (Button)v.findViewById(R.id.btn_three);
        four = (Button)v.findViewById(R.id.btn_four);
        five = (Button)v.findViewById(R.id.btn_five);
        six = (Button)v.findViewById(R.id.btn_six);
        seven = (Button)v.findViewById(R.id.btn_seven);
        eight = (Button)v.findViewById(R.id.btn_eight);
        nine = (Button)v.findViewById(R.id.btn_nine);
        zero = (Button)v.findViewById(R.id.btn_zero);
        dot = (Button)v.findViewById(R.id.btn_dot);
        delete = (Button)v.findViewById(R.id.btn_delete);

        initial = (EditText)v.findViewById(R.id.btn_delete1);
        monthly = (EditText)v.findViewById(R.id.et_monthly);

        initial.setShowSoftInputOnFocus(false);
        monthly.setShowSoftInputOnFocus(false);

        initial.setCursorVisible(false);
        monthly.setCursorVisible(false);

        initiall = (LinearLayout)v.findViewById(R.id.initiall);
        monthlyl = (LinearLayout)v.findViewById(R.id.monthlyl);

        initial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        initiall.setBackgroundColor(Color.GRAY);
                        break;
                    case MotionEvent.ACTION_UP:
                        //set color back to default
                        initiall.setBackgroundColor(Color.WHITE);
                        monthlyl.setBackgroundColor(Color.GRAY);
                        break;
                }

                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"1");
                    }
                });

                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"2");
                    }
                });

                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"3");
                    }
                });

                four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"4");
                    }
                });

                five.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"5");
                    }
                });

                six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"6");
                    }
                });

                seven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"7");
                    }
                });

                eight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"8");
                    }
                });

                nine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"9");
                    }
                });

                zero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"0");
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(a)
                        {

                        }
                        else
                        {

                            if(initial.getText().toString().length()<1)
                            {

                            }
                            else
                            {
                                initial.setText(initial.getText() + ".");
                            }
                            dot.setClickable(false);
                            a=true;
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText("");
                        a=false;
                        dot.setClickable(true);
                    }
                });
                return true;
            }
        });

        monthly.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {

                    case MotionEvent.ACTION_DOWN:
                        monthlyl.setBackgroundColor(Color.GRAY);
                        break;

                    case MotionEvent.ACTION_UP:

                        //set color back to default
                        monthlyl.setBackgroundColor(Color.WHITE);
                        initiall.setBackgroundColor(Color.GRAY);
                        break;
                }
                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"1");
                    }
                });

                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"2");
                    }
                });

                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"3");
                    }
                });

                four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"4");
                    }
                });

                five.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"5");
                    }
                });

                six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"6");
                    }
                });

                seven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"7");
                    }
                });

                eight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"8");
                    }
                });

                nine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"9");
                    }
                });

                zero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"0");
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(b)
                        {

                        }
                        else
                        {
                            monthly.setText(monthly.getText() + ".");
                            dot.setClickable(false);
                            b=true;
                        }

                    }
                });



                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText("");
                        b=false;
                        dot.setClickable(true);
                    }
                });
                return true;
            }
        });

        initiall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {

                    case MotionEvent.ACTION_DOWN:
                        initiall.setBackgroundColor(Color.GRAY);
                        break;

                    case MotionEvent.ACTION_UP:

                        //set color back to default
                        initiall.setBackgroundColor(Color.WHITE);
                        monthlyl.setBackgroundColor(Color.GRAY);
                        break;
                }
                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"1");
                    }
                });

                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"2");
                    }
                });

                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"3");
                    }
                });

                four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"4");
                    }
                });

                five.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"5");
                    }
                });

                six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"6");
                    }
                });

                seven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"7");
                    }
                });

                eight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"8");
                    }
                });

                nine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"9");
                    }
                });

                zero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(initial.getText()+"0");
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(a)
                        {

                        }
                        else
                        {
                            initial.setText(initial.getText() + ".");
                            dot.setClickable(false);
                            a=true;
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText("");
                        a=false;
                        dot.setClickable(true);
                    }
                });

                return true;
            }
        });

        monthlyl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {

                    case MotionEvent.ACTION_DOWN:
                        monthlyl.setBackgroundColor(Color.GRAY);
                        break;

                    case MotionEvent.ACTION_UP:

                        //set color back to default
                        monthlyl.setBackgroundColor(Color.WHITE);
                        initiall.setBackgroundColor(Color.GRAY);
                        break;
                }
                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"1");
                    }
                });

                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"2");
                    }
                });

                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"3");
                    }
                });

                four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"4");
                    }
                });

                five.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"5");
                    }
                });

                six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"6");
                    }
                });

                seven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"7");
                    }
                });

                eight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"8");
                    }
                });

                nine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"9");
                    }
                });

                zero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText(monthly.getText()+"0");
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(b)
                        {

                        }
                        else
                        {
                            monthly.setText(monthly.getText() + ".");
                            dot.setClickable(false);
                            b=true;
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monthly.setText("");
                        b=false;
                        dot.setClickable(true);
                    }
                });

                return true;
            }
        });

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
