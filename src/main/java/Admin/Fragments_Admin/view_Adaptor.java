package Admin.Fragments_Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class view_Adaptor extends RecyclerView.Adapter<view_Adaptor.Viewholder>  {

    ProfileData collection;
    ArrayList<ProfileData> array;
    Context context;
    public view_Adaptor(Context context, ArrayList<ProfileData> collections){
        this.context=context;
        array=collections;
    }

    @NonNull
    @Override
    public view_Adaptor.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new view_Adaptor.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull view_Adaptor.Viewholder holder, int position) {
        collection=array.get(position);
        String id=collection.getId();
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
        holder.txtname.setText(collection.getName());
        holder.txtWard.setText(collection.getUser());


    }

    @Override
    public int getItemCount() {
        return array.size();
    }
    class Viewholder extends RecyclerView.ViewHolder {
        CircleImageView pic;
        TextView txtname,txtWard,txtHouseNo,txtType;
        ImageView image;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            txtWard = itemView.findViewById(R.id.txtWard);
            txtHouseNo = itemView.findViewById(R.id.txtHouseNo);
            txtType = itemView.findViewById(R.id.txtType);
            pic = itemView.findViewById(R.id.pic);
            image= itemView.findViewById(R.id.cancel);
        }
    }



}
