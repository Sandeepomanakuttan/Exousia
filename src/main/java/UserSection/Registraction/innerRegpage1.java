package UserSection.Registraction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reformingkeralathroughdigitilization.Login_page;
import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Admin.datacollectionClass.ProfileData;
import Admin.datacollectionClass.UserDataCollection;

public class innerRegpage1 extends AppCompatActivity {

    EditText edtpassword,edtCmpPassword,edtPhone;
    String strpassword,strCmppassword,strPhone;
    Button submit;
    String strName,strAddress,strDob,strStatus,strUid,strFathername,strMatherName,Panchayathvalue,strRti,strAnual,Districtvalue,WardNo,HouseNo,strmob,personName;
    DatabaseReference RKTDRef= FirebaseDatabase.getInstance().getReference("User_Deails_Table");
    DatabaseReference RKTD= FirebaseDatabase.getInstance().getReference("LoginTable");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_regpage1);

        Intent intent=getIntent();
        strmob=intent.getStringExtra("mobile");
        personName=intent.getStringExtra("HouseOwner");
        Districtvalue=intent.getStringExtra("District");
        WardNo=intent.getStringExtra("WardNo");
        Panchayathvalue=intent.getStringExtra("panchayath");
        HouseNo=intent.getStringExtra("HouseNo");
        strName=intent.getStringExtra("Name");
        strDob=intent.getStringExtra("dob");
        strUid=intent.getStringExtra("uid");
        strStatus=intent.getStringExtra("status");
        strFathername=intent.getStringExtra("Father");
        strMatherName=intent.getStringExtra("mother");
        strAddress=intent.getStringExtra("Address");
        strRti=intent.getStringExtra("RITNo");
        strAnual=intent.getStringExtra("anualIncome");
//        String myUri = intent.getStringExtra("image");
//        Uri uri=Uri.parse(myUri);


                edtpassword=findViewById(R.id.edtPass);
        edtCmpPassword=findViewById(R.id.edtcmpPass);
        edtPhone=findViewById(R.id.edtPh);
        submit=findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strpassword=edtpassword.getText().toString().trim();
                strCmppassword=edtCmpPassword.getText().toString().trim();
                strPhone=edtPhone.getText().toString();
                if (TextUtils.isEmpty(strPhone)){
                    Toast.makeText(getApplicationContext(), "Enter your Phone No", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(strpassword)){
                    Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(strCmppassword)){
                    Toast.makeText(getApplicationContext(), "Enter your Confirm Password", Toast.LENGTH_SHORT).show();
                }else if (!strpassword.equals(strCmppassword)){
                    Toast.makeText(getApplicationContext(), "Password are Not Equals", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), Login_page.class);
                    ProfileData data=new ProfileData();
                    UserDataCollection collection=new UserDataCollection(personName,strName,Districtvalue,Panchayathvalue,WardNo,HouseNo,strDob,strUid,strStatus,strFathername,strMatherName,strAddress,strRti,strAnual,strmob);
                    data.setAuthority_Place(Panchayathvalue);
                    data.setPassword(strpassword);
                    data.setName(personName);
                    data.setUser("User");
                    data.setUserName(strmob);
                    check(collection,data);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void check(UserDataCollection collection, ProfileData data) {
        String name=collection.getHouseOwnerName();
        RKTDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(name).exists()){
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        UserDataCollection data = childSnapshot.getValue(UserDataCollection.class);
                        if (data.getDistrict().equals(collection.getDistrict())
                                ||data.getPanchayath().equals(collection.getPanchayath())||
                                data.getWardNo().equals(collection.getWardNo())||data.getHouseNo().equals(collection.getHouseNo())){
                            Toast.makeText(getApplicationContext(), "Data already Exist", Toast.LENGTH_SHORT).show();

                        }}} else {
                    insertData(collection,data);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void insertData(UserDataCollection collection, ProfileData data) {
        String key=RKTD.push().getKey();
        data.setId(key);
        RKTD.child(data.getUserName()).setValue(data);

        collection.setId(key);
        RKTDRef.child(collection.getMobile()).setValue(collection);

        Toast.makeText(getApplicationContext(), "succefully Inserted", Toast.LENGTH_SHORT).show();
    }
}