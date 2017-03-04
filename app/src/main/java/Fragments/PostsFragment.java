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


        getdatafromfirebase();
         return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.findItem(R.id.menu).setIcon(R.drawable.add);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Add_New_PostFragment()).addToBackStack(null).commit();





        return super.onOptionsItemSelected(item);

    }


    public JSONArray getdatafromfirebase()
    {

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        final JSONArray array=new JSONArray();
        pref=getActivity().getSharedPreferences("userpref",0);
        
        mref.child("Posts").child(pref.getString("solution_number","")).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                try {

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                       JSONObject json=new JSONObject();

                        json.put("name",messageSnapshot.child("userName").getValue());
                        json.put("content",messageSnapshot.child("content").getValue());

                        array.put(json);

                    }


                    adapter=new PostsAdapter(getActivity(),array);
                    posts.setAdapter(adapter);


                    Log.e("json",array.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("array_exception","e",e);
                }

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });

        return array;

    } 
    
    
}
