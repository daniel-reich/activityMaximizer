package u.activitymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import register_frag.Splash;

/**
 * Created by surender on 2/24/2017.
 */
public class SplashActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    Toolbar toolbar;
    public static TextView title;
    SharedPreferences pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);

        pref=getSharedPreferences("userpref",0);

        if(pref.getBoolean("signup",false)) {
            Intent in = new Intent(SplashActivity.this,HomeActivity.class);
            startActivity(in);
            finish();
            return;
        }


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=(TextView)findViewById(R.id.headertextid);
        title.setText("Registration");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.help);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().hide();

        frameLayout=(FrameLayout)findViewById(R.id.splash_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.splash_layout,new Splash()).addToBackStack(null).commit();
    }
}
