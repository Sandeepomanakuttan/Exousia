package Admin.RecycleAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Admin.datacollectionClass.MessegesData;
import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdaptor extends RecyclerView.Adapter {
    int item_send=1;
    int item_receive=2;
    Context context;
    ArrayList<MessegesData>arrayList;
    String id,curentid,link;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profile_Table");


    public ChatAdaptor(Context context, ArrayList<MessegesData> messageArrayList, String id, String curentid) {
        this.id=id;
        arrayList=messageArrayList;
        this.context=context;
        this.curentid=curentid;
    }

    @Override
    public RecyclerView.@NotNull ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType==item_send){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewholder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent,false);
            return new receiverViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        MessegesData messegesData=arrayList.get(position);

        if (holder.getClass()==senderViewholder.class){

            senderViewholder viewholder=(senderViewholder) holder;
            viewholder.txtMessage.setText(messegesData.getMassages());
            reference.orderByChild("id").equalTo(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        ProfileData data = childSnapshot.getValue(ProfileData.class);
                        assert data != null;
                        link =data.getImageUri();
                        Picasso.get().load(link).into(viewholder.profileImage);

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

           // Picasso.get().load(sImage).into(viewholder.profileImage);

        }else {

            receiverViewholder viewholder=(receiverViewholder) holder;
            viewholder.txtMessage.setText(messegesData.getMassages());
            reference.orderByChild("id").equalTo(curentid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        ProfileData data = childSnapshot.getValue(ProfileData.class);
                        assert data != null;
                        link =data.getImageUri();
                        Picasso.get().load(link).into(viewholder.profileImage);

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessegesData messegesData=arrayList.get(position);
        if (id.equals(messegesData.getSenderId())){
            return item_send;
        }
        else {
            return item_receive;
        }
    }



    static class senderViewholder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView txtMessage;
        public senderViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.pic);
            txtMessage=itemView.findViewById(R.id.txtMessage);
        }
    }
    static class receiverViewholder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView txtMessage;
        public receiverViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.pic);
            txtMessage=itemView.findViewById(R.id.txtMessage);
        }
    }
}
