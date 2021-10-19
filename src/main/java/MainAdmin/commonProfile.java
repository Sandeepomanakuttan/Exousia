package MainAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reformingkeralathroughdigitilization.Login_page;
import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class commonProfile extends AppCompatActivity {
    TextView profile,authority,message,setting,nameTxt,txtPosition;
    ImageView logout;
    CircleImageView proImage;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("LoginTable");
    DatabaseReference Profilereference= FirebaseDatabase.getInstance().getReference("Profile_Table");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_profile);
        proImage=findViewById(R.id.proImage);
        nameTxt=findViewById(R.id.txt);
        txtPosition=findViewById(R.id.txtPosition);
        profile=findViewById(R.id.Myprofile);
        authority=findViewById(R.id.txtAuthority);
        message=findViewById(R.id.txtMessage);
        setting=findViewById(R.id.txtSetting);
        logout=findViewById(R.id.icLogout);
        String id=getIntent().getStringExtra("id");
        String name=getIntent().getStringExtra("name");


        Query query=Profilereference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    String link1 =data.getImageUri();

                    Picasso.get().load(link1).into(proImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query1=reference.orderByChild("id").equalTo(id);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    nameTxt.setText(data.getName());
                    txtPosition.setText(data.getUser());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profile.setOnClickListener(v -> {
            Intent profileControl=new Intent(commonProfile.this, ViewandEditProfilePage.class);
            profileControl.putExtra("id",id);
            profileControl.putExtra("name",name);
            startActivity(profileControl);
        });

        authority.setOnClickListener(v -> {
            Intent profileControl=new Intent(commonProfile.this, main_admin_authority_field.class);
            profileControl.putExtra("id",id);
            profileControl.putExtra("name",name);
            startActivity(profileControl);
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileControl=new Intent(getApplicationContext(), MassegeMain.class);
                profileControl.putExtra("id",id);
                profileControl.putExtra("name",name);
                startActivity(profileControl);
            }
        });

        setting.setOnClickListener(v -> {

        });

        logout.setOnClickListener(v -> {
            Intent loginPage=new Intent(commonProfile.this, Login_page.class);
            startActivity(loginPage);
        });
    }
}