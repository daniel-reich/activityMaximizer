package Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.text.DecimalFormat;
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
    TextView tv_yeartosave,tv_annualinterest,tv_interestearned,tv_amountsaved;
    ArrayList<String> list,list1;
    View v;
    double Amount_saved,InterestEarned;
    double rate;
    double fv;
    int n=12;
    String str1="1",str2=".00%";
    double initialamount=0,annualinterest=0,monthly_deposit=0;
    int year=1;
    boolean value1=false,value2=false;
    String check_editText;

    //  calculations(1,100.0,2.00,10.0);
    // EditText et_initial_amount,et_monthly_deposit;

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
        tv_amountsaved=(TextView)v.findViewById(R.id.amount_saved);
        tv_annualinterest.setText(str1+str2);

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
                check_editText="1";
                checktext(initial);
                Log.e("check_edittext",check_editText);
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
//                one.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("1");
//                    }
//                });
//
//                two.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("2");
//                    }
//                });
//
//                three.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("3");
//                    }
//                });
//
//                four.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("4");
//                    }
//                });
//
//                five.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("5");
//                    }
//                });
//
//                six.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("6");
//                    }
//                });
//
//                seven.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("7");
//                    }
//                });
//
//                eight.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                         SetChangeInitalText("8");
//                    }
//                });
//
//                nine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("9");
//                    }
//                });
//
//                zero.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SetChangeInitalText("0");
//                    }
//                });
//
//                dot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String[] splitter = initial.getText().toString().split("\\.");
//
//                      /*  splitter[1].length();
//                        splitter[0].length();*/
//
//                        if(a)
//                        {
//                            Log.e("initial","initial");
//
//                        }
//                        else
//                        {
//
//                            if(initial.getText().toString().length()<1)
//                            {
//
//                            }
//                            else
//                            {
//                                SetChangeInitalText(".");
//                            }
//                            dot.setClickable(false);
//                            a=true;
//                        }
//                    }
//                });
//
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText("");
//                        tv_yeartosave.setText("1");
//                        tv_annualinterest.setText("1.00%");
//                        tv_interestearned.setText("0.00");
//                        tv_amountsaved.setText("0.00");
//                        a=false;
//                        dot.setClickable(true);
//                    }
//                });
                return true;
            }
        });

        monthly.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                check_editText="2";
                Log.e("check_edittext",check_editText);
                checktext(monthly);
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

