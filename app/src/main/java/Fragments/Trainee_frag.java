package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.BaseDownlineAdapter;
import Adapter.DownlineAdapter;
import interfaces.IDownloadAdapter;
import model.AllBaseDownlines;
import model.AllDownlines;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/9/2017.
 */
public class Trainee_frag extends Fragment implements IDownloadAdapter {
    View view;
    Dialog helpdialog;

    TextView team,trainees,tv_direct;
    RecyclerView directview;
    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManager1;
    Firebase mref;
    FragmentManager fm;
    SharedPreferences pref;
    ArrayList<AllDownlines> data;
    ArrayList<AllBaseDownlines> basedata;
    DownlineAdapter adapter;
    BaseDownlineAdapter baseadapter;
    String uid="",First_downline="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.trainee_frag,container,false);
        setHasOptionsMenu(true);
        fm=getActivity().getSupportFragmentManager();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PersonalFragment()).addToBackStack(null).commit();

        directview=(RecyclerView)view.findViewById(R.id.directview);

        // rView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager1=new LinearLayoutManager(getActivity());

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

        uid=pref.getString("uid","");


        data=new ArrayList<AllDownlines>();
        basedata=new ArrayList<AllBaseDownlines>();


        tv_direct=(TextView)view.findViewById(R.id.tv_direct);

//        tv_direct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        getalldownlinesuidfromfirebase();


        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            /*case android.R.id.home:
                // getActivity().getSupportFragmentManager().popBackStack();
                showHelpDialog();
                break;*/

            case R.id.menu:
                //showFloatingMenus();
                break;

        }
        return super.onOptionsItemSelected(item);
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



    public void getalldownlinesuidfromfirebase()
    {
        Log.e("testing1",pref.getString("solution_number","")+" abc");
        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("downlines").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("testing",child.getKey()+" abc");
//                    uiddata.add(child.getKey().toString());
                    getalldownlinesfromfirebase(child.getKey());

//                    allContactArrayList.add(new AllContact(child.child("competitive").getValue().toString(),child.child("created").getValue().toString(),child.child("credible").getValue().toString(),child.child("familyName").getValue().toString(),child.child("givenName").getValue().toString(),child.child("hasKids").getValue().toString(),
//                            child.child("homeowner").getValue().toString(),child.child("hungry").getValue().toString(),child.child("incomeOver40k").getValue().toString(),child.child("married").getValue().toString(),child.child("motivated").getValue().toString(),child.child("ofProperAge").getValue().toString(),child.child("peopleSkills").getValue().toString(),
//                            child.child("phoneNumber").getValue().toString(),child.child("rating").getValue().toString(),child.child("recruitRating").getValue().toString(),child.child("ref").getValue().toString()));
//
//                    Log.e("child",child.child("familyName").getValue()+" abc");
                }


            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


    public void getalldownlinesfromfirebase(final String uid)
    {
        mref.child("users").child(uid).addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");

                if(!dataSnapshot.child("trainer_solution_number").getValue().toString().equals(""))

                {

                    String s="";

                    if(dataSnapshot.child("givenName").getValue()!=null)

                         s=dataSnapshot.child("givenName").getValue().toString();

                    else
                       s= dataSnapshot.child("givename").getValue().toString();

                    data.add(new AllDownlines(uid,String.valueOf(s),String.valueOf(dataSnapshot.child("fivePointClients").getValue().toString()),String.valueOf(dataSnapshot.child("fivePointRecruits").getValue().toString())));


                }





                if(data.size()==1)
                {
                    putdownlineinfirebase();
                }
                else
                {
                    setDirectAdapter();
                }

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }



    public void setDirectAdapter() {

        if (getActivity() != null) {
            adapter = new DownlineAdapter(getActivity(), data, fm, 1,Trainee_frag.this);
            directview.setLayoutManager(layoutManager);
            directview.setAdapter(adapter);
        }
    }


    public void putdownlineinfirebase()
    {
        mref.child("users").child(uid).child("achievements").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server", dataSnapshot.getValue() + " data");
                Log.e("child", dataSnapshot.child("First_downline").getValue() + " abc");
                First_downline = dataSnapshot.child("First_downline").getValue().toString();
                if (First_downline.equalsIgnoreCase("false"))
                {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime() + "");
                    String timestamp = String.valueOf(timeStampDate.getTime());
                    Map newcontact = new HashMap();
                    newcontact.put("First_downline", "true");
                    newcontact.put("First_downline_date", timestamp);
                    mref.child("users").child(uid).child("achievements").updateChildren(newcontact);
                    setDirectAdapter();
                }
                else
                {
                    setDirectAdapter();
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }


    @Override
    public void onRefreshAdapter(AllDownlines allDownlines) {
        for (AllDownlines allDownlines1:data) {
           if (allDownlines1.getUid().equalsIgnoreCase(allDownlines.getUid())){
               data.remove(allDownlines1);
               adapter.notifyDataSetChanged();
               Toast.makeText(getActivity(),"on Refresh",Toast.LENGTH_LONG).show();
               break;
           }
        }
    }
}

