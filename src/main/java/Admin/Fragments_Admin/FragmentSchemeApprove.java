package Admin.Fragments_Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Admin.RecycleAdaptor.RequestSchemeViewAdaptor;
import UserSection.Collectionclass.PersonSchemeCollection;


public class FragmentSchemeApprove extends Fragment {
    View view;
    RecyclerView recyclerView;
    String id;
    String authority,authority_Place,department;
    ArrayList<UserSection.Collectionclass.PersonSchemeCollection> PersonSchemeCollection;
    RequestSchemeViewAdaptor viewAdaptor;
    public FragmentSchemeApprove(String authority, String authority_Place, String department) {
        this.authority=authority;
        this.authority_Place=authority_Place;
        this.department=department;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_scheme_approve, container, false);

        recyclerView=view.findViewById(R.id.recycleApprovescheme);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        PersonSchemeCollection=new ArrayList<>();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");

        Query query=reference.orderByChild("authority_type").equalTo(authority);
        if (department.equals("Admin")){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PersonSchemeCollection.clear();
                    UserSection.Collectionclass.PersonSchemeCollection collection = dataSnapshot.getValue(PersonSchemeCollection.class);
                    if (collection.getAuthority_Place().equals(authority_Place)) {
                    if (collection.getStatus().equals("Approve")){
                        PersonSchemeCollection.add(collection);

                    }}}
                viewAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });} else{
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonSchemeCollection.clear();
                        UserSection.Collectionclass.PersonSchemeCollection collection = dataSnapshot.getValue(PersonSchemeCollection.class);
                        if (collection.getStatus().equals("Approve") && collection.getDepartment().equals(department)){
                            PersonSchemeCollection.add(collection);

                        }}
                    viewAdaptor.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });


        }
        viewAdaptor = new RequestSchemeViewAdaptor(getContext(), PersonSchemeCollection);
        recyclerView.setAdapter(viewAdaptor);
        return view;
    }
}