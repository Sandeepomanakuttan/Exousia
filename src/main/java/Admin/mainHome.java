package Admin;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.reformingkeralathroughdigitilization.Login_page;
import com.example.reformingkeralathroughdigitilization.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import Admin.Fragments_Admin.FragmentAddCertificate;
import Admin.Fragments_Admin.FragmentMassegeMain;
import Admin.Fragments_Admin.FragmentProfile;
import Admin.Fragments_Admin.FragmentRequestCertificate;
import Admin.Fragments_Admin.FragmentRequestScheme;
import Admin.Fragments_Admin.FragmentSchemeAdd;
import Admin.Fragments_Admin.FragmentSchemeApprove;
import Admin.Fragments_Admin.FragmentSchemeView;
import Admin.Fragments_Admin.FragmentStaffAdd;
import Admin.Fragments_Admin.FragmentStaffView;
import Admin.Fragments_Admin.FragmentUserView;
import Admin.Fragments_Admin.FragmentViewCertificate;
import Admin.Fragments_Admin.viewpageSchemeAdapter;
import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;


public class mainHome extends AppCompatActivity {

    TextView name, Position,txtlogout;
    DrawerLayout drawerLayout;
    ViewPager viewPager, viewPager1, viewPageruser, viewPageroffice, viewPagerCertificate;
    FrameLayout fragment_container;
    androidx.appcompat.widget.Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    ImageView search;
    CircleImageView proimage;
    EditText txtsearch, txtUsername, txtpassword, txtpost, editName, editpass;
    TabLayout tabLayout, tabLayout1, tabLayoutuser, tabLayoutoffice, tabLayoutCertificate;
    ActionBarDrawerToggle toggle;
    ConstraintLayout schemeConstraintLayout, homecontainerLayout, Userlayout, officeLayout, certicateLayout;
    DatabaseReference databaseReference, profileref;
    Button submit;
    Uri imageuri = null;
    String stringusername, stringpassword, id,Authority_place,type,authority,authority_Place;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private String Uniqname;
    ProgressBar progressBar;
    View view;
    CardView btnlogout;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        profileref = FirebaseDatabase.getInstance().getReference("LoginTable");
        databaseReference = FirebaseDatabase.getInstance().getReference("Profile_Table");

        drawerLayout = findViewById(R.id.drawer);
        viewPager = findViewById(R.id.view_pager);
        schemeConstraintLayout = findViewById(R.id.schemeLayout);
        homecontainerLayout = findViewById(R.id.homecontainerLayout);
        certicateLayout = findViewById(R.id.certicateLayout);
        officeLayout = findViewById(R.id.officeLayout);
        Userlayout = findViewById(R.id.Userlayout);
        viewPager1 = findViewById(R.id.viewPager1);
        viewPageruser = findViewById(R.id.viewPageruser);
        viewPageroffice = findViewById(R.id.viewPageroffice);
        viewPagerCertificate = findViewById(R.id.viewPagerCertificate);
        fragment_container = findViewById(R.id.fragment_container);
        txtsearch = findViewById(R.id.txtsearch);
        txtUsername = findViewById(R.id.editUsername);
        txtpassword = findViewById(R.id.editpass);
        txtpost = findViewById(R.id.editpost);
        search = findViewById(R.id.imgsearch);
        progressBar=findViewById(R.id.progressBar);
       // progressBar.setVisibility(View.INVISIBLE);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout1 = findViewById(R.id.tabLayout1);
        tabLayoutuser = findViewById(R.id.tabLayoutuser);
        tabLayoutoffice = findViewById(R.id.tabLayoutoffice);
        tabLayoutCertificate = findViewById(R.id.tabLayoutCertificate);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        id = getIntent().getStringExtra("id");
        Uniqname = getIntent().getStringExtra("name");
        authority = getIntent().getStringExtra("authority");
        authority_Place = getIntent().getStringExtra("authority_Place");


        homecontainerLayout.setVisibility(View.VISIBLE);
        schemeConstraintLayout.setVisibility(View.GONE);


