package com.hoangpro.jsouppractice.morefunc;

import android.util.Log;

public class MySession {
    public static int currentId;
    public static String currentJson;

    public static long toMilies(String time) {
        if (time!=null) {
            String[] arr = time.split(":");
            long result = 0;
            if (arr.length == 3) {
                for (int i = 2; i >= 0; i--) {
                    switch (i) {
                        case 2:
                            result += (long) (Double.parseDouble(arr[i].replace(",", ".")) * 1000);
                            break;
                        case 1:
                            result += Integer.parseInt(arr[i]) * 60000;
                            break;
                        case 0:
                            result += Integer.parseInt(arr[i]) * 3600000;
                            break;
                    }
                }
            } else if (arr.length == 2) {
                for (int i = 1; i >= 0; i--) {
                    switch (i) {
                        case 1:
                            result += (long) (Double.parseDouble(arr[i].replace(",", ".")) * 1000);
                            break;
                        case 0:
                            result += Integer.parseInt(arr[i]) * 60000;
                            break;
                    }
                }
            }
            return result;
        }
        return 0;
    }
}
