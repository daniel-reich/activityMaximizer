package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Fragments.Activity_list_frag;
import Fragments.Need_to_Quality;
import model.AllContact;
import u.activitymanager.R;
import utils.Constants;
import utils.NetworkConnection;

/**
 * Created by Surbhi on 03-03-2017.
 */
public class ContactActivityListAdapter extends RecyclerView.Adapter<ContactActivityListAdapter.ViewHolder> {

    private Context context;

    private SharedPreferences pref;
    private Firebase mref;
    String uid="",Went_on_three_KTs="false",Fifty_KTs="false",One_hundred_KTs="false",Two_hundred_KTs="false",Three_appointments_set="false",Closed_three_life="false",Closed_three_IBAs="false",One_week_eight_five_three_one="false",Perfect_week="false",Two_week_eight_five_three_one="false",Perfect_month="false";
    ArrayList<AllContact> data;




    JSONArray array;
    String str="";
    public static int Went_on_three_KTscount=0,Three_appointments_setcount=0,Closed_three_lifecount=0,Closed_three_IBAscount=0;
    public ContactActivityListAdapter(Context context, JSONArray array,String str) {
        this.context = context;
        this.array=array;
        this.str=str;
        Log.e("notifydatasetchanged","notifydatasetchanged");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_need_activity_adap, viewGroup, false);
        pref=context.getSharedPreferences("userpref",0);
        uid=pref.getString("uid","");
        Firebase.setAndroidContext(context);

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContactActivityListAdapter.ViewHolder viewHolder, int i) {



       /* viewHolder.reward.setText(android.get(i).getCurrent_reward());

        viewHolder.current_leader.setText(android.get(i).getCurrent_leader());*/
        try {

            if(str.equalsIgnoreCase("simple")) {

                viewHolder.tv_activity_list.setText(array.getJSONObject(i).getString("name"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'hh:mm a");

                long time = Long.parseLong(array.getJSONObject(i).getString("time"));

                viewHolder.timestamp.setText(simpleDateFormat.format(time));

                if(array.getJSONObject(i).getString("name").equals("Went on KT"))
                {
                    Went_on_three_KTscount++;
                    if(Went_on_three_KTscount==3)
                    {
                        putthreewentonktinfirebase("3");
                    }
                    else if(Went_on_three_KTscount==50)
                    {
                        putthreewentonktinfirebase("50");
                    }
                    else if(Went_on_three_KTscount==100)
                    {
                        putthreewentonktinfirebase("100");
                    }
                    else if(Went_on_three_KTscount==200)
                    {
                        putthreewentonktinfirebase("200");
                    }
                }
                else if(array.getJSONObject(i).getString("name").equals("Set Appointment"))
                {
                    Three_appointments_setcount++;
                    if(Three_appointments_setcount==3)
                    {
                        putthreeappointmentinfirebase("3");
                    }
                }
                else if(array.getJSONObject(i).getString("name").equals("Closed Life"))
                {
                    Closed_three_lifecount++;
                    if(Closed_three_lifecount==3)
                    {
                        putthreeclosedlifeinfirebase("3");
                    }
                }
                else if(array.getJSONObject(i).getString("name").equals("Closed IBA"))
                {
                    Closed_three_IBAscount++;
                    if(Closed_three_IBAscount==3)
                    {
                        putthreeclosedibainfirebase("3");
                    }
                }
                Log.e("inside", "oo");
                getdatafromfirebase();
            }
            else if(str.equalsIgnoreCase("all"))
            {
                viewHolder.tv_activity_list.setText(array.getJSONObject(i).getString("type"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'hh:mm a");

                long time = Long.parseLong(array.getJSONObject(i).getString("date"));

                viewHolder.timestamp.setText(simpleDateFormat.format(time));
                if(array.getJSONObject(i).getString("type").equals("Went on KT"))
                {
                    Went_on_three_KTscount++;
                    if(Went_on_three_KTscount==3)
                    {
                        putthreewentonktinfirebase("3");
                    }
                    else if(Went_on_three_KTscount==50)
                    {
                        putthreewentonktinfirebase("50");
                    }
                    else if(Went_on_three_KTscount==100)
                    {
                        putthreewentonktinfirebase("100");
                    }
                    else if(Went_on_three_KTscount==200)
                    {
                        putthreewentonktinfirebase("200");
                    }
                }
                else if(array.getJSONObject(i).getString("type").equals("Set Appointment"))
                {
                    Three_appointments_setcount++;
                    if(Three_appointments_setcount==3)
                    {
                        putthreeappointmentinfirebase("3");
                    }
                }
                else if(array.getJSONObject(i).getString("type").equals("Closed Life"))
                {
                    Closed_three_lifecount++;
                    if(Closed_three_lifecount==3)
                    {
                        putthreeclosedlifeinfirebase("3");
                    }
                }
                else if(array.getJSONObject(i).getString("type").equals("Closed IBA"))
                {
                    Closed_three_IBAscount++;
                    if(Closed_three_IBAscount==3)
                    {
                        putthreeclosedibainfirebase("3");
                    }
                }
                Log.e("inside", "oo");
                getdatafromfirebase();
            }
        } catch (Exception e) {

            Log.e("i","e",e);

        }


    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity_list;

        private TextView timestamp;

        public ViewHolder(View view) {
            super(view);
            tv_activity_list=(TextView)view.findViewById(R.id.tv_start);
            timestamp=(TextView)view.findViewById(R.id.tv_date);

            tv_activity_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int position=getPosition();

                    Log.e("position",position+"");






                }
            });

        }
    }

    public void putthreewentonktinfirebase(final String str)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (str.equals("3"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Went_on_three_KTs").getValue() + " abc");
                    Went_on_three_KTs = dataSnapshot.child("Went_on_three_KTs").getValue().toString();
                    if (Went_on_three_KTs.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Went_on_three_KTs", "true");
                        newcontact.put("Went_on_three_KTs_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
                else if (str.equals("50"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Fifty_KTs").getValue() + " abc");
                    Fifty_KTs = dataSnapshot.child("Fifty_KTs").getValue().toString();
                    if (Fifty_KTs.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Fifty_KTs", "true");
                        newcontact.put("Fifty_KTs_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
                else if (str.equals("100"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("One_hundred_KTs").getValue() + " abc");
                    One_hundred_KTs = dataSnapshot.child("One_hundred_KTs").getValue().toString();
                    if (One_hundred_KTs.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("One_hundred_KTs", "true");
                        newcontact.put("One_hundred_KTs_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
                else if (str.equals("200"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Two_hundred_KTs").getValue() + " abc");
                    Two_hundred_KTs = dataSnapshot.child("Two_hundred_KTs").getValue().toString();
                    if (Two_hundred_KTs.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Two_hundred_KTs", "true");
                        newcontact.put("Two_hundred_KTs_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void putthreeappointmentinfirebase(final String str)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (str.equals("3"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Three_appointments_set").getValue() + " abc");
                    Three_appointments_set = dataSnapshot.child("Three_appointments_set").getValue().toString();
                    if (Three_appointments_set.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Three_appointments_set", "true");
                        newcontact.put("Three_appointments_set_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void putthreeclosedlifeinfirebase(final String str)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (str.equals("3"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Closed_three_life").getValue() + " abc");
                    Closed_three_life = dataSnapshot.child("Closed_three_life").getValue().toString();
                    if (Closed_three_life.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Closed_three_life", "true");
                        newcontact.put("Closed_three_life_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void putthreeclosedibainfirebase(final String str)
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (str.equals("3"))
                {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("Closed_three_IBAs").getValue() + " abc");
                    Closed_three_IBAs = dataSnapshot.child("Closed_three_IBAs").getValue().toString();
                    if (Closed_three_IBAs.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("Closed_three_IBAs", "true");
                        newcontact.put("Closed_three_IBAs_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void getdatafromfirebase()
    {
        mref.child("contacts").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("getdatauidd",uid+" abv");
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllContact>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("child",child+" abc");
                    try {
                        data.add(new AllContact(ConvertParseString(child.child("competitive").getValue()), ConvertParseString(child.child("created").getValue()), ConvertParseString(child.child("credible").getValue()), ConvertParseString(child.child("familyName").getValue()), ConvertParseString(child.child("givenName").getValue()), ConvertParseString(child.child("hasKids").getValue()),
                                ConvertParseString(child.child("homeowner").getValue()), ConvertParseString(child.child("hungry").getValue()), ConvertParseString(child.child("incomeOver40k").getValue()), ConvertParseString(child.child("married").getValue()), ConvertParseString(child.child("motivated").getValue()), ConvertParseString(child.child("ofProperAge").getValue()), ConvertParseString(child.child("peopleSkills").getValue()),
                                ConvertParseString(child.child("phoneNumber").getValue()), String.valueOf(ConvertParseInteger(child.child("rating").getValue())), String.valueOf(ConvertParseInteger(child.child("recruitRating").getValue())), ConvertParseString(child.child("ref").getValue())));

                        Log.e("child", child.child("familyName").getValue() + " abc");
                    }
                    catch(Exception e)
                    {
                        Log.e("Exception",e.getMessage());
                    }
                }
                int size=data.size();
                Log.e("sizee",size+" abcc"+Three_appointments_setcount+","+Closed_three_lifecount+","+Closed_three_IBAscount);

                if(Three_appointments_setcount>4 & Three_appointments_setcount<9 & Closed_three_lifecount==3 & Closed_three_IBAscount==1)
                {
                    puteightfivethreeoneinfirebase();
                }
                if(Three_appointments_setcount>4 & Three_appointments_setcount<9 & Closed_three_lifecount<=11 & Closed_three_IBAscount<4 & size>=20)
                {
                    putperfactweekinfirebase();
                }
                if(Three_appointments_setcount>19 & Three_appointments_setcount<33 & Closed_three_lifecount==12 & Closed_three_IBAscount==4)
                {
                    puteightfivethreeonetwoinfirebase();
                }
                if(Three_appointments_setcount>19 & Three_appointments_setcount<33 & Closed_three_lifecount>=12 & Closed_three_IBAscount>=4 & size>=80)
                {
                    putperfactmonthinfirebase();
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


    public static int ConvertParseInteger(Object obj) {
        if(obj==null)
        {
            return 0;
        }
        else {
            Long lastSeen = (Long) obj;
            if (lastSeen != null | lastSeen != 0) {
                String str=String.valueOf(lastSeen);
                return Integer.valueOf(str);
            }
            else
                return 0;
        }
    }
    public void puteightfivethreeoneinfirebase()
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    Log.e("get data from server", dataSnapshot.getValue() + " data");
                    Log.e("child", dataSnapshot.child("One_week_eight_five_three_one").getValue() + " abc");
                One_week_eight_five_three_one = dataSnapshot.child("One_week_eight_five_three_one").getValue().toString();
                    if (One_week_eight_five_three_one.equalsIgnoreCase("false"))
                    {
                        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                        Log.e("Today is ", timeStampDate.getTime() + "");
                        String timestamp = String.valueOf(timeStampDate.getTime());
                        Map newcontact = new HashMap();
                        newcontact.put("One_week_eight_five_three_one", "true");
                        newcontact.put("One_week_eight_five_three_one_date", timestamp);
                        mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void putperfactweekinfirebase()
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server", dataSnapshot.getValue() + " data");
                Log.e("child", dataSnapshot.child("Perfect_week").getValue() + " abc");
                Perfect_week = dataSnapshot.child("Perfect_week").getValue().toString();
                if (Perfect_week.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime() + "");
                    String timestamp = String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("Perfect_week", "true");
                    newcontact.put("Perfect_week_date", timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void puteightfivethreeonetwoinfirebase()
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server", dataSnapshot.getValue() + " data");
                Log.e("child", dataSnapshot.child("Two_week_eight_five_three_one").getValue() + " abc");
                Two_week_eight_five_three_one = dataSnapshot.child("Two_week_eight_five_three_one").getValue().toString();
                if (Two_week_eight_five_three_one.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime() + "");
                    String timestamp = String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("Two_week_eight_five_three_one", "true");
                    newcontact.put("Two_week_eight_five_three_one_date", timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void putperfactmonthinfirebase()
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server", dataSnapshot.getValue() + " data");
                Log.e("child", dataSnapshot.child("Perfect_month").getValue() + " abc");
                Perfect_month = dataSnapshot.child("Perfect_month").getValue().toString();
                if (Perfect_month.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime() + "");
                    String timestamp = String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("Perfect_month", "true");
                    newcontact.put("Perfect_month_date", timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

}
