package com.doug.cashflow.view.outputs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.doug.cashflow.R;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class SavingsGraph {
    private final static String TAG = "SavingsGraph";

    private View view;
    private Context mContext;
    private final Utils  utils = new Utils();

    public SavingsGraph(Context context, Integer name, Number[] ages, Number[] savings) {

        mContext = context;
        String title = utils.getString(context, name);
        view = createView(context, title, ages, savings);

    }

    public String getName() {
        return utils.getString(mContext, R.string.output_header_savings_graph);
    }

    public View getView() {
        return view;
    }

    @SuppressWarnings("WeakerAccess")
    @SuppressLint("InflateParams")
    public View createView(Context context, String title, final Number[] ages, final Number[] savings) {
        TextView titleView;
        XYPlot plot;

//        Log.d(TAG, "createView(LayoutInflater inflater, ViewGroup container)");
/*        Log.d(TAG, "    title: " + title);
        Log.d(TAG, "    ages.length: " + ages.length);
        Log.d(TAG, "    savings.length: " + savings.length);
        for (int i=0; i<savings.length; i++) {
            Log.d(TAG, "    i: " + i);
            Log.d(TAG, "    age: " + ages[i]);
            Log.d(TAG, "    savings[i]:" + savings[i]);
        }
*/
        view = LayoutInflater.from(context).inflate(R.layout.output_savings_graph, null);

        titleView=(TextView) view.findViewById(R.id.title);
        if (titleView != null) {
            titleView.setText(title);
        }

        plot=(XYPlot) view.findViewById(R.id.plot);

        float height = plot.getLegend().getSize().getHeight().getValue();
        float width = context.getResources().getDisplayMetrics().widthPixels * 0.79f; // 480 * 0.79 = 379.2
        Size size = new Size(height, SizeMode.ABSOLUTE, width, SizeMode.ABSOLUTE);
        plot.getLegend().setSize(size);
        plot.getLegend().setMargins(height +(height/4), 0, 0, height/2); // left, top, right, bottom
        plot.getLegend().setTableModel(new DynamicTableModel(3, 1)); // for 1 rows & 3 entries per row

        plot.setRangeLowerBoundary(0.0, BoundaryMode.FIXED);
        plot.setRangeLabel("In Millions");

        // Get the largest value
        double maxY = 0.0;
        for (Number saving : savings) {
            if ((double) saving > maxY) {
                maxY = (double) saving;
            }
        }

        if (utils.inLandscapeMode(context)) {
            plot.setRangeStep(StepMode.INCREMENT_BY_VAL, maxY / 3.0); // gives 3 gridlines + 0 value gridline
        } else {
            plot.setRangeStep(StepMode.INCREMENT_BY_VAL, maxY / 7.0); // gives 7 gridlines + 0 value gridline
        }

        plot.getInnerLimits().setMaxY(maxY);

        XYSeries series = new SimpleXYSeries(Arrays.asList(savings), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, mContext.getString(R.string.savings));
        BarFormatter seriesFormat = new BarFormatter(Color.argb(255,0,0,100), Color.LTGRAY);
        plot.addSeries(series, seriesFormat);

        BarRenderer renderer = plot.getRenderer(BarRenderer.class);
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP, 0);

        //Y axis config
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
                int i=Math.round(((Number) obj).floatValue());
                return toAppendTo.append(ages[i]);
            }

            @Override
            public Object parseObject(String source, @NonNull ParsePosition pos) {
                return null;
            }
        });

        return view;
    }
}
