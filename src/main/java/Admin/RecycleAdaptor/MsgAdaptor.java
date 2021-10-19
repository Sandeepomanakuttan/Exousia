package Admin.RecycleAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Admin.Fragments_Admin.FragmentChat;
import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class MsgAdaptor extends RecyclerView.Adapter<MsgAdaptor.Viewholder> {

    Context context;
    ArrayList<ProfileData> personArrayList;
    String currentid,id;
    String link;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profile_Table");

    public MsgAdaptor(Context context, ArrayList<ProfileData> personArrayList, String id) {
        this.context=context;
        this.personArrayList=personArrayList;
        this.id=id;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MsgAdaptor.Viewholder holder, int position) {
        ProfileData profileData=personArrayList.get(position);
        currentid=profileData.getId();
            Query query = reference.orderByChild("id").equalTo(currentid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        ProfileData data = childSnapshot.getValue(ProfileData.class);
                        assert data != null;
                        link =data.getImageUri();
                        Picasso.get().load(link).into(holder.pic);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        holder.txtname.setText(profileData.getName());
        holder.txtWard.setText(profileData.getUser());
        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentChat myFragment = new FragmentChat(profileData,id);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fra, myFragment).addToBackStack(null).commit();

        });
    }


    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{

        CircleImageView pic;
        ImageView cancel;
        TextView txtname;
        TextView txtWard;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.txtname);
            txtWard=itemView.findViewById(R.id.txtWard);
            pic=itemView.findViewById(R.id.pic);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
}
