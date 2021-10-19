package Admin.Fragments_Admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.Date;

import Admin.RecycleAdaptor.ChatAdaptor;
import Admin.datacollectionClass.MessegesData;
import Admin.datacollectionClass.ProfileData;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentChat extends Fragment {

    View view;
    String id,senderRoom,reciverRoom;
    String name,link,edtmsg;
    CircleImageView img;
    TextView txtname;
    EditText edtMsg;
    CardView btnsnd;
    RecyclerView recyclerView;
    ArrayList<MessegesData>messageArrayList;
    ProfileData person;
    MessegesData message;
    ChatAdaptor chatAdaptor;
    String cid;



    public FragmentChat(ProfileData personpassList, String id) {

        person=personpassList;
        this.id=id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_chat, container, false);
        img=view.findViewById(R.id.pic);
        txtname=view.findViewById(R.id.txtname);
        edtMsg=view.findViewById(R.id.edtMsg);
        btnsnd=view.findViewById(R.id.btnsnd);
        recyclerView=view.findViewById(R.id.recyclerView);



        name=person.getName();
        cid=person.getId();
        messageArrayList=new ArrayList<>();

        final DatabaseReference referencechat = FirebaseDatabase.getInstance().getReference().child("Chats");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profile_Table");



        senderRoom=id+cid;
        reciverRoom=cid+id;

        txtname.setText(""+name);

        Query query = reference.orderByChild("id").equalTo(cid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ProfileData data = childSnapshot.getValue(ProfileData.class);
                    link =data.getImageUri();
                    Picasso.get().load(link).into(img);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Query chatQuery=referencechat.child(senderRoom).child("Message");
        chatQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                messageArrayList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                    MessegesData messegesData = childSnapshot.getValue(MessegesData.class);
                    messageArrayList.add(messegesData);
                }
                chatAdaptor=new ChatAdaptor(getContext(),messageArrayList,id,cid);
                recyclerView.setAdapter(chatAdaptor);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });






        btnsnd.setOnClickListener(v -> {

            edtmsg=edtMsg.getText().toString();
            if (TextUtils.isEmpty(edtmsg)){
                Toast.makeText(getContext(), "Enter valiad Type.....", Toast.LENGTH_SHORT).show();
                return;
            }
            edtMsg.setText("");

            Date date=new Date();

            message=new MessegesData(edtmsg,id,date.getTime());

            referencechat.child(senderRoom).child("Message").push().setValue(message).addOnCompleteListener(task -> referencechat.child(reciverRoom).child("Message").push().setValue(message).addOnCompleteListener(task1 -> {

            }));

        });
        return view;
    }
}