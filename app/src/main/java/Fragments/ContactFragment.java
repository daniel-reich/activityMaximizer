package Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView AllContacts,tv_list;
    Dialog helpdialog,AddContactDialog,AddNewContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_contact,container,false);
        setHasOptionsMenu(true);
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Contacts");
        AllContacts=(TextView)view.findViewById(R.id.tv_all_contacts);
        tv_list=(TextView)view.findViewById(R.id.list_txt);
        AllContacts.setOnClickListener(this);

        tv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContactDialog();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.drawable.add_contacts);
       // menu.findItem(R.id.menu).setTitle("Add List");
        menu.findItem(R.id.list).setVisible(true);
        menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
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
                showAddContactDialog();
                break;
            case R.id.list:
                addContactDialog();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void addContactDialog() {
        AddNewContact=new Dialog(getActivity());
        AddNewContact.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AddNewContact.setContentView(R.layout.add_contact_dialog);
        Window window = AddNewContact.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AddNewContact.show();
    }

    private void showAddContactDialog() {
        AddContactDialog=new Dialog(getActivity());
        AddContactDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AddContactDialog.setContentView(R.layout.addcontactdialog);
        Window window = AddContactDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AddContactDialog.show();
        TextView Custom=(TextView)AddContactDialog.findViewById(R.id.tv_custom);
        Custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactDialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new CustomContactFragment()).addToBackStack(null).commit();
            }
        });
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
        switch (view.getId())
        {
            case R.id.tv_all_contacts:
                Log.e("check","check");
             getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new AllContacts()).addToBackStack(null).commit();
                break;
        }
    }
}
