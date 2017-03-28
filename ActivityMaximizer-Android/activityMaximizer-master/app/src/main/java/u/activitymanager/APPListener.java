package u.activitymanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import interfaces.ISelectNewActivity;

/**
 * Created by root on 10/3/17.
 */

public class APPListener {
    private static final APPListener ourInstance = new APPListener();

    public static APPListener getInstance() {
        return ourInstance;
    }

    private APPListener() {
    }

    ArrayList<ISelectNewActivity> iSelectNewActivityArrayList = new ArrayList<>();

    public void SetIAAPermissionListener(ISelectNewActivity iSelectNewActivity){
        if(!iSelectNewActivityArrayList.contains(iSelectNewActivity))
            iSelectNewActivityArrayList.add(iSelectNewActivity);
    }

    public void RemoveIAAPermissionRefreshListener(ISelectNewActivity iSelectNewActivity){
        if(iSelectNewActivityArrayList.contains(iSelectNewActivity))
            iSelectNewActivityArrayList.remove(iSelectNewActivity);
    }


    public void SetIAAPermissionRefreshListener(JSONObject json){
        for (ISelectNewActivity iappPermission:iSelectNewActivityArrayList)
            iappPermission.OnRefreshListener(json);
    }

}
