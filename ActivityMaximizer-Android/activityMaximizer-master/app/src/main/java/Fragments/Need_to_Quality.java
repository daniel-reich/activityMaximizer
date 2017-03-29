package Fragments;

import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.NetworkConnection;

/**
 * Created by surender on 2/17/2017.
 */

public class Need_to_Quality extends Fragment {

    View v;
    Firebase mref;
    SharedPreferences pref;
    TextView tv_name,tv_phone;
    ImageView tv_ratinginfo,tv_activitylist,tv_contactnotes;
    FrameLayout frameLayout;
    String uid="",First_call_from_app="";
 public static String competitive,created,credible,familyName,givenName,hasKids,homeowner,hungry,incomeOver40k,married,motivated,ofProperAge,peopleSkills,phoneNumber,ref,rating,recruitRating;
    private JSONObject obj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(v==null) {
            setHasOptionsMenu(true);
            v = inflater.inflate(R.layout.need_to_quality, container, false);
            HomeActivity.title.setText("");

            pref = getActivity().getSharedPreferences("userpref", 0);
            uid = pref.getString("uid", "");

            Firebase.setAndroidContext(getActivity());
            mref = new Firebase("https://activitymaximizer.firebaseio.com/");

            tv_ratinginfo = (ImageView) v.findViewById(R.id.tv_ratinginfo);
            tv_activitylist = (ImageView) v.findViewById(R.id.tv_activitylist);
            tv_contactnotes = (ImageView) v.findViewById(R.id.tv_contactnotes);
            frameLayout = (FrameLayout) v.findViewById(R.id.frame_layout1);

            tv_name = (TextView) v.findViewById(R.id.tv_username);
            tv_phone = (TextView) v.findViewById(R.id.tv_phone);



            competitive = getArguments().getString("competitive");
            created = getArguments().getString("created");
            credible = getArguments().getString("credible");
            familyName = getArguments().getString("familyName");
            givenName = getArguments().getString("givenName");
            hasKids = getArguments().getString("hasKids");
            homeowner = getArguments().getString("homeowner");
            hungry = getArguments().getString("hungry");
            incomeOver40k = getArguments().getString("incomeOver40k");
            married = getArguments().getString("married");
            motivated = getArguments().getString("motivated");
            ofProperAge = getArguments().getString("ofProperAge");
            peopleSkills = getArguments().getString("peopleSkills");
            phoneNumber = getArguments().getString("phoneNumber");
            ref = getArguments().getString("ref");
            rating = getArguments().getString("rating");
            recruitRating = getArguments().getString("recruitRating");

            tv_name.setText(givenName);
            tv_phone.setText(phoneNumber);


            tv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alert();
                }
            });


            Log.e("givenName", givenName + " abc");

            Rating_info_fram basic_frag = new Rating_info_fram();
            Bundle args = new Bundle();
            args.putString("givenName", givenName);
            args.putString("phoneNumber", phoneNumber);
            args.putString("competitive", competitive);
            args.putString("created", created);

            args.putString("credible", credible);
            args.putString("familyName", familyName);
            args.putString("hasKids", hasKids);
            args.putString("homeowner", homeowner);

            args.putString("hungry", hungry);
            args.putString("incomeOver40k", incomeOver40k);
            args.putString("married", married);
            args.putString("motivated", motivated);

            args.putString("ofProperAge", ofProperAge);
            args.putString("peopleSkills", peopleSkills);
            args.putString("rating", rating);
            args.putString("recruitRating", recruitRating);
            args.putString("ref", ref);
            basic_frag.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout1, basic_frag).addToBackStack(null).commit();

            tv_ratinginfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Rating_info_fram basic_frag = new Rating_info_fram();
                    Bundle args = new Bundle();
                    args.putString("givenName", givenName);
                    args.putString("phoneNumber", phoneNumber);
                    args.putString("competitive", competitive);
                    args.putString("created", created);

                    args.putString("credible", credible);
                    args.putString("familyName", familyName);
                    args.putString("hasKids", hasKids);
                    args.putString("homeowner", homeowner);

                    args.putString("hungry", hungry);
                    args.putString("incomeOver40k", incomeOver40k);
                    args.putString("married", married);
                    args.putString("motivated", motivated);

                    args.putString("ofProperAge", ofProperAge);
                    args.putString("peopleSkills", peopleSkills);
                    args.putString("rating", rating);
                    args.putString("recruitRating", recruitRating);
                    args.putString("ref", ref);
                    basic_frag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout1, basic_frag).addToBackStack(null).commit();
                }
            });

            tv_activitylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Activity_list_frag basic_frag = new Activity_list_frag();
                    Bundle args = new Bundle();
                    args.putString("givenName", givenName);
                    basic_frag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout1, basic_frag).addToBackStack(null).commit();
                }
            });

            tv_contactnotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Contact_notes_frag basic_frag = new Contact_notes_frag();
                    Bundle args = new Bundle();
                    args.putString("givenName", givenName);
                    basic_frag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout1, basic_frag).addToBackStack(null).commit();
                }
            });

        }
        return v;
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

    public void Alert() {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.contact_call_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView tv_number=(TextView)dialog.findViewById(R.id.tv_number);
        TextView tv_no=(TextView)dialog.findViewById(R.id.no);
        TextView tv_yes=(TextView)dialog.findViewById(R.id.yes);

        tv_number.setText(phoneNumber);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                getdatafromfirebase();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phoneNumber));
                startActivity(intent);
            }
        });
    }
    public void getdatafromfirebase()
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");

                try {
                    obj = new JSONObject(dataSnapshot.getValue() + "");
                }
                catch(Exception e)
                {

                }
                    Log.e("child",dataSnapshot.child("First_call_from_app").getValue()+" abc");
                First_call_from_app=dataSnapshot.child("First_call_from_app").getValue().toString();
                if(First_call_from_app.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime()+"");
                    String timestamp=String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("First_call_from_app","true");
                    newcontact.put("First_call_from_app_date",timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);

                    Achivement_Details frag = new Achivement_Details();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 3);
                    try {
                        obj.put("First_call_from_app","true");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    bundle.putString("data", obj + "");
                    frag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, frag).addToBackStack(null).commit();

                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

}
