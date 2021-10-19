package UserSection.Registraction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reformingkeralathroughdigitilization.ProfilePage;
import com.example.reformingkeralathroughdigitilization.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class GenerateOtpPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressBar progressBar;
    String phoneNo;
    String fullnumber,personName,userName,password;
    Uri myUri;
    Button btnGetOTP;
    EditText inputMobileNo;
    String strName,strAddress,strDob,strStatus,strUid,strFathername,strMatherName,Panchayathvalue,strRti,strAnual,Districtvalue,WardNo,HouseNo,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_otp_page);
        //checkConnection();

        inputMobileNo=findViewById(R.id.EtxtphoneNo);
        btnGetOTP=findViewById(R.id.btnGetOTP);
        progressBar=findViewById(R.id.progressbar);
        Intent intent=getIntent();
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
        image =intent.getStringExtra("image");



        mAuth=FirebaseAuth.getInstance();
        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               phoneNo = inputMobileNo.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNo)) {
                    Toast.makeText(GenerateOtpPage.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if(phoneNo.length() != 10) {

                        Toast.makeText(GenerateOtpPage.this, "Enter valid Mobile Number", Toast.LENGTH_SHORT).show();

                    }
                else {
                        progressBar.setVisibility(View.VISIBLE);
                        btnGetOTP.setVisibility(View.INVISIBLE);
                        String mobile = "+91" + phoneNo;
                        sendVerificationCode(mobile);
                    }



            }
        });
    }
    private void sendVerificationCode(String phoneNumber) {
        fullnumber=phoneNumber;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(GenerateOtpPage.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {


            progressBar.setVisibility(View.GONE);
            btnGetOTP.setVisibility(View.VISIBLE);

            //signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            progressBar.setVisibility(View.GONE);
            btnGetOTP.setVisibility(View.VISIBLE);
            Toast.makeText(GenerateOtpPage.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent( String verificationId, PhoneAuthProvider.ForceResendingToken token) {
            String otp=verificationId;
            progressBar.setVisibility(View.GONE);
            btnGetOTP.setVisibility(View.VISIBLE);
           Intent intent= new Intent(GenerateOtpPage.this, verifyOtp.class);
           intent.putExtra("mobile",inputMobileNo.getText().toString());
           intent.putExtra("VerificationId",verificationId);
           intent.putExtra("personName",personName);
           intent.putExtra("userName",userName);
           intent.putExtra("password",password);
            intent.putExtra("Name",strName);
            intent.putExtra("dob",strDob);
            intent.putExtra("uid",strUid);
            intent.putExtra("status",strStatus);
            intent.putExtra("Father",strFathername);
            intent.putExtra("mother",strMatherName);
            intent.putExtra("Address",strAddress);
            intent.putExtra("RITNo",strRti);
            intent.putExtra("anualIncome",strAnual);
            intent.putExtra("HouseOwner",personName);
            intent.putExtra("District",Districtvalue);
            intent.putExtra("panchayath",Panchayathvalue);
            intent.putExtra("HouseNo",HouseNo);
            intent.putExtra("WardNo",WardNo);
            intent.putExtra("image",image);
           startActivity(intent);

        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            Intent intent=new Intent(GenerateOtpPage.this, ProfilePage.class);
                            startActivity(intent);
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}


