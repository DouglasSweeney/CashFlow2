package com.doug.cashflow.model.db;

import java.text.DecimalFormat;

/**
 * Created by Doug on 6/1/2017.
 */

public class Util {
    /**
     * Convert an double that is based on the specified format.
     *
     * @since  1.0
     *
     * @param  input The double to format
     *
     * @return String
     */
    public String getDollarFormat(double input) {
        DecimalFormat defaultFormat = new DecimalFormat("###,###,##0.00");

        defaultFormat.setMaximumFractionDigits(2);
        defaultFormat.setMinimumFractionDigits(2);

        return defaultFormat.format(input);
    }

    /**
     * Convert an percentage that is based on the specified format.
     *
     * @since  1.0
     *
     * @param  input The double to format to a percentage
     *
     * @return String
     */
    public String getPercentFormat(double input) {
        DecimalFormat defaultFormat = new DecimalFormat("##0.0");

        defaultFormat.setMaximumFractionDigits(1);
        defaultFormat.setMinimumFractionDigits(1);

        return defaultFormat.format(input);
    }

}
