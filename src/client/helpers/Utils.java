package client.helpers;

import java.text.DecimalFormat;

public class Utils {
    public static String getCTR(int clicks, int views) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format((clicks / Math.max(views, 1.00f)) * 100.0f);
    }
}
