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

public class IncomeGraph {
    private final static String TAG = "IncomeGraph";

    private View   view;
    private Context mContext;
    private final Utils  utils = new Utils();

    public IncomeGraph(Context context, Integer name, Number[] ages, Number[] expenses, Number[] account401k, Number[] account403b, Number[] brokerage, Number[] cashBalance,
                       Number[] iraRoth, Number[] iraTraditional, Number[] pension, Number[] salary, Number[] savings, Number[]socialSecurity) {

       String title = utils.getString(context, name);
       view = createView(context, title, ages, expenses, account401k, account403b, brokerage, cashBalance, iraRoth, iraTraditional, pension, salary, savings, socialSecurity);;
       mContext = context;
    }

    public String getName() {
        return utils.getString(mContext, R.string.output_header_income_graph);
    }

    public View getView() {
        return view;
    }

    @SuppressWarnings("WeakerAccess")
    @SuppressLint("InflateParams")
    public View createView(Context context, String title, final Number[] ages, Number[] expense, Number[] account401k, Number[] account403b, Number[] brokerage, Number[] cashBalance,
                           Number[] iraRoth, Number[] iraTraditional, Number[] pension, Number[] salary, Number[] savings, Number[]socialSecurity) {
        TextView titleView;
        Number maxY;
        XYPlot plot;


//        Log.d(TAG, "createView(ext context, String title, final Number[] ages, ...");
//        Log.d(TAG, "    title: " + title);
//        Log.d(TAG, "    income.length: " + income.length);
//        for (int i=0; i<income.length; i++) {
//            Log.d(TAG, "    i: " + i);
//            Log.d(TAG, "    age: " + ages[i]);
//            Log.d(TAG, "    income[i]:" + income[i]);
//        }

        view = LayoutInflater.from(context).inflate(R.layout.output_income_graph, null);

        titleView=(TextView) view.findViewById(R.id.title);
        if (titleView != null) {
            titleView.setText(title);
        }

        plot=(XYPlot) view.findViewById(R.id.plot);

        float height = plot.getLegend().getSize().getHeight().getValue();
        float width = context.getResources().getDisplayMetrics().widthPixels * 0.79f; // 480 * 0.79 = 379.2
        Size size = new Size((height*2), SizeMode.ABSOLUTE, width, SizeMode.ABSOLUTE);
        plot.getLegend().setSize(size);
        plot.getLegend().setMargins(height +(height/4), 0, 0, height/2); // left, top, right, bottom
        plot.getLegend().setTableModel(new DynamicTableModel(3, 2)); // for 2 rows & 3 entries per row

        plot.setRangeLowerBoundary(0.0, BoundaryMode.FIXED);
        plot.setRangeLabel("In Thousands (Expenses)");

        maxY = expense[expense.length-1];
        if (utils.inLandscapeMode(context)) {
            plot.setRangeStep(StepMode.INCREMENT_BY_VAL, (double) maxY / 3.0); // gives 3 gridlines + 0 value gridline
        } else {
            plot.setRangeStep(StepMode.INCREMENT_BY_VAL, (double) maxY / 7.0); // 7 gives 7 gridlines + 0 value gridline
                                                                                    //  1.5 gives 1.5 gridlines
        }

        plot.getInnerLimits().setMaxY(maxY);

        if (checkIfSeriesIsGreaterThanZero(pension)) {
            XYSeries seriesPension = new SimpleXYSeries(Arrays.asList(pension), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Pension");
            BarFormatter seriesPensionFormat = new BarFormatter(Color.BLUE, Color.LTGRAY);
            plot.addSeries(seriesPension, seriesPensionFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(account401k)) {
            XYSeries series401k = new SimpleXYSeries(Arrays.asList(account401k), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "401K");
            BarFormatter series401kFormat = new BarFormatter(Color.YELLOW, Color.LTGRAY);
            plot.addSeries(series401k, series401kFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(account403b)) {
            XYSeries series403b = new SimpleXYSeries(Arrays.asList(account403b), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "403B");
            BarFormatter series403bFormat = new BarFormatter(Color.CYAN, Color.LTGRAY);
            plot.addSeries(series403b, series403bFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(brokerage)) {
            XYSeries seriesBrokerage = new SimpleXYSeries(Arrays.asList(brokerage), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Broker.");
            BarFormatter seriesBrokerageFormat = new BarFormatter(Color.RED, Color.LTGRAY);
            plot.addSeries(seriesBrokerage, seriesBrokerageFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(cashBalance)) {
            XYSeries seriesCashBalance = new SimpleXYSeries(Arrays.asList(cashBalance), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "C.B.");
            BarFormatter seriesCashBalanceFormat = new BarFormatter(Color.GRAY, Color.LTGRAY);
            plot.addSeries(seriesCashBalance, seriesCashBalanceFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(iraRoth)) {
            XYSeries seriesIraRoth = new SimpleXYSeries(Arrays.asList(iraRoth), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Roth");
            BarFormatter seriesIraRothFormat = new BarFormatter(Color.MAGENTA, Color.LTGRAY);
            plot.addSeries(seriesIraRoth, seriesIraRothFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(iraTraditional)) {
            XYSeries seriesIraTraditional = new SimpleXYSeries(Arrays.asList(iraTraditional), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "IRA");
            BarFormatter seriesIraTraditionalFormat = new BarFormatter(Color.WHITE, Color.LTGRAY);
            plot.addSeries(seriesIraTraditional, seriesIraTraditionalFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(salary)) {
            XYSeries seriesSalary = new SimpleXYSeries(Arrays.asList(salary), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Salary");
            BarFormatter seriesSalaryFormat = new BarFormatter(Color.BLACK, Color.LTGRAY);
            plot.addSeries(seriesSalary, seriesSalaryFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(savings)) {
            XYSeries seriesSavings = new SimpleXYSeries(Arrays.asList(savings), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Savings");
            BarFormatter seriesSavingsFormat = new BarFormatter(Color.DKGRAY, Color.LTGRAY);
            plot.addSeries(seriesSavings, seriesSavingsFormat);
        }

        if (checkIfSeriesIsGreaterThanZero(socialSecurity)) {
            XYSeries seriesSocialSecurity = new SimpleXYSeries(Arrays.asList(socialSecurity), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "S.S.");
            BarFormatter seriesSocialSecurityFormat = new BarFormatter(Color.GREEN, Color.LTGRAY);
            plot.addSeries(seriesSocialSecurity, seriesSocialSecurityFormat);
        }

        BarRenderer renderer = plot.getRenderer(BarRenderer.class);
        renderer.setBarOrientation(BarRenderer.BarOrientation.STACKED);
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

    private boolean checkIfSeriesIsGreaterThanZero(Number[] series) {
        boolean hasNumbersGreaterThanZero = false;

        if (series != null) {
            for (Number sery : series) {
                double number = (double) sery;
                if (number > 0) {
                    hasNumbersGreaterThanZero = true;
                    break;
                }
            }
        }

        return hasNumbersGreaterThanZero;
    }
}
