package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import u.activitymanager.R;

/**
 * Created by surender on 2/17/2017.
 */

public class Rating_info_fram extends Fragment {

    View v;
    TextView tv_clients,tv_recruits;
    FrameLayout frameLayout;
    String competitive,created,credible,familyName,givenName,hasKids,homeowner,hungry,incomeOver40k,married,motivated,ofProperAge,peopleSkills,phoneNumber,ref,rating,recruitRating;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.rating_info,container,false);


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



        frameLayout=(FrameLayout)v.findViewById(R.id.fram_rating);

        tv_clients=(TextView)v.findViewById(R.id.tv_clients);
        tv_recruits=(TextView)v.findViewById(R.id.tv_recruits);

        tv_recruits.setSelected(false);
        tv_clients.setSelected(true);


        Rating_info_clients_fram basic_frag = new Rating_info_clients_fram();
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
                .replace(R.id.fram_rating,basic_frag).addToBackStack(null).commit();


        tv_clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rating_info_clients_fram basic_frag = new Rating_info_clients_fram();
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
                        .replace(R.id.fram_rating,basic_frag).addToBackStack(null).commit();
                tv_recruits.setSelected(false);
                tv_clients.setSelected(true);
            }
        });
        tv_recruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rating_info_recruits_fram basic_frag = new Rating_info_recruits_fram();
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
                        .replace(R.id.fram_rating,basic_frag).addToBackStack(null).commit();
                tv_recruits.setSelected(true);
                tv_clients.setSelected(false);
            }
        });

        return v;
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
