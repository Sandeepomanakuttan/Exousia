package Admin.Fragments_Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Admin.datacollectionClass.ProfileData;

public class FragmentStaffView extends Fragment {

    View view;
    RecyclerView recyclerView;
    view_Adaptor adapter;
    String Status,authority,authority_Place;
    ArrayList<ProfileData> collections;


    public FragmentStaffView(String authority, String authority_Place,String Status) {
        this.Status=Status;
        this.authority=authority;
        this.authority_Place=authority_Place;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_staff_view, container, false);
        recyclerView=view.findViewById(R.id.recycleStaffView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        collections=new ArrayList<>();
        if (Status.equals("officer")) {
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("LoginTable");
            reference.orderByChild("athority_Type").equalTo(authority).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    collections.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ProfileData profileData=dataSnapshot.getValue(ProfileData.class);
                        Toast.makeText(getContext(), authority, Toast.LENGTH_SHORT).show();
                    if (profileData.getUser().equals("officer")){
                        collections.add(profileData);

                }}adapter.notifyDataSetChanged();}

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        adapter = new view_Adaptor(getContext(), collections);
        recyclerView.setAdapter(adapter);
        return view;
    }
}