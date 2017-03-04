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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Adapter.adapter;
import Adapter.personal_list_adapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.personal_frag,container,false);
        setHasOptionsMenu(true);
        Log.e("check","check");
        meter=(ImageView)view.findViewById(R.id.meter);
        rview=(RecyclerView)view.findViewById(R.id.rview);
        Profile_pic=(DiamondImageView)view.findViewById(R.id.profile_pic);

        tv_username=(TextView) view.findViewById(R.id.tv_username);
        tv_phone=(TextView)view.findViewById(R.id.tv_phone);

        linearLayoutManager=new LinearLayoutManager(getActivity());
        rview.setLayoutManager(linearLayoutManager);
        adapter=new personal_list_adapter(getActivity());
        rview.setAdapter(adapter);
        Profile_pic.setOnClickListener(this);
        meter.setOnClickListener(this);

        pref=getActivity().getSharedPreferences("userpref",0);

        Firebase.setAndroidContext(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();

        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        storageRef= FirebaseStorage.getInstance().getReference();

//        getdatafromfirebase();

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

        return view;
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
        adapter adap=new adapter();
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

}
