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

import Admin.datacollectionClass.SchemedataCollection;
import ViewAdaptor.SchemeViewAdaptor;


public class FragmentSchemeView extends Fragment {

    View view;
    RecyclerView recyclerView;
    String id;
    String status,authority,authority_Place,department;
    ArrayList<SchemedataCollection> SchemeArrayList;
    SchemeViewAdaptor viewAdaptor;
    public FragmentSchemeView(String authority, String authority_Place, String department) {
        this.authority=authority;
        this.authority_Place=authority_Place;
        this.department=department;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_scheme_view, container, false);
        recyclerView=view.findViewById(R.id.recycleViewscheme);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SchemeArrayList=new ArrayList<>();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scheme_Table");
        SchemeArrayList.clear();
        Query query=reference.orderByChild("authority").equalTo(authority);
        if (department.equals("Admin")){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SchemedataCollection collection = dataSnapshot.getValue(SchemedataCollection.class);
                    if (collection.getAuthority_Place().equals(authority_Place)){
                        SchemeArrayList.add(collection);

                }}
                viewAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
        }else {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SchemedataCollection collection = dataSnapshot.getValue(SchemedataCollection.class);
                        if (collection.getStrcategory().equals(department)&&collection.getAuthority_Place().equals(authority_Place)){
                            SchemeArrayList.add(collection);

                        }}
                    viewAdaptor.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });
        }
        viewAdaptor = new SchemeViewAdaptor(getContext(), SchemeArrayList,id);
        recyclerView.setAdapter(viewAdaptor);

        return view;

    }
}