//                one.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"1");
//                    }
//                });
//
//                two.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"2");
//                    }
//                });
//
//                three.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"3");
//                    }
//                });
//
//                four.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"4");
//                    }
//                });
//
//                five.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"5");
//                    }
//                });
//
//                six.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"6");
//                    }
//                });
//
//                seven.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"7");
//                    }
//                });
//
//                eight.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"8");
//                    }
//                });
//
//                nine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"9");
//                    }
//                });
//
//                zero.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"0");
//                    }
//                });
//
//                dot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
////                        monthly textonchangelistner
//
//                        if(b)
//                        {
//                            Log.e("initial1","initial");
//                        }
//                        else
//                        {
//                            monthly.setText(monthly.getText() + ".");
//                            dot.setClickable(false);
//                            b=true;
//                        }
//
//                    }
//                });
//
//
//
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText("");
//                        tv_yeartosave.setText("1");
//                        tv_annualinterest.setText("1.00%");
//                        tv_interestearned.setText("0.00");
//                        tv_amountsaved.setText("0.00");
//                        b=false;
//                        dot.setClickable(true);
//                    }
//                });

                return true;
            }
        });

        initiall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                check_editText="1";
                Log.e("check_edittext",check_editText);
                checktext(initial);
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
//                one.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        String text=initial.getText().toString();
//
//                        if(a){
//
//                            if(value1&value2){
//                                String[] arr=text.split(".");
//                                arr[1]="10";
//                                text=arr[0]+"."+arr[1];
//                                initial.setText(text);
//                                value1=false;
//                            }
//                            else if(value2){
////                                String text=initial.getText().toString();
//                                String[] arr=text.split(".");
//                                arr[1] = arr[1].substring(0,arr[1].length()-1) + "1";
//                                text=arr[0]+"."+arr[1];
//                                initial.setText(text);
//                                value2=false;
//                            }
//                            else if(value1==false&value2==false){
//                                initial.setText(text+"1");
//                            }
//                        }
//                        else
//                            initial.setText(text+"1");
//
//
////                        initial.setText(initial.getText()+"1");
//                    }
//                });
//
//                two.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"2");
//                    }
//                });
//
//                three.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"3");
//                    }
//                });
//
//                four.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"4");
//                    }
//                });
//
//                five.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"5");
//                    }
//                });
//
//                six.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"6");
//                    }
//                });
//
//                seven.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"7");
//                    }
//                });
//
//                eight.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"8");
//                    }
//                });
//
//                nine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"9");
//                    }
//                });
//
//                zero.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText(initial.getText()+"0");
//                    }
//                });
//
//                dot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Log.e("dot click initial","dot click initial");
//
//                        if(a)
//                        {
//                            Log.e("initial2","initial");
//                        }
//                        else
//                        {
//
//                                initial.setText(initial.getText() + ".00");
//
//                            value1=true;
//                            value2=true;
//
//                            dot.setClickable(false);
//                            a=true;
//                        }
//                    }
//                });
//
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        initial.setText("");
//                        tv_yeartosave.setText("1");
//                        tv_annualinterest.setText("1.00%");
//                        tv_interestearned.setText("0.00");
//                        tv_amountsaved.setText("0.00");
//                        a=false;
//                        dot.setClickable(true);
//                    }
//                });

                return true;
            }
        });

        monthlyl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                check_editText="2";
                checktext(monthly);

                Log.e("check_edittext",check_editText);

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
//                one.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"1");
//                    }
//                });
//
//                two.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"2");
//                    }
//                });
//
//                three.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"3");
//                    }
//                });
//
//                four.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"4");
//                    }
//                });
//
//                five.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"5");
//                    }
//                });
//
//                six.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"6");
//                    }
//                });
//
//                seven.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"7");
//                    }
//                });
//
//                eight.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"8");
//                    }
//                });
//
//                nine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"9");
//                    }
//                });
//
//                zero.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText(monthly.getText()+"0");
//                    }
//                });
//
//                dot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(b)
//                        {
//                            Log.e("initial3","initial");
//                        }
//                        else
//                        {
//                            monthly.setText(monthly.getText() + ".");
//                            dot.setClickable(false);
//                            b=true;
//                        }
//                    }
//                });
//
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        monthly.setText("");
//                        tv_yeartosave.setText("1");
//                        tv_annualinterest.setText("1.00%");
//                        tv_interestearned.setText("0.00");
//                        tv_amountsaved.setText("0.00");
//                        b=false;
//                        dot.setClickable(true);
//                    }
//                });

                return true;
            }
        });

        list=new ArrayList<>();
        list1=new ArrayList<>();

        for(int i=1;i<100;i++)
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


        //OnTextChangef
        initial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    try {

                        if (!TextUtils.isEmpty(monthly.getText().toString())) {
                            calculations(Integer.parseInt(tv_yeartosave.getText().toString()),
                                    Double.parseDouble(initial.getText().toString()),
                                    Double.parseDouble(tv_annualinterest.getText().toString().replace("%", "")), Double.parseDouble(monthly.getText().toString()));

                        } else {

                            calculations(Integer.parseInt(tv_yeartosave.getText().toString()),
                                    Double.parseDouble(initial.getText().toString()), Double.parseDouble(tv_annualinterest.getText().toString().replace("%", "")), 0.0);

                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        monthly.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {



                    if(!TextUtils.isEmpty(initial.getText().toString())) {
                        calculations(Integer.parseInt(tv_yeartosave.getText().toString()),
                                Double.parseDouble(initial.getText().toString()),
                                Double.parseDouble(tv_annualinterest.getText().toString().replace("%", "")),
                                Double.parseDouble(monthly.getText().toString()));
                        //  calculations(1,100.0,2.00,10.0);
                    }
                    else{

                        calculations(Integer.parseInt(tv_yeartosave.getText().toString()),
                                0.0,
                                Double.parseDouble(tv_annualinterest.getText().toString().replace("%","")),
                                Double.parseDouble(monthly.getText().toString()));

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SetChangeInitalText("1");
//

//                        Log.e("check_edittext",check_editText);
//
//                        String text;
//                        if(check_editText.equalsIgnoreCase("1"))
//                       text=initial.getText().toString();
//                        else
//                            text=monthly.getText().toString();
//
//                        if(a){
//
//                            Log.e("textvalue",text);
//
//                            if(value1&value2){
//                                String[] arr=text.split("\\.");
//                                arr[1]="10";
//                                text=arr[0]+"."+arr[1];
////                                initial.setText(text);
//                                value1=false;
//                            }
//                            else if(value2){
////                                String text=initial.getText().toString();
//                                String[] arr=text.split("\\.");
//                                arr[1] = arr[1].substring(0,arr[1].length()-1) + "1";
//                                text=arr[0]+"."+arr[1];
////                                initial.setText(text);
//                                value2=false;
//                            }
//                            else if(value1==false&value2==false){
////                                text=text+"1";
////                                initial.setText(text+"1");
//                            }
//                        }
//                        else
//                        text=text+"1";
//
//
//                        if(check_editText.equalsIgnoreCase("1"))
//                            initial.setText(text);
//                        else
//                            monthly.setText(text);

                    }
                });

                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SetChangeInitalText("2");
//
//                        initial.setText(initial.getText()+"2");
                    }
                });

                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("3");
