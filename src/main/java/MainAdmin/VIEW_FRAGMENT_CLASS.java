package MainAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class VIEW_FRAGMENT_CLASS extends Fragment {

    String id,name;

    public VIEW_FRAGMENT_CLASS(String id,String name) {
        this.id=id;
        this.name=name;
    }

    TextView editName,editUsername,editPost;
    CircleImageView imgpro;
    DatabaseReference profileref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup)inflater.inflate(R.layout.view_profile_tab_fragment,container,false);
        profileref= FirebaseDatabase.getInstance().getReference("LoginTable");
        imgpro=root.findViewById(R.id.img);
        editName=root.findViewById(R.id.editName);
        editUsername=root.findViewById(R.id.editUsername);
        editPost=root.findViewById(R.id.editpost);

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

        return root;
    }

    private void getData() {
        Query query;
        query = profileref.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                       editName.setText(data.getName());
                       editUsername.setText(data.getUserName());
                       editPost.setText(data.getUser());
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
                    String link1 =data.getImageUri().toString();

                    Picasso.get().load(link1).into(imgpro);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
