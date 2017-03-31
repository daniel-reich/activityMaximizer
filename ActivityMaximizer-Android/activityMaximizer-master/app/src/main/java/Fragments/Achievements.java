package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import Adapter.Achivement_Adap;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */

public class Achievements extends Fragment
{
    GridView gridView;
    Achivement_Adap adap;
    View v;
    Firebase mref;
    FirebaseAuth firebaseAuth;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String uid="",uidd="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.achievement_fragment,container,false);
        setHasOptionsMenu(true);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle("Achievements");

//        HomeActivity.tv_back.setVisibility(View.VISIBLE);
//        HomeActivity.tv_back.setText("Back");
       // setHasOptionsMenu(false);


        gridView=(GridView)v.findViewById(R.id.gridview);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase.setAndroidContext(getActivity());



        firebaseAuth = FirebaseAuth.getInstance();
        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);


//        try {
//            uidd = getArguments().getString("uid");
//            Log.e("uidd",uidd);
//            if (uidd.length() > 1) {
//                uid = uidd;
//            } else {
                uid = pref.getString("uid", "");
//            }
//        }catch (Exception e)
//        {
//            Log.e("Exception",e.getMessage());
//        }


//        Log.e("uid",pref.getString("uid",""));
//        try {
//            adap=new Achivement_Adap(getActivity(),new JSONObject(pref.getString("achivement","")));
//            gridView.setAdapter(adap);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        getdatafromfirebase();

        return v;
    }

    public void getdatafromfirebase()
    {
        mref.child("users").child(uid).child("achievements").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");

                try {
                    final JSONObject obj=new JSONObject(dataSnapshot.getValue()+"");
                    Log.e("json",obj.toString());

                    edit=pref.edit();
                    edit.putString("achivement",obj+"");
                    edit.commit();

                    adap=new Achivement_Adap(getActivity(),obj);
                    gridView.setAdapter(adap);
                    adap.notifyDataSetChanged();
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Achivement_Details frag=new Achivement_Details();
                            Bundle bundle=new Bundle();
                            bundle.putInt("position",i);
                            bundle.putString("data",obj+"");
                            frag.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.frame_layout,frag).addToBackStack(null).commit();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("json exception","e",e);
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
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

}
