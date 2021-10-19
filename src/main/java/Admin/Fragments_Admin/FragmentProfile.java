package Admin.Fragments_Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.reformingkeralathroughdigitilization.Login_page;
import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    String name,id;
    public FragmentProfile(String name,String id) {
        this.name=name;
        this.id=id;
    }

    TextView editName,editUsername,editPass,editPost;
    CircleImageView imgpro;
    DatabaseReference profileref;
    CardView logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup)inflater.inflate(R.layout.view_profile_tab_fragment,container,false);
        profileref= FirebaseDatabase.getInstance().getReference("LoginTable");
        imgpro=root.findViewById(R.id.img);
        editName=root.findViewById(R.id.editName);
        editUsername=root.findViewById(R.id.editUsername);
        editPass=root.findViewById(R.id.editpass);
        editPost=root.findViewById(R.id.editpost);
        logout=root.findViewById(R.id.logout);

        imgpro.setTranslationX(800);
        imgpro.setAlpha(0);
        imgpro.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editName.setTranslationX(800);
        editName.setAlpha(0);
        editName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editUsername.setTranslationX(800);
        editUsername.setAlpha(0);
        editUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        editPost.setTranslationX(800);
        editPost.setAlpha(0);
        editPost.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        getData();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login_page.class));

            }
        });

        return root;
    }

    private void getData() {
        Query query;
        query = profileref.orderByChild("name").equalTo(name);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    editName.setText(data.getName());
                    editUsername.setText(data.getUserName());
                    editPost.setText(data.getUser());
                    String link1 =data.getImageUri();
                    Picasso.get().load(link1).into(imgpro);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference profileimg= FirebaseDatabase.getInstance().getReference("Profile_Table");
        query=profileimg.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    // String link = snapshot.child("imageUri").getValue().toString();
                    String link1 =data.getImageUri();

                    Picasso.get().load(link1).into(imgpro);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}