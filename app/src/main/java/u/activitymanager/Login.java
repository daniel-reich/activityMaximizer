package u.activitymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView Login;
    EditText et_email,et_password;
    String st_email,st_pass;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();

       // mref=new Firebase("https://testingdatabase-62bfb.firebaseio.com/");

        pref=getSharedPreferences("userpref",0);

        Login=(TextView)findViewById(R.id.tv_login);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);

        Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_login:
                validation();
//                Intent i=new Intent(Login.this,HomeActivity.class);
//                startActivity(i);
//                finish();
                break;
        }
    }

    public void validation(){
        st_email=et_email.getText().toString();
        st_pass=et_password.getText().toString();

        boolean b=utils.isValidEmail(st_email);
        if(!b){
            Toast.makeText(Login.this,"Please Enter Valid Email Address",Toast.LENGTH_LONG).show();
            return;
        }
        if(st_pass.length()<8){
            Toast.makeText(Login.this,"Password Does't Match",Toast.LENGTH_LONG).show();
            return;
        }

        signIn();
    }

    private void signIn() {

        //showProgressDialog();
        firebaseAuth.signInWithEmailAndPassword(st_email, st_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("login", "signIn:onComplete:" + task.isSuccessful());
                        // hideProgressDialog();
                        if (task.isSuccessful()) {
                            user = task.getResult().getUser();

                            edit=pref.edit();
                            edit.putString("uid",user.getUid().toString());
                            edit.commit();
                            Toast.makeText(Login.this, "Sign In Success",
                                    Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(Login.this,HomeActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(Login.this, "Enter Valid Email or Password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
