package Fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.siyamed.shapeimageview.DiamondImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ntt.customgaugeview.library.GaugeView;
import com.soundcloud.android.crop.Crop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.adapter;
import Adapter.personal_list_adapter;
import model.Activity_breakdown_getset;
import u.activitymanager.R;
import u.activitymanager.StringUtils;
import utils.AnimateFirstDisplayListener;

/**
 * Created by Rohan on 3/8/2017.
 */
public class TeamFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView rview;
    LinearLayoutManager linearLayoutManager;
    personal_list_adapter adapter;
    ImageView meter;
    Dialog selectViewdialog,selectPicdialog;
    DiamondImageView Profile_pic;

    static final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 3;
    final int GALLAY_IMAGE= 2;
    private Uri picUri;
    StorageReference storageRef;
    FirebaseAuth firebaseAuth;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    TextView tv_phone,tv_username;
    private String uid,solutionnumber;

    DiamondImageView profile;
    ImageView image;
    GaugeView speedview;
    int count[]={0,0,0,0,0,0,0,0};
    HashMap<String,String> base,downline;

    JSONArray json;
    long team_members;
    long value=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.personal_frag,container,false);
        setHasOptionsMenu(true);
        Log.e("check","check");

        rview=(RecyclerView)view.findViewById(R.id.rview);
        Profile_pic=(DiamondImageView)view.findViewById(R.id.profile_pic);
        Profile_pic.setVisibility(View.GONE);
        image=(ImageView) view.findViewById(R.id.im_achievement);
        image.setVisibility(View.GONE);

        tv_username=(TextView) view.findViewById(R.id.tv_username);
        tv_phone=(TextView)view.findViewById(R.id.tv_phone);

        speedview=(GaugeView)view.findViewById(R.id.meter);



        speedview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectViewDialog();

            }
        });


       // speedview.setShowRangeValues(true);
        //speedview.setTargetValue(10);


// change MAX speed to 320
       // speedview.setMaxSpeed(100);
