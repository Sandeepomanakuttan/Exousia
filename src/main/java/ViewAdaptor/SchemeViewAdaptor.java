package ViewAdaptor;

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
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Admin.datacollectionClass.SchemedataCollection;
import Admin.datacollectionClass.UserDataCollection;
import UserSection.Collectionclass.PersonSchemeCollection;


public class SchemeViewAdaptor extends RecyclerView.Adapter<SchemeViewAdaptor.Viewholder> {

    Context context;
    String strName;
    ArrayList<SchemedataCollection> schemeArrayList;
    String id;
    int flag=0;
    SchemedataCollection collection;

    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Scheme_Table");
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("User_Deails_Table");

    public SchemeViewAdaptor(Context context, ArrayList<SchemedataCollection> schemeArrayList, String id) {
        this.context = context;
        this.schemeArrayList = schemeArrayList;
        this.id=id;
    }

    public @NotNull Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schemeview, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SchemeViewAdaptor.Viewholder holder, int position) {
        collection = schemeArrayList.get(position);
        strName=collection.getStrschName();
        String status=collection.getStrStatus();


            holder.txtschname.setText(collection.getStrschName());
            holder.txtcategory.setText(collection.getStrcategory());
            holder.txtType.setText(collection.getStrtype());
            holder.txtBelow.setText(collection.getStrbelow());
            holder.txtAbove.setText(collection.getStrabove());
            holder.txtAmount.setText(collection.getStramount());
            holder.btnapply.setText("Apply");

            holder.btnapply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonSchemeCollection personSchemeCollection = new PersonSchemeCollection();
                    personSchemeCollection.setPerson_id(id);
                    reference2.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                UserDataCollection dataCollection=dataSnapshot.getValue(UserDataCollection.class);
//                                int income;
//                                assert dataCollection != null;
//                                income = Integer.parseInt(dataCollection.getAnualIncime());
//                                int in=Integer.parseInt(collection.getStrabove());
//                                if (income>=in){
                                    personSchemeCollection.setScheme_id(collection.getId());
                                    personSchemeCollection.setScheme_Name(collection.getStrschName());
                                    personSchemeCollection.setAmount(collection.getStramount());
                                    personSchemeCollection.setStatus("Request");
                                    personSchemeCollection.setAuthority_type(collection.getAuthority());
                                    personSchemeCollection.setAuthority_Place(collection.getAuthority_Place());
                                    personSchemeCollection.setIncome(collection.getStrabove());
                                    Date date = new Date();
                                    personSchemeCollection.setDate(date);
                                    personSchemeCollection.setTimeStamp(date.getTime());
                                    personSchemeCollection.setDepartment(collection.getStrcategory());
                                    updateData("True");
                                    checkdata(personSchemeCollection);
                                //}//
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });



        }

    @Override
    public int getItemCount() {
        return schemeArrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        TextView txtschname, txtcategory, txtType, txtBelow, txtAbove,txtAmount;
        Button btnapply;


        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtschname = itemView.findViewById(R.id.txtschname);
            txtcategory = itemView.findViewById(R.id.txtcategory);
            txtType = itemView.findViewById(R.id.txtType);
            txtBelow = itemView.findViewById(R.id.txtBelow);
            txtAbove = itemView.findViewById(R.id.txtAbove);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            btnapply = itemView.findViewById(R.id.btnapply);


        }
    }

    private void InsertPersonalScheme(PersonSchemeCollection DataCollection) {
        String key = reference.push().getKey();
        DataCollection.setId(key);
        reference.child(DataCollection.getId()).setValue(DataCollection);
        Toast.makeText(context.getApplicationContext(), "succefully Inserted", Toast.LENGTH_SHORT).show();
    }
    private void updateData(String True) {
        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("strStatus", True);

        reference1.child(strName).updateChildren(update).addOnSuccessListener((OnSuccessListener) this::onSuccess).addOnFailureListener(e -> {
            Toast.makeText(context.getApplicationContext(), "Something is error", Toast.LENGTH_SHORT).show();

        });
    }

    private void onSuccess(Object o) {
    }

    public void checkdata(PersonSchemeCollection Data) {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    PersonSchemeCollection data=childSnapshot.getValue(PersonSchemeCollection.class);
                    assert data != null;
                    if (data.getPerson_id().equals(Data.getPerson_id()) && data.getScheme_id().equals(Data.getScheme_id())) {
                            flag=1;
                        break;
                    }
                    else {
                        flag=0;
                    }
                }
            if (flag==1){
                Toast.makeText(context, "Already Applyed", Toast.LENGTH_SHORT).show();
            }else {
                InsertPersonalScheme(Data);
                flag=0;
                Toast.makeText(context, "Insert", Toast.LENGTH_SHORT).show();
                collection.setStrStatus("Apply");

            }}

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });

    }
}