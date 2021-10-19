package UserSection;

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
import Admin.Fragments_Admin.FragmentStaffView;
import Admin.Fragments_Admin.viewpageSchemeAdapter;
import Admin.datacollectionClass.ProfileData;
import Admin.viewpageAdaptorMain;
import Admin.viewpageCertificateAdaptor;
import Admin.viewpageUserAdapter;
import Admin.viewpageofficeAdapter;
import UserSection.Fragments.FragmentViewCertificateUser;
import UserSection.Fragments.FragmentViewScheme;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ViewPager viewPager, viewPagerScheme, viewPageruser, viewPageroffice, viewPagerCertificate;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    TextView name, Position, txtlogout;
    View view;
    CircleImageView proimage;
    CardView btnlogout;
    Button submit;
    ConstraintLayout schemeConstraintLayout, homecontainerLayout, Userlayout, officeLayout, certicateLayout;
    EditText editName, editpass;
    ActionBarDrawerToggle toggle;
    androidx.appcompat.widget.Toolbar toolbar;
    TabLayout tabLayout, tabLayoutScheme, tabLayoutuser, tabLayoutoffice, tabLayoutCertificate;
    String id, Uniqname,authority;
    String stringusername, stringpassword, authType, user,authority_Place;
    Uri imageuri = null;
    DatabaseReference databaseReference, profileref;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

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
        view = navigationView.getHeaderView(0);
        editName = view.findViewById(R.id.editUsername);
        proimage = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        Position = view.findViewById(R.id.Position);
        submit = view.findViewById(R.id.submit);
        btnlogout = view.findViewById(R.id.btnlogout);
        txtlogout = view.findViewById(R.id.txtlogout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        //drawerLayout.addDrawerListener((DrawerLayout.DrawerListener) actionbar);


        id = getIntent().getStringExtra("id");
        Uniqname = getIntent().getStringExtra("name");
        authority = getIntent().getStringExtra("authority");
        authority_Place = getIntent().getStringExtra("authority_Place");

        Toast.makeText(this, authority_Place, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, authority, Toast.LENGTH_SHORT).show();



        EditprofileGetdata();

        final viewpageAdaptorMain viewpageAdaptorMain = new viewpageAdaptorMain(getSupportFragmentManager());
        viewpageAdaptorMain.AddFragment(new FragmentViewScheme(id,"Scheme",authority,authority_Place), "View Scheme");
        //viewpageAdaptorMain.AddFragment(new FragmentViewCertificateUser(id,"Certificate", authority), "View Certificate");
        viewpageAdaptorMain.AddFragment(new FragmentMassegeMain(id,authority_Place), "Message");


        viewPager.setAdapter(viewpageAdaptorMain);
        tabLayout.setupWithViewPager(viewPager);

        viewpageSchemeAdapter viewpageschemeAdapter = new viewpageSchemeAdapter(getSupportFragmentManager());
        viewpageschemeAdapter.AddFragment(new FragmentViewScheme(id,"Accept",authority,authority_Place), "Accept Scheme");
        viewpageschemeAdapter.AddFragment(new FragmentViewScheme(id,"Pending",authority,authority_Place), "Pending Scheme");
        viewpageschemeAdapter.AddFragment(new FragmentViewScheme(id,"Reject",authority,authority_Place), "Reject Scheme");

        viewPagerScheme.setAdapter(viewpageschemeAdapter);
        tabLayoutScheme.setupWithViewPager(viewPagerScheme);

        viewpageUserAdapter viewpageuserAdapter = new viewpageUserAdapter(getSupportFragmentManager());
        viewpageuserAdapter.AddFragment(new FragmentStaffView(authority,authority_Place,"Admin"), "view Admin");

        viewPageruser.setAdapter(viewpageuserAdapter);
        tabLayoutuser.setupWithViewPager(viewPageruser);


        viewpageofficeAdapter viewpageofficeAdapter = new viewpageofficeAdapter(getSupportFragmentManager());
        viewpageofficeAdapter.AddFragment(new FragmentStaffView(authority,authority_Place,"officer"), "view Officier");

        viewPageroffice.setAdapter(viewpageofficeAdapter);
        tabLayoutoffice.setupWithViewPager(viewPageroffice);


        viewpageCertificateAdaptor viewpagecertificateAdaptor = new viewpageCertificateAdaptor(getSupportFragmentManager());
        viewpagecertificateAdaptor.AddFragment(new FragmentViewCertificateUser(id,"Accept", authority), "Accept Certificate");
        viewpagecertificateAdaptor.AddFragment(new FragmentViewCertificateUser(id,"Pending", authority), "Pending Certificate");
        viewpagecertificateAdaptor.AddFragment(new FragmentViewCertificateUser(id,"Reject", authority), "Reject Certificate");

        viewPagerCertificate.setAdapter(viewpagecertificateAdaptor);
        tabLayoutCertificate.setupWithViewPager(viewPagerCertificate);


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
                    name.setText(data.getName());
                    Position.setText(data.getUser());

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query query = databaseReference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    String link1 = data.getImageUri();
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
                Toast.makeText(getApplicationContext(), "Please Enter your User name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringpassword)) {
                Toast.makeText(getApplicationContext(), "Please Enter your Password name", Toast.LENGTH_SHORT).show();
            } else {
                uploadTofirebase(imageuri, stringusername, stringpassword);
            }

            proimage.setOnClickListener(view -> openGallery());
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_page.class);
                startActivity(intent);
                finish();
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_page.class);
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
            Toast.makeText(getApplicationContext(), "Uploading Failure.....", Toast.LENGTH_SHORT).show();
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
                } else {
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
       // progressBar.setVisibility(View.INVISIBLE);
    }

    private void getData() {
        Query query;
        query = profileref.orderByChild("name").equalTo(Uniqname);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    user = data.getUser();
                    authType = data.getAthority_Type();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
