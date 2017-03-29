package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.firebase.client.Firebase;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class DownlineFragment extends Fragment implements View.OnClickListener {
    View view;
    Dialog helpdialog;

    TextView team,trainees,tv_direct;

    FragmentManager fm;
    SharedPreferences pref;
    String uid="",First_downline="";
   public static boolean showedit;
    public static boolean showaddtoteam,showaddtotrainne;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.downline,container,false);
        setHasOptionsMenu(true);
        fm=getActivity().getSupportFragmentManager();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();
        team=(TextView)view.findViewById(R.id.team_button);
        trainees=(TextView)view.findViewById(R.id.personal_button);
        team.setOnClickListener(this);
        trainees.setOnClickListener(this);
        team.setSelected(true);
        trainees.setSelected(false);
        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());


        uid=pref.getString("uid","");



        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Downline");

        tv_direct=(TextView)view.findViewById(R.id.tv_direct);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout1, new Team_fragment())
                .commitNow();

        showaddtoteam=true;
        showaddtotrainne=false;

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.menu:
                //showFloatingMenus();
                showedit=!showedit;
                Log.e("showedit",showedit+"");
                if(showedit)
                {
                    item.setTitle("Edit");


                }
                else{
                    item.setTitle("Done");

                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void showHelpDialog() {
        helpdialog=new Dialog(getActivity());
        helpdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpdialog.setContentView(R.layout.help_dialog);
        Window window = helpdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpdialog.show();



    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.team_button:
                trainees.setSelected(true);
                team.setSelected(false);
               // showaddtoteam=!showaddtoteam;
                showaddtoteam=false;
                showaddtotrainne=true;
                Log.e("showaddtoteam",showaddtoteam+"");
                Trainee_frag basic_frag1 = new Trainee_frag();
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout1,basic_frag1)
                        .commitNowAllowingStateLoss();

                break;
            case R.id.personal_button:
                team.setSelected(true);
                trainees.setSelected(false);
                showaddtoteam=true;
                showaddtotrainne=false;
                Team_fragment basic_frag = new Team_fragment();
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout1,basic_frag)
                        .commitNowAllowingStateLoss();
                break;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(null);
        menu.findItem(R.id.menu).setTitle("Done");
        menu.findItem(R.id.list).setVisible(false);
    }

}
