package com.example.reformingkeralathroughdigitilization;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Admin.datacollectionClass.ProfileData;
import Admin.mainHome;
import Employee.EmployeeMainPage;
import MainAdmin.commonProfile;
import UserSection.MainPageUser;
import UserSection.Registraction.Registration_form;

public class Login_page extends AppCompatActivity {
EditText userName,password;
Button login;
TextView createNewAccount;
String struserName,strPassword;
public ProgressBar progressBar;
DatabaseReference RKTDRef;


    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        RKTDRef= FirebaseDatabase.getInstance().getReference("LoginTable");
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        userName=findViewById(R.id.edittxtName);
        password=findViewById(R.id.edittxtPassword);
        createNewAccount=findViewById(R.id.txtcreate);
        progressBar=findViewById(R.id.progressBar1);
        login=findViewById(R.id.button);



        login.setOnClickListener(v -> {

            struserName=userName.getText().toString();
            strPassword=password.getText().toString();

            if (TextUtils.isEmpty(struserName)){
                Toast.makeText(Login_page.this,"Please Enter user Name",Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(strPassword)){
                Toast.makeText(Login_page.this,"Please Enter user password",Toast.LENGTH_SHORT).show();

            }
            else {
                if (awesomeValidation.validate()) {
                    Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                    ProfileData dataClass = new ProfileData();
                    dataClass.setUserName(struserName);
                    dataClass.setPassword(strPassword);
                    Query query;
                    query = RKTDRef;
//                    .orderByChild("userName").equalTo(dataClass.getUserName())
                    query.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                ProfileData data = childSnapshot.getValue(ProfileData.class);
                                if (data.getUserName().equals(dataClass.getUserName())){
                                    if(dataClass.getPassword().equals(data.getPassword())) {
                                        if (data.getUser().equalsIgnoreCase("General")) {
                                            Toast.makeText(Login_page.this, "welcome", Toast.LENGTH_SHORT).show();
                                            String id = data.getId();
                                            String name=data.getName();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Intent MainPage = new Intent(Login_page.this, commonProfile.class);
                                            MainPage.putExtra("id", id);
                                            MainPage.putExtra("name", name);
                                            startActivity(MainPage);
                                            finish();
                                            break;

                                        } else if (data.getUser().equalsIgnoreCase("Admin")) {
                                            String id = data.getId();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Intent MainPage = new Intent(Login_page.this, mainHome.class);
                                            MainPage.putExtra("id", id);
                                            String name=data.getName();
                                            MainPage.putExtra("name", name);
                                            String authority_Place=data.getAuthority_Place();
                                            String authority=data.getAthority_Type();
                                            MainPage.putExtra("authority_Place",authority_Place);
                                            MainPage.putExtra("authority",authority);
                                            startActivity(MainPage);
                                            finish();
                                            break;
                                        }else if (data.getUser().equalsIgnoreCase("officer")) {
                                            String id = data.getId();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Intent EmployeePage = new Intent(Login_page.this, EmployeeMainPage.class);
                                            EmployeePage.putExtra("id", id);
                                            String name=data.getName();
                                            EmployeePage.putExtra("name", name);
                                            String authority_Place=data.getAuthority_Place();
                                            String Depart=data.getDepartment();
                                            String authority=data.getAthority_Type();
                                            EmployeePage.putExtra("authority_Place",authority_Place);
                                            EmployeePage.putExtra("Department",Depart);
                                            EmployeePage.putExtra("authority",authority);
                                            startActivity(EmployeePage);
                                            finish();
                                            break;
                                        }else if (data.getUser().equalsIgnoreCase("User")) {
                                            String id = data.getId();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Intent UserPage = new Intent(Login_page.this, MainPageUser.class);
                                            UserPage.putExtra("id", id);
                                            String name=data.getName();
                                            UserPage.putExtra("name", name);
                                            String authority_Place=data.getAuthority_Place();
                                            UserPage.putExtra("authority_Place",authority_Place);
                                            startActivity(UserPage);
                                            finish();
                                            break;
                                        } else {
                                            Toast.makeText(Login_page.this, "invalid user", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(Login_page.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Login_page.this, "Invalid UserName", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Login_page.this, "Invalid UserName", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        createNewAccount.setOnClickListener(v -> {
            Intent registrationPage=new Intent(Login_page.this, Registration_form.class);
            startActivity(registrationPage);
            finish();
        });

    }
}
