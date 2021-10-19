package Admin.RecycleAdaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reformingkeralathroughdigitilization.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import Admin.datacollectionClass.SchemedataCollection;

public class Recycle_Scheme_view_Adaptor extends FirebaseRecyclerAdapter<SchemedataCollection,Recycle_Scheme_view_Adaptor.myviewholder> {

    public Recycle_Scheme_view_Adaptor(@NonNull FirebaseRecyclerOptions<SchemedataCollection> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull SchemedataCollection model) {
        holder.txtschname.setText(model.getStrschName());
        holder.txtcategory.setText(model.getStrcategory());
        holder.txtType.setText(model.getStrtype());
        holder.txtBelow.setText(model.getStrbelow());
        holder.txtAbove.setText(model.getStrabove());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.schemeviewcontainer,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView txtschname,txtcategory,txtType,txtBelow,txtAbove;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            txtschname=itemView.findViewById(R.id.txtschname);
            txtcategory=itemView.findViewById(R.id.txtcategory);
            txtType=itemView.findViewById(R.id.txtType);
            txtBelow=itemView.findViewById(R.id.txtBelow);
            txtAbove=itemView.findViewById(R.id.txtAbove);
        }
    }
}
