package edu.wkd.adminappbanghangonline.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.wkd.adminappbanghangonline.databinding.LayoutItemProductInOderBinding;
import edu.wkd.adminappbanghangonline.model.obj.Product;

public class ProductInOrderAdapter extends RecyclerView.Adapter<ProductInOrderAdapter.ViewHolder>{
    private ArrayList<Product> list;

    public ProductInOrderAdapter(ArrayList<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemProductInOderBinding binding = LayoutItemProductInOderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        if (product==null){
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.tvNameProductOrder.setText(product.getName());
        holder.binding.tvQuantityProductOrder.setText("x "+product.getQuantity()+"");
        holder.binding.tvPriceProductOrder.setText(decimalFormat.format(product.getPrice())+"đ");
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .into(holder.binding.imgProductOrder);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemProductInOderBinding binding;
        public ViewHolder(@NonNull LayoutItemProductInOderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
