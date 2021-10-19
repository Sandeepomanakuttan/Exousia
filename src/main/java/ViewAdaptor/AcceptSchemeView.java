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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import UserSection.Collectionclass.PersonSchemeCollection;

public class AcceptSchemeView extends RecyclerView.Adapter<AcceptSchemeView.Viewholder>{

    Context context;
    String agreeStatus,asid;
    ArrayList<PersonSchemeCollection> personSchemeCollections;
    String id;
    int flag=0;
    PersonSchemeCollection personScheme;

    public AcceptSchemeView(Context context, ArrayList<PersonSchemeCollection> personScheme, String id) {
        this.context = context;
        personSchemeCollections = personScheme;
        this.id=id;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schemeview, parent, false);
        return new AcceptSchemeView.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        personScheme=personSchemeCollections.get(position);
        agreeStatus=personScheme.getStatus();
        asid=personScheme.getId();

        Toast.makeText(context.getApplicationContext(), agreeStatus, Toast.LENGTH_SHORT).show();
        if (agreeStatus.equals("Approve")){
            holder.txtschname.setText(personScheme.getScheme_Name());
            holder.txtBelow.setVisibility(View.GONE);
            holder.txtAbove.setVisibility(View.GONE);
            holder.txtcategory.setText(personScheme.getDepartment());
            holder.txtType.setText(personScheme.getAuthority_type());
            holder.txtAmount.setText(personScheme.getAmount());
            holder.btnapply.setVisibility(View.GONE);
        }
        else if(agreeStatus.equals("Pending")){
            holder.txtschname.setText(personScheme.getScheme_Name());
            holder.txtBelow.setVisibility(View.GONE);
            holder.txtAbove.setVisibility(View.GONE);
            holder.txtcategory.setText(personScheme.getDepartment());
            holder.txtType.setText(personScheme.getAuthority_type());
            holder.txtAmount.setText(personScheme.getAmount());
            holder.btnapply.setText(personScheme.getDepartment());
            holder.btnapply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData(asid,"Request");
                }
            });
        }
        else if(agreeStatus.equals("Request")){
            holder.txtschname.setText(personScheme.getScheme_Name());
            holder.txtBelow.setVisibility(View.GONE);
            holder.txtAbove.setVisibility(View.GONE);
            holder.txtcategory.setText(personScheme.getDepartment());
            holder.txtType.setText(personScheme.getAuthority_type());
            holder.txtAmount.setText(personScheme.getAmount());
            holder.btnapply.setText("ReApply");
            holder.btnapply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData(asid, "Request");
                }
            });
        }else{
            Toast.makeText(context.getApplicationContext(), "doesn't have a Scheme", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData(String aid, String request) {
        HashMap<String, Object> update = new HashMap<String, Object>();
        update.put("status", request);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Agree_Scheme_Table");
        reference.child(aid).updateChildren(update).addOnSuccessListener((OnSuccessListener) this::onSuccess).addOnFailureListener(e -> {
            Toast.makeText(context.getApplicationContext(), "Something is error", Toast.LENGTH_SHORT).show();

        });
    }

    private void onSuccess(Object o) {
    }

    @Override
    public int getItemCount() {
        return personSchemeCollections.size();
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
}
