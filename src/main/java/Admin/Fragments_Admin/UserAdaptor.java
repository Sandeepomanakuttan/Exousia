package Admin.Fragments_Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;

import java.util.ArrayList;

import Admin.PersonDataCollection;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.Viewholder> {

    PersonDataCollection collection;
    ArrayList<PersonDataCollection> personArray;
    Context context;

    public UserAdaptor(Context context, ArrayList<PersonDataCollection> collections) {

        personArray=collections;
        this.context=context;
    }

    @NonNull
    @Override
    public UserAdaptor.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserAdaptor.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdaptor.Viewholder holder, int position) {
        collection = personArray.get(position);
        holder.txtname.setText(collection.getHouse_OwnerName());
        holder.txtWard.setText(collection.getWardNo());
        holder.txtHouseNo.setText(collection.getHouseNo());
        holder.txtType.setText(collection.getUserType());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return personArray.size();
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
