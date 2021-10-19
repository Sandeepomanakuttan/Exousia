
package UserSection.Registraction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyOtp extends AppCompatActivity {
    EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;
    String verification_id,personName,userName,password;
    String strName,strAddress,strDob,strStatus,strUid,strFathername,strMatherName,Panchayathvalue,strRti,strAnual,Districtvalue,WardNo,HouseNo,strmob,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView txtMobileno=findViewById(R.id.txtMobile);
        txtMobileno.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));
        strmob=getIntent().getStringExtra("mobile");
        inputcode1=findViewById(R.id.InputCode1);
        inputcode2=findViewById(R.id.InputCode2);
        inputcode3=findViewById(R.id.InputCode3);
        inputcode4=findViewById(R.id.InputCode4);
        inputcode5=findViewById(R.id.InputCode5);
        inputcode6=findViewById(R.id.InputCode6);
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
        image = intent.getStringExtra("image");



        setupOTPinput();

        final ProgressBar progressBar=findViewById(R.id.progressbar);
        final Button buttonverification=findViewById(R.id.btnverify);

        verification_id=getIntent().getStringExtra("VerificationId");

        buttonverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (inputcode1.getText().toString().trim().isEmpty()
                        ||inputcode2.getText().toString().trim().isEmpty()
                        ||inputcode3.getText().toString().trim().isEmpty()
                        ||inputcode4.getText().toString().trim().isEmpty()
                        ||inputcode5.getText().toString().trim().isEmpty()
                        ||inputcode6.getText().toString().trim().isEmpty()){
                    Toast.makeText(verifyOtp.this,"Please Enter Valid code",Toast.LENGTH_SHORT).show();
                    return;
                }

                String code=inputcode1.getText().toString()
                        +inputcode2.getText().toString()+
                        inputcode3.getText().toString()+
                        inputcode4.getText().toString()+
                        inputcode5.getText().toString()+
                        inputcode6.getText().toString();

                if (verification_id != null){

                    progressBar.setVisibility(View.VISIBLE);
                    buttonverification.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthcredential=PhoneAuthProvider.getCredential(
                            verification_id,code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthcredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            buttonverification.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(getApplicationContext(), innerRegpage1.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("mobile",strmob);
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
                                finish();

                            }else{
                                Toast.makeText(verifyOtp.this,"the verfication code entered was invaliad",Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
                }
            }
        });

        findViewById(R.id.TxtResendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("mobile"), 60, TimeUnit.SECONDS, verifyOtp.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String newverificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                       verification_id=newverificationId;
                       Toast.makeText(verifyOtp.this,"OTP send",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void setupOTPinput(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
           public void afterTextChanged(Editable s) {
            }
        });

    }
}