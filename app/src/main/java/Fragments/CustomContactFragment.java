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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.*;
import model.AllContacts;
import register_frag.Login;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;
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
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String uid,reflink="",First_contact_added="false";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.custom_contact_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HomeActivity.title.setText("Custom Contact");
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

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

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

        getdatafromfirebase();

    }
    public void Alert() {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addcontact_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView tv_no=(TextView)dialog.findViewById(R.id.no);
        TextView tv_yes=(TextView)dialog.findViewById(R.id.yes);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getActivity().getSupportFragmentManager().popBackStack();
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
                    getActivity().getSupportFragmentManager().popBackStack();
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
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                Log.e("child",dataSnapshot.child("First_contact_added").getValue()+" abc");
                First_contact_added=dataSnapshot.child("First_contact_added").getValue().toString();
                if(First_contact_added.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime()+"");
                    String timestamp=String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("First_contact_added","true");
                    newcontact.put("First_contact_added_date",timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    Alert();
                }
                else
                {
                    Alert();
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }
    public static String ConvertParseString(Object obj ) {
        if(obj==null)
        {
            return "";
        }
        else {
            String lastSeen= (String) obj;
            if (lastSeen != null && !TextUtils.isEmpty(lastSeen) && !lastSeen.equalsIgnoreCase("null"))
                return lastSeen;
            else
                return "";
        }

    }

}
