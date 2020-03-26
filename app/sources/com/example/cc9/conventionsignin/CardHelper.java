package com.example.cc9.conventionsignin;

public class CardHelper {
    public static String getHexString(byte[] b) throws Exception {
        String result = BuildConfig.FLAVOR;
        for (int i = b.length - 1; i >= 0; i--) {
            result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
        }
        return result;
    }
}
