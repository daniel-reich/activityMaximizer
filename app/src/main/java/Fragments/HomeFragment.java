package Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView team,personal;
    Dialog helpdialog;
    LinearLayout lay_day;
    Firebase mref;
    FirebaseAuth firebaseAuth;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();
        team=(TextView)view.findViewById(R.id.team_button);
        personal=(TextView)view.findViewById(R.id.personal_button);
        lay_day=(LinearLayout)view.findViewById(R.id.days_lyt);
        team.setOnClickListener(this);
        personal.setOnClickListener(this);
        team.setSelected(false);
        personal.setSelected(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Home");

        Firebase.setAndroidContext(getActivity());
        pref=getActivity().getSharedPreferences("userpref",0);
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();

        lay_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

                case android.R.id.home:
                   // getActivity().getSupportFragmentManager().popBackStack();
                    showHelpDialog();
                    break;

            case R.id.menu:
                showFloatingMenus();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void showFloatingMenus() {

        Fragments.FloatingMenusDialog dialog=new FloatingMenusDialog();
        dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), "");
        dialog.setCancelable(true);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.team_button:
                personal.setSelected(false);
                team.setSelected(true);

                break;
            case R.id.personal_button:
                team.setSelected(false);
                personal.setSelected(true);
                break;
        }

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

        TextView tv_cancel=(TextView)helpdialog.findViewById(R.id.tv_cancel);
        TextView tv_report=(TextView)helpdialog.findViewById(R.id.tv_report);
        TextView tv_tutorial=(TextView)helpdialog.findViewById(R.id.tv_tutorial);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpdialog.dismiss();
            }
        });

        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URI="mailto:surender9466073570@gmail.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse(URI);
                intent.setData(data);
                startActivity(intent);
//                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                intent.setType("text/html");
//                intent.setData(Uri.parse("mailto:" + "recipient@example.com"));
//                List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
//
//                if (!resInfo.isEmpty()) {
//                    for (ResolveInfo info : resInfo) {
//                        if (info.activityInfo.packageName.toLowerCase().contains("gmail") || info.activityInfo.name.toLowerCase().contains("gmail"))
//                        {
//                            intent.putExtra(android.content.Intent.EXTRA_TEXT, "extra text");
//
//                            intent.setPackage(info.activityInfo.packageName);
//                            startActivity(Intent.createChooser(intent, "Send email.."));
//                        }
//                    }
//                }
            }
        });

        helpdialog.show();
    }

}
