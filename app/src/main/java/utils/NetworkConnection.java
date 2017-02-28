package utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import u.activitymanager.R;

/**
 * Created by surender on 2/28/2017.
 */

public class NetworkConnection {
    Context c;

    public NetworkConnection(Context c) {
        this.c = c;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

     public void NetworkAlert() {
         final Dialog dialog=new Dialog(c);
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.alert_dialog);
         dialog.setCancelable(false);
         dialog.setCanceledOnTouchOutside(false);
         Window window = dialog.getWindow();
         WindowManager.LayoutParams wlp = window.getAttributes();
         wlp.gravity = Gravity.CENTER;
         wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
         window.setAttributes(wlp);
         window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         dialog.show();

         TextView tv_ok=(TextView)dialog.findViewById(R.id.tv_ok);

         tv_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
             }
         });
     }

}
