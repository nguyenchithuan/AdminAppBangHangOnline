package edu.wkd.adminappbanghangonline.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.wkd.adminappbanghangonline.databinding.LayoutItemOrderBinding;
import edu.wkd.adminappbanghangonline.model.obj.Order;
import edu.wkd.adminappbanghangonline.model.obj.Product;



public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<Order> list;
    private Context context;
    private ArrayList<Product> listProduct;
    private ProductInOrderAdapter productAdapter;
    public interface UpdateStatusInterface{
        void updateStatus(int id, int status);
    }

    private UpdateStatusInterface updateStatusInterface;
    public void setUpdateStatusInterface(UpdateStatusInterface updateStatusInterface){
        this.updateStatusInterface = updateStatusInterface;
    }

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Order> list){
        this.list = list;
        notifyDataSetChanged();
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
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.binding.rvProductInOrder.setLayoutManager(manager);
        holder.binding.rvProductInOrder.setAdapter(productAdapter);
        if (order.getStatus() == 0){
            holder.binding.tvStatusOrder.setText("Chờ xác nhận");
            holder.binding.btnChangeStatusOrder.setVisibility(View.VISIBLE);
        }else if(order.getStatus() == 1){
            holder.binding.tvStatusOrder.setText("Đang giao hàng");
            holder.binding.btnChangeStatusOrder.setVisibility(View.VISIBLE);
        }else if(order.getStatus() == 2){
            holder.binding.tvStatusOrder.setText("Đã giao hàng");
            holder.binding.btnChangeStatusOrder.setVisibility(View.INVISIBLE);
        }else if(order.getStatus() == 3){
            holder.binding.tvStatusOrder.setText("Đơn hàng đã bị hủy");
            holder.binding.btnChangeStatusOrder.setVisibility(View.INVISIBLE);
        }

        int totalPrice = 0, totalQuantityProduct = 0;
        for(Product product : listProduct){
            totalPrice += product.getPrice();
            totalQuantityProduct += product.getQuantity();
        }
        holder.binding.tvIdOrder.setText("Đơn hàng số: "+order.getId());
        holder.binding.tvAllProductInOrder.setText(totalQuantityProduct + " sản phẩm");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.tvAllPriceOrder.setText(decimalFormat.format(totalPrice)+"đ");

        holder.binding.btnChangeStatusOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateStatusInterface != null){
                    updateStatusInterface.updateStatus(order.getId(), order.getStatus());
                }
            }
        });
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
