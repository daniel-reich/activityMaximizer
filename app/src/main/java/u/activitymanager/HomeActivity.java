package u.activitymanager;

import android.app.Dialog;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

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

    public static   TextView title;
Dialog helpdialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=(TextView)findViewById(R.id.headertextid);
//        menu=(TextView)findViewById(R.id.menu);
//        menu.setOnClickListener(this);
        //toolbar.setNavigationIcon(null);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frame=(FrameLayout)findViewById(R.id.frame_layout);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).addToBackStack(null).commit();
        navigation_bar=(BottomNavigation)findViewById(R.id.BottomNavigation);
        navigation_bar.setOnMenuItemClickListener(this);
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
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
