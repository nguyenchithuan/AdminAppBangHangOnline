package edu.wkd.adminappbanghangonline.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.model.obj.User;
import edu.wkd.adminappbanghangonline.ultil.Utils;
import edu.wkd.adminappbanghangonline.view.activity.ChatActivity;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ChatUserViewHolder>{
    private Context context;
    private List<User> listUserChat;


    public ChatUserAdapter(Context context, List<User> listUserChat) {
        this.context = context;
        this.listUserChat = listUserChat;
    }

    public void setListUserChat(List<User> listUserChat) {
        this.listUserChat = listUserChat;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_chat_user, parent, false);
        return new ChatUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserViewHolder holder, int position) {
        User user = listUserChat.get(position);
        if(user == null) {
            return;
        }

        holder.tvUsername.setText(user.getUsername());
        holder.tvMessage.setText("Tin nhắn");

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("userId", user.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(listUserChat != null) {
            return listUserChat.size();
        }
        return 0;
    }

    public class ChatUserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private TextView tvMessage;
        public ChatUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
