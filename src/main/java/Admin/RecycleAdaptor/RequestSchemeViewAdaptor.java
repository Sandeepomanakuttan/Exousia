package Admin.RecycleAdaptor;

import static android.graphics.Color.rgb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import Admin.datacollectionClass.UserDataCollection;
import UserSection.Collectionclass.PersonSchemeCollection;

public class RequestSchemeViewAdaptor extends RecyclerView.Adapter<RequestSchemeViewAdaptor.Viewholder> {

    Context context;
    ArrayList<PersonSchemeCollection> PersonschemeArrayList;
    String id,Name,status,aid;
    int flag=0;
    PersonSchemeCollection collection;
    UserDataCollection userDataCollection;
    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("User_Deails_Table");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");

    public RequestSchemeViewAdaptor(Context context, ArrayList<PersonSchemeCollection> PersonschemeArrayList) {
        this.context = context;
        this.PersonschemeArrayList = PersonschemeArrayList;
    }

    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userschemeview, parent, false);
        return new Viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RequestSchemeViewAdaptor.Viewholder holder, int position) {
        collection = PersonschemeArrayList.get(position);
        id= collection.getPerson_id();
        aid=collection.getId();
        status=collection.getStatus();
        if (status.equals("Request")){
        Toast.makeText(context.getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        Query query=reference1.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userDataCollection = dataSnapshot.getValue(UserDataCollection.class);
                    Name=userDataCollection.getPersonName();
                    Toast.makeText(context.getApplicationContext(), Name, Toast.LENGTH_SHORT).show();
                    holder.txtName.setText(Name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.txtschname.setText(collection.getScheme_Name());
        holder.btnScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.orderByChild("scheme_Name").equalTo(collection.getScheme_Name()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.btnScheme.setText("Verified");
                        holder.btnScheme.setBackgroundColor(rgb(132,222,2));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        holder.btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference1.orderByChild("personName").equalTo(Name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.btnAddress.setText("Verified");
                        holder.btnAddress.setBackgroundColor(rgb(132,222,2));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(aid,"Verify");
            }
        });
        holder.btnRej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(aid,"Reject");
            }
        });
    }else if (status.equals("Verify")){

            holder.btnAddress.setText("Verified");
            holder.btnAddress.setBackgroundColor(rgb(132,222,2));
            holder.btnScheme.setText("Verified");
            holder.btnScheme.setBackgroundColor(rgb(132,222,2));
            Toast.makeText(context.getApplicationContext(), id, Toast.LENGTH_SHORT).show();
            Query query=reference1.orderByChild("id").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        userDataCollection = dataSnapshot.getValue(UserDataCollection.class);
                        Name=userDataCollection.getPersonName();
                        Toast.makeText(context.getApplicationContext(), Name, Toast.LENGTH_SHORT).show();
                        holder.txtName.setText(Name);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.txtschname.setText(collection.getScheme_Name());
           // holder.btnRej.setVisibility();
            holder.btnScheme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.orderByChild("scheme_Name").equalTo(collection.getScheme_Name()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            holder.btnScheme.setText("Verified");
                            holder.btnScheme.setBackgroundColor(rgb(135,231,235));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
            holder.btnAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference1.orderByChild("personName").equalTo(Name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            holder.btnAddress.setText("Verified");
                            holder.btnAddress.setBackgroundColor(rgb(135,231,235));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData(aid,"Approve");
                }
            });
        }else if (status.equals("Approve")){
            Toast.makeText(context.getApplicationContext(), id, Toast.LENGTH_SHORT).show();
            Query query=reference1.orderByChild("id").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        userDataCollection = dataSnapshot.getValue(UserDataCollection.class);
                        Name=userDataCollection.getPersonName();
                        Toast.makeText(context.getApplicationContext(), Name, Toast.LENGTH_SHORT).show();
                        holder.txtName.setText(Name);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.txtschname.setText(collection.getScheme_Name());
            holder.btnScheme.setVisibility(View.GONE);
            holder.btnAddress.setVisibility(View.GONE);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.btnRej.setVisibility(View.GONE);
        }else{
            Toast.makeText(context.getApplicationContext(), id, Toast.LENGTH_SHORT).show();
            Query query=reference1.orderByChild("id").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        userDataCollection = dataSnapshot.getValue(UserDataCollection.class);
                        Name=userDataCollection.getPersonName();
                        Toast.makeText(context.getApplicationContext(), Name, Toast.LENGTH_SHORT).show();
                        holder.txtName.setText(Name);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.txtschname.setText(collection.getScheme_Name());
            holder.btnScheme.setVisibility(View.GONE);
            holder.btnAddress.setVisibility(View.GONE);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.btnRej.setVisibility(View.GONE);
        }}


    private void updateData(String aid, String verify) {
        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("status", verify);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");
        reference.child(aid).updateChildren(update).addOnSuccessListener((OnSuccessListener) this::onSuccess).addOnFailureListener(e -> {
            Toast.makeText(context.getApplicationContext(), "Something is error", Toast.LENGTH_SHORT).show();

        });
    }

    private void onSuccess(Object o) {
    }

    @Override
    public int getItemCount() {
        return PersonschemeArrayList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder {

        TextView txtschname, txtName;
        Button btnScheme,btnAddress,btn,btnRej;


        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtschname = itemView.findViewById(R.id.txtschname);
            txtName = itemView.findViewById(R.id.txtName);
            btnScheme = itemView.findViewById(R.id.btnScheme);
            btnAddress = itemView.findViewById(R.id.btnAddress);
            btn = itemView.findViewById(R.id.btn);
            btnRej = itemView.findViewById(R.id.btnRej);



        }

    }}





