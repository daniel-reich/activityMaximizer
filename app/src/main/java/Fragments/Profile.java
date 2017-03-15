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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import u.activitymanager.HomeActivity;
import u.activitymanager.R;
import u.activitymanager.SplashActivity;
import utils.AnimateFirstDisplayListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Surbhi on 14-02-2017.
 */

public class Profile extends Fragment implements View.OnClickListener {

    MenuItem menu;
    Toolbar toolbar;
    Dialog settingsdialog,helpdialog;
    RelativeLayout lay_branch_tran,addPartnershipLayout;
    View view;
    TextView RVP;
    Dialog RvpDialog;
    CircleImageView profile_pic;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    TextView tv_phone,tv_username,tv_email,tv_solution_num,tv_rvp_num,tv_trainer_num,tv_upline_num,tv_partner_num;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    static final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 3;
    final int GALLAY_IMAGE= 2;
    private Uri picUri;
    StorageReference storageRef;
    Firebase mref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_profile,container,false);
        setHasOptionsMenu(true);
        HomeActivity.title.setText("My Profile");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_prev);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase.setAndroidContext(getActivity());
        storageRef= FirebaseStorage.getInstance().getReference();
        mref=new Firebase("https://activitymaximizer-d07c2.firebaseio.com/");

        init();

        return view;
    }

    public  void init() {

        pref=getActivity().getSharedPreferences("userpref",0);

        tv_partner_num=(TextView)view.findViewById(R.id.tv_partnernumber);
        tv_upline_num=(TextView)view.findViewById(R.id.tv_uplinenumber);
        tv_trainer_num=(TextView)view.findViewById(R.id.tv_trainernumber);
        tv_rvp_num=(TextView)view.findViewById(R.id.tv_rvpnumber);
        tv_solution_num=(TextView)view.findViewById(R.id.tv_solutionnumber);
        tv_email=(TextView)view.findViewById(R.id.tv_email);
        tv_username=(TextView)view.findViewById(R.id.tv_username);
        tv_phone=(TextView)view.findViewById(R.id.tv_phone);

        RVP=(TextView)view.findViewById(R.id.tv_rvp);
        lay_branch_tran=(RelativeLayout)view.findViewById(R.id.lay_branchtransfer);
        profile_pic=(CircleImageView)view.findViewById(R.id.profile_pic);
         addPartnershipLayout=(RelativeLayout) view.findViewById(R.id.add_partnership_layout);
        addPartnershipLayout.setOnClickListener(this);
        RVP.setOnClickListener(this);
        lay_branch_tran.setOnClickListener(this);

        setImage();

        tv_partner_num.setText(pref.getString("partner_solution_number",""));
        tv_upline_num.setText(pref.getString("upline_solution_number",""));
        tv_trainer_num.setText(pref.getString("trainer_solution_number",""));
        tv_rvp_num.setText(pref.getString("rvp_solution_number",""));
        tv_solution_num.setText(pref.getString("solution_number",""));
        tv_email.setText(pref.getString("email",""));
        tv_username.setText(pref.getString("givenName","")+" "+pref.getString("familyName",""));
        tv_phone.setText(pref.getString("phoneNumber",""));

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicDialog();
            }
        });
    }

    public  void setImage(){
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.getInstance().displayImage(pref.getString("profilePictureURL",""), profile_pic, options, animateFirstListener);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu).setIcon(R.drawable.settings);
        menu.findItem(R.id.menu).setTitle("Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                showSettingsDialog();
                break;
            case android.R.id.home:
               // showHelpDialog();
               getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void showSettingsDialog()
    {
      //  AlertDialog dialog=new AlertDialog(getActivity(),)selectPicdialog=new Dialog(getActivity());
        settingsdialog=new Dialog(getActivity());
        settingsdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsdialog.setContentView(R.layout.settings_dialog);
        Window window = settingsdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_cancel=(TextView)settingsdialog.findViewById(R.id.tv_cancel);
        TextView tv_signout=(TextView)settingsdialog.findViewById(R.id.tv_signout);
        TextView tv_editprofile=(TextView)settingsdialog.findViewById(R.id.tv_edit_profile);
        TextView tv_admin=(TextView)settingsdialog.findViewById(R.id.tv_admin);

        tv_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mref.unauth();
                edit=pref.edit();
                edit.clear();
                edit.commit();
                Intent in = new Intent(getActivity(), SplashActivity.class);
                startActivity(in);
                getActivity().finish();
            }
        });

        tv_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Edit_Profile()).addToBackStack(null).commit();
                settingsdialog.dismiss();
            }
        });

        settingsdialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_rvp:
                showDialog();
                break;
            case R.id.lay_branchtransfer:
                showBranch();
                break;
            case R.id.add_partnership_layout:
                showPartnershipDialog();
                break;
        }
    }

    private void showBranch() {
        RvpDialog=new Dialog(getActivity());
        RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RvpDialog.setContentView(R.layout.branch_transfer_dialog);
        Window window = RvpDialog.getWindow();
        TextView tv_send=(TextView)RvpDialog.findViewById(R.id.tv_send);
        TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
        tv_send.setText("Send");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();

            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RvpDialog.show();
    }

    private void showDialog() {
        RvpDialog=new Dialog(getActivity());
        RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RvpDialog.setContentView(R.layout.rvp_dialog);
        Window window = RvpDialog.getWindow();
        TextView tv_send=(TextView)RvpDialog.findViewById(R.id.tv_save);
        TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
        tv_send.setText("Send");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RvpDialog.dismiss();
                Log.e("sltn no",pref.getString("rvp_solution_number","")+","+(pref.getString("givenName","")));
                Map sendRequest = new HashMap();
                sendRequest.put("ref", "https://activitymaximizer.firebaseio.com/Soluti...");
                sendRequest.put("username", pref.getString("givenName","")+" " +pref.getString("familyName",""));
                sendRequest.put("userRVPSolutionNumber",pref.getString("rvp_solution_number",""));
                sendRequest.put("userSolutionNumber",pref.getString("solution_number",""));
                sendRequest.put("userUID",pref.getString("uid",""));
                sendRequest.put("userUplineSolutionNumber",pref.getString("upline_solution_number",""));
                Log.e("sltn no1",pref.getString("rvp_solution_number","")+","+(pref.getString("givenName","")));
                mref.child("Solution Numbers").child(pref.getString("rvp_solution_number","")).child("Pending Requests").child("RVP Requests").child(pref.getString("givenName","")).updateChildren(sendRequest);
                Log.e("sltn no2",pref.getString("rvp_solution_number","")+","+(pref.getString("givenName","")));

            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RvpDialog.show();
    }








    private void showPartnershipDialog()
    {
        RvpDialog=new Dialog(getActivity());
        RvpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        RvpDialog.setContentView(R.layout.partnershiprequestdialog);
        Window window = RvpDialog.getWindow();
        TextView tv_send=(TextView)RvpDialog.findViewById(R.id.tv_send);
        TextView tv_cancel=(TextView)RvpDialog.findViewById(R.id.cancel);
        final EditText PartnerSolutionNumber=(EditText)RvpDialog.findViewById(R.id.et_partner_solution_number);

        //tv_send.setText("Send");
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RvpDialog.dismiss();

                if (PartnerSolutionNumber.getText().toString().length()>0)
                {
                    // add Partnership request
                    Map sendRequest = new HashMap();
                    sendRequest.put("name",(pref.getString("givenName","")+" "+(pref.getString("familyName",""))));
                    sendRequest.put("solutionNumber",(pref.getString("solution_number","")));
                    sendRequest.put("uid",(pref.getString("uid","")) );
//                    sendRequest.put("userRVPSolutionNumber",pref.getString("rvp_solution_number",""));
//                    sendRequest.put("userSolutionNumber",pref.getString("solution_number",""));
//                    sendRequest.put("userUID",pref.getString("uid",""));
//                    sendRequest.put("userUplineSolutionNumber",pref.getString("upline_solution_number",""));
                    Log.e("sltn no1",pref.getString("rvp_solution_number","")+","+(pref.getString("givenName","")));
                    mref.child("Solution Numbers").child(PartnerSolutionNumber.getText().toString()).child("PartnerShip Request").updateChildren(sendRequest);
                }
                else
                {
                    Toast.makeText(getActivity(),"Please enter your Partner Solution Number",Toast.LENGTH_SHORT).show();

                }


            }
        });
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RvpDialog.show();
    }
    //  Select Image  statrt

    private void selectPicDialog() {

       Dialog selectPicdialog=new Dialog(getActivity());
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
                profile_pic.setImageBitmap(bitmap);

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
            profile_pic.setImageBitmap(thePic);
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

    // Select Image End...
}
