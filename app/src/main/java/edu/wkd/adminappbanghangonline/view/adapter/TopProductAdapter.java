package edu.wkd.adminappbanghangonline.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.model.obj.Product;
import edu.wkd.adminappbanghangonline.view.fragment.TopProductFragment;

public class TopProductAdapter extends RecyclerView.Adapter<TopProductAdapter.TopViewHolder> {

    private Context context;
    private List<Product> listProduct;

    public TopProductAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_top_product,parent,false);
        return new TopProductAdapter.TopViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Product product = listProduct.get(position);
        if (product == null) {
            return;
        }

        int rank = position + 1;

        switch (rank){
            case 1:
                holder.tvRank.setText("Top "+rank);
                holder.tvRank.setTextColor(R.color.red);
                holder.tvRank.setTextSize(25);
                holder.tvRank.setBackgroundResource(R.drawable.baseline_star_24);
                break;
            case 2:
                holder.tvRank.setText("Top "+rank);
                holder.tvRank.setTextColor(R.color.pink);
                holder.tvRank.setTextSize(21);
                holder.tvRank.setBackgroundResource(R.drawable.baseline_star_24);
                break;
            case 3:
                holder.tvRank.setText("Top "+rank);
                holder.tvRank.setTextColor(R.color.violet);
                holder.tvRank.setTextSize(20);
                holder.tvRank.setBackgroundResource(R.drawable.baseline_star_24);
                break;
            default:
                holder.tvRank.setText("Top "+rank);
                holder.tvRank.setTextColor(R.color.black);
                holder.tvRank.setTextSize(15);
                break;
        }

        Glide.with(context)
                .load(product.getImage())
                .error(R.mipmap.ic_launcher)
                .into(holder.imgProduct);

        holder.tvName.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        if(listProduct != null){
            return  listProduct.size();
        }
        return 0;
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder{

        TextView tvRank, tvName;
        ImageView imgProduct;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tv_rank);
            tvName = itemView.findViewById(R.id.tv_top_name_product);
            imgProduct = itemView.findViewById(R.id.img_top_product);
        }
    }
}
