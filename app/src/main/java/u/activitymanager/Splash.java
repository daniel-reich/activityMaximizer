package u.activitymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Splash extends AppCompatActivity implements View.OnClickListener {
    TextView Register,Login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Register=(TextView)findViewById(R.id.tv_register);
        Login=(TextView)findViewById(R.id.tv_login);
        Register.setOnClickListener(this);
        Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_register:
                Intent reg=new Intent(Splash.this,Register.class);
                startActivity(reg);
                finish();
                break;
            case R.id.tv_login:
                Intent login=new Intent(Splash.this,Login.class);
                startActivity(login);
                finish();
                break;
        }
    }
}
