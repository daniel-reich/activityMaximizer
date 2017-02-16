package Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import u.activitymanager.R;


public class AddChallange extends Fragment {

    TextView title;
View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_challenge,container,false);
        title = (TextView)view.findViewById(R.id.challange_set_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_dialog();
            }
        });
        return view;
    }



    public void title_dialog(){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());

        TextView tv = new TextView(getActivity());
        tv.setText("Name");
        tv.setPadding(40, 40, 40, 40);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);

         EditText et = new EditText(getActivity());
         et.setBackgroundResource(R.drawable.et_bg);
         String etStr = et.getText().toString();

        alertdialog.setView(et);
        alertdialog.setCustomTitle(tv);


        alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alertdialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog alertDialog = alertdialog.create();

        try {
            alertDialog.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would
            // not display the 'Force Close' message
            e.printStackTrace();
        }
    }


}
