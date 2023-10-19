package edu.wkd.adminappbanghangonline.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.FragmentTopProductBinding;
import edu.wkd.adminappbanghangonline.model.obj.Product;
import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import edu.wkd.adminappbanghangonline.ultil.CheckConection;
import edu.wkd.adminappbanghangonline.ultil.ProgressDialogLoading;
import edu.wkd.adminappbanghangonline.view.adapter.ProductAdapter;
import edu.wkd.adminappbanghangonline.view.adapter.TopProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopProductFragment extends Fragment {

    private FragmentTopProductBinding binding;
    private List<Product> listProduct;
    private TopProductAdapter adapter;
    private ProgressDialogLoading dialogLoading;

    public TopProductFragment() {
        // Required empty public constructor
    }

    public static TopProductFragment newInstance() {
        TopProductFragment fragment = new TopProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setAdapter();
        getTopProduct();
    }

    private void setAdapter() {
        listProduct = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                1, GridLayoutManager.VERTICAL, false);
        binding.rcvTopProduct.setLayoutManager(gridLayoutManager);
        binding.rcvTopProduct.setHasFixedSize(true);
        adapter = new TopProductAdapter(getActivity(),listProduct);
        binding.rcvTopProduct.setAdapter(adapter);
    }

    private void getTopProduct() {
        dialogLoading.show();
        ApiService.apiService.getTopProduct()
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        if (response.isSuccessful()) {
                            ProductResponse productResponse = response.body();
                            if (productResponse.isSuccess()) {
                                adapter.setListProduct(productResponse.getResult()); // set dữ liệu lên rcv
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

                    }
                });
    }

    private void initView() {
        dialogLoading = new ProgressDialogLoading(getActivity());
    }
}