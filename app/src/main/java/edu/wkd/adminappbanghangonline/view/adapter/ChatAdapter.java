package edu.wkd.adminappbanghangonline.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.model.obj.ChatMessage;
import edu.wkd.adminappbanghangonline.ultil.UserUltil;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ChatMessage> chatMessageList;
    private static final int TYPE_SEND = 1;
    private static final int TYPE_RECEIVE = 2;

    public ChatAdapter(Context context, List<ChatMessage> chatMessageList) {
        this.context = context;
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_SEND) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_send_message, parent, false);
            return new SendMessViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_receive_message, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        if(getItemViewType(position) == TYPE_SEND) {
            SendMessViewHolder holderSend = (SendMessViewHolder) holder;
            holderSend.tvMessage.setText(chatMessage.getMess());
            holderSend.tvDatetime.setText(chatMessage.getDatatime());
        } else {
            ReceivedViewHolder holderReceive = (ReceivedViewHolder) holder;
            holderReceive.tvMessage.setText(chatMessage.getMess());
            holderReceive.tvDatetime.setText(chatMessage.getDatatime());
            if(chatMessage.avatar == null) {
                return;
            }
            if(chatMessage.avatar.contains("uploads")) {
                Glide.with(context)
                        .load("https://guyinterns2003.000webhostapp.com/" + chatMessage.avatar)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(holderReceive.imgAvatar);
            } else {
                Glide.with(context)
                        .load(chatMessage.avatar)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(holderReceive.imgAvatar);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(chatMessageList != null) {
            return chatMessageList.size();
        }
        return 0;
    }


    // Chọn lại hiển thị item
    @Override
    public int getItemViewType(int position) {
        // Kiểm tra nếu sendId Bằng với id đăng nhập thì là Type_send
        if(chatMessageList.get(position).sendId.equals(UserUltil.user.getId() + "")) {
            Log.d("zzz", "getItemViewType: " + true);
            return TYPE_SEND;
        } else {
            return TYPE_RECEIVE;
        }
    }

    public class SendMessViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage;
        private TextView tvDatetime;
        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDatetime = itemView.findViewById(R.id.tvDatetime);
        }
    }

    public class ReceivedViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage;
        private TextView tvDatetime;
        private ImageView imgAvatar;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDatetime = itemView.findViewById(R.id.tvDatetime);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
