package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Fragments.Activity_list_frag;
import Fragments.Need_to_Quality;
import Fragments.NewActivityFrag;
import register_frag.Register;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Surbhi on 03-03-2017.
 */
public class SelectNewActivityListAdapter extends RecyclerView.Adapter<SelectNewActivityListAdapter.ViewHolder> {

    private Context context;
    String activity_list[]={"Set Appointment","Went on KT","Closed Life","Closed IBA","Closed Other Business","Appt Set To Closed Life",
          "Appt Set To Closed IBA","Invited to Opportunity Meeting","Went To Opportunity Meeting","Call Back","Dark House","Not Interested"};
    private SharedPreferences pref;
    private Firebase mref;
    private Dialog AddNewContact;
    int amount,increement;
    String increement_value;

    public SelectNewActivityListAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_new_activity_list_adapter, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SelectNewActivityListAdapter.ViewHolder viewHolder, int i) {

       /* viewHolder.reward.setText(android.get(i).getCurrent_reward());

        viewHolder.current_leader.setText(android.get(i).getCurrent_leader());*/
        viewHolder.tv_activity_list.setText(activity_list[i]);


    }

    @Override
    public int getItemCount() {
        return activity_list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity_list;

        public ViewHolder(View view) {
            super(view);
            tv_activity_list=(TextView)view.findViewById(R.id.tv_activitylist);
            tv_activity_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int position=getPosition();

                    Log.e("position",position+"");


                    if(position==0 || position==5 || position==6 || position==9)

                    {

                        Activity_list_frag.dialog.dismiss();
                        HomeActivity activity = (HomeActivity) context;

                        Bundle bundle=new Bundle();
                        bundle.putString("name",activity_list[position]);
                        switch (position)

                        {
                            case 0:
                                increement_value="Set Appointment";
                                increement=2;
                                bundle.putString("title","Appointment with "+ Need_to_Quality.givenName);
                                bundle.putString("increementvalue","Appointments Set");

                                break;

                            case 5:
                                increement=0;
//                                increement_value="Appointment set to Close Life";
                                bundle.putString("title","Appointment set to Close Life with "+ Need_to_Quality.givenName);
                                bundle.putString("increementvalue","null");
                                break;

                            case 6:
                                increement=0;
//                                increement_value="Appointment set to Close IBA";
                                bundle.putString("title","Appointment set to Close IBA with "+ Need_to_Quality.givenName);
                                bundle.putString("increementvalue","null");
                                break;

                            case 9:
                                increement=0;
                                increement_value="Call Back";
                                bundle.putString("title","CallBack  with "+ Need_to_Quality.givenName);
                                bundle.putString("increementvalue","null");
                                break;

                        }

                       NewActivityFrag act= new NewActivityFrag();
                        act.setArguments(bundle);

                        activity.getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_layout,act).addToBackStack(null).commit();
                    }

                    else if(position==2)
                    {
                        increement=5;
                        increement_value="Closed Life";
                        Activity_list_frag.dialog.dismiss();
                        addContactDialog(position);
                    }

                    else {

                        if(position==1) {
                            increement=3;
                            increement_value="Went on KT";
                        }
                       else if(position==3) {
                            increement=5;
                            increement_value="Closed IBA";
                        }
                        else if(position==7) {
                            increement=1;
                            increement_value="Invited to Opportunity Meeting";
                        }
                        else if(position==8) {
                            increement=3;
                            increement_value="Went To Opportunity Meeting";

                        }
                        else if(position==4){
                            increement=0;
                            increement_value="Closed Other Business";
                        }
                        else
                        increement=0;

                        Activity_list_frag.dialog.dismiss();
                        addNewContact(position);
                    }
                }
            });

        }
    }

    private void addContactDialog(final int position) {

        Activity activity = (Activity) context;
        AddNewContact=new Dialog(activity);
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

        listname.setInputType(InputType.TYPE_CLASS_NUMBER);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=Integer.valueOf(listname.getText().toString());
                if(amount>0) {
                    AddNewContact.dismiss();
                    addNewContact(position);

                    check_Goals_premium();
                }
                else
                    Toast.makeText(context,"Please Enter Amount",Toast.LENGTH_LONG).show();
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

    private void addNewContact(int position) {

        Log.e("addNewContact_call","addnewcontact call");

        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
        Log.e("Today is ", timeStampDate.getTime()+"");
        String timestamp=String.valueOf(timeStampDate.getTime());

        JSONObject json=new JSONObject();
        try {
            json.put("name",activity_list[position]);
            json.put("time",timestamp+"");

            Activity_list_frag.js.put(json);
            Activity_list_frag.listadapter.notifyDataSetChanged();
        } catch (Exception e) {

            Log.e("y","e",e);
            e.printStackTrace();
        }

        pref=context.getSharedPreferences("userpref",0);
        String   uid=pref.getString("uid","");
        String noteref= Constants.URL+"events/"+uid+"/"+timestamp;

//        mref.child("contacts").child(uid)
//                .child(st_fname).child("notes").child(timestamp)
//                .setValue(newnote);

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        Map newcontact = new HashMap();
        newcontact.put("contactName", Need_to_Quality.givenName);
        newcontact.put("contactRef",Need_to_Quality.ref);
        newcontact.put("created",timestamp);
        newcontact.put("date",timestamp);
        newcontact.put("eventKitID","");
        newcontact.put("ref",noteref);

        if(amount>0)
            newcontact.put("amount",amount);
        else
            newcontact.put("amount",0);

        newcontact.put("type",activity_list[position]);
        newcontact.put("userName",pref.getString("givenName","")+" "+pref.getString("familyName",""));
        newcontact.put("userRef",pref.getString("ref",""));

        Log.e("Aa",newcontact.toString());

        mref.child("events")
                .child(uid)
                .child(timestamp)
                .setValue(newcontact, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                        Log.e("addNewContact_call","addnewcontact oncomplete");

                       if(increement==1|increement==3|increement==5|increement==2)
                        incrementCounter();

                        if(increement_value.equalsIgnoreCase("Closed Life")|increement_value.equalsIgnoreCase("Went on KT")|
                               increement_value.equalsIgnoreCase("Closed IBA")|increement_value.equalsIgnoreCase("Set Appointment")|
                                increement_value.equalsIgnoreCase("Closed Other Business")){
//                            Log.e("check_goals_call","check_goals_call");
                            check_Goals();
                        }
                        else
                        Log.e("increement_value",increement_value);

                        Activity_list_frag.listadapter.notifyDataSetChanged();
                    }
                });
    }

    public String datecurrent(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Log.e("currenttime",currentDateTimeString);
        String formattedDate = null;
//        DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("MMM dd");
        Date date = null;

        try {
             formattedDate = targetFormat.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();

        }
        return formattedDate;
    }

    public void incrementCounter() {
    Log.e("uid increment counter",pref.getString("uid",""));

        String currentDate = datecurrent();

        mref.child("users").child(pref.getString("uid",""))
                .child("dailyPointAverages").child(currentDate).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(increement);
                } else {
                    currentData.setValue((Long) currentData.getValue() + increement);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    Log.e("Firebase counter","increement failed");
                } else {
                    Log.d("Firebase counter","increment succeeded.");
                }
            }
        });
    }

    public void check_Goals(){

        Log.e("check_goals_call","check_goals_call");

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/users/"+pref.getString("uid","")+"/Goals/");
        mref.keepSynced(true);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Child_Event_Verse", "onChildChanged:" + dataSnapshot.getKey());
                String key=dataSnapshot.getKey();

//                long key= Long.parseLong(dataSnapshot.getKey());
//                long v=1488570223417l;
//                int vv=v.compareTo(key);
                //if (v<key){

                if(!key.equalsIgnoreCase("users")) {
                    String startdate = (String) dataSnapshot.child("startDate").getValue();
                    String enddate = (String) dataSnapshot.child("endDate").getValue();
                    //04/03/2017 12:46 PM
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
                    String currentDate = formatter.format(new Date());

                    try {

                        Log.e("start_date1", "startdate: " + startdate);
                        Log.e("end_date1", "enddate: " + enddate);
                        Log.e("currnet_date1", "Date: " + currentDate);

                        Date start_d = new Date();
                        Date end_d = new Date();
                        Date current_d = new Date();

                        start_d = (Date) formatter.parse(startdate);
                        end_d = (Date) formatter.parse(enddate);
                        current_d = (Date) formatter.parse(currentDate);

                        Log.e("date_start", start_d + "");
                        Log.e("date_end", end_d + "");
                        Log.e("date_current", current_d + "");

                        Log.e("increement_value", increement_value + "  ------- ");

                        int cur_to_end = end_d.compareTo(current_d);
                        int cur_to_start = start_d.compareTo(current_d);
//                    Log.e("cur_to_end",cur_to_end+"");
//                    Log.e("cur_to_start",cur_to_start+"");
                        if (cur_to_end > 0) {
                            Log.e("end_date_is ", "greater than cur_date");
                            if (cur_to_start < 0) {
                                Log.e("current_date_is", "greater than start date");
                                //update value
                                Map<String, Object> activitycount = (Map<String, Object>) dataSnapshot.child("activityCount").getValue();

                                if (Integer.valueOf(activitycount.get(increement_value).toString()) > 0) {
                                    increment_Goals_Counter(mref, key, increement_value);
                                }

                            } else
                                Log.e("start_date_is", "greater than current date");
                        } else
                            Log.e("currnet_date_is ", "greater than end_date");


                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("date_time_exception", "e", e);
                    }

                    Log.e("start_date2", "startdate: " + startdate);
                    Log.e("end_date2", "enddate: " + enddate);
                    Log.e("currnet_date2", "Date: " + currentDate);
                }

//                    if (ss!=null){
//                        Log.e("TEST","Changed values: null ");
//                    }
//                }
//                else
//                    Log.e("onChildAdded","Changed values: null ");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged","Changed values: null ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildremoved","Changed values: null ");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildMoved","Changed values: null ");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("onCancelled","Changed values: null ");
            }
        };
        mref.addChildEventListener(childEventListener);
    }