// change speed to 140 Km/h

        linearLayoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(linearLayoutManager);

        Profile_pic.setOnClickListener(this);
        speedview.setOnClickListener(this);


        pref=getActivity().getSharedPreferences("userpref",0);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase("https://activitymaximizer.firebaseio.com/");
        uid=pref.getString("uid","");
        solutionnumber=pref.getString("solution_number","");

        storageRef= FirebaseStorage.getInstance().getReference();




        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.userprofile)
                .showImageOnFail(R.drawable.userprofile)
                .showImageForEmptyUri(R.drawable.userprofile)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();

        Log.e("profilepic",pref.getString("profilePictureURL","null image path"));

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.getInstance().displayImage(pref.getString("profilePictureURL",""), Profile_pic, options, animateFirstListener);

        tv_username.setText(pref.getString("givenName","")+" "+pref.getString("familyName",""));
        tv_phone.setText(pref.getString("phoneNumber",""));

        String json_data=pref.getString("team","");

        try {
            if(json_data.length()<=0)
                json=new JSONArray();
            else
            json=new JSONArray(json_data);



            if(find_json_state(uid))
            getnotefromfirebase(uid);
            getallteambasefromfirebase(solutionnumber);
            getallteamdownlinefromfirebase(solutionnumber);


        } catch (JSONException e) {


            Log.e("aaa","aaa",e);
            e.printStackTrace();
        }

        return view;
    }


    public boolean find_json(String uid)
    {


      for(int i=0;i<json.length();i++)
      {
          try {
              JSONObject json_obj= (JSONObject) json.get(i);

              if(json_obj.getString("uid").equals(uid))
                  return true;


          } catch (JSONException e) {
              e.printStackTrace();
          }


      }


      return false;


    }



    public boolean find_json_state(String uid)
    {



        if(!find_json(uid))

            return true;



        for(int i=0;i<json.length();i++)
        {
            try {
                JSONObject json_obj= (JSONObject) json.get(i);

                if(json_obj.getString("uid").equals(uid) && json_obj.getBoolean("selected"))
                    return true;


            } catch (JSONException e) {
                e.printStackTrace();
            }






        }











        return false;


    }





    public int find_json_position(String uid) {


        for (int i = 0; i < json.length(); i++) {
            try {
                JSONObject json_obj = (JSONObject) json.get(i);

                if (json_obj.getString("uid").equals(uid))
                    return i;


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return -1;
    }




    public long find_count() {

        long count=0;
        for (int i = 0; i < json.length(); i++) {
            try {
                JSONObject json_obj = (JSONObject) json.get(i);

                if (json_obj.getBoolean("selected"))

                    count++;



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return count;
    }


        @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.meter:
                selectViewDialog();
                break;
            case R.id.tv_breakdown:
                selectViewdialog.dismiss();
                showDialog();
                break;
            case R.id.tv_graph:

                selectViewdialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new ChartFragment()).addToBackStack(null).commit();

                break;
            case R.id.profile_pic:
                selectPicDialog();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                //getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void selectViewDialog() {
        selectViewdialog=new Dialog(getActivity());
        selectViewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectViewdialog.setContentView(R.layout.graphviewdialog1);
        Window window = selectViewdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView Activity_value_breakdown=(TextView)selectViewdialog.findViewById(R.id.tv_breakdown);
        TextView Graph=(TextView)selectViewdialog.findViewById(R.id.tv_graph);

        TextView filter=(TextView)selectViewdialog.findViewById(R.id.tv_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("json",json.toString());
                selectViewdialog.dismiss();


                edit=pref.edit();
                edit.putString("team",json.toString());
                edit.commit();


                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_layout,new Team_Points_Selection_Frag()).addToBackStack(null).commit();





            }
        });
        Activity_value_breakdown.setOnClickListener(this);
        Graph.setOnClickListener(this);
        selectViewdialog.show();
    }

    private void showDialog()
    {
        ArrayList<Activity_breakdown_getset> data=new ArrayList<>();
        Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points);
        RecyclerView rview=(RecyclerView)dialog.findViewById(R.id.recyclerview);
        LinearLayoutManager lManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(lManager);
        rview.setNestedScrollingEnabled(false);
        Adapter.adapter adap=new adapter(getActivity(),data);
        rview.setAdapter(adap);
        dialog.show();
    }

    //  Select Image  statrt
    private void selectPicDialog() {
        selectPicdialog=new Dialog(getActivity());
        selectPicdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPicdialog.setContentView(R.layout.pick_image_dialog);
        Window window = selectPicdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_camera=(TextView)selectPicdialog.findViewById(R.id.tv_camera);
        TextView tv_gallary=(TextView)selectPicdialog.findViewById(R.id.tv_gallery);
        TextView tv_cancel=(TextView)selectPicdialog.findViewById(R.id.tv_cancel);

        tv_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Crop.pickImage(getActivity());
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLAY_IMAGE);
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                    File imageFile = new File(imageFilePath);
                    picUri = Uri.fromFile(imageFile); // convert path to Uri
                    takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  picUri );
                    startActivityForResult(takePictureIntent, CAMERA_CAPTURE);

                } catch(ActivityNotFoundException anfe){
                    //display an error message
                    String errorMessage = "Whoops - your device doesn't support capturing images!";
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        selectPicdialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == GALLAY_IMAGE && resultCode == getActivity().RESULT_OK) {
//            beginCrop(result.getData());
            Uri uri = result.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                Profile_pic.setImageBitmap(bitmap);

                StorageReference filepath= storageRef.child("Images").child(uri.getLastPathSegment());

                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("onSuccess iamge",taskSnapshot.getDownloadUrl()+" uri");

                        String usr= String.valueOf(taskSnapshot.getDownloadUrl());
                        updateimageurl(usr);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("onFailure iamge","e",e);
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(resultCode == getActivity().RESULT_OK&&requestCode == CAMERA_CAPTURE){
            //get the Uri for the captured image
            Uri uri = picUri;
            //carry out the crop operation
            performCrop();
            Log.d("picUri", uri.toString());
        }
        else if(resultCode == getActivity().RESULT_OK&&requestCode == PIC_CROP){
            //get the returned data
            Bundle extras = result.getExtras();
            //get the cropped bitmap
            Bitmap thePic = (Bitmap) extras.get("data");
            //display the returned cropped image
            Profile_pic.setImageBitmap(thePic);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thePic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            StorageReference mountainsRef= storageRef.child("Images").child(pref.getString("uid",""));

            UploadTask uploadTask = mountainsRef.putBytes(byteArray);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.e("Image load fail","Image load Fail");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.e("Image load success","Image load success");
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//
                    Log.e("Image load success","Image load success "+downloadUrl);
                    String usr= String.valueOf(downloadUrl);
                    updateimageurl(usr);
                }
            });

        }
    }

    public void updateimageurl(String url){

        edit=pref.edit();
        edit.putString("profilePictureURL",url+"");
        edit.commit();

        Log.e("Image load success","Image load success "+url);

        Map newUserData = new HashMap();
//                    newUserData.put("solution_number","qwe");
        newUserData.put("profilePictureURL", url);
        mref.child("users").child(pref.getString("uid","")).updateChildren(newUserData);

    }

    private void performCrop(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity());
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == getActivity().RESULT_OK) {
            Log.e("uri",Crop.getOutput(result)+"");
            Profile_pic.setImageURI(Crop.getOutput(result));

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() ,Crop.getOutput(result) );
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                scaled.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

