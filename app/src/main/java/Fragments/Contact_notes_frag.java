package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.ListAdapter;
import Adapter.Note_Adapter;
import model.AllList;
import model.AllNote;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by surender on 2/17/2017.
 */

public class Contact_notes_frag extends Fragment {

    RecyclerView rView;
    View v;
    TextView tv_newactivity;
    Firebase mref;
    SharedPreferences pref;
    LinearLayoutManager layoutManager;
    String name="",notetxt="",reflink="",uid="",timestamp="";
    Note_Adapter adapter;
    ArrayList<AllNote> data;
    Dialog AddNewNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        v=inflater.inflate(R.layout.activity_list_frag,container,false);

        tv_newactivity=(TextView)v.findViewById(R.id.tv_newactivity);
        tv_newactivity.setText("+ New Note");

        rView=(RecyclerView)v.findViewById(R.id.rview);
        // rView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(getActivity());

        name= getArguments().getString("givenName");

        pref=getActivity().getSharedPreferences("userpref",0);
        Firebase.setAndroidContext(getActivity());
        uid=pref.getString("uid","");


        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");


        tv_newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteDialog();
            }
        });

        getnotefromfirebase();
        return v;
    }


    private void addNoteDialog() {
        AddNewNote=new Dialog(getActivity());
        AddNewNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AddNewNote.setContentView(R.layout.add_note_dialog);
        Window window = AddNewNote.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText listname=(EditText)AddNewNote.findViewById(R.id.et_name);
        TextView save=(TextView)AddNewNote.findViewById(R.id.tv_save);
        TextView cancel=(TextView)AddNewNote.findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notetxt=listname.getText().toString();
                if(notetxt.length()>0) {
                    java.sql.Timestamp timeStampDate = new Timestamp(new Date().getTime());
                    Log.e("Today is ", timeStampDate.getTime()+"");
                    timestamp=String.valueOf(timeStampDate.getTime());

                    AddNewNote.dismiss();
                    reflink = Constants.URL +"contacts/"+ uid + "/" + name+"/notes/"+timestamp;
                    addNewNote();
                }
                else {
                    Toast.makeText(getActivity(),"Note Name is Required",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNote.dismiss();
            }
        });

        AddNewNote.show();
    }
    private void addNewNote() {
        Map newnote = new HashMap();
        newnote.put("content",notetxt);
        newnote.put("created",timestamp);
        newnote.put("ref",reflink);

//        Map tms = new HashMap();
//        tms.put(timestamp,newnote);

//        mref.child("contacts").child(uid)
//                .child(st_fname).child("notes").child(timestamp)
//                .setValue(newnote);



//        Map newcontact = new HashMap();
//        newcontact.put("notes",newnote);

        mref.child("contacts")
                .child(uid)
                .child(name).child("notes").child(timestamp)
                .updateChildren(newnote);
        getnotefromfirebase();
    }

    public void getnotefromfirebase()
    {
        mref.child("contacts").child(pref.getString("uid","")).child(name).child("notes").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                data=new ArrayList<AllNote>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("child",child.getKey()+" abc");
                    data.add(new AllNote(child.child("content").getValue().toString(),child.child("created").getValue().toString(),child.child("ref").getValue().toString()));

                    Log.e("child",child.child("content").getValue()+" abc");
                }
                adapter=new Note_Adapter(getActivity(),data);
                rView.setLayoutManager(layoutManager);
                rView.setAdapter(adapter);







            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }
}
