package UserSection.Registraction;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reformingkeralathroughdigitilization.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonDetaills extends AppCompatActivity {

    EditText edtName,etdAddress,etdDob,edtUid,etdFathername,edtPhoneNo,etdRIT,etdMatherName,etdAnual;
    RadioButton single,maried,radioButton;
    Button Regbutton;
    CircleImageView profileimage;
    Uri imageUri;
    String Result;
    String strName,strAddress,strDob,strStatus,strUid,strFathername,strMatherName,Panchayathvalue,strRti,strAnual,personName,Districtvalue,WardNo,HouseNo;
    RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detaills);

        Intent intent=getIntent();
        personName=intent.getStringExtra("HouseOwner");
        Districtvalue=intent.getStringExtra("District");
        WardNo=intent.getStringExtra("WardNo");
        Panchayathvalue=intent.getStringExtra("panchayath");
        HouseNo=intent.getStringExtra("HouseNo");
        profileimage=findViewById(R.id.img);
        edtName=findViewById(R.id.edtName);
        etdAddress=findViewById(R.id.etdAddress);
        etdDob=findViewById(R.id.etdDob);
        group=findViewById(R.id.etdMarried);
        edtUid=findViewById(R.id.etdAdhaar);
        etdFathername=findViewById(R.id.etdFather);
        etdMatherName=findViewById(R.id.etdMatherName);
        etdRIT=findViewById(R.id.etdRIT);
        etdAnual=findViewById(R.id.etdAnual);
        Regbutton=findViewById(R.id.Regbutton);

        Regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName=edtName.getText().toString();
                strDob=etdDob.getText().toString();
                strUid=edtUid.getText().toString();
                strAddress=etdAddress.getText().toString();
                strFathername=etdFathername.getText().toString();
                strMatherName=etdMatherName.getText().toString();
                strRti=etdRIT.getText().toString();
                strAnual=etdRIT.getText().toString();

//                if(imageUri==null){
//                   Toast.makeText(getApplicationContext(), "select Your image", Toast.LENGTH_SHORT).show();
//               }
                if (TextUtils.isEmpty(strName)){
                    Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(strDob)){
                    Toast.makeText(getApplicationContext(), "Enter your Date of Birth", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(strUid)){
                    Toast.makeText(getApplicationContext(), "Enter your Adhaar UID", Toast.LENGTH_SHORT).show();

                }

                else if (TextUtils.isEmpty(strFathername)){
                    Toast.makeText(getApplicationContext(), "Enter your Father Name", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(strMatherName)){
                    Toast.makeText(getApplicationContext(), "Enter your Mather Name", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(strAddress)){
                    Toast.makeText(getApplicationContext(), "Enter your Address", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(strRti)){
                    Toast.makeText(getApplicationContext(), "Enter your RIT Number", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(strAnual)){
                    Toast.makeText(getApplicationContext(), "Enter your Anual income as per Rit", Toast.LENGTH_SHORT).show();

                }
                else {

                   if (strUid!=""){
                        boolean res=Verhoeff.validateVerhoeff(strUid);
                        Result=String.valueOf(res);
                        if (Result=="false"){
                            Toast.makeText(getApplicationContext(), "Enter Valid Aadhar Number", Toast.LENGTH_SHORT).show();
                        }
                    else {
                       int radioId = group.getCheckedRadioButtonId();
                       radioButton = findViewById(radioId);
                       Intent intent = new Intent(PersonDetaills.this, GenerateOtpPage.class);
                       intent.putExtra("image", imageUri);
                       intent.putExtra("Name", strName);
                       intent.putExtra("dob", strDob);
                       intent.putExtra("uid", strUid);
                       intent.putExtra("status", radioButton.getText().toString());
                       intent.putExtra("Father", strFathername);
                       intent.putExtra("mother", strMatherName);
                       intent.putExtra("Address", strAddress);
                       intent.putExtra("RITNo", strRti);
                       intent.putExtra("anualIncome", strAnual);
                       intent.putExtra("HouseOwner", personName);
                       intent.putExtra("District", Districtvalue);
                       intent.putExtra("panchayath", Panchayathvalue);
                       intent.putExtra("HouseNo", HouseNo);
                       intent.putExtra("WardNo", WardNo);
                       startActivity(intent);
                       finish();

                   }}
                     }
                      }
        });

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }


    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data != null){
            imageUri = data.getData();
            profileimage.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri muri) {

        ContentResolver contentResolver;
        contentResolver= getApplicationContext().getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(muri));
    }

    public void cheked(View view) {

    }
}
