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
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Update;

/**
 * Created by Surbhi on 15-02-2017.
 */

public class Register extends AppCompatActivity
{
    Firebase mref;
    Toolbar toolbar;
    TextView Next;
    String st_name,st_email,st_lname,st_pass,st_conf_pass,st_phone;
    EditText et_name,et_lname,et_email,et_password,et_conf_pass,et_phone;
    FirebaseAuth firebaseAuth;
    FirebaseUser fuser;

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer.firebaseio.com/");

        pref=getSharedPreferences("userpref",0);

//        Update userinfo = new Update("surender", "kumar","9999999999","surender@gamil.com",pref.getString("uid",""));
//        Log.e("userid",pref.getString("uid",""));
//        mref.child("users").child(pref.getString("uid","")).push().setValue(userinfo);

        Next=(TextView)findViewById(R.id.tv_register);
        et_name=(EditText)findViewById(R.id.et_fname);
        et_lname=(EditText)findViewById(R.id.et_lname);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_password=(EditText)findViewById(R.id.et_password);
        et_conf_pass=(EditText)findViewById(R.id.et_confirm_password);

        Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                st_email=et_email.getText().toString();
                st_name=et_name.getText().toString();
                st_lname=et_lname.getText().toString();
                st_phone=et_phone.getText().toString();
                st_pass=et_password.getText().toString();
                st_conf_pass=et_conf_pass.getText().toString();

                if(!utils.isValidEmail(st_email)){
                    Toast.makeText(Register.this,"Please Enter Valid Emil Address",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_pass.length()<8){
                    Toast.makeText(Register.this,"you need to enter at least 8 characters in password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_pass.length()>15){
                    Toast.makeText(Register.this,"maximum 15 characters in password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_name.length()>15|st_name.length()<4){
                    Toast.makeText(Register.this,"name length should be between 4 and 15 charaters ",Toast.LENGTH_LONG).show();
                    return;
                }
                if (st_phone.length()!=10){
                    Toast.makeText(Register.this,"Please enter valid phone no.",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!st_pass.equals(st_conf_pass)){
                    Toast.makeText(Register.this,"Confirm password does't match",Toast.LENGTH_LONG).show();
                    return;
                }

                register();

//                Intent i=new Intent(Register.this,RegisterationDetail.class);
//                startActivity(i);
//                finish();
            }
        });
    }


    public void register(){

        firebaseAuth.createUserWithEmailAndPassword(st_email, st_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            edit=pref.edit();
                            edit.putString("uid",task.getResult().getUser().getUid().toString());
                            edit.commit();
                            Toast.makeText(Register.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            writeNewUser(task.getResult().getUser());
                        }else{
                            //display some message here
                            Toast.makeText(Register.this,"Registration Error", Toast.LENGTH_LONG).show();
                        }
                        //     progressDialog.dismiss();
                    }
                });
    }

    private void writeNewUser(FirebaseUser user) {

        String uid = user.getUid();
        Log.e("writeuserid",uid);
        Update userinfo = new Update(st_name, st_lname,st_phone,st_email,uid);

        mref.child("users")
                .child(uid)
                .setValue(userinfo, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError != null) {
                            Log.e("Data could not be" ,firebaseError.getMessage().toString());
                        } else {
                            Log.e("Data saved successfull",firebaseError.getMessage().toString());
                        }
                    }
                });

        mref.child("users").child(uid).setValue(userinfo);

//        Intent i=new Intent(Register.this,HomeActivity.class);
//        startActivity(i);
//        finish();

    }

}
