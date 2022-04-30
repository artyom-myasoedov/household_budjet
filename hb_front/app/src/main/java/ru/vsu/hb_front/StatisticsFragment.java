package ru.vsu.hb_front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.vsu.hb_front.databinding.FragmentCategoriesBinding;
import ru.vsu.hb_front.databinding.FragmentStatisticsBinding;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding b;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        b = FragmentStatisticsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}