//
//                        initial.setText(initial.getText()+"3");
                    }
                });

                four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("4");
//                        initial.setText(initial.getText()+"4");
                    }
                });

                five.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("5");
//                        initial.setText(initial.getText()+"5");
                    }
                });

                six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("6");
//                        initial.setText(initial.getText()+"6");
                    }
                });

                seven.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("7");
//                        initial.setText(initial.getText()+"7");
                    }
                });

                eight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("8");
//                        initial.setText(initial.getText()+"8");
                    }
                });

                nine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("9");
//                        initial.setText(initial.getText()+"9");
                    }
                });

                zero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetChangeInitalText("0");
//                        initial.setText(initial.getText()+"0");
                    }
                });

                dot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("dot click initial","dot click initial");

                        if(a)
                        {
                            Log.e("initial2","initial");
                        }
                        else
                        {
                                if(check_editText.equalsIgnoreCase("1"))
                                initial.setText(initial.getText() + ".00");
                            else
                                monthly.setText(monthly.getText()+".00");

                            value1=true;
                            value2=true;
                            dot.setClickable(false);
                            a=true;
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(check_editText.equalsIgnoreCase("1"))
                        initial.setText("");
                        else
                        monthly.setText("");

                        tv_yeartosave.setText("1");
                        tv_annualinterest.setText("1.00%");
                        tv_interestearned.setText("0.00");
                        tv_amountsaved.setText("0.00");

                        a=false;
                        value1=false;
                        value2=false;
                        dot.setClickable(true);
                    }
                });

            return v;
    }

    public void checktext(EditText et){

        String text=et.getText().toString();

        Log.e("editext_value",text+"  value");

        if(text.contains(".")){

            String[] arr=text.split("\\.");

            if(arr[1].equalsIgnoreCase("00")){
                value1=true;
                value2=true;
            }
            else {
                value1 = false;
                value2=false;
            }

            dot.setClickable(true);

        }
        else {
            a = false;
            dot.setClickable(true);
        }

    }

    private void SetChangeInitalText(String s) {

        Log.e("check_edittext",check_editText);

        String text;
        if(check_editText.equalsIgnoreCase("1"))
            text=initial.getText().toString();
        else
            text=monthly.getText().toString();

        if(a){

            Log.e("textvalue",text);

            if(value1&value2){
                String[] arr=text.split("\\.");
                arr[1]=s+"0";
                text=arr[0]+"."+arr[1];
//                                initial.setText(text);
                value1=false;
            }
            else if(value2){
//                                String text=initial.getText().toString();
                String[] arr=text.split("\\.");
                arr[1] = arr[1].substring(0,arr[1].length()-1) + s;
                text=arr[0]+"."+arr[1];
//                                initial.setText(text);
                value2=false;
            }
            else if(value1==false&value2==false){
//                                text=text+"1";
//                                initial.setText(text+"1");
            }
        }
        else
            text=text+s;


        if(check_editText.equalsIgnoreCase("1"))
            initial.setText(text);
        else
            monthly.setText(text);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {

        if(picker.getId()==R.id.main_wheel1) {

            if(!TextUtils.isEmpty(initial.getText().toString())  & !TextUtils.isEmpty(monthly.getText().toString())) {
                tv_yeartosave.setText(list.get(position));
                year = Integer.parseInt(tv_yeartosave.getText().toString());
                initialamount = Double.parseDouble(initial.getText().toString());
                annualinterest = Double.parseDouble(tv_annualinterest.getText().toString().replace("%", ""));
                monthly_deposit = Double.parseDouble(monthly.getText().toString());

                calculations(year,
                        initialamount,
                        annualinterest,
                        monthly_deposit);
            }
        }
        else if(picker.getId()==R.id.main_wheel2) {
            // tv_annualinterest.setText(list.get(position)+list1.get(position));
            if(!TextUtils.isEmpty(initial.getText().toString()) & !TextUtils.isEmpty(monthly.getText().toString())) {
                str1 = list.get(position);
                tv_annualinterest.setText(str1 + str2);
                year = Integer.parseInt(tv_yeartosave.getText().toString());
                initialamount = Double.parseDouble(initial.getText().toString());
                annualinterest = Double.parseDouble(tv_annualinterest.getText().toString().replace("%", ""));
                monthly_deposit = Double.parseDouble(monthly.getText().toString());

                calculations(year,
                        initialamount,
                        annualinterest,
                        monthly_deposit);
            }
        }
        else if(picker.getId()==R.id.main_wheel3) {
            // tv_interestearned.setText("$0"+list1.get(position));
            if(!TextUtils.isEmpty(initial.getText().toString())  & !TextUtils.isEmpty(monthly.getText().toString())) {
                str2 = list1.get(position);
                tv_annualinterest.setText(str1 + str2);
                year = Integer.parseInt(tv_yeartosave.getText().toString());
                initialamount = Double.parseDouble(initial.getText().toString());
                annualinterest = Double.parseDouble(tv_annualinterest.getText().toString().replace("%", ""));
                monthly_deposit = Double.parseDouble(monthly.getText().toString());

                calculations(year,
                        initialamount,
                        annualinterest,
                        monthly_deposit);
                //tv_annualinterest.setText(list.get(position)+list1.get(position));
            }
        }

    }


    private void calculations(int year,double principal,double rate,double monthlydeposit)
    {
        if(monthlydeposit==0.0) {
            double amount=0.0;
            InterestEarned=0.0;


            amount = principal * Math.pow((1 + (rate) / (n * 100)), n * year);
            InterestEarned=amount-principal;
            tv_amountsaved.setText("$ "+amount+" ");
            tv_interestearned.setText("$ "+InterestEarned+" ");

            Log.e("interest",amount+","+InterestEarned+"");
            tv_interestearned.setText("$ "+new DecimalFormat("###,###.##").format(InterestEarned)+" ");

            tv_amountsaved.setText("$ "+new DecimalFormat("###,###.##").format(amount)+" ");
        }
        else
        {
            double A =principal* Math.pow(( 1 + ( rate /( n*100) )), (n * year));
            double fv =  monthlydeposit * ((Math.pow((1 + rate/(n*100)), (n * year)) - 1) / (rate/(n*100)));
            double interest = (A - principal) + (fv - monthlydeposit * (n * year));
            double saved =  (fv + A);
            Log.e("interest_m",(interest+","+saved+""));
            tv_amountsaved.setText("$ "+saved+" ");
            tv_interestearned.setText("$ "+new DecimalFormat("###,###.##").format(interest)+" ");

            tv_amountsaved.setText("$ "+new DecimalFormat("###,###.##").format(saved)+" ");


        }



    }



   /*
    if (splitter[0].length() > 0) {
                                if (splitter[1].length() == 0) {

                                } else if (splitter[1].length() == 1) {

                                } else if (splitter[1].length() == 2) {

                                } else if (splitter[1].length() > 2) {
                                    Log.e("gg", "hh");
                                    initial.setEnabled(false);
                                    initial.setFocusable(false);
                                    DecimalFormat numberFormat = new DecimalFormat("#.00");

                                    initial.setText(numberFormat.format(Double.parseDouble(initial.getText().toString())));
                                }
                            }

   Compound interest for principal:
    P(1+r/n)^nt

    Future value of a series:
    PMT * (((1 + r/n)^nt - 1) / (r/n)) * (1+r/n)

    A = the future value of the investment/loan, including interest
    P = the principal investment amount (the initial deposit or loan amount)
    PMT = the monthly payment
            r = the annual interest rate (decimal)
    n = the number of times that interest is compounded per year AND additional payment frequency
    t = the number of years the money is invested or borrowed for

            */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}


