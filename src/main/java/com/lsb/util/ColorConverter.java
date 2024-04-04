package com.lsb.util;

import javafx.scene.paint.Color;

public class ColorConverter {
    private static String encode(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    private static double decode(int i) {
        double d = (double) i / 255;
        return d;
    }
    
    public static String toHexString(Color value) {
        return "#" + (encode(value.getRed()) + encode(value.getGreen()) + encode(value.getBlue())).toUpperCase();
    }

    public static Color toColor(String str) {
        int red = Integer.valueOf(str.substring(1, 3), 16);
        int green = Integer.valueOf(str.substring(3, 5), 16);
        int blue = Integer.valueOf(str.substring(5, 7), 16);
        
        return new Color(
            decode(red),
            decode(green),
            decode(blue),
            1);
    }
}
