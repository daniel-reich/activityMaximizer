package u.activitymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Login extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView Login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Login=(TextView)findViewById(R.id.tv_login);
        Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_login:
                Intent i=new Intent(Login.this,HomeActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
