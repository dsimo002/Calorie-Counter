package com.cs180.team2.caloriecounter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by briannguyen on 11/11/2016.
 */

public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Mon", "Tues", "Wed", "Thur", "Fri", "Sat","Sun"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {   //change only y values please, y values are total calories consumed
                new DataPoint(0, 10),   //Monday
                new DataPoint(1, 5),    //Tuesday
                new DataPoint(2, 6),    //Wednesday
                new DataPoint(3, 1),    //Thursday
                new DataPoint(4, 12),   //Friday
                new DataPoint(5, 6),    //Saturday
                new DataPoint(6, 0)     //Sunday

        });

        LineGraphSeries<DataPoint> line = new LineGraphSeries<>(new DataPoint[] {   //change y according to calorie goal
                new DataPoint(0,8),
                new DataPoint(1,8),
                new DataPoint(2,8),
                new DataPoint(3,8),
                new DataPoint(4,8),
                new DataPoint(5,8),
                new DataPoint(6,8)

        });

        series.setDrawValuesOnTop(true);
        series.setTitle("Calorie Intake");
        series.setColor(Color.RED);
        series.setValuesOnTopColor(Color.RED);

        graph.addSeries(series);
        graph.addSeries(line);

        line.setTitle("Calorie Goal");
        line.setColor(Color.BLUE);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


// styling
        series.setSpacing(25);

    }

}
