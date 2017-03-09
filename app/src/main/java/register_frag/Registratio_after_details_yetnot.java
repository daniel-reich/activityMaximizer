package register_frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import u.activitymanager.R;
import u.activitymanager.SplashActivity;

/**
 * Created by Surbhi on 15-02-2017.
 */

public class Registratio_after_details_yetnot extends Fragment implements View.OnClickListener {
    TextView tv_dontknow,tv_next;
    EditText et_rvpnumber;
    View v;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.registration_after_detail_yetnot,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SplashActivity.title.setText("Registration");
        setHasOptionsMenu(true);

        pref=getActivity().getSharedPreferences("userpref",0);

        tv_dontknow=(TextView)v.findViewById(R.id.tv_dontknow);
        tv_next=(TextView)v.findViewById(R.id.tv_next);
        et_rvpnumber=(EditText)v.findViewById(R.id.et_rvpsolutionnumber);
        tv_dontknow.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_dontknow:
                edit=pref.edit();
                edit.putString("rvp_solution_number",et_rvpnumber.getText().toString());
                edit.commit();

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.splash_layout,new Registratio_after_details_yetnot_dontknow()).addToBackStack(null).commit();
                break;
            case R.id.tv_next:
                //check rvp solution number and  call dontknow fragment
                if (et_rvpnumber.getText().toString().length()<1) {
                    Toast.makeText(getActivity(),"Enter RVP Solution Number",Toast.LENGTH_LONG).show();
                    return;
                }

                edit=pref.edit();
                edit.putString("rvp_solution_number",et_rvpnumber.getText().toString());
                edit.commit();

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.splash_layout,new Registratio_after_details_yetnot_dontknow()).addToBackStack(null).commit();
                break;
        }
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
