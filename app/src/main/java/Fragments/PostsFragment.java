package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import Adapter.Current_Goals_adap;
import Adapter.PostsAdapter;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class PostsFragment extends Fragment
{
    View view;
    LinearLayoutManager linearLayoutManager;
    PostsAdapter adapter;
    RecyclerView posts;
    private Firebase mref;
    private SharedPreferences pref;
    JSONArray array;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
         view=inflater.inflate(R.layout.posts,container,false);
         setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        posts=(RecyclerView)view.findViewById(R.id.rview);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        posts.setLayoutManager(linearLayoutManager);
        array=new JSONArray();

        getdatafromfirebase();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(pref.getString("rvp_solution_number","").equals(""))

        menu.findItem(R.id.menu).setIcon(R.drawable.add);

        else

            menu.findItem(R.id.menu).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Add_New_PostFragment()).addToBackStack(null).commit();

        return super.onOptionsItemSelected(item);

    }

    public JSONArray getdatafromfirebase()
    {
        Log.e("getdatafromfir called","getdatafromfirebase called");

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        pref=getActivity().getSharedPreferences("userpref",0);
        
        mref.child("Posts").child(pref.getString("solution_number","")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                       JSONObject json=new JSONObject();

                        json.put("created",messageSnapshot.child("created").getValue());
                        json.put("name",messageSnapshot.child("userName").getValue());
                        json.put("content",messageSnapshot.child("content").getValue());
                        json.put("type","post");
                        array.put(json);

                    }

                    getChallanges();

//                    adapter=new PostsAdapter(getActivity(),array);
//                    posts.setAdapter(adapter);

                    Log.e("json",array.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("array_exception","e",e);
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
                getChallanges();
            }
        });

        return array;
    }


    public JSONArray getChallanges()
    {
        Log.e("getChallanges called","getChallanges called");
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
//        final JSONArray array=new JSONArray();
        pref=getActivity().getSharedPreferences("userpref",0);

        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("contests")
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        JSONObject obj=new JSONObject();

                        Log.e("messageSnapshot_key",messageSnapshot.getKey()+"");

                        obj.put("created", messageSnapshot.child("created").getValue());
//                        obj.put("currentLeader", messageSnapshot.child("currentLeader").getValue());
                        obj.put("endDate", messageSnapshot.child("endDate").getValue());
//                        obj.put("finished", messageSnapshot.child("finished").getValue());
                        obj.put("finishedDate", messageSnapshot.child("finishedDate").getValue());
                        obj.put("hasTimeLimit", messageSnapshot.child("hasTimeLimit").getValue());
//                        obj.put("ref", messageSnapshot.child("ref").getValue());
                        obj.put("reward", messageSnapshot.child("reward").getValue());
                        obj.put("startDate", messageSnapshot.child("startDate").getValue());
                        obj.put("title", messageSnapshot.child("title").getValue());
                        obj.put("type","challange");

                        array.put(obj);

                    }

                    adapter=new PostsAdapter(getActivity(),array);
                    posts.setAdapter(adapter);

                    Log.e("json",array.toString());
                   // shortArray();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("array_exception","e",e);
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");

                adapter=new PostsAdapter(getActivity(),array);
                posts.setAdapter(adapter);
//                shortArray();
            }
        });
        return array;
    }

    public void shortArray(){

        Log.e("shortArray called","shortArray called");

        JSONArray arraysort=new JSONArray();

        try {
            arraysort.put(array.get(0));

            for(int i=1; i < array.length(); i++) {

                JSONObject obj=new JSONObject();

                obj=array.getJSONObject(i);

                long lgj, lgi=Long.parseLong(obj.getString("created"));

                inner:
                for(int j=0;j<arraysort.length();j++)
                {
                    JSONObject obj1=new JSONObject();
                    obj1=arraysort.getJSONObject(j);

                    lgj=Long.parseLong(obj1.getString("created"));

                    if(lgj<lgi)
                    {
                        arraysort.put(obj1);
                        Log.e("jsonarray1",obj1.getString("created"));
                        break inner;
                    }
                    else if(j==(arraysort.length()-1)){
                        arraysort.put(obj);
                        Log.e("jsonarray",obj.getString("created"));
                        break inner;
                    }
                }
            }

            adapter=new PostsAdapter(getActivity(),arraysort);
            posts.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("shortArrar exception","e",e);
        }

        return;
    }
}
