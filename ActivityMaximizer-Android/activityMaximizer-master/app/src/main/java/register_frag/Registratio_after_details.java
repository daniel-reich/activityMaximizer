package register_frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import u.activitymanager.R;
import u.activitymanager.SplashActivity;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Registratio_after_details extends Fragment implements View.OnClickListener {
    TextView tv_notyet,tv_yes;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.registration_after_detail,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SplashActivity.title.setText("Registration");
        setHasOptionsMenu(true);


        tv_notyet=(TextView)v.findViewById(R.id.tv_notyet);
        tv_yes=(TextView)v.findViewById(R.id.tv_yes);
        tv_notyet.setOnClickListener(this);
        tv_yes.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_notyet:
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.splash_layout,new Registratio_after_details_yetnot()).addToBackStack(null).commit();
                break;
            case R.id.tv_yes:
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.splash_layout,new Check_info_is_correct()).addToBackStack(null).commit();
                //open info check fragment
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
