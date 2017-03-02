package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */
public class Achivement_Adap extends BaseAdapter {

    LayoutInflater inflater;
    Context c;
    JSONObject obj;

    public Achivement_Adap(Context c, JSONObject obj) {
        this.c = c;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        Context c=viewGroup.getContext();
        inflater =(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.achievement_adap,null);
        ImageView iv=(ImageView)v.findViewById(R.id.imageview);

        try {
        if(i==0){
                if(obj.getString("Closed_three_IBAs").equalsIgnoreCase("true"))
                    iv.setImageResource(R.drawable.closed_three_ibas);
                else
                    iv.setImageResource(R.drawable.closed_three_ibas1);
        }
        else if(i==1) {
            if(obj.getString("Closed_three_life").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.closed_three_life);
            else
                iv.setImageResource(R.drawable.closed_three_life1);
        }
        else if(i==2) {
            if(obj.getString("Fifty_KTs").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.fifty_kts);
            else
                iv.setImageResource(R.drawable.fifty_kts1);
        }else if(i==3) {
            if(obj.getString("First_call_from_app").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.first_call_from_app);
            else
                iv.setImageResource(R.drawable.first_call_from_app1);
        }else if(i==4) {
            if(obj.getString("First_contact_added").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.first_contact_added);
            else
                iv.setImageResource(R.drawable.first_contact_added1);
        }else if(i==5) {
            if(obj.getString("First_downline").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.first_downline);
            else
                iv.setImageResource(R.drawable.first_downline1);
        }else if(i==6) {
            if(obj.getString("One_hundred_KTs").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.one_hundred_kts);
            else
                iv.setImageResource(R.drawable.one_hundred_kts1);
        }else if(i==7) {
            if(obj.getString("One_week_eight_five_three_one").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.one_week_eight_five_three_one);
            else
                iv.setImageResource(R.drawable.one_week_eight_five_three_one1);
        }else if(i==8) {
            if(obj.getString("Perfect_month").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.perfect_month);
            else
                iv.setImageResource(R.drawable.perfect_month1);
        }else if(i==9) {
            if(obj.getString("Perfect_week").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.perfect_week);
            else
                iv.setImageResource(R.drawable.perfect_week1);
        }else if(i==10) {
            if(obj.getString("Ten_five_point_clients").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.ten_five_point_clients);
            else
                iv.setImageResource(R.drawable.ten_five_point_clients1);
        }else if(i==11) {
            if(obj.getString("Ten_five_point_recruits").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.ten_five_point_recruits);
            else
                iv.setImageResource(R.drawable.ten_five_point_recruits1);
        }else if(i==12) {
            if(obj.getString("Ten_new_contacts_added").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.ten_new_contacts_added);
            else
                iv.setImageResource(R.drawable.ten_new_contacts_added1);
        }else if(i==13) {
            if(obj.getString("Thirty_new_contacts_added").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.thirty_new_contacts_added);
            else
                iv.setImageResource(R.drawable.thirty_new_contacts_added1);
        }else if(i==14) {
            if(obj.getString("Three_appointments_set").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.three_appointments_set);
            else
                iv.setImageResource(R.drawable.three_appointments_set1);
        }else if(i==15) {
            if(obj.getString("Twenty_new_contacts_added").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.twenty_new_contacts_added);
            else
                iv.setImageResource(R.drawable.twenty_new_contacts_added1);
        }else if(i==16) {
            if(obj.getString("Two_hundred_KTs").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.two_hundred_kts);
            else
                iv.setImageResource(R.drawable.two_hundred_kts1);
        }else if(i==17) {
            if(obj.getString("Two_week_eight_five_three_one").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.two_week_eight_five_three_one);
            else
                iv.setImageResource(R.drawable.two_week_eight_five_three_one1);
        }else if(i==18) {
            if(obj.getString("Went_on_three_KTs").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.went_on_three_kts);
            else
                iv.setImageResource(R.drawable.went_on_three_kts1);
        }else if(i==19) {
            if(obj.getString("Top_speed").equalsIgnoreCase("true"))
                iv.setImageResource(R.drawable.top_speed);
            else
                iv.setImageResource(R.drawable.top_speed1);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
