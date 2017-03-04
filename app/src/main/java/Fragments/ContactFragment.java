package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.ClientAdapter;
import Adapter.ListAdapter;
import model.AllContact;
import model.AllList;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;
import utils.NetworkConnection;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    LinearLayoutManager layoutManager;
    RecyclerView rView;
    View view;
    TextView AllContacts,tv_list,tv_need_to_qualify;
    Dialog helpdialog,AddContactDialog,AddNewContact;
    Firebase mref;
    ListAdapter adapter;
    SharedPreferences pref;
    String uid,reflink="",st_name="";
    ArrayList<AllList> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_contact,container,false);
        setHasOptionsMenu(true);
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();
        pref=getActivity().getSharedPreferences("userpref",0);
        uid=pref.getString("uid","");

        Firebase.setAndroidContext(getActivity());

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        rView=(RecyclerView)view.findViewById(R.id.list_recycler);
        layoutManager=new LinearLayoutManager(getActivity());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Contacts");
        AllContacts=(TextView)view.findViewById(R.id.tv_all_contacts);
        tv_need_to_qualify=(TextView)view.findViewById(R.id.tv_need_to_qualify);
        tv_list=(TextView)view.findViewById(R.id.list_txt);
        tv_need_to_qualify.setOnClickListener(this);
        AllContacts.setOnClickListener(this);

//        tv_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addContactDialog();
//            }
//        });

        getdatafromfirebase();

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
        final EditText listname=(EditText)AddNewContact.findViewById(R.id.et_name);
        TextView save=(TextView)AddNewContact.findViewById(R.id.tv_save);
        TextView cancel=(TextView)AddNewContact.findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_name=listname.getText().toString();
                if(st_name.length()>0) {
                    AddNewContact.dismiss();
                    reflink = Constants.URL +"lists/"+ uid + "/" + st_name;
                    addNewList();
                }
                else {
                    Toast.makeText(getActivity(),"List name is Required",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewContact.dismiss();
            }
        });

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
        TextView tv_import=(TextView)AddContactDialog.findViewById(R.id.tv_import);
        TextView tv_cancel=(TextView)AddContactDialog.findViewById(R.id.tv_cancel);
        Custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactDialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new CustomContactFragment()).addToBackStack(null).commit();
            }
        });

        tv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactDialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new Import_Contact_Frag()).addToBackStack(null).commit();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactDialog.dismiss();
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
            case R.id.tv_need_to_qualify:
                Log.e("check","check");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new NeedToQualify()).addToBackStack(null).commit();
                break;
        }
    }

    private void addNewList() {
        Map newlist = new HashMap();
        newlist.put("name",st_name);
        newlist.put("ref",reflink);
        mref.child("lists")
                .child(uid)
                .child(st_name)
                .setValue(newlist);

        getdatafromfirebase();

    }


    public void getdatafromfirebase()
    {
        mref.child("lists").child(pref.getString("uid","")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllList>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("child",child+" abc");
                    data.add(new AllList(child.child("name").getValue().toString(),child.child("ref").getValue().toString()));

                    Log.e("child",child.child("name").getValue()+" abc");
                }
                adapter=new ListAdapter(getActivity(),data);
                rView.setLayoutManager(layoutManager);
                rView.setAdapter(adapter);







            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


}
