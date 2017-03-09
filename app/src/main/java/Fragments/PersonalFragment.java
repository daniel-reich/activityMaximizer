package Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.siyamed.shapeimageview.DiamondImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.soundcloud.android.crop.Crop;

import org.json.JSONArray;
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
import register_frag.RegisterationDetail;
import u.activitymanager.R;
import utils.AnimateFirstDisplayListener;

/**
 * Created by Surbhi on 14-02-2017.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener {
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
    private String uid;
    ImageView iv;
    ArrayList<Activity_breakdown_getset> table_data;
    SpeedView speedview;
    String achieve_detail="";
    int count[]={0,0,0,0,0,0,0,0};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.personal_frag,container,false);
        setHasOptionsMenu(true);
        Log.e("check","check");

        rview=(RecyclerView)view.findViewById(R.id.rview);
        Profile_pic=(DiamondImageView)view.findViewById(R.id.profile_pic);

        tv_username=(TextView) view.findViewById(R.id.tv_username);
        tv_phone=(TextView)view.findViewById(R.id.tv_phone);
        iv=(ImageView)view.findViewById(R.id.im_achievement);

        speedview=(SpeedView)view.findViewById(R.id.meter);

        speedview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectViewDialog();

            }
        });



// change MAX speed to 320
        speedview.setMaxSpeed(100);
// change speed to 140 Km/h

        linearLayoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(linearLayoutManager);

        Profile_pic.setOnClickListener(this);
        speedview.setOnClickListener(this);


        pref=getActivity().getSharedPreferences("userpref",0);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");
        uid=pref.getString("uid","");

        storageRef= FirebaseStorage.getInstance().getReference();

        getnotefromfirebase();

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
        tv_phone.setText(pref.getString("solution_number",""));

        getinfirebase();

        return view;
    }


    public void getinfirebase()
    {
        mref.child("users").child(uid).child("achievementToShow").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("get data from server", dataSnapshot.getValue() + " data");
                Log.e("child", dataSnapshot.getValue() + " abc");
                achieve_detail = ConvertParseString(dataSnapshot.getValue());
                setAch();

            }
            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("get data error",error.getMessage()+" data");
            }
        });
    }

    public static String ConvertParseString(Object obj ) {
        if(obj==null)
        {
            return "";
        }
        else {
            String lastSeen= String.valueOf(obj);
            if (lastSeen != null && !TextUtils.isEmpty(lastSeen) && !lastSeen.equalsIgnoreCase("null"))
                return lastSeen;
            else
                return "";
        }

    }

    public void setAch()
    {

        if(achieve_detail.equalsIgnoreCase("Closed_three_IBAs"))
            iv.setImageResource(R.drawable.closed_three_ibas);

        else
        if(achieve_detail.equalsIgnoreCase("Closed_three_life"))
            iv.setImageResource(R.drawable.closed_three_life);
        else
        if(achieve_detail.equalsIgnoreCase("Fifty_KTs"))
            iv.setImageResource(R.drawable.fifty_kts);
        else
        if(achieve_detail.equalsIgnoreCase("First_call_from_app"))
            iv.setImageResource(R.drawable.first_call_from_app);
        else
        if(achieve_detail.equalsIgnoreCase("First_contact_added"))
            iv.setImageResource(R.drawable.first_contact_added);

        else
        if(achieve_detail.equalsIgnoreCase("First_downline"))
            iv.setImageResource(R.drawable.first_downline);
        else
        if(achieve_detail.equalsIgnoreCase("One_hundred_KTs"))
            iv.setImageResource(R.drawable.one_hundred_kts);
        else
        if(achieve_detail.equalsIgnoreCase("One_week_eight_five_three_one"))
            iv.setImageResource(R.drawable.one_week_eight_five_three_one);
        else
        if(achieve_detail.equalsIgnoreCase("Perfect_month"))
            iv.setImageResource(R.drawable.perfect_month);
        else
        if(achieve_detail.equalsIgnoreCase("Perfect_week"))
            iv.setImageResource(R.drawable.perfect_week);
        else
        if(achieve_detail.equalsIgnoreCase("Ten_five_point_clients"))
            iv.setImageResource(R.drawable.ten_five_point_clients);
        else
        if(achieve_detail.equalsIgnoreCase("Ten_five_point_recruits"))
            iv.setImageResource(R.drawable.ten_five_point_recruits);
        else
        if(achieve_detail.equalsIgnoreCase("Ten_new_contacts_added"))
            iv.setImageResource(R.drawable.ten_new_contacts_added);
        else
        if(achieve_detail.equalsIgnoreCase("Thirty_new_contacts_added"))
            iv.setImageResource(R.drawable.thirty_new_contacts_added);
        else
        if(achieve_detail.equalsIgnoreCase("Three_appointments_set"))
            iv.setImageResource(R.drawable.three_appointments_set);
        else
        if(achieve_detail.equalsIgnoreCase("Twenty_new_contacts_added"))
            iv.setImageResource(R.drawable.twenty_new_contacts_added);
        else
        if(achieve_detail.equalsIgnoreCase("Two_hundred_KTs"))
            iv.setImageResource(R.drawable.two_hundred_kts);
        else
        if(achieve_detail.equalsIgnoreCase("Two_week_eight_five_three_one"))
            iv.setImageResource(R.drawable.two_week_eight_five_three_one);
        else
        if(achieve_detail.equalsIgnoreCase("Went_on_three_KTs"))
            iv.setImageResource(R.drawable.went_on_three_kts);
        else
        if(achieve_detail.equalsIgnoreCase("Top_speed"))
            iv.setImageResource(R.drawable.top_speed);


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

    public ArrayList<Activity_breakdown_getset> setData(){

        table_data=new ArrayList<>();
        table_data.add(new Activity_breakdown_getset("Kitchen Table Set",2,8,16));
        table_data.add(new Activity_breakdown_getset("Kitchen Table Done",3,5,15));
        table_data.add(new Activity_breakdown_getset("Closed Life App",5,3,15));
        table_data.add(new Activity_breakdown_getset("Closed IBA",5,1,5));
        table_data.add(new Activity_breakdown_getset("Confirmed Invites",1,10,10));
        table_data.add(new Activity_breakdown_getset("New Shows",3,3,9));
        table_data.add(new Activity_breakdown_getset("New Contacts",1,20,20));
        table_data.add(new Activity_breakdown_getset("Bonus for 8-5-3-1",0,0,10));

        return table_data;
    }

    private void selectViewDialog() {
        selectViewdialog=new Dialog(getActivity());
        selectViewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectViewdialog.setContentView(R.layout.graphviewdialog);
        Window window = selectViewdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView Activity_value_breakdown=(TextView)selectViewdialog.findViewById(R.id.tv_breakdown);
        TextView Graph=(TextView)selectViewdialog.findViewById(R.id.tv_graph);
        Activity_value_breakdown.setOnClickListener(this);
        Graph.setOnClickListener(this);
        selectViewdialog.show();
    }

    private void showDialog()
    {
        Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.points);
        RecyclerView rview=(RecyclerView)dialog.findViewById(R.id.recyclerview);
        LinearLayoutManager lManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(lManager);
        rview.setNestedScrollingEnabled(false);
        adapter adap=new adapter(getActivity(),setData());
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

    public void getnotefromfirebase()
    {
        mref.child("events")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.e("get data from server",dataSnapshot.getValue()+" data");

                        JSONArray jsonArray =  new JSONArray();
                        for (com.firebase.client.DataSnapshot child : dataSnapshot.getChildren()) {
                            JSONObject jGroup = new JSONObject();
                            Log.e("childddd",child.child("contactName").getKey()+" abc");
                            //alue().toString(),child.child("created").getValue().toString(),child.child("date").getValue().toString(),child.child("eventKitID").getValue().toString(),child.child("ref").getValue().toString(),child.child("type").getValue().toString(),child.child("userName").getValue().toString(),child.child("userRef").getValue().toString()));
                            try {

                                Log.e ("oo",child.child("type").getValue().toString());

                                String activity_list[]={"Set Appointment","Went on KT","Closed Life","closed IBA","Closed Other Business","Appt Set To Closed Life",
                                        "Appt Set To Closed IBA","Invite to Opportunity Meeting","Went To Opportunity Meeting","Call Back","Dark House","Not Interested"};


                                switch(child.child("type").getValue().toString())

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
                            catch (Exception e)
                            {
                                Log.e("Exception",e+"");
                            }
                        }



                        adapter=new personal_list_adapter(getActivity(),count);
                        rview.setAdapter(adapter);
                        Log.e("jsonarray",jsonArray+" abc");








                    }
                    @Override
                    public void onCancelled(FirebaseError error) {
                        Log.e("get data error",error.getMessage()+" data");
                    }
                });
    }

}
