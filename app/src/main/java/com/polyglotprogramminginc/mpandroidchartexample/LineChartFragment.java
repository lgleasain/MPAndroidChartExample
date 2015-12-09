package com.polyglotprogramminginc.mpandroidchartexample;


import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LineChartFragment extends Fragment {

    LineChart lineChart;

    public LineChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_line_chart, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        getView().post(new Runnable() {
            @Override
            public void run() {
                drawLineGraph();
            }
        });
    }
    /**
     *  The code for creating the line graph
     */
    private void drawLineGraph(){
        setupLineChart();

        // get the paint renderer to create the line shading.
        Paint paint = lineChart.getRenderer().getPaintRender();
        int height = lineChart.getHeight();

        LinearGradient linGrad = new LinearGradient(0, 0, 0, height,
                getResources().getColor(R.color.lineGraphHigh),
                getResources().getColor(R.color.lineGraphLow),
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);

        setupLineChartData();

        // dont forget to refresh the drawing
        lineChart.invalidate();
    }

    private void setupLineChart(){
         // set up the line chart
        lineChart = (LineChart) getView().findViewById(R.id.active_minutes_day_chart);

        // setting up the max and min values for the graph
        int rawMaxValue = 60;
        int maxValue = (int) (rawMaxValue * 1.05);
        int minValue = 0;

        // invalidate the graph for good measure
        lineChart.invalidate();

        // disable the legend
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        // disable the description
        lineChart.setDescription("");

        // set up the axis at the bottom of the graph
        XAxis x = lineChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setTypeface(Typeface.DEFAULT_BOLD);
        x.setTextSize(13);
        x.setLabelsToSkip(5);
        x.setAvoidFirstLastClipping(false);

        // animate the drawing of the graph....because it is cool
        lineChart.animateXY(2000, 2000);

        // turn off the y axis labels and set the min and max values
        YAxis yLeft = lineChart.getAxisLeft();
        yLeft.setEnabled(false);
        yLeft.setAxisMinValue(minValue);
        yLeft.setAxisMaxValue(maxValue);
        yLeft.setStartAtZero(false);
        YAxis yRight = lineChart.getAxisRight();
        yRight.setEnabled(false);
        yRight.setAxisMinValue(minValue);
        yRight.setAxisMaxValue(maxValue);
        yRight.setStartAtZero(false);

        lineChart.setDrawGridBackground(false);
    }

    private void setupLineChartData(){
         // git the chart to the screen
        lineChart.fitScreen();

         ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> vals1 = new ArrayList<>();

        vals1.add(new Entry(0,0));
        vals1.add(new Entry(0,1));
        for (int i = 2; i < 24; i++) {
            vals1.add(new Entry((int)(Math.random() * 60), i));
        }

        vals1.add(new Entry(0, 24));
        vals1.add(new Entry(0, 25));

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
        set1.setDrawCubic(true);
        set1.setCubicIntensity(0.2f);
        set1.setDrawCircles(false);
        set1.setLineWidth(6f);
        set1.setCircleSize(5f);
        set1.setDrawHorizontalHighlightIndicator(false);

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);
        data.setValueTextSize(9f);
        data.setDrawValues(false);

        // set data
        lineChart.setData(data);
    }
}
