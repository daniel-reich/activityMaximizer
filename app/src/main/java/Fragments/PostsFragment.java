package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import Adapter.PostsAdapter;
import u.activitymanager.R;

/**
 * Created by Surbhi on 16-02-2017.
 */
public class PostsFragment extends Fragment
{
    View view;
    LinearLayoutManager linearLayoutManager;
    PostsAdapter adapter;
    RecyclerView posts;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
         view=inflater.inflate(R.layout.posts,container,false);
         setHasOptionsMenu(true);
         posts=(RecyclerView)view.findViewById(R.id.rview);
         linearLayoutManager=new LinearLayoutManager(getActivity());
         posts.setLayoutManager(linearLayoutManager);
         adapter=new PostsAdapter(getActivity());
         posts.setAdapter(adapter);
         return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
           // case R.id.
        }
        return super.onOptionsItemSelected(item);

    }
}
