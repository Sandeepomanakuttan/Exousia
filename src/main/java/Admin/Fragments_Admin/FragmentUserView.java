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

import Admin.PersonDataCollection;

public class FragmentUserView extends Fragment {

    View view;
    RecyclerView recyclerView;
    String authority,authority_Place;
    ArrayList<PersonDataCollection>collections;
    UserAdaptor userAdaptor;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User_Verification_Table");
    public FragmentUserView(String authority, String authority_Place) {

        this.authority=authority;
        this.authority_Place=authority_Place;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_user_view, container, false);
        recyclerView=view.findViewById(R.id.recycleUserView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        collections=new ArrayList<>();
        Toast.makeText(getContext(), authority_Place, Toast.LENGTH_SHORT).show();
        reference.orderByChild("panchayath").equalTo(authority_Place).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    collections.clear();
                    PersonDataCollection personDataCollection=dataSnapshot.getValue(PersonDataCollection.class);
                    if (personDataCollection.getPanchayath().equals(authority_Place)){
                        collections.add(personDataCollection);
                    }
                }userAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userAdaptor = new UserAdaptor(getContext(), collections);
        recyclerView.setAdapter(userAdaptor);
        return view;
    }
}