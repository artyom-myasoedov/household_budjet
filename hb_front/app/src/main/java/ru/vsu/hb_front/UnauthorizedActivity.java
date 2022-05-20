package ru.vsu.hb_front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.ActivityUnauthorizedBinding;
import ru.vsu.hb_front.dto.GlobalStatistics;

public class UnauthorizedActivity extends AppCompatActivity {

    private ActivityUnauthorizedBinding b;
    private Disposable globalStatisticDisposable;
    private GlobalStatistics statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_unauthorized);

        globalStatisticDisposable = Api.getInstance().getGlobalStat().subscribe(resp->{
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

            }else{
                Toast.makeText(getBaseContext(), "Возникла ошибка(", Toast.LENGTH_SHORT).show();
            }
        }, err->{
            err.printStackTrace();
            Toast.makeText(getBaseContext(), "Возникла ошибка(", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (globalStatisticDisposable != null) globalStatisticDisposable.dispose();
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
}