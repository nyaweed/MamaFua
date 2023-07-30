package com.example.mamafua;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<ModelChatList> chatListList = new ArrayList<>();
    List<ModelUsers> usersList = new ArrayList<>();
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    AdapterChatList adapterChatList;
    List<ModelChat> chatList = new ArrayList<>();

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        // getting current user
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.chatlistrecycle);

        adapterChatList = new AdapterChatList(getActivity(), usersList);
        recyclerView.setAdapter(adapterChatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatListList = new ArrayList<>();
        chatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatListList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelChatList modelChatList = ds.getValue(ModelChatList.class);
                    if (!firebaseUser.getUid().equals(modelChatList.getId())) {
                        chatListList.add(modelChatList);
                    }

                }
                loadChats();
                loadLastMessage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //loadChats();
        return view;

    }

    private void loadLastMessage() {
        for (int i = 0; i < usersList.size(); i++) {
            lastMessage(usersList.get(i).getUid());
        }
    }

    // loading the user chat layout using chat node
    private void loadChats() {
        //usersList = new ArrayList<>();
        Log.d("Mama fua", "chatListList size: " + chatListList.size());
        // Check the size of chatListList
        Log.d("mama fua", "userlist size: " + usersList.size());

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String user1 = firebaseUser.getUid();
                    ModelUsers user = dataSnapshot1.getValue(ModelUsers.class);


                    if (user != null && isUserInChatList()) {
                        usersList.add(user);
                    }
                }
                adapterChatList.notifyDataSetChanged();
            }
            

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            
        });
    }

    private boolean isUserInChatList() {
        for (ModelChatList chatList : chatListList) {
            if (chatList.getId().equals(firebaseUser.getUid())) {
                return true;
            }
        }
        return false;
    }

    private void lastMessage(final String uid) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lastmess = "default";
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelChat chat = dataSnapshot1.getValue(ModelChat.class);
                    if (chat == null) {
                        continue;
                    }
                    String sender = chat.getSender();
                    String receiver = chat.getReceiver();
                    if (sender == null || receiver == null) {
                        continue;
                    }
                    // checking for the type of message if
                    // message type is image then set
                    // last message as sent a photo
                    if (chat.getReceiver().equals(firebaseUser.getUid()) &&
                            chat.getSender().equals(uid) ||
                            chat.getReceiver().equals(uid) &&
                                    chat.getSender().equals(firebaseUser.getUid())) {
                        if (chat.getType().equals("images")) {
                            lastmess = "Sent a Photo";
                        } else {
                            lastmess = chat.getMessage();
                        }
                    }
                }
                adapterChatList.setlastMessageMap(uid, lastmess);
                //adapterChatList = new AdapterChatList(getActivity(), usersList);
                //recyclerView.setAdapter(adapterChatList);
                adapterChatList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
