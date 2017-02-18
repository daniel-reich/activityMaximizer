package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */
public class Achivement_Adap extends BaseAdapter {

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        Context c=viewGroup.getContext();
        inflater =(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.achievement_adap,null);

        return v;
    }
}
