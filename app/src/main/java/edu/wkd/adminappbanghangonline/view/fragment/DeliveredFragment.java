package edu.wkd.adminappbanghangonline.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.wkd.adminappbanghangonline.databinding.FragmentDeliveredBinding;
import edu.wkd.adminappbanghangonline.model.obj.Order;
import edu.wkd.adminappbanghangonline.ultil.GetListOrderInterface;
import edu.wkd.adminappbanghangonline.view.activity.OrderActivity;
import edu.wkd.adminappbanghangonline.view.adapter.OrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveredFragment extends Fragment implements GetListOrderInterface {
    private FragmentDeliveredBinding binding;
    private ArrayList<Order> listOrder;
    private OrderAdapter orderAdapter;
    public DeliveredFragment() {
        // Required empty public constructor
    }

    public static DeliveredFragment newInstance(String param1, String param2) {
        DeliveredFragment fragment = new DeliveredFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDeliveredBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData(){
        OrderActivity orderActivity = (OrderActivity) getActivity();
        if (orderActivity != null){
            orderActivity.setGetListOrderInterface(this::getListOrder);
            orderActivity.getListOrderByStatus(2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void getListOrder(List<Order> list) {
        listOrder = (ArrayList<Order>) list;
        if (listOrder.isEmpty() || listOrder.size() == 0){
            binding.layoutEmptyOrder.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }else{
            binding.layoutEmptyOrder.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
            orderAdapter = new OrderAdapter(getContext().getApplicationContext());
            orderAdapter.setData(listOrder);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.rvOrderDelivered.setLayoutManager(manager);
            binding.rvOrderDelivered.setAdapter(orderAdapter);
        }
    }
}