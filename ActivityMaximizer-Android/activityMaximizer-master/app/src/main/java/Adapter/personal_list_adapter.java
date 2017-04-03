package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedHashMap;

import u.activitymanager.R;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class personal_list_adapter extends RecyclerView.Adapter<personal_list_adapter.ViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    LinkedHashMap<String, Integer> mMap;

    public personal_list_adapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public personal_list_adapter(Context context, int[] count) {
        this(context);
    }

    public void setData(LinkedHashMap<String, Integer> data) {
        mMap = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.personal_list_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(personal_list_adapter.ViewHolder viewHolder, int position) {
        String key = (String) (mMap.keySet().toArray())[position];
        viewHolder.reward.setText(key);
        viewHolder.current_leader.setText(String.valueOf(mMap.get(key)));
    }

    @Override
    public int getItemCount() {
        Log.e("count", "count");
        return mMap != null ? mMap.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reward;
        private TextView current_leader;

        public ViewHolder(View view) {
            super(view);

            reward = (TextView) view.findViewById(R.id.name);
            current_leader = (TextView) view.findViewById(R.id.value);
        }
    }
}
