package Employee;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.reformingkeralathroughdigitilization.Login_page;
import com.example.reformingkeralathroughdigitilization.R;
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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import Admin.Fragments_Admin.FragmentMassegeMain;
import Admin.Fragments_Admin.FragmentProfile;
import Admin.Fragments_Admin.FragmentRequestCertificate;
import Admin.Fragments_Admin.FragmentRequestScheme;
import Admin.Fragments_Admin.FragmentSchemeApprove;
import Admin.Fragments_Admin.FragmentSchemeView;
import Admin.Fragments_Admin.FragmentStaffView;
import Admin.Fragments_Admin.FragmentUserView;
import Admin.Fragments_Admin.viewpageSchemeAdapter;
import Admin.datacollectionClass.ProfileData;
import Admin.viewpageAdaptorMain;
import Admin.viewpageUserAdapter;
import Admin.viewpageofficeAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeMainPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ViewPager viewPager,viewPagerScheme,viewPageruser,viewPageroffice,viewPagerCertificate;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    TextView name, Position,txtlogout;
    View view;
    CircleImageView proimage;
    CardView btnlogout;
    Button submit;
    ConstraintLayout schemeConstraintLayout, homecontainerLayout, Userlayout, officeLayout, certicateLayout;
    EditText editName, editpass;
    ActionBarDrawerToggle toggle;
    androidx.appcompat.widget.Toolbar toolbar;
    TabLayout tabLayout,tabLayoutScheme,tabLayoutuser,tabLayoutoffice,tabLayoutCertificate;
    String id,Uniqname;
    String stringusername, stringpassword,type,authority,authority_Place,Department;
    Uri imageuri = null;
    DatabaseReference databaseReference, profileref;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        profileref = FirebaseDatabase.getInstance().getReference("LoginTable");
        databaseReference = FirebaseDatabase.getInstance().getReference("Profile_Table");

        drawerLayout = findViewById(R.id.drawer);
        viewPager = findViewById(R.id.view_pager);
        viewPagerScheme = findViewById(R.id.viewPager1);
        viewPageruser = findViewById(R.id.viewPageruser);
        viewPageroffice = findViewById(R.id.viewPageroffice);
        viewPagerCertificate = findViewById(R.id.viewPagerCertificate);


        tabLayout = findViewById(R.id.tabLayout);
        tabLayoutScheme = findViewById(R.id.tabLayout1);
        tabLayoutuser = findViewById(R.id.tabLayoutuser);
        tabLayoutoffice = findViewById(R.id.tabLayoutoffice);
        tabLayoutCertificate = findViewById(R.id.tabLayoutCertificate);

        schemeConstraintLayout = findViewById(R.id.schemeLayout);
        homecontainerLayout = findViewById(R.id.homecontainerLayout);
        certicateLayout = findViewById(R.id.certicateLayout);
        officeLayout = findViewById(R.id.officeLayout);
        Userlayout = findViewById(R.id.Userlayout);

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

       ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

 //       drawerLayout.addDrawerListener(actionbar);


        id = getIntent().getStringExtra("id");
        Uniqname = getIntent().getStringExtra("name");
        authority = getIntent().getStringExtra("authority");
        authority_Place = getIntent().getStringExtra("authority_Place");
        Department = getIntent().getStringExtra("Department");

        EditprofileGetdata();

        final viewpageAdaptorMain viewpageAdaptorMain = new viewpageAdaptorMain(getSupportFragmentManager());
        viewpageAdaptorMain.AddFragment(new FragmentProfile(Uniqname,id), "Profile");
        viewpageAdaptorMain.AddFragment(new FragmentMassegeMain(id), "Message");
        viewpageAdaptorMain.AddFragment(new FragmentRequestScheme(authority, authority_Place,Department), "Request Scheme");
        viewpageAdaptorMain.AddFragment(new FragmentRequestCertificate(authority, authority_Place,Department), "Request Certificate");

       viewPager.setAdapter(viewpageAdaptorMain);
       tabLayout.setupWithViewPager(viewPager);

        viewpageSchemeAdapter viewpageschemeAdapter = new viewpageSchemeAdapter(getSupportFragmentManager());
        viewpageschemeAdapter.AddFragment(new FragmentSchemeView(authority, authority_Place,Department), "view Scheme");
        viewpageschemeAdapter.AddFragment(new FragmentSchemeApprove(authority, authority_Place,Department), "Verify Scheme");

        viewPagerScheme.setAdapter(viewpageschemeAdapter);
        tabLayoutScheme.setupWithViewPager(viewPagerScheme);

        viewpageUserAdapter viewpageuserAdapter = new viewpageUserAdapter(getSupportFragmentManager());
        viewpageuserAdapter.AddFragment(new FragmentUserView(authority, authority_Place), "view User");

        viewPageruser.setAdapter(viewpageuserAdapter);
        tabLayoutuser.setupWithViewPager(viewPageruser);


        viewpageofficeAdapter viewpageofficeAdapter = new viewpageofficeAdapter(getSupportFragmentManager());
        viewpageofficeAdapter.AddFragment(new FragmentStaffView(authority,authority_Place,"officer"), "view Office Staff");

        viewPageroffice.setAdapter(viewpageofficeAdapter);
        tabLayoutoffice.setupWithViewPager(viewPageroffice);


//        viewpageCertificateAdaptor viewpagecertificateAdaptor = new viewpageCertificateAdaptor(getSupportFragmentManager());
//        viewpagecertificateAdaptor.AddFragment(new FragmentViewCertificate(), "view Certificate");
//        viewpagecertificateAdaptor.AddFragment(new FragmentApproveCertificate(), "Verify Certificate");

//        viewPagerCertificate.setAdapter(viewpagecertificateAdaptor);
//        tabLayoutCertificate.setupWithViewPager(viewPagerCertificate);


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

                Toast.makeText(getApplicationContext(), "Please select your Profile Image", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringusername)) {
                Toast.makeText(EmployeeMainPage.this, "Please Enter your User name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringpassword)) {
                Toast.makeText(EmployeeMainPage.this, "Please Enter your Password name", Toast.LENGTH_SHORT).show();
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

    private void uploadTofirebase(Uri imageuri, String stringusername, String stringpassword) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));
        fileRef.putFile(imageuri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            ProfileData profilleLogin = new ProfileData();
            profilleLogin.setUserName(stringusername);
            profilleLogin.setPassword(stringpassword);
            updateData(profilleLogin);
            ProfileData profileData = new ProfileData();
            profileData.setId(id);
            profileData.setImageUri(uri.toString());
            checktoInsert(profileData);

        })).addOnProgressListener(snapshot -> {
            //progressBar.setVisibility(View.VISIBLE);
        }).addOnFailureListener(e -> {
            //progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(EmployeeMainPage.this, "Uploading Failure.....", Toast.LENGTH_SHORT).show();
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
                   // progressBar.setVisibility(View.INVISIBLE);
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
                Toast.makeText(getApplicationContext(), "Succefully Updated", Toast.LENGTH_SHORT).show();
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

}