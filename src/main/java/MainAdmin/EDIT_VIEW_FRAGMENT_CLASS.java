package MainAdmin;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.reformingkeralathroughdigitilization.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class EDIT_VIEW_FRAGMENT_CLASS extends Fragment {

    EditText editName,editUsername,editPass,editPost;
    CircleImageView imgpro;
    Button submit;
    Uri imageuri=null;
    ProgressBar progressBar;
    FirebaseStorage storage;
    private AwesomeValidation awesomeValidation;
    StorageReference storageReference=FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile_Table");
    DatabaseReference databaseReferencelogin = FirebaseDatabase.getInstance().getReference("LoginTable");

    public String stringname,stringusername,stringpassword,stringpost,id,uniqueName;
    public EDIT_VIEW_FRAGMENT_CLASS(String id,String name) {
        this.id=id;
        uniqueName=name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup)inflater.inflate(R.layout.edit_profile_tab_fragment,container,false);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        imgpro=root.findViewById(R.id.img);
        editName=root.findViewById(R.id.editName);
        editUsername=root.findViewById(R.id.editUsername);
        editPass=root.findViewById(R.id.editpass);
        editPost=root.findViewById(R.id.editpost);
        submit=root.findViewById(R.id.submit);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        imgpro.setTranslationX(800);
        imgpro.setAlpha(0);
        imgpro.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editName.setTranslationX(800);
        editName.setAlpha(0);
        editName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editUsername.setTranslationX(800);
        editUsername.setAlpha(0);
        editUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editPass.setTranslationX(800);
        editPass.setAlpha(0);
        editPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editPost.setTranslationX(800);
        editPost.setAlpha(0);
        editPost.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        submit.setTranslationX(800);
        submit.setAlpha(0);
        submit.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringname=editName.getText().toString();
                stringusername=editUsername.getText().toString();
                stringpassword=editPass.getText().toString();
                stringpost=editPost.getText().toString();

                if (imageuri== null){

                    Toast.makeText(getContext(), "Please select your Profile Image", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(stringname)){
                    Toast.makeText(getContext(), "Please Enter your name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(stringusername)){
                    Toast.makeText(getContext(), "Please Enter your user name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(stringpost)){
                    Toast.makeText(getContext(), "Please Enter your password", Toast.LENGTH_SHORT).show();
                }
                else{
                        uploadTofirebase(imageuri,stringname,stringusername,stringpassword,stringpost);
                }


            }
        });
        

        imgpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        return root;
    }

    private void uploadTofirebase(Uri uri,String stringname,String stringusername,String stringpassword,String stringpost) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ProfileData profilleLogin=new ProfileData();
                        profilleLogin.setName(stringname);
                        profilleLogin.setUserName(stringusername);
                        profilleLogin.setPassword(stringpassword);
                        updateData(profilleLogin);
                        ProfileData profileData=new ProfileData();
                        profileData.setId(id);
                        profileData.setImageUri(uri.toString());
                        profileData.setUser(stringpost);
                        checktoInsert(profileData);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Uploading Failure.....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checktoInsert(ProfileData profileData) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()) {
                    updatProfile(profileData);
                }
                else{
                    databaseReference.child(id).setValue(profileData);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatProfile(ProfileData profileData) {

        HashMap update=new HashMap();
        update.put("imageUri",profileData.getImageUri());
        update.put("user",profileData.getUser());
        databaseReference.child(id).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getContext(), "Update Profile table", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something is error", Toast.LENGTH_SHORT).show();
                editName.setError("error");
                editName.requestFocus();

            }
        });
    }

    private String getFileExtension(Uri muri) {

       ContentResolver contentResolver;
        contentResolver= getContext().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(muri));
    }


    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data != null){
            imageuri = data.getData();
            imgpro.setImageURI(imageuri);
        }
    }

    private void updateData(ProfileData profileData){
        HashMap update=new HashMap();
        update.put("userName",profileData.getUserName());
        update.put("password",profileData.getPassword());
        String name1;
        databaseReferencelogin.child("MainAdmin").updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getContext(), "Update login table", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something is error", Toast.LENGTH_SHORT).show();
                editName.setError("error");
                editName.requestFocus();

            }
        });
    }
}
