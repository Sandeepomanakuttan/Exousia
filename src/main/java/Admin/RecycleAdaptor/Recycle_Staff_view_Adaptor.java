package Admin.RecycleAdaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class Recycle_Staff_view_Adaptor extends FirebaseRecyclerAdapter<ProfileData,Recycle_Staff_view_Adaptor.myviewholder> {

    String authority,authority_Place;

    public Recycle_Staff_view_Adaptor(@NonNull FirebaseRecyclerOptions<ProfileData> options) {
        super(options);
        this.authority_Place=authority_Place;
        this.authority=authority;
    }

    @Override
    protected void onBindViewHolder(@NonNull Recycle_Staff_view_Adaptor.myviewholder holder, int position, @NonNull ProfileData model) {
            String id=model.getId();
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Profile_Table");
            Query query =reference.orderByChild("id").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        ProfileData data = childSnapshot.getValue(ProfileData.class);
                        // String link = snapshot.child("imageUri").getValue().toString();
                        String link1 =data.getImageUri();
                        Picasso.get().load(link1).into(holder.pic);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.txtname.setText(model.getName());
            holder.txtWard.setText(model.getUser());



    }

    @NonNull
    @Override
    public Recycle_Staff_view_Adaptor.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new Recycle_Staff_view_Adaptor.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView pic;
        ImageView cancel;
        TextView txtname,txtWard,txtHouseNo,txtType;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.txtname);
            txtWard=itemView.findViewById(R.id.txtWard);
            txtHouseNo=itemView.findViewById(R.id.txtHouseNo);
            pic=itemView.findViewById(R.id.pic);
            txtType=itemView.findViewById(R.id.txtType);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
}

