package Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;

import Fragments.Goals_Details;
import Fragments.Need_to_Quality;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by surender on 12/17/2016.
 */

public class Current_Goals_adap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;
    JSONArray array;
    int pe1=0;
    public Current_Goals_adap(Context c, JSONArray array) {
        this.c = c;
        this.array=array;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // Log.e("oncreateviewholder","oncreateviewholder");
        View view=null;
        final Context c=parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_goals_adap, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int p) {
       // Log.e("onbindviewholder","onbindviewholder");
        ViewHolder holder1= (ViewHolder) holder;
        //holder1.name.setText("abc");

        try {
            Log.e("array",array+"");
            JSONObject obj=array.getJSONObject(p);
            Log.e("obj",obj+"");
            JSONObject obj1=obj.getJSONObject("activityCount");
            int app_set=Integer.valueOf(obj1.getString("Appointment Set"));
            int cls_life=Integer.valueOf(obj1.getString("Closed Life"));
            int went_kt=Integer.valueOf(obj1.getString("Went on KT"));
            int tot_pre=Integer.valueOf(obj1.getString("Total Premium"));
            int cls_bus=Integer.valueOf(obj1.getString("Closed Other Business"));
            int cont_add=Integer.valueOf(obj1.getString("Contacts Added"));
            int cls_iba=Integer.valueOf(obj1.getString("Closed IBA"));
            int tot=app_set+cls_bus+cls_iba+cls_life+went_kt+tot_pre+cont_add;

            int app_set1=0,cls_bus1=0,cls_iba1=0,cls_life1=0,went_kt1=0,tot_pre1=0,cont_add1=0;

            JSONObject obj2=obj.getJSONObject("currentCount");
            if(obj2.has("Appointment Set"))
                app_set1=Integer.valueOf(obj2.getString("Appointment Set"));
            if(obj2.has("Closed Life"))
                cls_life1=Integer.valueOf(obj2.getString("Closed Life"));
            if(obj2.has("Went on KT"))
                went_kt1=Integer.valueOf(obj2.getString("Went on KT"));
            if(obj2.has("Total Premium"))
                tot_pre1=Integer.valueOf(obj2.getString("Total Premium"));
            if(obj2.has("Closed Other Business"))
                cls_bus1=Integer.valueOf(obj2.getString("Closed Other Business"));
            if(obj2.has("Contacts Added"))
                cont_add1=Integer.valueOf(obj2.getString("Contacts Added"));
            if(obj2.has("Closed IBA"))
                cls_iba1=Integer.valueOf(obj2.getString("Closed IBA"));

            int tot_achive=app_set1+cls_bus1+cls_iba1+cls_life1+went_kt1+tot_pre1+cont_add1;

            int per=tot_achive*100;
           final int pe=(Integer)per/tot;

            Log.e("total value",tot+" "+pe);

            holder1.tv_value.setText(tot_achive+"/"+tot);
            holder1.name.setText(obj.getString("title"));
            holder1.bar.setProgress(pe);


        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Constants.jsonObject=array.getJSONObject(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Bundle bundle=new Bundle();
                bundle.putInt("percentage",pe);
                Goals_Details frag=new Goals_Details();
                frag.setArguments(bundle);

                ((FragmentActivity) c).getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout, frag).
                        addToBackStack(null).commit();
            }
        });

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("exception","e",e);
        }


    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

       public TextView name,tv_value;
        public LinearLayout layout;
        public ProgressBar bar;

        public ViewHolder(View v) {
            super(v);

            name=(TextView)v.findViewById(R.id.tv_goalname);
            tv_value=(TextView)v.findViewById(R.id.tv_value) ;
            layout=(LinearLayout)v.findViewById(R.id.layout);
            bar=(ProgressBar)v.findViewById(R.id.progressbar);

        }
    }
}
