package Fragments;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.Import_Contact_Adapter;
import Adapter.ListAllContactAdapter;
import model.AllContact;
import model.AllList;
import model.AllPhoneContact;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Rohan on 3/4/2017.
 */
public class Import_Contact_Frag extends Fragment {

    RecyclerView rView;
    View view;
    Firebase mref;
    SharedPreferences pref;
    LinearLayoutManager layoutManager;
    Import_Contact_Adapter adapter;
    ArrayList<AllPhoneContact> AllContactDataList;
    String uid;
    String name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.import_contact,container,false);
        setHasOptionsMenu(true);
        rView=(RecyclerView)view.findViewById(R.id.rview);
        // rView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(getActivity());

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());

        uid=pref.getString("uid","");

//        name= getArguments().getString("givenName");


        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

//        Log.e("name",name);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Import Contacts");





        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(true);
        menu.findItem(R.id.menu).setTitle("Import");
//        menu.findItem(R.id.list).setVisible(false);
//           menu.findItem(R.id.list).setIcon(R.mipmap.addlist);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ContentResolver cr = getActivity().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            AllContactDataList = new ArrayList<AllPhoneContact>();
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        AllContactDataList.add(new AllPhoneContact(0,contactNumber,contactName));
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext()) ;
        }
        adapter=new Import_Contact_Adapter(getActivity(),AllContactDataList,"rohan");
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:

                ArrayList<AllPhoneContact> data=new ArrayList<AllPhoneContact>();
                for (AllPhoneContact allPhoneContact:AllContactDataList)
                {
                    if(allPhoneContact.getStatus()==1)
                    {
                        data.add(allPhoneContact);
                    }

                }

                for (AllPhoneContact allPhoneContact:data)
                {
                    String relink=Constants.URL+"contacts/"+uid+"/"+allPhoneContact.getAllContactNameList();
                    Log.e("contactname",allPhoneContact.getAllContactNameList()+"  123456");
                    addNewContact(allPhoneContact.getAllContactNameList(),"",allPhoneContact.getAllContactList(),relink);
                }
                getActivity().getSupportFragmentManager().popBackStack();
//                Toast.makeText(getActivity(), data.size()+" abc", Toast.LENGTH_SHORT).show();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new SortByFragment()).addToBackStack(null).commit();
                break;

            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void addNewContact(String st_fname,String st_lname,String st_phone,String reflink) {
//        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
//        Log.e("Today is ", timeStampDate.getTime()+"");
//        String timestamp=String.valueOf(timeStampDate.getTime());
//        String noteref= Constants.URL+"contacts/"+uid+"/"+st_fname+"/notes/"+timestamp;
//        Map newnote = new HashMap();
//        newnote.put("content",et_note.getText().toString());
//        newnote.put("created",timestamp);
//        newnote.put("ref",noteref);
//
//        Map tms = new HashMap();
//        tms.put(timestamp,newnote);

//        mref.child("contacts").child(uid)
//                .child(st_fname).child("notes").child(timestamp)
//                .setValue(newnote);



        Map newcontact = new HashMap();
        newcontact.put("competitive","false");
        newcontact.put("created","");
        newcontact.put("credible","false");
        newcontact.put("familyName",st_lname);
        newcontact.put("givenName",st_fname);
        newcontact.put("hasKids","true");
        newcontact.put("homeowner","false");
        newcontact.put("hungry","false");
        newcontact.put("incomeOver40k","false");
        newcontact.put("married","false");
        newcontact.put("motivated","false");
        newcontact.put("ofProperAge","true");
        newcontact.put("peopleSkills","true");
        newcontact.put("phoneNumber",st_phone);
        newcontact.put("rating",0);
        newcontact.put("recruitRating",0);
        newcontact.put("ref",reflink);
        mref.child("contacts")
                .child(uid)
                .child(st_fname)
                .setValue(newcontact);

    }


}
