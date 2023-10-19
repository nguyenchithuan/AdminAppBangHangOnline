package edu.wkd.adminappbanghangonline.view.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.FragmentRevenueBinding;
import edu.wkd.adminappbanghangonline.model.response.RevenueResponse;
import edu.wkd.adminappbanghangonline.ultil.CheckConection;
import edu.wkd.adminappbanghangonline.ultil.ProgressDialogLoading;
import edu.wkd.adminappbanghangonline.ultil.Validator;
import edu.wkd.adminappbanghangonline.view.activity.RevenueMonthActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevenueFragment extends Fragment {

    private FragmentRevenueBinding binding;
    private ProgressDialogLoading dialogLoading;
    private int statusDateTime; //1=day 2=month 3=year
    private boolean statusEdDay; //true = day_a false = day_b
    private DecimalFormat decimalFormat;
    private BarChart barChartRevenueMonth;

    public RevenueFragment() {
        // Required empty public constructor
    }

    public static RevenueFragment newInstance() {
        RevenueFragment fragment = new RevenueFragment();
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
        binding = FragmentRevenueBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideView();
        decimalFormat = new DecimalFormat("###,###,###,### VNĐ");
        dialogLoading = new ProgressDialogLoading(getActivity());
        binding.btnDay.setOnClickListener(v -> {
            setHideBtn();
            binding.layoutInputDay.setVisibility(View.VISIBLE);
            statusDateTime = 1;

            //click edit chọn ngày
            binding.inputDayA.setOnClickListener(v1 -> {
                statusEdDay = true;
                showDatePickerDialog();
            });
            binding.inputDayB.setOnClickListener(v1 -> {
                statusEdDay = false;
                showDatePickerDialog();
            });
        });

        binding.btnCancel.setOnClickListener(v -> {
            statusDateTime = 0;
            showHideBtn();
            hideView();
        });

        binding.btnMonth.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RevenueMonthActivity.class));
        });

        binding.btnGet.setOnClickListener(v -> {
            switch (statusDateTime){
                case 1: //day
                    getRevenueDay();
                    break;
                default:
                    CheckConection.ShowToast(getActivity(),
                            "Xin mời lựa chọn mục thống kê!");
            }
        });
    }

    private void showDatePickerDialog() {
        // Lấy ngày, tháng và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Tạo DatePickerDialog và thiết lập ngày, tháng, năm mặc định
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        if (statusEdDay == true){
                            binding.inputDayA.setText(selectedDate);
                        }else {
                            binding.inputDayB.setText(selectedDate);
                        }
                    }

                }, currentYear, currentMonth, currentDay);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void getRevenueDay() {
        String day_a = binding.inputDayA.getText().toString().trim(); //day-begin
        String day_b = binding.inputDayB.getText().toString().trim(); //day-ena
        if (day_a.isEmpty() || day_b.isEmpty()){
            CheckConection.ShowToast(getActivity(),"Xin chọn hoặc nhập ngày!");
        } else if (!Validator.isValidDateTime(day_a) || !Validator.isValidDateTime(day_b)) {
            CheckConection.ShowToast(getActivity(),"Xin nhập đúng định dạnh: năm-tháng-ngày");
        } else {
            dialogLoading.show();
            ApiService.apiService.getRevenueDay(day_a,day_b)
                    .enqueue(new Callback<RevenueResponse>() {
                        @Override
                        public void onResponse(Call<RevenueResponse> call,
                                               Response<RevenueResponse> response) {
                            if (response.isSuccessful()){
                                RevenueResponse revenueResponse = response.body();
                                if (revenueResponse.isSuccess()){
                                    binding.tvResult.setText(decimalFormat.format(revenueResponse.getResult()));
                                    CheckConection.ShowToast(getActivity(),
                                            "lấy ngày thành công!");
                                    binding.tvResult.setVisibility(View.VISIBLE);
                                    dialogLoading.hide();
                                }else {
                                    CheckConection.ShowToast(getActivity(),
                                            "Thống kê thất bại!");
                                    dialogLoading.hide();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RevenueResponse> call, Throwable t) {
                            CheckConection.ShowToast(getActivity(),t.getMessage());
                            dialogLoading.hide();
                        }
                    });
        }

    }

    private void setHideBtn() {
        binding.btnMonth.setVisibility(View.INVISIBLE);
        binding.btnDay.setVisibility(View.INVISIBLE);
    }

    private void showHideBtn() {
        binding.btnMonth.setVisibility(View.VISIBLE);
        binding.btnDay.setVisibility(View.VISIBLE);
    }

    private void hideView() {
        binding.tvResult.setVisibility(View.INVISIBLE);
        binding.layoutInputDay.setVisibility(View.INVISIBLE);
    }
}