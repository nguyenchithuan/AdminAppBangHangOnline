package edu.wkd.adminappbanghangonline.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.ActivityOrderBinding;
import edu.wkd.adminappbanghangonline.model.obj.Order;
import edu.wkd.adminappbanghangonline.model.response.OrderResponse;
import edu.wkd.adminappbanghangonline.ultil.GetListOrderInterface;
import edu.wkd.adminappbanghangonline.view.adapter.OrderAdapter;
import edu.wkd.adminappbanghangonline.view.adapter.ViewPager2Adapter;
import edu.wkd.adminappbanghangonline.view.fragment.CancelledFragment;
import edu.wkd.adminappbanghangonline.view.fragment.ConfirmationFragment;
import edu.wkd.adminappbanghangonline.view.fragment.DeliveredFragment;
import edu.wkd.adminappbanghangonline.view.fragment.DeliveringFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.toString();
    private ActivityOrderBinding binding;
    private GetListOrderInterface getListOrderInterface;
    public void setGetListOrderInterface(GetListOrderInterface getListOrderInterface){
        this.getListOrderInterface = getListOrderInterface;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onBack();
        setTabLayoutAndViewPager2();

    }

    private void setTabLayoutAndViewPager2() {
        List<Fragment> list = new ArrayList<>();
        list.add(new ConfirmationFragment());
        list.add(new DeliveringFragment());
        list.add(new DeliveredFragment());
        list.add(new CancelledFragment());
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(OrderActivity.this, list);
        binding.viewPager2.setAdapter(viewPager2Adapter);

        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chờ xác nhận");
                        break;
                    case 1:
                        tab.setText("Đang giao hàng");
                        break;
                    case 2:
                        tab.setText("Đã giao");
                        break;
                    case 3:
                        tab.setText("Đã hủy");
                        break;
                }
            }
        });
        mediator.attach();
    }

    public void getListOrderByStatus(int status){
        ApiService.apiService.getAllOrderByStatus(status).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    getListOrderInterface.getListOrder(response.body().getListOrder());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Lỗi server (chi tiết trong logcat)", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: " + t);
            }
        });
    }

    public void updateStatusOrder(OrderAdapter orderAdapter) {
        orderAdapter.setUpdateStatusInterface(new OrderAdapter.UpdateStatusInterface() {
            @Override
            public void updateStatus(int id, int status) {
                new AlertDialog.Builder(OrderActivity.this)
                        .setTitle("Cập nhật trạng thái đơn hàng")
                        .setMessage("Bạn chắc chắn muốn thay đổi trạng thái đơn hàng này?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int newStatus = status+1;
                                ApiService.apiService.updateStatusOrder(id, newStatus).enqueue(new Callback<Order>() {
                                    @Override
                                    public void onResponse(Call<Order> call, Response<Order> response) {
                                        if (response.isSuccessful()){
                                            Toast.makeText(OrderActivity.this, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(OrderActivity.this, "Cập nhật trạng thái không thành công", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<Order> call, Throwable t) {
                                        Toast.makeText(OrderActivity.this, "Lỗi server (chi tiết trong logcat)", Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "onFailure: " + t);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    private void onBack() {
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}