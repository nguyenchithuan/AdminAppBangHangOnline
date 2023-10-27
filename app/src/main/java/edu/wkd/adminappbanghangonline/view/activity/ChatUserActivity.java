package edu.wkd.adminappbanghangonline.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.wkd.adminappbanghangonline.databinding.ActivityChatUserBinding;
import edu.wkd.adminappbanghangonline.model.obj.User;
import edu.wkd.adminappbanghangonline.view.adapter.ChatUserAdapter;

public class ChatUserActivity extends AppCompatActivity {
    private ActivityChatUserBinding binding;
    private List<User> listUserChat;
    private ChatUserAdapter chatUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        getUserFromFire();
    }

    private void initView() {
        listUserChat = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcvChatUser.setLayoutManager(linearLayoutManager);
        binding.rcvChatUser.setHasFixedSize(true);
        chatUserAdapter = new ChatUserAdapter(this, listUserChat);
        binding.rcvChatUser.setAdapter(chatUserAdapter);
    }

    private void getUserFromFire() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            listUserChat = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                User user  = new User();
                                user.setId(documentSnapshot.getLong("id").intValue());
                                user.setUsername(documentSnapshot.getString("name"));
                                listUserChat.add(user);
                            }
                            if(listUserChat.size() > 0) {
                                chatUserAdapter.setListUserChat(listUserChat);
                            }
                        }
                    }
                });
    }

}