        navigationView = findViewById(R.id.NavigationView);
        view=navigationView.getHeaderView( 0);
        editName = view.findViewById(R.id.editUsername);
        editpass = view.findViewById(R.id.editpass);
        proimage =view.findViewById(R.id.image);
        name =view.findViewById(R.id.name);
        Position =view.findViewById(R.id.Position);
        submit =view.findViewById(R.id.submit);
        btnlogout =view.findViewById(R.id.btnlogout);
        txtlogout =view.findViewById(R.id.txtlogout);

       EditprofileGetdata();



        final viewpageAdaptorMain viewpageAdaptorMain = new viewpageAdaptorMain(getSupportFragmentManager());
        viewpageAdaptorMain.AddFragment(new FragmentProfile(Uniqname,id), "Profile");
        viewpageAdaptorMain.AddFragment(new FragmentMassegeMain(id,authority_Place), "Message");
        viewpageAdaptorMain.AddFragment(new FragmentRequestScheme(authority,authority_Place,"Admin"), "Request Scheme");
        viewpageAdaptorMain.AddFragment(new FragmentRequestCertificate(authority,authority_Place,""), "Request Certificate");

        viewPager.setAdapter(viewpageAdaptorMain);
        tabLayout.setupWithViewPager(viewPager);

        viewpageSchemeAdapter viewpageschemeAdapter = new viewpageSchemeAdapter(getSupportFragmentManager());
        viewpageschemeAdapter.AddFragment(new FragmentSchemeView(authority,authority_Place,"Admin"), "view Scheme");
        viewpageschemeAdapter.AddFragment(new FragmentSchemeAdd(authority,authority_Place), "Add Scheme");
        viewpageschemeAdapter.AddFragment(new FragmentSchemeApprove(authority,authority_Place,"Admin"), "Approve Scheme");

        viewPager1.setAdapter(viewpageschemeAdapter);
        tabLayout1.setupWithViewPager(viewPager1);

        viewpageUserAdapter viewpageuserAdapter = new viewpageUserAdapter(getSupportFragmentManager());
        viewpageuserAdapter.AddFragment(new FragmentUserView(authority,authority_Place), "view User");
        viewpageuserAdapter.AddFragment(new FragmentUserAdd(authority,authority_Place), "Add User");

        viewPageruser.setAdapter(viewpageuserAdapter);
        tabLayoutuser.setupWithViewPager(viewPageruser);


        viewpageofficeAdapter viewpageofficeAdapter = new viewpageofficeAdapter(getSupportFragmentManager());
        viewpageofficeAdapter.AddFragment(new FragmentStaffView(authority, authority_Place,"officer"), "view Office Staff");
        viewpageofficeAdapter.AddFragment(new FragmentStaffAdd(authority_Place,authority), "Add Office Staff");

        viewPageroffice.setAdapter(viewpageofficeAdapter);
        tabLayoutoffice.setupWithViewPager(viewPageroffice);


        viewpageCertificateAdaptor viewpagecertificateAdaptor = new viewpageCertificateAdaptor(getSupportFragmentManager());
        viewpagecertificateAdaptor.AddFragment(new FragmentViewCertificate(), "view Certificate");
        viewpagecertificateAdaptor.AddFragment(new FragmentAddCertificate(), "Add Certificate");
        viewpagecertificateAdaptor.AddFragment(new FragmentApproveCertificate(), "Approve Certificate");

        viewPagerCertificate.setAdapter(viewpagecertificateAdaptor);
        tabLayoutCertificate.setupWithViewPager(viewPagerCertificate);


        //setUpnavFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {


                case R.id.nav_home:
                    schemeConstraintLayout.setVisibility(View.GONE);
                    Userlayout.setVisibility(View.GONE);
                    officeLayout.setVisibility(View.GONE);
                    certicateLayout.setVisibility(View.GONE);

                    break;

