package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import java.util.ArrayList;

import Fragments.DownlineFragment;
import Fragments.Downline_details_frag;
import Fragments.ListContact;
import model.AllDownlines;
import model.AllList;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/7/2017.
 */
public class DownlineAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final SharedPreferences pref;
    Context c;
    ArrayList<AllDownlines> data;
    FragmentManager fm;
    private Firebase mref;



    int mode;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    
    

    public DownlineAdapter(Context c, ArrayList<AllDownlines> data, FragmentManager fm,int mode) {
        this.c = c;
        this.data=data;
        this.fm=fm;
        this.mode=mode;
        pref=c.getSharedPreferences("userpref",0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView username,clientpoint,recruiterpoint;
        SwipeRevealLayout swipeRevealLayout;
        TextView delete,addtoitem;
        ImageView iv_minus;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            clientpoint=(TextView)itemView.findViewById(R.id.tv_pointclients_count);
            recruiterpoint=(TextView)itemView.findViewById(R.id.tv_pointrecuirters_count);
            swipeRevealLayout=(SwipeRevealLayout)itemView.findViewById(R.id.swipeview);
            delete=(TextView)itemView.findViewById(R.id.delete);

            addtoitem=(TextView)itemView.findViewById(R.id.addtoteam);
            if(DownlineFragment.showaddtoteam)
            {
                addtoitem.setVisibility(View.GONE);
            }
            else {
                addtoitem.setVisibility(View.VISIBLE);
            }

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downline_trainees_adap_item, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(data.get(position).getName());
        Log.e("dddd",data.get(position).getFivePointRecruits().toString()+" abc");
        if(data.get(position).getFivePointClients().toString().equalsIgnoreCase(""))
        {
            holder1.clientpoint.setText("0");
        }
        else {
            holder1.clientpoint.setText(data.get(position).getFivePointClients());
        }
        if(data.get(position).getFivePointRecruits().toString().equalsIgnoreCase(""))
        {
            holder1.recruiterpoint.setText("0");
        }
        else {
            holder1.recruiterpoint.setText(data.get(position).getFivePointRecruits());
        }

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                fm.beginTransaction().
//                        replace(R.id.frame_layout,new Downline_details_frag()).addToBackStack(null).commit();
                Downline_details_frag basic_frag = new Downline_details_frag();
                Bundle args = new Bundle();
                args.putString("uid", data.get(position).getUid());
                basic_frag.setArguments(args);

                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, basic_frag).
                        addToBackStack(null).commit();
            }
        });



        holder1.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
                Deleteitem(position);
                //Options(position);

            }
        });

        holder1.addtoitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
                AddToTeam(position);

            }
        });




        viewBinderHelper.bind(holder1.swipeRevealLayout, String.valueOf(data.get(position)));

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }

public void Options(final int position)
{

    Activity activity=(Activity)c;
    final AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);

    builderSingle.setTitle("Select");

    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice);

   if(mode==1)
    arrayAdapter.add("Add To Team");


    arrayAdapter.add("Delete");


    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {


            if(which==0)
            {


                mref.child("users").child(data.get(position).getUid())
                        .child("trainer_solution_number").runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(final MutableData currentData) {


                            currentData.setValue("");


                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                        if (firebaseError != null) {
                            Log.e("Firebase counter","increement failed");



                        } else {
                            Log.d("Firebase counter","increment succeeded.");

                            data.remove(position);
                            notifyDataSetChanged();

                        }
                    }
                });

            }


            else
            {

                mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("downlines").child(data.get(position).getUid()).removeValue();

                data.remove(position);
                notifyDataSetChanged();
            }
                    }
    });
    builderSingle.show();



}


public void Deleteitem(int position)
{
    mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("downlines").child(data.get(position).getUid()).removeValue();

    data.remove(position);

    notifyDataSetChanged();
}


public void AddToTeam(final int position)
{
    mref.child("users").child(data.get(position).getUid())
            .child("trainer_solution_number").runTransaction(new Transaction.Handler() {
        @Override
        public Transaction.Result doTransaction(final MutableData currentData) {


            currentData.setValue("");


            return Transaction.success(currentData);
        }

        @Override
        public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
            if (firebaseError != null) {
                Log.e("Firebase counter","increement failed");



            } else {
                Log.d("Firebase counter","increment succeeded.");

                data.remove(position);
                notifyDataSetChanged();

            }
        }
    });
}




    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
       viewBinderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
       viewBinderHelper.restoreStates(inState);
    }
}





