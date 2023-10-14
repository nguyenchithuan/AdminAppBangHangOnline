package edu.wkd.adminappbanghangonline.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.ActivityOrderBinding;
import edu.wkd.adminappbanghangonline.model.obj.Order;
import edu.wkd.adminappbanghangonline.model.response.OrderResponse;
import edu.wkd.adminappbanghangonline.view.adapter.OrderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.toString();
    private ActivityOrderBinding binding;
    private ArrayList<Order> listOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showData();
    }

    private void showData() {
        ApiService.apiService.getAllOrder().enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()){
                    listOrder = response.body().getListOrder();
                    OrderAdapter orderAdapter = new OrderAdapter(listOrder);
                    LinearLayoutManager manager = new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL, false);
                    binding.rvOrder.setLayoutManager(manager);
                    binding.rvOrder.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Lỗi server (chi tiết trong logcat)", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }
}