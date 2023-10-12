package edu.wkd.adminappbanghangonline.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.model.obj.Product;
import edu.wkd.adminappbanghangonline.ultil.ProductInterface;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> listProduct;
    private ProductInterface productInterface;


    public ProductAdapter(Context context, List<Product> listProduct, ProductInterface productInterface) {
        this.context = context;
        this.listProduct = listProduct;
        this.productInterface = productInterface;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = listProduct.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###đ");
        if(product == null){
            return;
        }
        if(product.getImage().contains("uploads")) {
            Glide.with(context)
                    .load("https://guyinterns2003.000webhostapp.com/" + product.getImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.imgProduct);
        } else {
            Glide.with(context)
                    .load(product.getImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.imgProduct);
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(decimalFormat.format(product.getPrice()));

        holder.ratingBar.setRating(product.getRating());
        holder.tvQuantityRating.setText("(" + product.getQuantityRating() + ")");

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productInterface.deleteProdcut(product);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productInterface.updateProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listProduct != null){
            return  listProduct.size();
        }
        return 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice;
        private RatingBar ratingBar;
        private TextView tvQuantityRating;
        private Button btnEdit;
        private Button btnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvQuantityRating = itemView.findViewById(R.id.tvQuantityRating);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
