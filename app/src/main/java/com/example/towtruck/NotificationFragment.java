package com.example.towtruck;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
//import com.bottomnavigationview.R;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public NotificationFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private TextInputEditText mNameField, mPhoneField, mSurnameField, mEmailField ;

    private TextView  TopName,TopEmail;

    private TextView mLogout;
    private CircularProgressButton mConfirm;

    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;
    private DatabaseReference refrence;

    private String userID;
    private String mName;
    private String mPhone;
    private String mSurname;
    private String mEmail;


    private Uri resultUri;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

   // Edit text identifire

        mNameField = (TextInputEditText) view.findViewById(R.id.name);
        mPhoneField = (TextInputEditText) view.findViewById(R.id.phone);
        mSurnameField = (TextInputEditText) view.findViewById(R.id.surname);
        mEmailField = (TextInputEditText) view.findViewById(R.id.email);

        // text View identifier
        TopName = (TextView) view.findViewById(R.id.fullname_field);
        TopEmail = (TextView) view.findViewById(R.id.username_field);

        mProfileImage = (ImageView) view.findViewById(R.id.profileImage);

        mConfirm = (CircularProgressButton) view.findViewById(R.id.confirm);
        mLogout = (TextView) view.findViewById(R.id.txtlogout);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        //readData(userID);
        getUserInfo();

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

checkLocationPermission1();



            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();

                Toast.makeText(getActivity(), "Submited", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Thank you")
                        .setMessage(" Your user information has been saved successfully ")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .create();
                dialog.show();


            }
        });


        return  view;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission1() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Permission")
                        .setMessage("Please allow Tow Truck to get access to your Storage.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // prompt the user for storage
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                    // permission denied
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Sorry Denied!")
                            .setMessage("You will not be able to set a profile image, because application is not allowed to storage.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // prompt the user for storage
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }
                            })
                            .create()
                            .show();

                    startActivity(new Intent(getActivity(),MainActivity.class));

                }
                return;
            }

        }
    }






//getting the users information
    private void getUserInfo(){
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(userID);
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("email")!=null){
                        mEmail = map.get("email").toString();
                        TopEmail.setText(mEmail);
                    }

                    if(map.get("name")!=null){
                        mName = map.get("name").toString();
                        TopName.setText(mName);
                    } if(map.get("name")!=null){
                        mName = map.get("name").toString();
                        mNameField.setText(mName);

                    }
                    if(map.get("mobile")!=null){
                        mPhone = map.get("mobile").toString();
                        mPhoneField.setText(mPhone);
                    }
                    if(map.get("email")!=null){
                        mEmail = map.get("email").toString();
                        mEmailField.setText(mEmail);
                        //TopEmail.setText(mEmail);
                    }
                    if(map.get("surname")!=null){
                        mSurname = map.get("surname").toString();
                        mSurnameField.setText(mSurname);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    private void readData(String userID){
        refrence = FirebaseDatabase.getInstance().getReference("Users").child("Customer");
        Log.i("TowTruck",userID);
        refrence.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
if (task.getResult().exists()){
    DataSnapshot dataSnapshot = task.getResult();
    String firstname = String.valueOf(dataSnapshot.child("name").getValue());
    String surname = String.valueOf(dataSnapshot.child("surname").getValue());
    String email = String.valueOf(dataSnapshot.child("email").getValue());
    String mobile = String.valueOf(dataSnapshot.child("mobile").getValue());

    mNameField.setText(firstname);
mSurnameField.setText(surname);
mEmailField.setText(email);
mPhoneField.setText(mobile);



}else {
    new AlertDialog.Builder(getActivity())
            .setTitle("Error")
            .setMessage("User does not exist")
            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .create()
            .show();
}

                }else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Sorry unable to read the users information.")
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create()
                            .show();
                }
            }
        });
    }


    private void saveUserInformation() {
        mName = mNameField.getText().toString();
        mPhone = mPhoneField.getText().toString();
        mSurname = mSurnameField.getText().toString();
        mEmail = mEmailField.getText().toString();



        Map userInfo = new HashMap();
        userInfo.put("name", mName);
        userInfo.put("surname", mSurname);
        userInfo.put("mobile", mPhone);
        userInfo.put("email", mEmail);

        // commented bottom line for now.....
       // mCustomerDatabase.updateChildren(userInfo);


    // It was !=
        if (resultUri != null) {

            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
            Bitmap bitmap = null;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Map newImage = new HashMap();
                    // newImage.put("profileImageUrl", downloadUrl.toString());
                    mCustomerDatabase.updateChildren(newImage);
                    return;
                }
            });

        }
    }
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data){
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                    final Uri imageUri = data.getData();
                    resultUri = imageUri;
                    mProfileImage.setImageURI(resultUri);
                }
            }
        }
