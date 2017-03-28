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
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.PostsAdapter;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class Add_New_PostFragment extends Fragment
{
    View view;
    EditText post;
    TextView share;
    private Firebase mref;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
         view=inflater.inflate(R.layout.share,container,false);

        post=(EditText)view.findViewById(R.id.post);

        share=(TextView)view.findViewById(R.id.share);
        HomeActivity.title.setText("Posts");



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNewContact();

            }
        });

         setHasOptionsMenu(true);

         return view;
    }



    private void addNewContact() {
        java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
        Log.e("Today is ", timeStampDate.getTime()+"");
        String timestamp=String.valueOf(timeStampDate.getTime());

        pref=getActivity().getSharedPreferences("userpref",0);
        String   uid=pref.getString("uid","");
        String   solution_number=pref.getString("solution_number","");
        String noteref= Constants.URL+"Posts/"+solution_number+"/"+timestamp;







//        mref.child("contacts").child(uid)
//                .child(st_fname).child("notes").child(timestamp)
//                .setValue(newnote);


        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

        Map newcontact = new HashMap();
        newcontact.put("content",post.getText().toString());
        newcontact.put("created",timestamp);

        newcontact.put("ref",noteref);
        newcontact.put("uid",pref.getString("uid",""));

        newcontact.put("userName",pref.getString("givenName","")+" "+pref.getString("familyName",""));
        newcontact.put("uid",uid);

        Log.e("Aa",newcontact.toString());

        mref.child("Posts")
                .child(solution_number)
                .child(timestamp)
                .setValue(newcontact);


        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new PostsFragment()).addToBackStack(null).commit();




    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
           // case R.id.
        }
        return super.onOptionsItemSelected(item);

    }
}
