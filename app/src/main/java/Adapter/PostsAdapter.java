package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class PostsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
Context c;
    JSONArray json;
    SharedPreferences pref;

    public PostsAdapter(Context c, JSONArray json) {
        this.c = c;
        this.json=json;
        pref=c.getSharedPreferences("userpref",0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView name,content,tv_date;


        public ViewHolder(View itemView) {

            super(itemView);

            name=(TextView)itemView.findViewById(R.id.tv_username);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            content=(TextView)itemView.findViewById(R.id.tv_desc);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            final Context c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postsadapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;

        try {
            JSONObject json_object=json.getJSONObject(position);

            String type=json_object.getString("type");
            if(type.equalsIgnoreCase("challange")){

                holder1.name.setText("new Challange!");

                String date=timediff(json_object.getString("created"));
                holder1.tv_date.setText(date+"");

                if(json_object.getBoolean("hasTimeLimit")) {

                    String dd=getDate(json_object.getString("endDate"));

                    holder1.content.setText(pref.getString("givenName", "") + " " + pref.getString("familyName", "") +
                            " has created a new challenge called time true. Get the most required activity by " +
                            dd + " to earn the reward: " + json_object.getString("reward") + ". See chalenges for more details.");
                }
                else{
                    holder1.content.setText(pref.getString("givenName", "") + " " + pref.getString("familyName", "") +
                            " has created a new challenge called time false. Get the most required activity to earn the reward: " + json_object.getString("reward") + ". See chalenges for more details.");
                }

            }else {

                String date=timediff(json_object.getString("created"));
                holder1.tv_date.setText(date+"");
                holder1.name.setText(json_object.getString("name"));
                holder1.content.setText(json_object.getString("content"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("json exception","e",e);
        }


    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return json.length();
    }

    public String getDate(String timestamp){
        String date = "";
        Date d = new Date(timestamp);

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        Log.e("date before convert",d+"");

        sdf.setCalendar(cal);
        cal.setTime(d);
        date= sdf.format(d);

        Log.e("date after convert",date);


        return date;
    }

    public String getDatecount(String timestamp){
        String date = "";
        Date d = new Date(Long.valueOf(timestamp));

//        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        Log.e("date before convert",d+"");

        sdf.setCalendar(cal);
        cal.setTime(d);
        date= sdf.format(d);

        Log.e("date after convert",date);


        return date;
    }

    public String timediff(String timestamp)
    {

        String prevdate=getDatecount(timestamp);
        String curdate;

        java.util.Date d11 = null;
        java.util.Date d2 = null;

        String[] dd1= prevdate.split(" ");
        String[] date1=dd1[0].split("-");
        // String curdate;
        Calendar cal=Calendar.getInstance();

        curdate=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+" "+
                cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        try {
            d11 = format.parse(prevdate);
            d2 = format.parse(curdate);

            //in milliseconds
            long diff = d2.getTime() - d11.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            String pd=dd1[0];

            String cd;//=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
            String[] cdarr=curdate.split(" ");
            cd=cdarr[0];
            String[] date2=cd.split("-");

            int years=0,months=0,days=0;

            years=Integer.parseInt(date2[0])-Integer.parseInt(date1[0]);
            months=Integer.parseInt(date2[1])-Integer.parseInt(date1[1]);
            days=Integer.parseInt(date2[2])-Integer.parseInt(date1[2]);

            years=(months<0)? years-1 : years;
            months=(months<0)? 12+(months) : months;
            months=(days<0)? months-1 : months;
            days=(days<0)? 30+days : days;

            //	Log.e("di time= "," hrs= "+diffHours+" min= "+diffMinutes+" "+" years=  "+years+" month=  "+months+" days= "+days+" pd= "+pd+" cd= "+cd);

            if(years>0)
            {
                return (String.valueOf(years)+"Y");
            }
            else if(months>0&years<1)
            {

                return (String.valueOf(months) + "Mt");
                //newsdateedit.putString("postdate"+i,String.valueOf(months)+"Months ago");
            }
            else if(days>0&months<1)
            {
                return (String.valueOf(days) + "D");
                //newsdateedit.putString("postdate"+i,String.valueOf(months)+" Days ago");
            }
            else if(diffHours>0&days<1)
            {
                return (String.valueOf(diffHours) + "H");
                //newsdateedit.putString("postdate"+i,String.valueOf(months)+" Hours ago");
            }
            else if(diffMinutes>0&diffHours<1)
            {
                return (String.valueOf(diffMinutes) + "M");
                //newsdateedit.putString("postdate"+i,String.valueOf(months)+" Minutes ago");
            }
            else if(diffSeconds>0&diffMinutes<1)
            {
                return ("0M");
                //newsdateedit.putString("postdate"+i,"0 Minutes ago");
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.e("di time error= ",e+"");
        }
        return ("1 Minutes ago");
    }


}