//                st_base64 = Base64.encodeToString(b, Base64.DEFAULT);
//
//                //st_base64=st_base64.replaceAll(" ","").replaceAll("\n","");
//
//                Log.e("base64",st_base64);

//                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver() ,Crop.getOutput(result) );
//
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                byte[] byteArray = byteArrayOutputStream .toByteArray();
//                st_base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                //st_base64=st_base64.replaceAll(" ","").replaceAll("\n","");

//                Log.e("base642",st_base64);
//                Upload_Base64();

            } catch (Exception e) {
                Log.e("e","e",e);
                e.printStackTrace();
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // select image end

    public void getnotefromfirebase(final String uid)
    {
        mref.child("events")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server",dataSnapshot.getValue()+" data");

                        for (com.firebase.client.DataSnapshot child : dataSnapshot.getChildren()) {
                            try {


                                if (!find_json(uid))

                                {

                                    JSONObject js = new JSONObject();
                                    js.put("uid", uid);
                                    js.put("name", child.child("userName").getValue());
                                    js.put("count", 0);
                                    js.put("selected", true);

                                    json.put(js);

                                }

                                Log.e("oo", child.child("type").getValue().toString());


                                if (find_json_state(uid))

                                {


                                    String activity_list[] = {"Set Appointment", "Went on KT", "Closed Life", "closed IBA", "Closed Other Business", "Appt Set To Closed Life",
                                            "Appt Set To Closed IBA", "Invite to Opportunity Meeting", "Went To Opportunity Meeting", "Call Back", "Dark House", "Not Interested"};


                                    switch (child.child("type").getValue().toString())

                                    {
                                        case "Invited to Opportunity Meeting":

                                            count[7]++;

                                            break;


                                        case "Went to Opportunity Meeting":

                                            count[8]++;

                                            break;

                                        case "Set Appointment":

                                            count[0]++;
                                            break;

                                        case "Went on KT":

                                            count[1]++;
                                            break;

                                        case "Closed IBA":

                                            count[3]++;
                                            break;

                                    }

                                }
                            }
                            catch (Exception e)
                            {
                                Log.e("Exception",e+"");
                            }
                        }



                        adapter=new personal_list_adapter(getActivity(),count);
                        rview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        if(find_json_state(uid))
                        getinfirebasedailipoint(uid);

                    }
                    @Override
                    public void onCancelled(FirebaseError error) {
                        Log.e("get data error",error.getMessage()+" data");
                    }
                });
    }



    public void getinfirebasedailipoint(final String uid)
    {
        mref.child("users").child(uid).child("dailyPointAverages").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get achievements1111", dataSnapshot.getValue() + " data");
                Log.e("child11111111111", dataSnapshot.getValue() + " abc");

                long val=0;

              int pos=  find_json_position(uid);


                for(com.firebase.client.DataSnapshot child:dataSnapshot.getChildren()){

//                    String key=child.getKey();

                    //  int value= Integer.parseInt(child.getValue());
                    value =value+((Long) child.getValue());

                    val=val+ (Long)child.getValue();
                    Log.e("achievementToShow",value+"");


                }


                try {
                    json.getJSONObject(pos).put("count",val);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                ;

                if(find_count()==0)

                speedview.setTargetValue(0);

                else

                    speedview.setTargetValue(StringUtils.clamp(value, 0, 100));




                // achieve_detail = String.valueOf(dataSnapshot.getValue());
                // setAch();

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }



    public void getallteambasefromfirebase(final String solutionid)
    {
        mref.child("Solution Numbers").child(solutionid).child("Base").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from server",dataSnapshot.getValue()+" data");
                base=new HashMap<String, String>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    base.put(child.getKey().toString(),child.getValue().toString());
                    Log.e("base",child.getKey().toString());
                    getnotefromfirebase(child.getKey().toString());

                }

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public void getallteamdownlinefromfirebase(final String solutionid)
    {
        mref.child("Solution Numbers").child(solutionid).child("downlines").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("get data from serverssn",dataSnapshot.getValue()+" data");
                downline=new HashMap<String, String>();
                Log.e("child",dataSnapshot.getChildren()+"");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    downline.put(child.getKey().toString(),"");
                    Log.e("downline",child.getKey().toString());
                    getnotefromfirebase(child.getKey().toString());
                }
                Log.e("fff",downline.size()+"");

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

}

