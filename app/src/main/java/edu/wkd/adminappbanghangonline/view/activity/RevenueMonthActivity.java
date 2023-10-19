package edu.wkd.adminappbanghangonline.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.ActivityRevenueMonthBinding;
import edu.wkd.adminappbanghangonline.model.response.RevenueResponse;
import edu.wkd.adminappbanghangonline.ultil.CheckConection;
import edu.wkd.adminappbanghangonline.ultil.ProgressDialogLoading;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevenueMonthActivity extends AppCompatActivity {

    private ActivityRevenueMonthBinding binding;
    private ProgressDialogLoading dialogLoading;
    private String[] arrMonths = {"Tháng","Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
            "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
    private static final List<String> xValues = new ArrayList<>();
    private static final ArrayList<BarEntry> entries = new ArrayList<>();
    private ArrayAdapter<String> monthAdrapter;
    private float resultRevenueMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRevenueMonthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialogLoading = new ProgressDialogLoading(this);
        setSpinner();
        spinnerEvent();
    }

    private void setSpinner() {
        monthAdrapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrMonths);
        monthAdrapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthSpinner.setAdapter(monthAdrapter);
    }

    private void spinnerEvent() {
        binding.barChartRevenueMonth.getAxisRight().setDrawLabels(false);
        Date currentDate = new Date();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int currentYear = Integer.parseInt(yearFormat.format(currentDate));

        binding.monthSpinner.setSelected(false);
        binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int currentMonth = position;
                dialogLoading.show();
                ApiService.apiService.getRevenueMonth(currentYear+"-"+currentMonth).enqueue(
                        new Callback<RevenueResponse>() {
                            @Override
                            public void onResponse(Call<RevenueResponse> call, Response<RevenueResponse> response) {
                                if (response.isSuccessful()){
                                    RevenueResponse revenueResponse = response.body();
                                    if (revenueResponse.isSuccess()){
                                        resultRevenueMonth = revenueResponse.getResult(); //ket qua thong ke
                                        setUpBarChart(currentMonth,resultRevenueMonth);
                                        dialogLoading.hide();
                                    }
                                }else {
                                    CheckConection.ShowToast(RevenueMonthActivity.this,
                                            "Không lấy được dữ liệu!");
                                }
                            }

                            @Override
                            public void onFailure(Call<RevenueResponse> call, Throwable t) {
                                Log.e("error-"+this, t.getMessage());
                            }
                        }
                );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpBarChart(int currentMonth, float resultRevenueMonth) {

        xValues.clear();
        entries.clear();

        xValues.add(String.valueOf(currentMonth)); //
        entries.add(new BarEntry(xValues.size(), resultRevenueMonth));

        //set trục y
        YAxis yAxis = binding.barChartRevenueMonth.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(1000000000);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(100000000);

        binding.barChartRevenueMonth.getDescription().setEnabled(false);
        binding.barChartRevenueMonth.setHighlightFullBarEnabled(true);


        binding.barChartRevenueMonth.getXAxis()
                .setValueFormatter(new IndexAxisValueFormatter(xValues));

        binding.barChartRevenueMonth.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChartRevenueMonth.getXAxis().setGranularity(1f);
        binding.barChartRevenueMonth.getXAxis().setGranularityEnabled(false);

        BarDataSet barDataSet = new BarDataSet(entries, "Tháng");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(barDataSet);
        binding.barChartRevenueMonth.setData(barData);
        binding.barChartRevenueMonth.invalidate();
    }
}