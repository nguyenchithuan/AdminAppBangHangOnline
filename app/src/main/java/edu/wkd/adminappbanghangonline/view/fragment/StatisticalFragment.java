package edu.wkd.adminappbanghangonline.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.databinding.FragmentStatisticalBinding;

public class StatisticalFragment extends Fragment {

    private FragmentStatisticalBinding binding;
    public StatisticalFragment() {
        // Required empty public constructor
    }

    public static StatisticalFragment newInstance() {
        StatisticalFragment fragment = new StatisticalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticalBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}