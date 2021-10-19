
package MainAdmin;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.reformingkeralathroughdigitilization.LoginDataClass;
import com.example.reformingkeralathroughdigitilization.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import Admin.datacollectionClass.ProfileData;

public class myAdapterclass extends FirebaseRecyclerAdapter <ProfileData,myAdapterclass.myViewholder>{
String Authority,id;
LoginDataClass data;
    public myAdapterclass(@NonNull FirebaseRecyclerOptions<ProfileData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, int position, @NonNull ProfileData model) {
        DatabaseReference Profilereference= FirebaseDatabase.getInstance().getReference("Profile_Table");
        String id= model.getId();
        Query query=Profilereference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    String link1 =data.getImageUri();

                    Picasso.get().load(link1).into(holder.img1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.name.setText(model.getName());
            holder.username.setText(model.getUserName());
            holder.password.setText(model.getPassword());
            holder.authority.setText(model.getAuthority_Place());
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.authorityview,parent,false);
        return new myViewholder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    class myViewholder extends ViewHolder{

        ImageView img1;
        EditText editAuthority;
        TextView name,username,password,authority;
        Button update;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            img1= itemView.findViewById(R.id.img1);
            name= itemView.findViewById(R.id.txtName);
            username= itemView.findViewById(R.id.txtusername);
            password= itemView.findViewById(R.id.txtPassword);
            authority= itemView.findViewById(R.id.txtAuthorityName);
            update= itemView.findViewById(R.id.btnupdate);
            editAuthority= itemView.findViewById(R.id.AuthorityName);
            Authority=editAuthority.getText().toString();
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(Authority)){
                        editAuthority.setVisibility(View.VISIBLE);
                        authority.setVisibility(View.GONE);
                        editAuthority.requestFocus();
                    }
                    else{
                        UpdateAuthorityPlace(Authority);
                    }
                }
            });

        }

    }

    private void UpdateAuthorityPlace(String authority) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("LoginTable");
        HashMap update=new HashMap();
        update.put("AuthorityPlace",authority);
        reference.child(id).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}