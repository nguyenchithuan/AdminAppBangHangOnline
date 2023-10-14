package edu.wkd.adminappbanghangonline.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.wkd.adminappbanghangonline.databinding.LayoutItemOrderBinding;
import edu.wkd.adminappbanghangonline.model.obj.Order;
import edu.wkd.adminappbanghangonline.model.obj.Product;



public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<Order> list;
    private ArrayList<Product> listProduct;
    private ProductInOrderAdapter productAdapter;

    public OrderAdapter(ArrayList<Order> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemOrderBinding binding = LayoutItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = list.get(position);
        if (order == null){
            return;
        }
        holder.binding.tvUsernameInOrder.setText(order.getUsername());
        holder.binding.tvDateOrder.setText(order.getDateTime());
        //Hiển thị danh sách sản phẩm
        listProduct = order.getListProduct();
        productAdapter = new ProductInOrderAdapter(listProduct);
        LinearLayoutManager manager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
        holder.binding.rvProductInOrder.setLayoutManager(manager);
        holder.binding.rvProductInOrder.setAdapter(productAdapter);
        if (order.getStatus() == 0){
            holder.binding.tvStatusOrder.setText("Chờ xác nhận");
        }else if(order.getStatus() == 1){
            holder.binding.tvStatusOrder.setText("Đang giao hàng");
        }else if(order.getStatus() == 2){
            holder.binding.tvStatusOrder.setText("Đã giao hàng");
        }else if(order.getStatus() == 3){
            holder.binding.tvStatusOrder.setText("Đơn hàng đã bị hủy");
        }
    }


    private ArrayList<Product> getListProduct(int position){
        Order order = list.get(position);
        if (order != null){
            return order.getListProduct();
        }
        return new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemOrderBinding binding;
        public ViewHolder(@NonNull LayoutItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
