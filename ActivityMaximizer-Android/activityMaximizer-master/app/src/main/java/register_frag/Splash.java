package register_frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import u.activitymanager.R;

/**
 * Created by Surbhi on 15-02-2017.
 */
public class Splash extends Fragment implements View.OnClickListener {
    TextView Register,Login;
    View v;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.splash,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Register=(TextView)v.findViewById(R.id.tv_register);
        Login=(TextView)v.findViewById(R.id.tv_login);
        Register.setOnClickListener(this);
        Login.setOnClickListener(this);





        return v;
    }


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash);
//        Register=(TextView)findViewById(R.id.tv_register);
//        Login=(TextView)findViewById(R.id.tv_login);
//        Register.setOnClickListener(this);
//        Login.setOnClickListener(this);
//
//    }





    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_register:
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.splash_layout,new Register()).addToBackStack(null).commit();
//                Intent reg=new Intent(Splash.this,Register.class);
//                startActivity(reg);
//                finish();
                break;
            case R.id.tv_login:
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.splash_layout,new Login()).addToBackStack(null).commit();
//                Intent login=new Intent(Splash.this,Login.class);
//                startActivity(login);
//                finish();
                break;
        }
    }
}
