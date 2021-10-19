package MainAdmin;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import Admin.datacollectionClass.ProfileData;

public class SearchAdaptor extends FirebaseRecyclerAdapter<ProfileData,SearchAdaptor.myviewholder> {
    String search,Name,userName,Password,Authority,id;
    Context context;

    public SearchAdaptor(@NonNull FirebaseRecyclerOptions<ProfileData> options,String search) {
        super(options);
        this.search=search;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ProfileData model) {
        id= model.getId();
        Name= model.getName();
        userName= model.getUserName();
        Password= model.getPassword();
        Authority= model.getAuthority_Place();
        if (Name.equalsIgnoreCase(search)) {
            holder.name.setText(model.getName());
            holder.username.setText(model.getUserName());
            holder.password.setText(model.getPassword());
            holder.authority.setText(model.getAuthority_Place());
        }else if (userName.equalsIgnoreCase(search)){
            holder.name.setText(model.getName());
            holder.username.setText(model.getUserName());
            holder.password.setText(model.getPassword());
            holder.authority.setText(model.getAuthority_Place());
        }else if (Password.equalsIgnoreCase(search)){
            holder.name.setText(model.getName());
            holder.username.setText(model.getUserName());
            holder.password.setText(model.getPassword());
            holder.authority.setText(model.getAuthority_Place());
        }else if (Authority.equalsIgnoreCase(search)){
            holder.name.setText(model.getName());
            holder.username.setText(model.getUserName());
            holder.password.setText(model.getPassword());
            holder.authority.setText(model.getAuthority_Place());
        }
        else{
            Toast.makeText(context, "invaild search...", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.authorityview,parent,false);
        return new SearchAdaptor.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{


       ImageView img1;
       EditText editAuthority;
       TextView name,username,password,authority;
       Button update;
       String Authority;

       public myviewholder(@NonNull View itemView) {
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
        reference.child(Name).updateChildren(update).addOnSuccessListener(new OnSuccessListener() {
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
