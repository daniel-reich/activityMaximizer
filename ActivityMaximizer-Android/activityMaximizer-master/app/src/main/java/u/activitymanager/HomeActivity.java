package u.activitymanager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Fragments.ActivityFragments;
import Fragments.ContactFragment;
import Fragments.DownlineFragment;
import Fragments.FloatingMenusDialog;
import Fragments.HomeFragment;
import Fragments.Team_fragment;
import Fragments.Trainee_frag;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class HomeActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {

    FrameLayout frame;
    Toolbar toolbar;
    TextView menu;
    BottomNavigation navigation_bar;
    TextView titleTv;
    Dialog helpdialog;
    HashMap<String, String> partner_request;
    StorageReference storageRef;
    ArrayList<JSONObject> data;
    Firebase mref;
    SharedPreferences pref;
    //private Object FirebaseError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleTv = (TextView) findViewById(R.id.headertextid);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frame = (FrameLayout) findViewById(R.id.frame_layout);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
        }

        navigation_bar = (BottomNavigation) findViewById(R.id.BottomNavigation);
        navigation_bar.setOnMenuItemClickListener(this);
        Firebase.setAndroidContext(HomeActivity.this);
        storageRef = FirebaseStorage.getInstance().getReference();
        mref = new Firebase("https://activitymaximizer.firebaseio.com/");
        pref = getSharedPreferences("userpref", 0);
       /*GetPartnershipRequest*/
        getPartnershipRequestsfromFirebase();
    }

    @Override
    public void setTitle(int titleId) {
        titleTv.setText(titleId);
    }

    @Override
    public void setTitle(CharSequence title) {
        titleTv.setText(title);
    }

    private void getPartnershipRequestsfromFirebase() {

        Log.e("solution number", pref.getString("solution_number", ""));

        mref.child("Solution Numbers").child(pref.getString("solution_number", "")).child("PartnerShip Request").addValueEventListener(new ValueEventListener() {

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

            private void showPartnershipRequestDialog(final HashMap<String, String> request)

            {
                Log.e("entery", "enter2");
                final Dialog PartnershipDialog = new Dialog(HomeActivity.this);
                PartnershipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                PartnershipDialog.setContentView(R.layout.partnershiprequestdialog);
                Window window = PartnershipDialog.getWindow();
                TextView tv_yes = (TextView) PartnershipDialog.findViewById(R.id.tv_send);
                TextView tv_no = (TextView) PartnershipDialog.findViewById(R.id.cancel);
                TextView tv_request = (TextView) PartnershipDialog.findViewById(R.id.tv_request);
                EditText et_sol = (EditText) PartnershipDialog.findViewById(R.id.et_partner_solution_number);
                et_sol.setVisibility(View.GONE);
                tv_request.setText(request.get("name") + " has request a partnership with you, do you accept?");
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
                        Log.e("enter", "enter1");
                        PartnershipDialog.dismiss();
 /* Update PartnerSolutionNumber*/
                        Map sendRequest = new HashMap();
                        sendRequest.put("partner_solution_number", request.get("solutionNumber"));
                        mref.child("users").child(pref.getString("uid", "")).updateChildren(sendRequest);
                        Log.e("receiver", pref.getString("uid", "") + "," + request.get("solutionNumber"));

                        Map sendRequest1 = new HashMap();
                        sendRequest1.put("partner_solution_number", pref.getString("solution_number", ""));
                        mref.child("users").child(request.get("uid")).updateChildren(sendRequest1);
                        Log.e("sender", pref.getString("solution_number", "") + "," + request.get("uid"));

                        /*Remove Partnership Request*/
                        mref.child("Solution Numbers").child(pref.getString("solution_number", "")).child("PartnerShip Request").removeValue();


                    }
                });
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                PartnershipDialog.show();

            }


            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error", error.getMessage() + " data");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                if (fragment instanceof HomeFragment || fragment instanceof Team_fragment || fragment instanceof Trainee_frag ||
                        fragment instanceof DownlineFragment) {
                    showHelpDialog();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        helpdialog = new Dialog(HomeActivity.this);
        helpdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpdialog.setContentView(R.layout.help_dialog);
        Window window = helpdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_cancel = (TextView) helpdialog.findViewById(R.id.tv_cancel);
        TextView tv_report = (TextView) helpdialog.findViewById(R.id.tv_report);
        TextView tv_tutorial = (TextView) helpdialog.findViewById(R.id.tv_tutorial);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpdialog.dismiss();
            }
        });

        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URI = "mailto:activitymaximizerapp@gmail.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse(URI);
                intent.setData(data);
                startActivity(intent);

            }
        });

        helpdialog.show();
    }

    private void showFloatingMenus() {
        Fragments.FloatingMenusDialog dialog = new FloatingMenusDialog();
        dialog.show(getSupportFragmentManager().beginTransaction(), "");
        dialog.setCancelable(true);
    }

    @Override
    public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (i) {
            case R.id.home:
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.contact:
                fragment = new ContactFragment();
                break;
            case R.id.downline:
                fragment = new DownlineFragment();
                break;
            case R.id.activity:
                fragment = new ActivityFragments();
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment newFragment = null;
        switch (i) {
            case R.id.home:
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_layout);
                fragmentManager.beginTransaction()
                        .remove(currentFragment)
                        .add(R.id.frame_layout, new HomeFragment())
                        .commitNow();
                break;
            case R.id.contact:
                newFragment = new ContactFragment();
                break;
            case R.id.downline:
                newFragment = new DownlineFragment();
                break;
            case R.id.activity:
                newFragment = new ActivityFragments();
                break;
        }

        if (newFragment != null) {
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, newFragment)
                    .addToBackStack(null)
                    .commit();
            fragmentManager.executePendingTransactions();
        }
    }
}
