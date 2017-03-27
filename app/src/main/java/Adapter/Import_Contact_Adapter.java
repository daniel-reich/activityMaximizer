package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.AllContact;
import model.AllPhoneContact;
import u.activitymanager.R;
import utils.Constants;

/**
 * Created by Rohan on 3/4/2017.
 */
public class Import_Contact_Adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    Firebase mref;
    SharedPreferences pref;
    ArrayList<AllPhoneContact> data;
    String uid="",name="";
    public Import_Contact_Adapter(Context c, ArrayList<AllPhoneContact> data, String name) {
        this.c = c;
        this.data=data;
        this.name=name;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        TextView username;
        ImageView check;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            check=(ImageView)itemView.findViewById(R.id.next);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listadapter, parent, false);
            pref=c.getSharedPreferences("userpref",0);
            Firebase.setAndroidContext(c);
            mref=new Firebase("https://activitymaximizer.firebaseio.com/");
            uid=pref.getString("uid","");
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(data.get(position).getAllContactNameList());
       if(data.get(position).getStatus()==0)
           ((ViewHolder) holder).check.setVisibility(View.INVISIBLE);
        else
           ((ViewHolder) holder).check.setVisibility(View.VISIBLE);

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(position).setStatus(1);
                notifyDataSetChanged();
//                String reflink= Constants.URL+"contacts/"+uid+"/"+data.get(position).getGivenName();
//                Map newlist = new HashMap();
//                newlist.put("ref",reflink);
//                mref.child("lists").child(uid).child(name).child("contacts")
//                        .child(data.get(position).getGivenName())
//                        .setValue(newlist);
            }
        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }




}
