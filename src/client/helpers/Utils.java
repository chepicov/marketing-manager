package client.helpers;

import java.text.DecimalFormat;

public class Utils {
    public static String getCTR(int clicks, int views) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format((clicks / Math.max(views, 1.00f)) * 100.0f);
    }

    public static boolean isDigitDouble(String s) throws NumberFormatException {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
