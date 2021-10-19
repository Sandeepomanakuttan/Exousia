package UserSection.Registraction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Admin.PersonDataCollection;
import de.hdodenhof.circleimageview.CircleImageView;

public class Registration_form extends AppCompatActivity {
    Button Regbutton,btnuploadbutton,btnselectimg;
    CircleImageView profileimage;
    EditText Name,edtWardNo,edtHouseNo;
    Spinner spinnerDistrict,spinnerPanchayath;
    FirebaseDatabase varDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> spinnerDataList,spinnerPanchayathList;
    String Panchayathvalue,Districtvalue;
    ArrayAdapter<String> adapter,panAdaptor;
    Uri imageUri;
    Spinner spin;
    String District,panchayath;
    ValueEventListener listener;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);


        profileimage=findViewById(R.id.img1);
        Name=findViewById(R.id.etdName);
        spinnerDistrict=findViewById(R.id.spinnerDistrict);
        spinnerPanchayath=findViewById(R.id.spinnerPanchayath);
        edtWardNo=findViewById(R.id.edtWardNo);
        edtHouseNo=findViewById(R.id.edtHouseNo);
        varDatabase = FirebaseDatabase.getInstance();
        Regbutton=findViewById(R.id.Regbutton);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(Registration_form.this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);

        spinnerPanchayathList = new ArrayList<>();
        panAdaptor = new ArrayAdapter<>(Registration_form.this, android.R.layout.simple_spinner_dropdown_item, spinnerPanchayathList);
        panAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPanchayath.setAdapter(panAdaptor);

        retrieveData();





        Regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String personName=Name.getText().toString();
                String WardNo=edtWardNo.getText().toString();
                String HouseNo=edtHouseNo.getText().toString();



                if (TextUtils.isEmpty(personName)) {
                    Toast.makeText(Registration_form.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }

               else if(TextUtils.isEmpty(WardNo)) {
                    Toast.makeText(Registration_form.this, "Enter Your WardNo", Toast.LENGTH_LONG).show();
                }
               else if (TextUtils.isEmpty(HouseNo)){
                    Toast.makeText(Registration_form.this, "Enter Your HouseNo", Toast.LENGTH_LONG).show();
                }
               else {

                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("User_Verification_Table");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.child(personName).exists()){
                            for (DataSnapshot childSnapshot : snapshot.getChildren()){
                                PersonDataCollection data = childSnapshot.getValue(PersonDataCollection.class);
                                if (data.getDistrict().equals(Districtvalue)
                                        ||data.getPanchayath().equals(Panchayathvalue)||
                                        data.getWardNo().equals(WardNo)||data.getHouseNo().equalsIgnoreCase(HouseNo)){
//
                                    Toast.makeText(getApplicationContext(), "Succefully Verified", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),PersonDetaills.class);
                                    intent.putExtra("HouseOwner",personName);
                                    intent.putExtra("District",Districtvalue);
                                    intent.putExtra("panchayath",Panchayathvalue);
                                    intent.putExtra("HouseNo",HouseNo);
                                    intent.putExtra("WardNo",WardNo);
                                    startActivity(intent);
                                    finish();
                                    break;

                                }else {
                                    Toast.makeText(Registration_form.this, "Data Does not match", Toast.LENGTH_LONG).show();
                                    Name.setText("");
                                    edtWardNo.setText("");
                                    edtHouseNo.setText("");
                                }
                                }

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

               }
            }
        });


    }

    public void retrieveData(){
        Toast.makeText(this, "fetch", Toast.LENGTH_SHORT).show();
        databaseReference.child("User_Verification_Table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spinnerDataList.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String dis = item.child("district").getValue(String.class);
                    spinnerDataList.add(dis);
                }
                adapter.notifyDataSetChanged();
                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                        spinnerDistrict.setSelection(i);
                        Districtvalue=adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(Registration_form.this, Districtvalue, Toast.LENGTH_SHORT).show();
                        getPanchayat(Districtvalue);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
                 }

    private void getPanchayat(String value) {
        databaseReference.child("User_Verification_Table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spinnerPanchayathList.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String Panchayath = item.child("panchayath").getValue(String.class);
                    spinnerPanchayathList.add(Panchayath);
                }
                panAdaptor.notifyDataSetChanged();
                spinnerPanchayath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                        spinnerDistrict.setSelection(i);
                        Panchayathvalue=adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(Registration_form.this, Panchayathvalue, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}


