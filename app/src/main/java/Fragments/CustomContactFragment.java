package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.NetworkConnection;

/**
 * Created by Surbhi on 18-02-2017.
 */
public class CustomContactFragment extends Fragment
{
    View view;
    EditText et_fname,et_lname,et_phone,et_note;
    TextView tv_save;
    String st_fname="",st_lname="",st_phone="",st_note="";
    NetworkConnection nwc;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.custom_contact_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Custom Contact");
         setHasOptionsMenu(true);

        et_fname=(EditText)view.findViewById(R.id.et_fname);
        et_lname=(EditText)view.findViewById(R.id.et_lname);
        et_phone=(EditText)view.findViewById(R.id.et_phone);
        et_note=(EditText)view.findViewById(R.id.et_note);
        tv_save=(TextView)view.findViewById(R.id.tv_save);

        nwc=new NetworkConnection(getActivity());

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st_fname=et_fname.getText().toString();
                st_lname=et_lname.getText().toString();
                st_phone=et_phone.getText().toString();
                st_note=et_note.getText().toString();

                if(st_fname.length()<1){
                    Toast.makeText(getActivity(),"First Name is Required",Toast.LENGTH_LONG).show();
                    return;
                }
                if(st_phone.length()<1){
                    Toast.makeText(getActivity(),"Phone No. is Required",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
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
