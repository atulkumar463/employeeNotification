package com.beadcore.employeenotification.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.beadcore.employeenotification.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.chart1)
    BarChart chart;

    ArrayList<BarEntry> x = new ArrayList<>();
    HashMap<String, String> dailyAttendenceMarkIn;
    HashMap<String, String> dailyAttendenceMarkOut;
    HashMap<String, String> overtime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);

        dailyAttendenceMarkIn = (HashMap<String, String>) getIntent().getSerializableExtra("markin");

        Log.i("hashmap", "onCreate: " + dailyAttendenceMarkIn);

        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        chart.setMaxVisibleValueCount(60);


        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        //       ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(30);
        //   xAxis.setValueFormatter(xAxisFormatter);


        // ValueFormatter custom = new MyValueFormatter("$");

        YAxis leftAxis = chart.getAxisLeft();
        //  leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(10, false);
        //    leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //  rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        // rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        ArrayList<BarEntry> x = new ArrayList<>();
        ArrayList<String> xdata = new ArrayList<>();

        int i = 0;
        for (Map.Entry<String, String> entry : dailyAttendenceMarkIn.entrySet()) {
            xdata.add(entry.getKey());
            Log.i("hashmap", "onCreate: key " + entry.getKey() + "value " + entry.getValue());
            x.add(new BarEntry(i, Float.parseFloat(entry.getValue())));
            i += 1;
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xdata));

        BarDataSet set1;

        set1 = new BarDataSet(x,"In-Time of this Month" );
        set1.setValues(x);


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);


        BarData data = new BarData(dataSets);
        data.setValueTextSize(.8f);
        data.setBarWidth(0.9f);

        chart.setData(data);

      /*  BarData data = new BarData(x);
        data.setValueTextSize(10f);
        data.setValueTypeface(tfLight);
        data.setBarWidth(0.9f);
    chart.setData(x);*/


    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {


    }

    @Override
    public void onNothingSelected() {

    }
}
