package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Need_to_Quality extends Fragment {

    View v;
    TextView tv_name,tv_phone;
    ImageView tv_ratinginfo,tv_activitylist,tv_contactnotes;
    FrameLayout frameLayout;
 public static    String competitive,created,credible,familyName,givenName,hasKids,homeowner,hungry,incomeOver40k,married,motivated,ofProperAge,peopleSkills,phoneNumber,ref,rating,recruitRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.need_to_quality,container,false);
        HomeActivity.title.setText("");
        tv_ratinginfo=(ImageView)v.findViewById(R.id.tv_ratinginfo);
        tv_activitylist=(ImageView)v.findViewById(R.id.tv_activitylist);
        tv_contactnotes=(ImageView)v.findViewById(R.id.tv_contactnotes);
        frameLayout=(FrameLayout)v.findViewById(R.id.frame_layout1);

        tv_name=(TextView)v.findViewById(R.id.tv_username);
        tv_phone=(TextView)v.findViewById(R.id.tv_phone);

        competitive = getArguments().getString("competitive");
        created = getArguments().getString("created");
        credible = getArguments().getString("credible");
        familyName = getArguments().getString("familyName");
        givenName = getArguments().getString("givenName");
        hasKids = getArguments().getString("hasKids");
        homeowner = getArguments().getString("homeowner");
        hungry = getArguments().getString("hungry");
        incomeOver40k = getArguments().getString("incomeOver40k");
        married = getArguments().getString("married");
        motivated = getArguments().getString("motivated");
        ofProperAge = getArguments().getString("ofProperAge");
        peopleSkills = getArguments().getString("peopleSkills");
        phoneNumber = getArguments().getString("phoneNumber");
        ref = getArguments().getString("ref");
        rating = getArguments().getString("rating");
        recruitRating = getArguments().getString("recruitRating");

        tv_name.setText(givenName);
        tv_phone.setText(phoneNumber);

        Log.e("givenName",givenName+" abc");

        Rating_info_fram basic_frag = new Rating_info_fram();
        Bundle args = new Bundle();
        args.putString("givenName", givenName);
        args.putString("phoneNumber", phoneNumber);
        args.putString("competitive", competitive);
        args.putString("created", created);

        args.putString("credible", credible);
        args.putString("familyName", familyName);
        args.putString("hasKids", hasKids);
        args.putString("homeowner", homeowner);

        args.putString("hungry", hungry);
        args.putString("incomeOver40k", incomeOver40k);
        args.putString("married", married);
        args.putString("motivated", motivated);

        args.putString("ofProperAge", ofProperAge);
        args.putString("peopleSkills", peopleSkills);
        args.putString("rating", rating);
        args.putString("recruitRating", recruitRating);
        args.putString("ref", ref);
        basic_frag.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout1,basic_frag).addToBackStack(null).commit();

        tv_ratinginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rating_info_fram basic_frag = new Rating_info_fram();
                Bundle args = new Bundle();
                args.putString("givenName", givenName);
                args.putString("phoneNumber", phoneNumber);
                args.putString("competitive", competitive);
                args.putString("created", created);

                args.putString("credible", credible);
                args.putString("familyName", familyName);
                args.putString("hasKids", hasKids);
                args.putString("homeowner", homeowner);

                args.putString("hungry", hungry);
                args.putString("incomeOver40k", incomeOver40k);
                args.putString("married", married);
                args.putString("motivated", motivated);

                args.putString("ofProperAge", ofProperAge);
                args.putString("peopleSkills", peopleSkills);
                args.putString("rating", rating);
                args.putString("recruitRating", recruitRating);
                args.putString("ref", ref);
                basic_frag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout1,basic_frag).addToBackStack(null).commit();
            }
        });

        tv_activitylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout1,new Activity_list_frag()).addToBackStack(null).commit();
            }
        });

        tv_contactnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contact_notes_frag basic_frag = new Contact_notes_frag();
                Bundle args = new Bundle();
                args.putString("givenName", givenName);
                basic_frag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout1,basic_frag).addToBackStack(null).commit();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
