package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class PostsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
Context c;

    JSONArray json;

    public PostsAdapter(Context c, JSONArray json) {
        this.c = c;
        this.json=json;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView name,content;


        public ViewHolder(View itemView) {

            super(itemView);

            name=(TextView)itemView.findViewById(R.id.tv_username);

            content=(TextView)itemView.findViewById(R.id.tv_desc);




        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        {
            final Context c=parent.getContext();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postsadapter, parent, false);
            return new ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1= (ViewHolder) holder;

        try {
            JSONObject json_object=json.getJSONObject(position);
            holder1.name.setText(json_object.getString("name"));
            holder1.content.setText(json_object.getString("content"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        //Log.e("size", String.valueOf(r.getDotdList().size()));
        return json.length();
    }
}
