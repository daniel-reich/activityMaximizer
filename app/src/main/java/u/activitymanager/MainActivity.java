package u.activitymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import Adapter.adapter;

public class MainActivity extends AppCompatActivity {


    RecyclerView rv;
    adapter adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points);
        rv = (RecyclerView)findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setNestedScrollingEnabled(false);
        //adap=new adapter();
        //rv.setAdapter(adap);
    }
}
