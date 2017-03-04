package Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import Fragments.Need_to_Quality;
import model.AllContact;
import model.AllNote;
import u.activitymanager.R;

/**
 * Created by Rohan on 3/3/2017.
 */
public class Note_Adapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    ArrayList<AllNote> data;
    public Note_Adapter(Context c, ArrayList<AllNote> data) {
        this.c = c;
        this.data=data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        TextView username,tv_date;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=(LinearLayout)itemView.findViewById(R.id.layout);
            username=(TextView)itemView.findViewById(R.id.tv_username);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;
        holder1.username.setText(data.get(position).getContent());
        holder1.tv_date.setText(getDate(Long.parseLong(data.get(position).getCreated())));
        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return data.size();
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd/MM/yyyy hh:mm a", cal).toString();
        return date;
    }
}
