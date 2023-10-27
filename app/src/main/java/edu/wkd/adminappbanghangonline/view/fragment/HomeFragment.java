package edu.wkd.adminappbanghangonline.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.FragmentHomeBinding;
import edu.wkd.adminappbanghangonline.model.obj.Product;
import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import edu.wkd.adminappbanghangonline.model.response.ServerResponse;
import edu.wkd.adminappbanghangonline.ultil.CheckConection;
import edu.wkd.adminappbanghangonline.ultil.ProductInterface;
import edu.wkd.adminappbanghangonline.ultil.ProgressDialogLoading;
import edu.wkd.adminappbanghangonline.view.activity.ChatUserActivity;
import edu.wkd.adminappbanghangonline.view.activity.CrudProductActivity;
import edu.wkd.adminappbanghangonline.view.adapter.ProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ProductInterface {
    private FragmentHomeBinding binding;
    private List<Product> listProduct;
    private ProductAdapter productAdapter;
    private ProgressDialogLoading dialogLoading;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initController();
        getListProduct();
        callApiGetProduct();
        searchProduct();
    }

    private void getListProduct() {
        listProduct = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                2, GridLayoutManager.VERTICAL, false);
        binding.rcvProduct.setLayoutManager(gridLayoutManager);
        binding.rcvProduct.setHasFixedSize(true);
        productAdapter = new ProductAdapter(getActivity(), listProduct, this);
        binding.rcvProduct.setAdapter(productAdapter);
    }

    private void callApiGetProduct(){
        dialogLoading.show();
        ApiService.apiService.getListProduct().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("zzzz", "onResponse-product-error: " + response.body());
                    ProductResponse productResponse = response.body();
                    if (productResponse.isSuccess()) {
                        productAdapter.setListProduct(productResponse.getResult()); // set dữ liệu lên rcv
                    } else {
                        CheckConection.ShowToast(getContext(), "Load danh sách sản phẩm lỗi!");
                    }
                    dialogLoading.cancel();
                } else {
                    CheckConection.ShowToast(getContext(), "Không có dữ liệu trả về");
                    dialogLoading.cancel();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("zzzz", "onResponse-product-error: " + t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                dialogLoading.cancel();
            }
        });
    }

    private void initView() {
        dialogLoading = new ProgressDialogLoading(getActivity());
    }

    private void initController() {
        binding.btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CrudProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.imgChat.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ChatUserActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void updateProduct(Product product) {
        Intent intent = new Intent(getActivity(), CrudProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putSerializable("product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteProdcut(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogLoading.show();
                ApiService.apiService.deleteProduct(product.getId()).enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        ServerResponse serverResponse = response.body();
                        Log.d("zzzzz", "onResponse: " + serverResponse);
                        Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        dialogLoading.cancel();
                        callApiGetProduct();
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.d("zzzz", "onFailure: " + t.toString());
                        Toast.makeText(getActivity(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        dialogLoading.cancel();
                    }
                });
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void searchProduct() {
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //trước khi text thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //trong khi text thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Sau khi text thay đổi
                getDataSearch();
            }
        });
    }

    private void getDataSearch() {
        dialogLoading.show();
        String product_name = binding.edSearch.getText().toString().trim();
        if (!product_name.isEmpty()) { //nếu có dữ liệu mới search
            ApiService.apiService.getProductSearch(product_name).enqueue(
                    new Callback<ProductResponse>() {
                        @Override
                        public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                            if (response.isSuccessful()){
                                ProductResponse productResponse = response.body();
                                if (productResponse.isSuccess()){
                                    productAdapter.setListProduct(productResponse.getResult()); // set dữ liệu lên rcv
                                    dialogLoading.cancel();
                                }else {
                                    CheckConection.ShowToast(getContext(), "Không tìm thấy sản phẩm");
                                    dialogLoading.cancel();
                                }
                            }else {
                                dialogLoading.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductResponse> call, Throwable t) {

                        }
                    }
            );
        }else { // không thì trả về list sản phẩm ban đầu
            getListProduct();
        }
    }
}