                case R.id.nav_office:
                    officeLayout.setVisibility(View.VISIBLE);
                    schemeConstraintLayout.setVisibility(View.GONE);
                    Userlayout.setVisibility(View.GONE);
                    certicateLayout.setVisibility(View.GONE);

                    break;
//
                case R.id.nav_User:
                    Userlayout.setVisibility(View.VISIBLE);
                    officeLayout.setVisibility(View.GONE);
                    schemeConstraintLayout.setVisibility(View.GONE);
                    certicateLayout.setVisibility(View.GONE);


                    break;


                case R.id.nav_certificate:
                    certicateLayout.setVisibility(View.VISIBLE);
                    schemeConstraintLayout.setVisibility(View.GONE);
                    Userlayout.setVisibility(View.GONE);
                    officeLayout.setVisibility(View.GONE);
                    break;

                case R.id.nav_scheme:
                    schemeConstraintLayout.setVisibility(View.VISIBLE);
                    Userlayout.setVisibility(View.GONE);
                    officeLayout.setVisibility(View.GONE);
                    certicateLayout.setVisibility(View.GONE);
                    break;
            }
            return true;
        });

    }

    private void EditprofileGetdata() {

        Query query1;
        query1 = profileref.orderByChild("name").equalTo(Uniqname);
        query1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    editName.setText(data.getUserName());
                    editpass.setText(data.getPassword());
                    name.setText(data.getName());
                    Position.setText(data.getUser());
                    type=data.getAthority_Type();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query query=databaseReference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    String link1 =data.getImageUri();
                    Picasso.get().load(link1).into(proimage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        proimage.setOnClickListener(view -> openGallery());

        submit.setOnClickListener(v -> {
            stringusername = editName.getText().toString();
            stringpassword = editpass.getText().toString();
            if (imageuri == null) {

                Toast.makeText(mainHome.this, "Please select your Profile Image", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringusername)) {
                Toast.makeText(mainHome.this, "Please Enter your User name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringpassword)) {
                Toast.makeText(mainHome.this, "Please Enter your Password name", Toast.LENGTH_SHORT).show();
            } else {
                uploadTofirebase(imageuri, stringusername, stringpassword);
            }

            proimage.setOnClickListener(view -> openGallery());
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Login_page.class);
                startActivity(intent);
                finish();
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Login_page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void uploadTofirebase(Uri uri,String stringusername,String stringpassword) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ProfileData profilleLogin=new ProfileData();
                        profilleLogin.setUserName(stringusername);
                        profilleLogin.setPassword(stringpassword);
                        updateData(profilleLogin);
                        ProfileData profileData=new ProfileData();
                        profileData.setId(id);
                        profileData.setImageUri(uri.toString());
                        checktoInsert(profileData);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Uploading Failure.....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri muri) {

        ContentResolver contentResolver;
        contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(muri));
    }

    private void checktoInsert(ProfileData profileData) {
        profileref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()) {
                    updatProfile(profileData);
                }
                else{
                    databaseReference.child(id).setValue(profileData);
//                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatProfile(ProfileData profileData) {

        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("Image", profileData.getImageUri());
        profileref.child(id).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(mainHome.this, "Succefully Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            proimage.setImageURI(imageuri);
        }
    }

    private void updateData(ProfileData profileData) {
        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("userName", profileData.getUserName());
        update.put("password", profileData.getPassword());
        profileref.child(Uniqname).updateChildren(update).addOnSuccessListener((OnSuccessListener) this::onSuccess).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(), "Something is error", Toast.LENGTH_SHORT).show();
            editName.setError("error");
            editName.requestFocus();

        });
    }


    private void onSuccess(Object o) {
        Toast.makeText(getApplicationContext(),
                R.string.update,
                Toast.LENGTH_SHORT).show();
//        progressBar.setVisibility(View.INVISIBLE);
    }

    private void onProgress(UploadTask.TaskSnapshot snapshot) {
     //   progressBar.setVisibility(View.VISIBLE);
    }
}


