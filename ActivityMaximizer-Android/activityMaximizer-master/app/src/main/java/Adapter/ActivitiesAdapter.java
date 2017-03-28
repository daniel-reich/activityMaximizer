package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import model.Activities;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class ActivitiesAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    List<Activities> list;

    public ActivitiesAdapter(Context c, List<Activities> list) {
        this.c = c;
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_date;
        LayoutInflater inflater;
        LinearLayout layout,layout1;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            layout=(LinearLayout)itemView.findViewById(R.id.lay_appointment);
            layout1=(LinearLayout)itemView.findViewById(R.id.layout1);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        {
            final Context c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.layout.removeAllViews();

        Log.e("list",list.get(position).getJson());
        DateFormat targetFormat = new SimpleDateFormat("MMM dd,yyyy");

        DateFormat targetFormat1 = new SimpleDateFormat("hh:mm a");
        holder1.tv_date.setText(targetFormat.format(list.get(position).getTime()));
        if(list.get(position).getJson().length()>0) {

            try {
                JSONArray json_array=new JSONArray(list.get(position).getJson());

            for(int i=0;i<json_array.length();i++)
            {

                JSONObject js= (JSONObject) json_array.get(i);


                holder1.layout1.setBackgroundColor(Color.parseColor("#dddddd"));
                holder1.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                holder1.view = holder1.inflater.inflate(R.layout.activity_adapter_item, null);
                TextView tv_username=(TextView)holder1.view.findViewById(R.id.tv_username);
                tv_username.setText(js.getString("userName"));

                TextView tv_type=(TextView)holder1.view.findViewById(R.id.tv_appointmentname);
                tv_type.setText(js.getString("type"));
                TextView tv_time=(TextView)holder1.view.findViewById(R.id.tv_time);
                TextView contact_name=(TextView)holder1.view.findViewById(R.id.tv_username1);
                contact_name.setText(js.getString("contactName"));
                tv_time.setText(targetFormat1.format(Long.parseLong(js.getString("date"))));
                holder1.layout.addView(holder1.view);


            }

            } catch (Exception e) {

                Log.e("e","e",e);


            }
        }

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return list.size();
    }
}
