package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public RVPRequestAdapter(Context c, ArrayList<JSONObject> data) {
        this.c = c;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        TextView name,detail;
        public ViewHolder(View itemView) {
            super(itemView);

            this.name=(TextView)itemView.findViewById(R.id.name);
            this.detail=(TextView)itemView.findViewById(R.id.detail);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 showAllowDialog();
             }

             private void showAllowDialog() {


            final Dialog     RvpDialog=new Dialog(c);
                 RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                 RvpDialog.setContentView(R.layout.allowrequest_dialog);
                 Window window = RvpDialog.getWindow();
                 TextView tv_send=(TextView)RvpDialog.findViewById(R.id.tv_save);
                 TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
                 tv_send.setText("Send");

                 tv_cancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         RvpDialog.dismiss();
                     }
                 });
                 tv_send.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         RvpDialog.dismiss();

                     }
                 });

                 window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 RvpDialog.show();
             }
         });
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
