package Admin.Fragments_Admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import Admin.datacollectionClass.ProfileData;


public class FragmentStaffAdd extends Fragment {
Spinner spinner;
EditText txt_user,etdpass,edtName;
Button btnsubmit;
ProgressBar prograss;
View view;
    DatabaseReference profileref = FirebaseDatabase.getInstance().getReference("LoginTable");
    String str_wrkofficer,str_user,str_password,str_nameofficer,Authority_place,type;
    public FragmentStaffAdd(String Authority_place, String type) {

        this.Authority_place=Authority_place;
        this.type=type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_staff_add, container, false);
        btnsubmit=view.findViewById(R.id.btnsubmit);
        spinner=view.findViewById(R.id.spinner);
        txt_user=view.findViewById(R.id.txt_user);
        etdpass=view.findViewById(R.id.etdpass);
        edtName=view.findViewById(R.id.edtName);
        prograss=view.findViewById(R.id.prograss);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_dep = spinner.getSelectedItem().toString();
                str_nameofficer=edtName.getText().toString();
                str_user=txt_user.getText().toString();
                str_password=etdpass.getText().toString();
                if(Objects.equals(str_dep, "select category")){
                    Toast.makeText(getContext(),"pls select category",Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(str_user)){
                    Toast.makeText(getContext(), "Enter user Name", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(str_password)){
                    Toast.makeText(getContext(), " Create password", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(str_nameofficer)){
                    Toast.makeText(getContext(),"enter Officer Name",Toast.LENGTH_LONG).show();
                }
                else{

                    ProfileData Data=new ProfileData();
                    Data.setUser("officer");
                    Data.setAthority_Type(type);
                    Data.setDepartment(str_dep);
                    Data.setUserName(str_user);
                    Data.setPassword(str_password);
                    Data.setName(str_nameofficer);
                    Data.setAuthority_Place(Authority_place);
                    prograss.setVisibility(View.VISIBLE);
                    checkdata(Data);
                }


            }


        });

        return view;
    }

    private void InsertLoginData(ProfileData data) {
        String key=profileref.push().getKey();
        data.setId(key);
        profileref.child(data.getName()).setValue(data);
        prograss.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
    }

    public void checkdata(ProfileData Data) {
        profileref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData dataClass = childSnapshot.getValue(ProfileData.class);
                    if (dataClass.getUserName().equalsIgnoreCase(Data.getUserName())) {
                        Toast.makeText(getContext(), "username already exist", Toast.LENGTH_SHORT).show();
                    } else if (dataClass.getPassword().equalsIgnoreCase(Data.getPassword())) {
                        Toast.makeText(getContext(), "Password already exist", Toast.LENGTH_SHORT).show();
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
}