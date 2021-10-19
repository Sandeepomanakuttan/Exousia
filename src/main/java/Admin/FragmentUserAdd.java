package Admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentUserAdd extends Fragment {
    View view;
    EditText edtName,edtDistrict,edtPanchayath,edtWardNo,edtHouseNo;
    String stringName,stringDistrict,stringPanchayath,stringWardNo,stringHouseNo,authority,authority_Place;
    Button btnSubmit;
    DatabaseReference RKTDRef= FirebaseDatabase.getInstance().getReference("User_Verification_Table");
    public FragmentUserAdd(String authority, String authority_Place) {
        this.authority=authority;
        this.authority_Place=authority_Place;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_user_add, container, false);
        edtName=view.findViewById(R.id.etdName);
        edtDistrict=view.findViewById(R.id.edtDistrict);
        edtPanchayath=view.findViewById(R.id.edtPanchayath);
        edtWardNo=view.findViewById(R.id.edtWardNo);
        edtHouseNo=view.findViewById(R.id.edtHouseNo);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        edtPanchayath.setText(authority_Place);
        btnSubmit.setOnClickListener(v -> {
            stringName=edtName.getText().toString();
            stringDistrict=edtDistrict.getText().toString();
            stringPanchayath=edtPanchayath.getText().toString();
            stringWardNo=edtWardNo.getText().toString();
            stringHouseNo=edtHouseNo.getText().toString();
            String stringuser="User";

            if (TextUtils.isEmpty(stringName)){
                Toast.makeText(getContext(), "Please Enter House Owner Name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringDistrict)){
                Toast.makeText(getContext(), "Please Enter District Name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringPanchayath)){
                Toast.makeText(getContext(), "Please Enter Panchayath Name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringWardNo)){
                Toast.makeText(getContext(), "Please Enter Ward No", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(stringHouseNo)){
                Toast.makeText(getContext(), "Please Enter House No", Toast.LENGTH_SHORT).show();
            }else{
                PersonDataCollection personDataCollection=new PersonDataCollection(stringName,stringDistrict,stringPanchayath,stringWardNo,stringHouseNo,stringuser);
                checktoInsert(personDataCollection);
            }

        });

        return view;
    }

    private void InsertUser(PersonDataCollection personDataCollection) {
        String key=RKTDRef.push().getKey();
        personDataCollection.setId(key);
        RKTDRef.child(personDataCollection.getHouse_OwnerName()).setValue(personDataCollection);
        Toast.makeText(getContext(), "succefully Inserted", Toast.LENGTH_SHORT).show();
    }

    private void checktoInsert(PersonDataCollection personDataCollection) {
        String name=personDataCollection.getHouse_OwnerName();
        RKTDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(name).exists()){
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    PersonDataCollection data = childSnapshot.getValue(PersonDataCollection.class);
                    if (data.getDistrict().equals(personDataCollection.getDistrict())
                    &&data.getPanchayath().equals(personDataCollection.getPanchayath())&&
                            data.getWardNo().equals(personDataCollection.getWardNo())&&data.getHouseNo().equals(personDataCollection.getHouseNo())){
                        Toast.makeText(getContext(), "Data already Exist", Toast.LENGTH_SHORT).show();

                }}} else {
                    InsertUser(personDataCollection);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