//
    public void increment_Goals_Counter(Firebase ref,String key,String node)
    {

        Log.e("uid",pref.getString("uid",""));
        Log.e("increment_Goals_call","increment_Goals_Counter_call");
//        String currentDate = datecurrent();

        Log.e("key_node",key+"/"+node);

        ref.child(key).child("currentCount").child(node).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    Log.e("Firebase goals counter","increement failed");
                } else {
                    Log.e("Firebase goals counter","increment succeeded.");
                }
            }
        });
    }


    //************   increement  Total Premium ***********************//


    public void check_Goals_premium(){

        Log.e("check_goals_call","check_goals_call");

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/users/"+pref.getString("uid","")+"/Goals/");
//        mref.keepSynced(true);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Child_Event_Verse", "onChildChanged:" + dataSnapshot.getKey());
                String key=dataSnapshot.getKey();
//                long key= Long.parseLong(dataSnapshot.getKey());
//                long v=1488570223417l;
//                int vv=v.compareTo(key);
                //if (v<key){
                String startdate = (String) dataSnapshot.child("startDate").getValue();

                String enddate = (String) dataSnapshot.child("endDate").getValue();
                //04/03/2017 12:46 PM
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
                String currentDate = formatter.format(new Date());

                Log.e("date_start",startdate+"");
                Log.e("date_end",enddate+"");
                Log.e("date_current",currentDate+"");
                if(!key.equalsIgnoreCase("users")) {
                    try {
                        Date start_d = new Date();
                        Date end_d = new Date();
                        Date current_d = new Date();

                        start_d = (Date) formatter.parse(startdate);
                        end_d = (Date) formatter.parse(enddate);
                        current_d = (Date) formatter.parse(currentDate);

                        Log.e("date_start", start_d + "");
                        Log.e("date_end", end_d + "");
                        Log.e("date_current", current_d + "");

                        Log.e("increement_value", increement_value + "  ------- ");

                        int cur_to_end = end_d.compareTo(current_d);
                        int cur_to_start = start_d.compareTo(current_d);
//                    Log.e("cur_to_end",cur_to_end+"");
//                    Log.e("cur_to_start",cur_to_start+"");
                        if (cur_to_end > 0) {
                            Log.e("end_date_is ", "greater than cur_date");
                            if (cur_to_start < 0) {
                                Log.e("current_date_is", "greater than start date");
                                //update value
                                Map<String, Object> activitycount = (Map<String, Object>) dataSnapshot.child("activityCount").getValue();

                                if (Integer.valueOf(activitycount.get(increement_value).toString()) > 0) {
                                    increment_Goals_Counter_premium(mref, key, "Total Premium");
                                }

                            } else
                                Log.e("start_date_is", "greater than current date");
                        } else
                            Log.e("currnet_date_is ", "greater than end_date");


                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("date_time_exception", "e", e);
                    }
                    Log.e("start_date", "startdate: " + startdate);
                    Log.e("end_date", "enddate: " + enddate);
                    Log.e("currnet_date", "Date: " + currentDate);
                }

//                    if (ss!=null){
//                        Log.e("TEST","Changed values: null ");
//                    }
//                }
//                else
//                    Log.e("onChildAdded","Changed values: null ");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged","Changed values: null ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildremoved","Changed values: null ");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildMoved","Changed values: null ");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("onCancelled","Changed values: null ");
            }
        };
        mref.addChildEventListener(childEventListener);
    }
    //
    public void increment_Goals_Counter_premium(Firebase ref,String key,String node) {

        Log.e("uid",pref.getString("uid",""));
        Log.e("increment_Goals_call","increment_Goals_Counter_call");
//        String currentDate = datecurrent();

        Log.e("key_node",key+"/"+node);

        ref.child(key).child("currentCount").child(node).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(amount);
                } else {
                    currentData.setValue((Long) currentData.getValue() + amount);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    Log.e("Firebase goals counter","increement failed");
                } else {
                    Log.e("Firebase goals counter","increment succeeded.");
                }
            }
        });
    }


    //**************  end icreement Total Premium ******************//


//
//
//    public void check_Challange(){
//
//        Log.e("check_challange_call","check_challange_call");
//
//        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/Solution Numbers/"+pref.getString("solution_number","")+"/contests/");
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
//                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
//                String currentDate = formatter.format(new Date());
//                try {
//                    Date start_d = (Date)formatter.parse(startdate);
//                    Date end_d = (Date)formatter.parse(enddate);
//                    Date current_d = (Date)formatter.parse(currentDate);
//
//                    Log.e("date_start",start_d+"");
//                    Log.e("date_end",end_d+"");
//                    Log.e("date_current",current_d+"");
//
//                    Log.e("increement_value",increement_value+"  ------- ");
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
//                            if(Integer.valueOf(activitycount.get(increement_value).toString())>0){
//                                increment_Challange_Counter(mref,key,increement_value);
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
//    public void increment_Challange_Counter(Firebase ref,String key,String node) {
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
