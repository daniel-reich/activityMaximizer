package Fragments;

import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import u.activitymanager.R;
import utils.Constants;
import utils.NetworkConnection;

import static Fragments.Activity_list_frag.dialog;

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
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String uid,reflink="",First_contact_added="false";

    JSONObject obj = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.custom_contact_fragment,container,false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("Custom Contact");

         setHasOptionsMenu(true);
        pref=getActivity().getSharedPreferences("userpref",0);
        uid=pref.getString("uid","");

        et_fname=(EditText)view.findViewById(R.id.et_fname);
        et_lname=(EditText)view.findViewById(R.id.et_lname);
        et_phone=(EditText)view.findViewById(R.id.et_phone);
        et_note=(EditText)view.findViewById(R.id.et_note);
        tv_save=(TextView)view.findViewById(R.id.tv_save);

        Firebase.setAndroidContext(getActivity());
        nwc=new NetworkConnection(getActivity());

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

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
                else if(st_phone.length()<1){
                    Toast.makeText(getActivity(),"Phone No. is Required",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    if(st_lname.length()>1)
                    {
                        st_fname=st_fname+" "+st_lname;
                    }
                    reflink= Constants.URL+"contacts/"+uid+"/"+st_fname;
                    addNewContact();

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

    private void addNewContact() {
        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
        Log.e("Today is ", timeStampDate.getTime()+"");
        String timestamp=String.valueOf(timeStampDate.getTime());
        String noteref=Constants.URL+"contacts/"+uid+"/"+st_fname+"/notes/"+timestamp;



        Map newnote = new HashMap();
        newnote.put("content",et_note.getText().toString());
        newnote.put("created",timestamp);
        newnote.put("ref",noteref);

        Map tms = new HashMap();
        tms.put(timestamp,newnote);

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
        newcontact.put("notes",tms);
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
//        check_Goals();
        getdatafromfirebase();

    }
    public void Alert( boolean cond) {
         dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addcontact_dialog);
        dialog.setCancelable(false);

        final boolean condition=cond;
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Log.e("cond",condition+"");

        dialog.setCancelable(false);

        TextView tv_no=(TextView)dialog.findViewById(R.id.no);
        final TextView tv_yes=(TextView)dialog.findViewById(R.id.yes);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


                if(getActivity()==null)
                    return;


                Log.e("cond",condition+"");
                if(condition) {
                    Achivement_Details frag = new Achivement_Details();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 4);
                    bundle.putString("data", obj + "");
                    frag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, frag).addToBackStack(null).commit();

                }
                else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                ArrayList<ContentProviderOperation> ops =
                        new ArrayList<ContentProviderOperation>();

                int rawContactID = ops.size();

                // Adding insert operation to operations list
                // to insert a new raw contact in the table ContactsContract.RawContacts
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                // Adding insert operation to operations list
                // to insert display name in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, st_fname)
                        .build());

                // Adding insert operation to operations list
                // to insert Mobile Number in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, st_phone)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());

                try{
                    // Executing all the insert operations as a single database transaction
                    getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//                    Toast.makeText(getActivity(), "Contact is successfully added", Toast.LENGTH_SHORT).show();

                }catch (RemoteException e) {
                    e.printStackTrace();
                }catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getdatafromfirebase()
    {

        mref.child("users").child(uid).child("achievements").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Log.e("get data from server",dataSnapshot.getValue()+" data");
                Log.e("child",dataSnapshot.child("First_contact_added").getValue()+" abc");

                try {
                    obj = new JSONObject(dataSnapshot.getValue()+"");

                    Log.e("obj",obj.toString());
                } catch (JSONException e) {

                    Log.e("e","e",e);
                    e.printStackTrace();
                }

                First_contact_added=String.valueOf(dataSnapshot.child("First_contact_added").getValue());
                if(First_contact_added.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime()+"");
                    String timestamp=String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("First_contact_added","true");
                    try {
                        obj.put("First_contact_added","true");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    newcontact.put("First_contact_added_date",timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);




                    Alert(true);
                }
                else
                {
                    Alert(false);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


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
//                    Log.e("increement_value"," Contacts Added ------- ");
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
//                    currentData.setValue(1);
//                } else {
//                    currentData.setValue((Long) currentData.getValue() + 1);
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
