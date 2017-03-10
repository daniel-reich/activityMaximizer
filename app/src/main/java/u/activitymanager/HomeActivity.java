package u.activitymanager;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Fragments.ActivityFragments;
import Fragments.ContactFragment;
import Fragments.DownlineFragment;
import Fragments.FloatingMenusDialog;
import Fragments.HomeFragment;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class HomeActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {

    FrameLayout frame;
    Toolbar toolbar;
    TextView menu;
    BottomNavigation navigation_bar;
    public static   TextView title,tv_back;
    Dialog helpdialog;
    HashMap<String,String> partner_request;
    StorageReference storageRef;
    ArrayList<JSONObject> data;
    Firebase mref;
    SharedPreferences pref;
    //private Object FirebaseError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=(TextView)findViewById(R.id.headertextid);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frame=(FrameLayout)findViewById(R.id.frame_layout);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).addToBackStack(null).commit();
        navigation_bar=(BottomNavigation)findViewById(R.id.BottomNavigation);
        navigation_bar.setOnMenuItemClickListener(this);
        Firebase.setAndroidContext(HomeActivity.this);
        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        pref=getSharedPreferences("userpref",0);
       /*GetPartnershipRequest*/
        getPartnershipRequestsfromFirebase();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getPartnershipRequestsfromFirebase() {

       Log.e("solution number",pref.getString("solution_number",""));

        mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("PartnerShip Request").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Partnership Request", dataSnapshot.getValue() + " data");
                if (dataSnapshot.getValue() != null) {
                    partner_request = new HashMap<String, String>();

                    //  if( dataSnapshot.hasChild(""))
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        partner_request.put(child.getKey().toString(), child.getValue().toString());


                    }
                 /*ShowPartnership Request Dialog*/
                    showPartnershipRequestDialog(partner_request);
                }
            }

            private void showPartnershipRequestDialog(final HashMap<String,String> request)

            {
                Log.e("entery","enter2");
                final Dialog PartnershipDialog=new Dialog(HomeActivity.this);
                PartnershipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                PartnershipDialog.setContentView(R.layout.partnershiprequestdialog);
                Window window = PartnershipDialog.getWindow();
                TextView tv_yes=(TextView)PartnershipDialog.findViewById(R.id.tv_send);
                TextView tv_no=(TextView)PartnershipDialog.findViewById(R.id.cancel);
                TextView tv_request=(TextView)PartnershipDialog.findViewById(R.id.tv_request);
                EditText et_sol= (EditText) PartnershipDialog.findViewById(R.id.et_partner_solution_number);
                et_sol.setVisibility(View.GONE);
                tv_request.setText(request.get("name")+" has request a partnership with you, do you accept?");
                tv_yes.setText("YES");
                tv_no.setText("NO");
                PartnershipDialog.setCanceledOnTouchOutside(false);
                tv_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PartnershipDialog.dismiss();

                    }
                });
                tv_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("enter","enter1");
                        PartnershipDialog.dismiss();
 /* Update PartnerSolutionNumber*/
                        Map sendRequest = new HashMap();
                        sendRequest.put("partner_solution_number",request.get("solutionNumber"));
                        mref.child("users").child(pref.getString("uid","")).updateChildren(sendRequest);
                        Log.e("receiver",pref.getString("uid","")+","+request.get("solutionNumber"));

                        Map sendRequest1=new HashMap();
                        sendRequest1.put("partner_solution_number",pref.getString("solution_number",""));
                        mref.child("users").child(request.get("uid")).updateChildren(sendRequest1);
                        Log.e("sender",pref.getString("solution_number","")+","+request.get("uid"));

                        /*Remove Partnership Request*/
                         mref.child("Solution Numbers").child(pref.getString("solution_number","")).child("PartnerShip Request").removeValue();


                    }
                });
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                PartnershipDialog.show();

            }


            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void showFloatingMenus() {

       Fragments.FloatingMenusDialog dialog=new FloatingMenusDialog();
        dialog.show(getSupportFragmentManager().beginTransaction(), "");
        dialog.setCancelable(true);
    }

    @Override
    public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {
        switch (i)
        {

            case R.id.home:
                //Log.e("eft","eft");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ContactFragment()).addToBackStack(null).commit();
                break;
            case R.id.downline:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new DownlineFragment()).addToBackStack(null).commit();
                break;
            case R.id.activity:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ActivityFragments()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

    }
}
