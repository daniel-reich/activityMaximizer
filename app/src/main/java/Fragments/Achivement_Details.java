package Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.Current_challanges;
import Adapter.Past_challanges;
import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Achivement_Details extends Fragment
{
    View v;
    ImageView iv;
    TextView tv_achivementname,tv_achivementdate;
    boolean b;
    int i;
    String achivement_name;
    JSONObject obj;
    SharedPreferences pref;
    Firebase mref;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      v=inflater.inflate(R.layout.achivement_details,container,false);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        HomeActivity.tv_back.setVisibility(View.VISIBLE);
//        HomeActivity.tv_back.setText("Back");
//        HomeActivity.title.setText("");

        setHasOptionsMenu(true);

        iv=(ImageView)v.findViewById(R.id.iv_imageview);
        tv_achivementname=(TextView)v.findViewById(R.id.tv_achivementname);
        tv_achivementdate=(TextView)v.findViewById(R.id.tv_achivementdate);

        pref=getActivity().getSharedPreferences("userpref",0);

        Firebase.setAndroidContext(getActivity());
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        i=getArguments().getInt("position");

        try {

            obj=new JSONObject(getArguments().getString("data"));

            if(i==9){
                if(obj.getString("Closed_three_IBAs").equalsIgnoreCase("true")) {
                    iv.setImageResource(R.drawable.closed_three_ibas);
                    tv_achivementname.setText("Closed Three IBAs");
                    tv_achivementdate.setText("Earn on,");
                    achivement_name="Closed_three_IBAs";
                    b=true;
                 }
                else {
                    iv.setImageResource(R.drawable.closed_three_ibas1);
                    tv_achivementname.setText("Closed Three IBAs");
                    tv_achivementdate.setText("Earn the Closed three IBAs achievement by closing three IBAs");
                    b=false;
                }
            }
            else if(i==8) {
                if(obj.getString("Closed_three_life").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.closed_three_life);
                    tv_achivementname.setText("Closed Three Life");
                    tv_achivementdate.setText("Earn on,");
                    achivement_name="Closed_three_life";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.closed_three_life1);
                    tv_achivementname.setText("Closed Three Life");
                    tv_achivementdate.setText("Earn the Cosed three life achievement by closing three life apps");
                 b=false;
                }
            }
            else if(i==14) {
                if(obj.getString("Fifty_KTs").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.fifty_kts);
                    tv_achivementname.setText("Fifty KTs");
                    tv_achivementdate.setText("Earn on, ");
                    achivement_name="Fifty_KTs";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.fifty_kts1);
                    tv_achivementname.setText("Fifty KTs");
                    tv_achivementdate.setText("Earn the Fifty KTs achievement by going on 50 KTs in one year");
               b=false;
                }
            }else if(i==2) {
                if(obj.getString("First_call_from_app").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.first_call_from_app);
                    tv_achivementname.setText("First Call From App");
                    tv_achivementdate.setText("Earn on,");
                    achivement_name="First_call_from_app";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.first_call_from_app1);
                    tv_achivementname.setText("First Call From App");
                    tv_achivementdate.setText("Earn the First call from app achievement by making your first call from app");
                b=false;
                }
            }else if(i==1) {
                if(obj.getString("First_contact_added").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.first_contact_added);
                    tv_achivementname.setText("First Contact Added");
                    tv_achivementdate.setText("Earn on");
                    achivement_name="First_contact_added";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.first_contact_added1);
                    tv_achivementname.setText("First Contact Added");
                    tv_achivementdate.setText("Earn the First contact added achievement by adding your first contact");
                b=false;
                }
            }else if(i==3) {
                if(obj.getString("First_downline").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.first_downline);
                    tv_achivementname.setText("First Downline");
                    tv_achivementdate.setText("Earn on");
                    achivement_name="First_downline";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.first_downline1);
                    tv_achivementname.setText("First Downline");
                    tv_achivementdate.setText("Earn the First downline achievement by getting your first downline");
               b=false;
                }
            }else if(i==15) {
                if(obj.getString("One_hundred_KTs").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.one_hundred_kts);
                    tv_achivementname.setText("One hundred KTs");
                    tv_achivementdate.setText("Earn on,");
                    achivement_name="One_hundred_KTs";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.one_hundred_kts1);
                    tv_achivementname.setText("One hundred KTs");
                    tv_achivementdate.setText("Earn the One hundred KTs achievement by going on 100 KTs in one year");
                b=false;
                }
            }else if(i==11) {
                if(obj.getString("One_week_eight_five_three_one").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.one_week_eight_five_three_one);
                    tv_achivementname.setText("One Week 8531");
                    tv_achivementdate.setText("Earn on,");
                    achivement_name="One_week_eight_five_three_one";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.one_week_eight_five_three_one1);
                    tv_achivementname.setText("One Week 8531");
                    tv_achivementdate.setText("Earn the One week eight five three one achievement by getting an 8-5-3-1 in one a week period");
               b=false;
                }
            }else if(i==13) {
                if(obj.getString("Perfect_month").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.perfect_month);
                    tv_achivementname.setText("Perfect Month");
                    tv_achivementdate.setText("Earn on, ");
                    achivement_name="Perfect_month";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.perfect_month1);
                    tv_achivementname.setText("Perfect Month");
                    tv_achivementdate.setText("Earn the Perfect month achievement by getting your first perfect month");
               b=false;
                }
            }else if(i==12) {
                if(obj.getString("Perfect_week").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.perfect_week);
                    tv_achivementname.setText("Perfect Week");
                    tv_achivementdate.setText("Earn on, ");

                    achivement_name="Perfect_week";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.perfect_week1);
                    tv_achivementname.setText("Perfect Week");
                    tv_achivementdate.setText("Earn the Perfect week achievement by getting your first perfect week");
                b=false;
                }
            }else if(i==4) {
                if(obj.getString("Ten_five_point_clients").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.ten_five_point_clients);
                    tv_achivementname.setText("Ten Five Points Clients");
                    tv_achivementdate.setText("Earn on");
                    achivement_name="Ten_five_point_clients";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.ten_five_point_clients1);
                    tv_achivementname.setText("Ten Five Points Clients");
                    tv_achivementdate.setText("Earn the Ten five point clients achievement by rating ten 4/5 point clients");
                b=false;
                }
            }else if(i==5) {
                if(obj.getString("Ten_five_point_recruits").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.ten_five_point_recruits);
                    tv_achivementname.setText("Ten Five Points Recruits");
                    tv_achivementdate.setText("Earn on,");
                    achivement_name="Ten_five_point_recruits";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.ten_five_point_recruits1);
                    tv_achivementname.setText("Ten Five Point Recruits");
                    tv_achivementdate.setText("Earn the Ten five point recruits achievement by rating ten 4/5 point recruits");
               b=false;
                }
            }else if(i==17) {
                if(obj.getString("Ten_new_contacts_added").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.ten_new_contacts_added);
                    tv_achivementname.setText("Ten new contacts added");
                    tv_achivementdate.setText("Earn on, ");
                    achivement_name="Ten_new_contacts_added";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.ten_new_contacts_added1);
                    tv_achivementname.setText("Ten new contacts added");
                    tv_achivementdate.setText("Earn the Ten new contacts added achievement by adding 10 new contacts within one week");
               b=false;
                }
            }else if(i==19) {
                if(obj.getString("Thirty_new_contacts_added").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.thirty_new_contacts_added);
                    tv_achivementname.setText("Thirty new contacts added");
                    tv_achivementdate.setText("Earn on, ");
                    achivement_name="Thirty_new_contacts_added";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.thirty_new_contacts_added1);
                    tv_achivementname.setText("Thirty new contacts added");
                    tv_achivementdate.setText("Earn the Thirty new contacts added achievement by");
               b=false;
                }
            }else if(i==6) {
                if(obj.getString("Three_appointments_set").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.three_appointments_set);
                    tv_achivementname.setText("3 Appointment Set");
                    tv_achivementdate.setText("Earn on,");

                    achivement_name="Three_appointments_set";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.three_appointments_set1);
                    tv_achivementname.setText("3 Appointment Set");
                    tv_achivementdate.setText("Earn the Three appointments set achievement by setting three appointments");
               b=false;
                }
            }else if(i==18) {
                if(obj.getString("Twenty_new_contacts_added").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.twenty_new_contacts_added);
                    tv_achivementname.setText("Twenty New Contacts Added");
                    tv_achivementdate.setText("Earn on, ");

                    achivement_name="Twenty_new_contacts_added";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.twenty_new_contacts_added1);
                    tv_achivementname.setText("Twenty New Contacts Added");
                    tv_achivementdate.setText("Earn the Twenty new contacts added achievement by adding 20 new contacts within one week");
               b=false;
                }
            }else if(i==16) {
                if(obj.getString("Two_hundred_KTs").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.two_hundred_kts);
                    tv_achivementname.setText("Two hundred KTs");
                    tv_achivementdate.setText("Earn on,");

                    achivement_name="Two_hundred_KTs";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.two_hundred_kts1);
                    tv_achivementname.setText("Two hundred KTs");
                    tv_achivementdate.setText("Earn the Two hundred KTs achievement by going on 200 KTs in one year");
               b=false;
                }
            }else if(i==10) {
                if(obj.getString("Two_week_eight_five_three_one").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.two_week_eight_five_three_one);
                    tv_achivementname.setText("Two Week 8531");
                    tv_achivementdate.setText("Earn on,");

                    achivement_name="Two_week_eight_five_three_one";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.two_week_eight_five_three_one1);
                    tv_achivementname.setText("Two Week 8531");
                    tv_achivementdate.setText("Earn the Two week eight five three one achievement by getting an 8-5-3-1 in a two week period");
                b=false;
                }
            }else if(i==7) {
                if(obj.getString("Went_on_three_KTs").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.went_on_three_kts);
                    tv_achivementname.setText("Went on three KTs");
                    tv_achivementdate.setText("Earn on,");

                    achivement_name="Went_on_three_KTs";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.went_on_three_kts1);
                    tv_achivementname.setText("Went on three KTs");
                    tv_achivementdate.setText("Earn the first Went on three KTs achievement by going on three KTs");
                b=false;
                }
            }else if(i==0) {
                if(obj.getString("Top_speed").equalsIgnoreCase("true")){
                    iv.setImageResource(R.drawable.top_speed);
                    tv_achivementname.setText("Top Speed");
                    long timestamp = Long.parseLong(obj.getString("Top_speed_date")) * 1000L;
                    tv_achivementdate.setText("Earned on, "+getDate(timestamp ));
                    achivement_name="Top_speed";
                    b=true;
                }
                else {
                    iv.setImageResource(R.drawable.top_speed1);
                    tv_achivementname.setText("Top Speed");
                    tv_achivementdate.setText("Earn the First Top Speed from app achievement");
                    b=false;
                }
            }
            if(b)
            setHasOptionsMenu(true);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if(b) {
            menu.findItem(R.id.menu).setIcon(null);
            menu.findItem(R.id.menu).setTitle("Show");
        }
        else
            menu.findItem(R.id.menu).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId())
          {
              case R.id.menu:
                  if(b)
                  showDialog();
               break;
              case android.R.id.home:
                  getActivity().getSupportFragmentManager().popBackStack();
                  break;
          }
        return super.onOptionsItemSelected(item);

    }

    private void showDialog() {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rvp_dialog);
        Window window = dialog.getWindow();
        TextView tv_send=(TextView)dialog.findViewById(R.id.tv_save);
        TextView tv_cancel=(TextView)dialog.findViewById(R.id.cancel);
        TextView tv_title=(TextView)dialog.findViewById(R.id.tv_title);
        TextView tv_text=(TextView)dialog.findViewById(R.id.tv_text);
        tv_title.setText("Show Achievement On Profile?");
        tv_text.setVisibility(View.GONE);
        tv_cancel.setText("No");
        tv_send.setText("Yes");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateachivement();
                dialog.dismiss();
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void updateachivement(){

        Map newUserData = new HashMap();
//                    newUserData.put("solution_number","qwe");
        newUserData.put("achievementToShow", achivement_name);
        mref.child("users").child(pref.getString("uid","")).updateChildren(newUserData);

        Log.e("Achivement success","Achivement success ");

    }







    private String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
