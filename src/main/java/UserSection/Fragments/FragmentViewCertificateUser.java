package UserSection.Fragments;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Admin.datacollectionClass.SchemedataCollection;
import ViewAdaptor.SchemeViewAdaptor;


public class FragmentViewCertificateUser extends Fragment {

    View view;
    RecyclerView recyclerView;

    String id;
    String status,authority;
    DatabaseReference reference;
    private ArrayList<SchemedataCollection> SchemeArrayList;
    SchemeViewAdaptor viewAdaptor;

    public FragmentViewCertificateUser(String id, String certificate, String authority) {
        this.id=id;
        status=certificate;
        this.authority=authority;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_view_certificate_user, container, false);
        recyclerView=view.findViewById(R.id.recycleViewcertificate);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SchemeArrayList=new ArrayList<>();
        if (status.equals("Certificate")){
            reference=FirebaseDatabase.getInstance().getReference("Scheme_Table");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SchemedataCollection collection=dataSnapshot.getValue(SchemedataCollection.class);

                        SchemeArrayList.add(collection);
                        viewAdaptor=new SchemeViewAdaptor(getContext(),SchemeArrayList, id);
                        recyclerView.setAdapter(viewAdaptor);
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }else {
            reference = FirebaseDatabase.getInstance().getReference("AgreeScheme_Table");
            Query query=reference.orderByChild("Person_id").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SchemedataCollection collection = dataSnapshot.getValue(SchemedataCollection.class);
                        if (collection.getStrStatus().equals("Accept")) {
                            SchemeArrayList.add(collection);

                        }else if (collection.getStrStatus().equals("Pending")){
                            SchemeArrayList.add(collection);

                        }else {
                            SchemeArrayList.add(collection);

                        }
                    }
                    viewAdaptor.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            viewAdaptor=new SchemeViewAdaptor(getContext(),SchemeArrayList, id);
            recyclerView.setAdapter(viewAdaptor);


        }
        return view;
    }
}