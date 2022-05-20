package ru.vsu.hb_front;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.adapters.RecommendationsAdapter;
import ru.vsu.hb_front.adapters.TransactionsAdapter;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.FragmentCategoriesBinding;
import ru.vsu.hb_front.databinding.FragmentStatisticsBinding;
import ru.vsu.hb_front.dto.UserStatisticsRecommendations;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding b;
    private Disposable userStatisticsDisposable;
    private UserStatisticsRecommendations statistics;
    private RecommendationsAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);

        userStatisticsDisposable = Api.getInstance().getUserStat().subscribe(resp->{
            if(resp.isSuccessful()){
                statistics = resp.body().getData();

                b.curMonthChart.getDescription().setEnabled(false);
                b.curMonthChart.setEntryLabelColor(Color.BLACK);
                b.curMonthChart.setCenterText("Текущий месяц");
                b.curMonthChart.setCenterTextSize(15f);
                b.curMonthChart.setHoleRadius(45f);
                b.curMonthChart.setTransparentCircleRadius(50f);
                b.curMonthChart.getLegend().setEnabled(false);
                b.curMonthChart.setData(generateCurMonthPieData());
                b.curMonthChart.notifyDataSetChanged();
                b.curMonthChart.invalidate();

                b.preMonthChart.getDescription().setEnabled(false);
                b.preMonthChart.setEntryLabelColor(Color.BLACK);
                b.preMonthChart.setCenterText("Предыдуший месяц");
                b.preMonthChart.setCenterTextSize(15f);
                b.preMonthChart.setHoleRadius(45f);
                b.preMonthChart.setTransparentCircleRadius(50f);
                b.preMonthChart.getLegend().setEnabled(false);
                b.preMonthChart.setData(generatePreMonthPieData());
                b.preMonthChart.notifyDataSetChanged();
                b.preMonthChart.invalidate();


                adapter = new RecommendationsAdapter(statistics.getRecommendations(), getActivity());
                LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
                b.recommendation.setLayoutManager(llm);
                b.recommendation.setAdapter(adapter);

            }else{
                Toast.makeText(getContext(), "Возникла ошибка(", Toast.LENGTH_SHORT).show();
            }
        }, err->{
            err.printStackTrace();
            Toast.makeText(getContext(), "Возникла ошибка(", Toast.LENGTH_SHORT).show();
        });

        return b.getRoot();
    }

    private PieData generateCurMonthPieData() {

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(String s: statistics.getCurrMonthPercents().keySet()) {
            PieEntry entry = new PieEntry(statistics.getCurrMonthPercents().get(s).floatValue(), s);
            entries1.add(entry);
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(Typeface.DEFAULT);

        return d;
    }

    private PieData generatePreMonthPieData() {

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(String s: statistics.getPrevMonthPercents().keySet()) {
            PieEntry entry = new PieEntry(statistics.getPrevMonthPercents().get(s).floatValue(), s);
            entries1.add(entry);
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(Typeface.DEFAULT);

        return d;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (userStatisticsDisposable != null) userStatisticsDisposable.dispose();
    }
}