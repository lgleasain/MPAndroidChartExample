package com.polyglotprogramminginc.mpandroidchartexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoundChartFragment extends Fragment {


    public RoundChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_round_chart, container, false);
    }

        @Override
    public void onStart(){
        super.onStart();
        drawGraph(getView());
    }

    private void drawGraph(View view){
        int stepGoal = 10000;
        DecoView decoView = (DecoView) view.findViewById(R.id.stepsArc);

        // delete any current graph elements
        decoView.deleteAll();

        // do not draw a full circle
        decoView.configureAngles(330, 0);

        // draw a gray background
        decoView.addSeries(new SeriesItem.Builder(getResources().getColor(R.color.decobackground),
                getResources().getColor(R.color.decobackground))
                .setRange(0, stepGoal, stepGoal)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(20f))
                .build());

        // draw the gradient arc.
        int seriesIndex = decoView.addSeries(new SeriesItem.Builder(getResources().getColor(R.color.lineGraphLow),
                getResources().getColor(R.color.lineGraphHigh))
                .setRange(0, stepGoal, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(18f))
                .build());

        // draw the gradient arc again to add vibrance.
        int seriesIndex2 = decoView.addSeries(new SeriesItem.Builder(getResources().getColor(R.color.lineGraphLow),
                getResources().getColor(R.color.lineGraphHigh))
                .setRange(0, stepGoal, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(12f))
                .build());

        // reset the view again
        decoView.executeReset();


        int graphSteps = 9000;
        if (graphSteps > stepGoal)
            graphSteps = stepGoal;

        // draw the gray background
        decoView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(100)
                .setDuration(100)
                .build());

        // draw the first colored arc
        decoView.addEvent(new DecoEvent.Builder(graphSteps)
                .setIndex(seriesIndex)
                .setDelay(200)
                .setDuration(1000)
                .build());

        // draw the second color arc.
        decoView.addEvent(new DecoEvent.Builder(graphSteps)
                .setIndex(seriesIndex2)
                .setDelay(800)
                .setDuration(1000)
                .build());

        decoView.invalidate();
    }

    protected float getDimension(float base) {
        // get the proper scaled dimensions for the display
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, base, getResources().getDisplayMetrics());
    }
}


