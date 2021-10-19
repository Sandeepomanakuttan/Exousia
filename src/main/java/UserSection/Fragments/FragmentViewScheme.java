package UserSection.Fragments;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Admin.datacollectionClass.SchemedataCollection;
import UserSection.Collectionclass.PersonSchemeCollection;
import ViewAdaptor.AcceptSchemeView;
import ViewAdaptor.SchemeViewAdaptor;


public class FragmentViewScheme extends Fragment {

    View view;
    RecyclerView recyclerView;
    String id;
    String status,authority,authority_Place;
    ArrayList<SchemedataCollection> SchemeArrayList;
    ArrayList<PersonSchemeCollection> personSchemeCollections;
    SchemeViewAdaptor viewAdaptor;
    AcceptSchemeView Adaptor;


    public FragmentViewScheme(String id, String status, String authority, String authority_Place) {
        this.id=id;
        this.status=status;
        this.authority=authority;
        this.authority_Place=authority_Place;


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
        personSchemeCollections=new ArrayList<>();
        if (status.equals("Scheme")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scheme_Table");

            Query query=reference.orderByChild("authority").equalTo(authority);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    SchemeArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SchemedataCollection collection = dataSnapshot.getValue(SchemedataCollection.class);
                        String s=collection.getAuthority_Place();
                            if (collection.getAuthority_Place().equals(authority_Place)){
                            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                        SchemeArrayList.add(collection);

                    }}
                    viewAdaptor.notifyDataSetChanged();
                }



                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });
            viewAdaptor = new SchemeViewAdaptor(getContext(), SchemeArrayList,id);
            recyclerView.setAdapter(viewAdaptor);

        }
        else if (status.equals("Accept")){

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");

            Query query=reference1.orderByChild("authority_type").equalTo(authority);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    personSchemeCollections.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonSchemeCollection collection = dataSnapshot.getValue(PersonSchemeCollection.class);
                        String s=collection.getAuthority_Place();
                        if (collection.getPerson_id().equals(id)){
                        if (collection.getStatus().equals("Approve")){
                            if (collection.getAuthority_Place().equals(authority_Place)){
                                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                personSchemeCollections.add(collection);


                            }}}}
                    Adaptor.notifyDataSetChanged();
                }



                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });
            Adaptor = new AcceptSchemeView(getContext(), personSchemeCollections,id);
            recyclerView.setAdapter(Adaptor);
        }

        else if (status.equals("Reject")){

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");

            Query query=reference1.orderByChild("authority_type").equalTo(authority);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    personSchemeCollections.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonSchemeCollection collection = dataSnapshot.getValue(PersonSchemeCollection.class);
                        String s=collection.getAuthority_Place();
                        if (collection.getPerson_id().equals(id)){
                            if (collection.getStatus().equals("Reject")){
                                Toast.makeText(getContext(), collection.getStatus(), Toast.LENGTH_SHORT).show();
                                if (collection.getAuthority_Place().equals(authority_Place)){
                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                    personSchemeCollections.add(collection);


                                }}}}
                    Adaptor.notifyDataSetChanged();
                }



                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });
            Adaptor = new AcceptSchemeView(getContext(), personSchemeCollections,id);
            recyclerView.setAdapter(Adaptor);
        }
        else if (status.equals("Pending")){

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");

            Query query=reference1.orderByChild("authority_type").equalTo(authority);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    personSchemeCollections.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PersonSchemeCollection collection = dataSnapshot.getValue(PersonSchemeCollection.class);
                        String s=collection.getAuthority_Place();
                        if (collection.getPerson_id().equals(id)){
                            if (collection.getStatus().equals("Request")){
                                Toast.makeText(getContext(), collection.getStatus(), Toast.LENGTH_SHORT).show();
                                if (collection.getAuthority_Place().equals(authority_Place)){
                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                    personSchemeCollections.add(collection);


                                }}}}
                    Adaptor.notifyDataSetChanged();
                }



                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });
            Adaptor = new AcceptSchemeView(getContext(), personSchemeCollections,id);
            recyclerView.setAdapter(Adaptor);
        }
        else {}


        return view;

    }
}