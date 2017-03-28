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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    int sizee=0;
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


        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

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

//                sizee=data.size();
//                if(sizee>0)
//                {
//                    check_Goals();
//                }
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


//    public void check_Goals(){
//
//        Log.e("check_goals_call","check_goals_call");
//
//        mref=new Firebase("https://activitymaximizer.firebaseio.com/users/"+pref.getString("uid","")+"/Goals/");
//        mref.keepSynced(true);
//
//        ChildEventListener childEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.e("Child_Event_Verse", "onChildChanged:" + dataSnapshot.getKey());
//                String key=dataSnapshot.getKey();
////                long key= Long.parseLong(dataSnapshot.getKey());
////                long v=1488570223417l;
////                int vv=v.compareTo(key);
//                //if (v<key){
//                String startdate = (String) dataSnapshot.child("startDate").getValue();
//                String enddate = (String) dataSnapshot.child("endDate").getValue();
//                //04/03/2017 12:46 PM
//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
//                String currentDate = formatter.format(new Date());
//
//                try {
//                    Date start_d = (Date)formatter.parse(startdate);
//                    Date end_d = (Date)formatter.parse(enddate);
//                    Date current_d = (Date)formatter.parse(currentDate);
//
//                    Log.e("date_start",start_d+"");
//                    Log.e("date_end",end_d+"");
//                    Log.e("date_current",current_d+"");
//
//                    Log.e("increement_value","Contacts Added  ------- ");
//
//                    int cur_to_end=end_d.compareTo(current_d);
//                    int cur_to_start=start_d.compareTo(current_d);
////                    Log.e("cur_to_end",cur_to_end+"");
////                    Log.e("cur_to_start",cur_to_start+"");
//                    if(cur_to_end>0){
//                        Log.e("end_date_is ","greater than cur_date");
//                        if(cur_to_start<0) {
//                            Log.e("current_date_is", "greater than start date");
//                            //update value
//                            Map<String, Object> activitycount = (Map<String, Object>) dataSnapshot.child("activityCount").getValue();
//
//                            if(Integer.valueOf(activitycount.get("Contacts Added").toString())>0){
//                                increment_Goals_Counter(mref,key,"Contacts Added");
//                            }
//
//                        }
//                        else
//                            Log.e("start_date_is","greater than current date");
//                    }
//                    else
//                        Log.e("currnet_date_is ","greater than end_date");
//
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    Log.e("date_time_exception","e",e);
//                }
//                Log.e("start_date","startdate: "+startdate);
//                Log.e("end_date","enddate: "+enddate);
//                Log.e("currnet_date","Date: "+currentDate);
//
////                    if (ss!=null){
////                        Log.e("TEST","Changed values: null ");
////                    }
////                }
////                else
////                    Log.e("onChildAdded","Changed values: null ");
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.e("onChildChanged","Changed values: null ");
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.e("onChildremoved","Changed values: null ");
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                Log.e("onChildMoved","Changed values: null ");
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.e("onCancelled","Changed values: null ");
//            }
//        };
//        mref.addChildEventListener(childEventListener);
//    }
//
//    public void increment_Goals_Counter(Firebase ref,String key,String node) {
//
//        Log.e("uid",pref.getString("uid",""));
//        Log.e("increment_Goals_call","increment_Goals_Counter_call");
////        String currentDate = datecurrent();
//
//        Log.e("key_node",key+"/"+node);
//
//        ref.child(key).child("currentCount").child(node).runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(final MutableData currentData) {
//                if (currentData.getValue() == null) {
//                    currentData.setValue(sizee);
//                } else {
//                    currentData.setValue((Long) currentData.getValue() + sizee);
//                }
//                return Transaction.success(currentData);
//            }
//
//            @Override
//            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
//                if (firebaseError != null) {
//                    Log.e("Firebase goals counter","increement failed");
//                } else {
//                    Log.e("Firebase goals counter","increment succeeded.");
//                }
//            }
//        });
//    }



}
