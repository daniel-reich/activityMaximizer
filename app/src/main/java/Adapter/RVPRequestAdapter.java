package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Fragments.ListContact;
import model.AllList;
import model.RVPRequest;
import u.activitymanager.R;

/**
 * Created by Surbhi on 09-03-2017.
 */
public class RVPRequestAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<JSONObject> data;
    StorageReference storageRef;
    Firebase mref;
    SharedPreferences pref;


    public RVPRequestAdapter(Context c, ArrayList<JSONObject> data) {
        this.c = c;
        this.data = data;
        Log.e("size",data.size()+"");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layout;
        TextView name, detail;
         LinearLayout view;
        public ViewHolder(View itemView) {
            super(itemView);
            this.view=(LinearLayout)itemView.findViewById(R.id.layout);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.detail = (TextView) itemView.findViewById(R.id.detail);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvp_request_adapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;

        try {
            holder1.name.setText(data.get(position).getString("username"));
            holder1.detail.setText(data.get(position).getString("key")+" has requested to become an RVP");
            holder1.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog RvpDialog = new Dialog(c);
                    RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    RvpDialog.setContentView(R.layout.allowrequest_dialog);
                    Window window = RvpDialog.getWindow();
                    TextView tv_send = (TextView) RvpDialog.findViewById(R.id.tv_save);
                    TextView tv_cancel = (TextView) RvpDialog.findViewById(R.id.cancel);
                   // tv_send.setText("Send");

                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RvpDialog.dismiss();

                        Log.e("pos",position+"");
                            try {

                                Log.e("enter", "enter");
            pref = c.getSharedPreferences("userpref", 0);
            Firebase.setAndroidContext(c);
            storageRef = FirebaseStorage.getInstance().getReference();
            mref = new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
            Log.e("values", data.get(position).getString("key") + "," + data.get(position).getString("key"));
            mref.child("Solution Numbers").child(data.get(position).getString("RVPsoltn_number")).child("Pending Requests").child("RVP Requests").child(data.get(position).getString("key")).removeValue();

                                //removeRequest(data.get(getAdapterPosition()).getString("key"), data.get(getAdapterPosition()).getString("data.get(getAdapterPosition()).getString(\"key\")"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    tv_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //  RvpDialog.dismiss();
                            RvpDialog.dismiss();

                            Log.e("pos",position+"");
                            try {

                                Log.e("enter", "enter");
                                pref = c.getSharedPreferences("userpref", 0);
                                Firebase.setAndroidContext(c);
                                storageRef = FirebaseStorage.getInstance().getReference();
                                mref = new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
                                Map sendRequest = new HashMap();
                                sendRequest.put("rvp_solution_number","");
                                sendRequest.put("upline_solution_number","");
                                Log.e("uid",data.get(position).getString("uid"));
                                mref.child("users").child(data.get(position).getString("uid")).updateChildren(sendRequest);
                                Log.e("values", data.get(position).getString("key") + "," + data.get(position).getString("key"));
                                mref.child("Solution Numbers").child(data.get(position).getString("RVPsoltn_number")).child("Pending Requests").child("RVP Requests").child(data.get(position).getString("key")).removeValue();

                                //removeRequest(data.get(getAdapterPosition()).getString("key"), data.get(getAdapterPosition()).getString("data.get(getAdapterPosition()).getString(\"key\")"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    RvpDialog.show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        holder1.username.setText(data.get(position).getName());
//
//        holder1.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.e("givennemee",data.get(position).getName()+" avc");
//                ListContact basic_frag = new ListContact();
//                Bundle args = new Bundle();
//                args.putString("givenName", data.get(position).getName());
//                basic_frag.setArguments(args);
//
//                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, basic_frag).
//                        addToBackStack(null).commit();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }
}
