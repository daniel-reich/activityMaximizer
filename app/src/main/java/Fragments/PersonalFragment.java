package Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.DiamondImageView;

import Adapter.adapter;
import Adapter.personal_list_adapter;
import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView rview;
    LinearLayoutManager linearLayoutManager;
    personal_list_adapter adapter;
    ImageView meter;
    Dialog selectViewdialog,selectPicdialog;
    DiamondImageView Profile_pic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.personal_frag,container,false);
        setHasOptionsMenu(true);
        Log.e("check","check");
        meter=(ImageView)view.findViewById(R.id.meter);
        rview=(RecyclerView)view.findViewById(R.id.rview);
        Profile_pic=(DiamondImageView)view.findViewById(R.id.profile_pic);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(linearLayoutManager);
        adapter=new personal_list_adapter(getActivity());
        rview.setAdapter(adapter);
        Profile_pic.setOnClickListener(this);
        meter.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.meter:
                selectViewDialog();
                break;
            case R.id.tv_breakdown:
                selectViewdialog.dismiss();
                showDialog();
                break;
            case R.id.tv_graph:
                break;
            case R.id.profile_pic:
                selectPicDialog();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                //getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectPicDialog() {
        selectPicdialog=new Dialog(getActivity());
        selectPicdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPicdialog.setContentView(R.layout.pick_image_dialog);
        Window window = selectPicdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectPicdialog.show();
    }

    private void selectViewDialog() {
        selectViewdialog=new Dialog(getActivity());
        selectViewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectViewdialog.setContentView(R.layout.graphviewdialog);
        Window window = selectViewdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView Activity_value_breakdown=(TextView)selectViewdialog.findViewById(R.id.tv_breakdown);
        TextView Graph=(TextView)selectViewdialog.findViewById(R.id.tv_graph);
        Activity_value_breakdown.setOnClickListener(this);
        Graph.setOnClickListener(this);
        selectViewdialog.show();
    }

    private void showDialog()
    {

        Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points);
        RecyclerView rview=(RecyclerView)dialog.findViewById(R.id.recyclerview);
        LinearLayoutManager lManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(lManager);
        rview.setNestedScrollingEnabled(false);
        adapter adap=new adapter();
        rview.setAdapter(adap);
        dialog.show();
    }
}
