    package MainAdmin;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

    public class main_admin_authority_field extends AppCompatActivity {
TextView panchayath,village;
    EditText edtUsername,edtPass,edtAdminName,AthorityName,editsearch;
    Spinner edtauthority;
    Button btnsubmit;
    String str_authority,str_user,str_password,str_nameadmin,strAthorityName,id,name;
    RecyclerView layoutPanchayath,layoutVillage,layoutsearch;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout layoutConstraintLayout,layoutmainConstraintLayout,constraint;
    TableLayout layoutTable;
    CircleImageView imgsearch;
    myAdapterclass adapter;
    SearchAdaptor adapterclass;
    DatabaseReference RKTDRef;
    ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"CutPasteId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_authority_field);
        layoutConstraintLayout=findViewById(R.id.layoutConstraint);
        layoutmainConstraintLayout=findViewById(R.id.main);
        constraint=findViewById(R.id.constrain);
        panchayath=findViewById(R.id.txtPanchayath);
        village=findViewById(R.id.txtVillege);
        edtUsername=findViewById(R.id.usd_txt);
        AthorityName=findViewById(R.id.edx_AthorityName);
        edtPass=findViewById(R.id.etdpass);
        edtAdminName=findViewById(R.id.adm_pan);
        btnsubmit=findViewById(R.id.btnsubmit);
        edtauthority=findViewById(R.id.spinner);
        layoutTable=findViewById(R.id.simpleTableLayout);
        bottomNavigationView=findViewById(R.id.bottomnav);
        layoutVillage=findViewById(R.id.layoutVillage);
        layoutPanchayath=findViewById(R.id.layoutpanchayath);
        layoutsearch=findViewById(R.id.layoutsearch);
        imgsearch=findViewById(R.id.imgsearch);
        progressBar=findViewById(R.id.prograss);
        RKTDRef= FirebaseDatabase.getInstance().getReference("LoginTable");
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        progressBar.setVisibility(View.INVISIBLE);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        btnsubmit.setOnClickListener(v -> {
            str_authority=edtauthority.getSelectedItem().toString();
            strAthorityName=AthorityName.getText().toString();
            str_user=edtUsername.getText().toString();
            str_password=edtPass.getText().toString();
            str_nameadmin=edtAdminName.getText().toString();
            if(Objects.equals(str_authority, "select Authority")){
                Toast.makeText(getApplicationContext(),"pls select Authority",Toast.LENGTH_LONG).show();
            }

            else if(TextUtils.isEmpty(strAthorityName)){
                Toast.makeText(getApplicationContext(), "Enter Your Athority Name", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(str_user)){
                Toast.makeText(getApplicationContext(), "Enter user Name", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(str_password)){
                Toast.makeText(getApplicationContext(), " Create password", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(str_nameadmin)){
                Toast.makeText(getApplicationContext(),"enter Admin Name",Toast.LENGTH_LONG).show();
            }
            else{
                ProfileData Data=new ProfileData();
                Data.setUser("Admin");
                Data.setAthority_Type(str_authority);
                Data.setUserName(str_user);
                Data.setPassword(str_password);
                Data.setName(str_nameadmin);
                Data.setAuthority_Place(strAthorityName);
                progressBar.setVisibility(View.VISIBLE);
                checkdata(Data);
                }


        });



        panchayath.setOnClickListener(this::onClick);


        village.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                panchayath.setBackground(getDrawable(R.drawable.textborder));
                village.setBackground(getDrawable(R.drawable.styleborder));
                layoutPanchayath.setVisibility(GONE);
                layoutVillage.setVisibility(View.VISIBLE);
                Query query1=RKTDRef.orderByChild("athority_Type").equalTo("Village");
                FirebaseRecyclerOptions<ProfileData> options =
                        new FirebaseRecyclerOptions.Builder<ProfileData>()
                                .setQuery(query1,ProfileData.class)
                                .build();
                Log.e("test",options.toString());
                adapter=new myAdapterclass(options);               
                layoutVillage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                layoutVillage.setAdapter(adapter);

                adapter.startListening();

            }
        });



    }

        public void InsertLoginData(ProfileData Data){

            String key=RKTDRef.push().getKey();
            Data.setId(key);
            RKTDRef.child(Data.getUserName()).setValue(Data);
            Toast.makeText(main_admin_authority_field.this, "User succefully added", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);

        }

        public void checkdata(ProfileData Data) {
            Query query = RKTDRef;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        ProfileData dataClass = childSnapshot.getValue(ProfileData.class);
                        if (dataClass.getUserName().equalsIgnoreCase(Data.getUserName())) {
                            Toast.makeText(main_admin_authority_field.this, "username already exist", Toast.LENGTH_SHORT).show();
                        } else if (dataClass.getPassword().equalsIgnoreCase(Data.getPassword())) {
                            Toast.makeText(main_admin_authority_field.this, "Password already exist", Toast.LENGTH_SHORT).show();
                        } else {
                            InsertLoginData(Data);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        private final BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    panchayath.setBackground(getDrawable(R.drawable.textborder));
                    village.setBackground(getDrawable(R.drawable.textborder));

                    constraint.setVisibility(GONE);
                    layoutsearch.setVisibility(GONE);
                    layoutConstraintLayout.setVisibility(GONE);
                    layoutPanchayath.setVisibility(GONE);
                    layoutVillage.setVisibility(GONE);
                    layoutmainConstraintLayout.setVisibility(View.VISIBLE);
                    break;

                case R.id.nav_add:
                    panchayath.setBackground(getDrawable(R.drawable.textborder));
                    village.setBackground(getDrawable(R.drawable.textborder));
                    layoutConstraintLayout.setVisibility(View.VISIBLE);
                    layoutPanchayath.setVisibility(GONE);
                    layoutVillage.setVisibility(GONE);
                    constraint.setVisibility(GONE);
                    layoutsearch.setVisibility(GONE);
                    break;

                case R.id.nav_search:
                    panchayath.setBackground(getDrawable(R.drawable.textborder));
                    village.setBackground(getDrawable(R.drawable.textborder));
                    layoutConstraintLayout.setVisibility(GONE);
                    constraint.setVisibility(View.VISIBLE);
                    layoutsearch.setVisibility(View.VISIBLE);
                    layoutPanchayath.setVisibility(GONE);
                    layoutVillage.setVisibility(GONE);
                    search();
                    break;

            }
            return false;
        }
    };

    private void search() {

        editsearch = findViewById(R.id.editsearch);
        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editsearch.getText().toString();
                if (TextUtils.isEmpty(search)){
                    Toast.makeText(main_admin_authority_field.this, "Enter search item", Toast.LENGTH_SHORT).show();
                }else {
                    Query query = FirebaseDatabase.getInstance().getReference("LoginTable");
                    FirebaseRecyclerOptions<ProfileData> options =
                            new FirebaseRecyclerOptions.Builder<ProfileData>()
                                    .setQuery(query, ProfileData.class)
                                    .build();
                    adapterclass=new SearchAdaptor(options,search);
                    layoutsearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    adapterclass.startListening();

                    layoutsearch.setAdapter(adapter);
                }
            }
        });
    }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void onClick(View v) {
            panchayath.setBackground(getDrawable(R.drawable.styleborder));
            village.setBackground(getDrawable(R.drawable.textborder));
            layoutPanchayath.setVisibility(View.VISIBLE);
            layoutVillage.setVisibility(GONE);
            Query query = RKTDRef.orderByChild("athority_Type").equalTo("Panchayath");
            FirebaseRecyclerOptions<ProfileData> options =
                    new FirebaseRecyclerOptions.Builder<ProfileData>()
                            .setQuery(query, ProfileData.class)
                            .build();
            Log.e("test", options.toString());
            adapter = new myAdapterclass(options);
            layoutPanchayath.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            adapter.startListening();

            layoutPanchayath.setAdapter(adapter);

        }
    }