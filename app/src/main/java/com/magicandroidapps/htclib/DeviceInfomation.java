package com.magicandroidapps.htclib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;

public class DeviceInfomation {


    public static String getContent(InputStream paramInputStream) throws IOException {
        if (paramInputStream == null)
            return null;
        byte[] arrayOfByte = new byte[1024];
        String str = "";
        while (true) {
            int i = paramInputStream.read(arrayOfByte);
            String str1 = str;
            if (i >= 0) {
                str = String.valueOf(str) + new String(arrayOfByte, 0, i);
                continue;
            }
            return str1;
        }
    }



    public static String getDeviceAbi() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("getprop ro.product.cpu.abi");
            if (process.waitFor() == 0) {
                String[] arrayOfString1 = getContent(process.getInputStream()).split("\n");
                if (arrayOfString1 != null)
                    return arrayOfString1[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }


//    public static String getDeviceInfo() { return String.valueOf(String.valueOf("") + getSoftwareRevision()) + getHardwareRevision(); }

//    public static String getDeviceManufacturer() {
//        String[] arrayOfString = (String[])null;
//        runtime = Runtime.getRuntime();
//        try {
//            Process process = runtime.exec("getprop ro.product.manufacturer");
//            if (process.waitFor() == 0) {
//                String[] arrayOfString1 = getContent(process.getInputStream()).split("\n");
//                if (arrayOfString1 != null)
//                    return arrayOfString1[0];
//            }
//        } catch (Exception runtime) {
//            runtime.printStackTrace();
//        }
//        return "Unknown";
//    }
//
//    public static String getDeviceModel() {
//        String[] arrayOfString = (String[])null;
//        runtime = Runtime.getRuntime();
//        try {
//            Process process = runtime.exec("getprop ro.product.model");
//            if (process.waitFor() == 0) {
//                String[] arrayOfString1 = getContent(process.getInputStream()).split("\n");
//                if (arrayOfString1 != null)
//                    return arrayOfString1[0];
//            }
//        } catch (Exception runtime) {
//            runtime.printStackTrace();
//        }
//        return "Unknown";
//    }

//    private static String getHardwareRevision() {
//        str2 = "";
//        String str3 = Runtime.getRuntime();
//        String str1 = str2;
//        try {
//            Process process = str3.exec("cat /proc/cpuinfo");
//            str1 = str2;
//            str3 = str2;
//            if (process.waitFor() == 0) {
//                str1 = str2;
//                String[] arrayOfString1 = getContent(process.getInputStream()).split("\n");
//                str1 = str2;
//                String[] arrayOfString2 = new String[4];
//                arrayOfString2[0] = "Processor";
//                arrayOfString2[1] = "CPU part";
//                arrayOfString2[2] = "Hardware";
//                arrayOfString2[3] = "Revision";
//                str3 = str2;
//                if (arrayOfString1 != null) {
//                    str1 = str2;
//                    int i = arrayOfString1.length;
//                    for (byte b = 0;; b++) {
//                        if (b >= i)
//                            return str2;
//                        String str = arrayOfString1[b];
//                        str1 = str2;
//                        int j = arrayOfString2.length;
//                        byte b1 = 0;
//                        while (b1 < j) {
//                            String str4 = arrayOfString2[b1];
//                            str3 = str2;
//                            str1 = str2;
//                            if (str.indexOf(str4) >= 0) {
//                                str2 = (str1 = str2).valueOf(str2) + str4.toLowerCase() + ": ";
//                                str1 = str2;
//                                int k = str.indexOf(':');
//                                str2 = (str1 = str2).valueOf(str2) + str.substring(k + 1);
//                                str3 = (str1 = str2).valueOf(str2) + "\n";
//                            }
//                            b1++;
//                            str2 = str3;
//                        }
//                    }
//                }
//            }
//        } catch (Exception str2) {
//            str2.printStackTrace();
//            return str1;
//        }
//        return str3;
//    }
//
//    private static String getSoftwareRevision() {
//        String str2 = "";
//        String str1 = Runtime.getRuntime();
//        try {
//            Process process = str1.exec("cat /proc/version");
//            String str = str2;
//            if (process.waitFor() == 0) {
//                String str3 = getContent(process.getInputStream());
//                int i = str3.indexOf(')');
//                str = str2;
//                if (i >= 0)
//                    str = String.valueOf("") + "Kernel: " + str3.substring(0, i + 1) + "\n";
//            }
//        } catch (Exception null) {
//            str1.printStackTrace();
//            str1 = str2;
//        }
//        return String.valueOf(str1) + "Build Number: " + Build.PRODUCT + Build.VERSION.RELEASE + "(" + Build.DISPLAY + ")\n";
//    }
//
//    public static String getWifiInfo() {
//        String str1 = getDeviceHardware();
//        String str2 = getDeviceModel();
//        String str3 = getDevice();
//        return (str2.compareTo("ADR6300") == 0) ? "WLAN/BT: BCM4329" : ((str2.compareTo("LG GW620") == 0) ? "WLAN/BT: BCM4325" : ((str2.compareTo("beagleboard") == 0) ? "WLAN/BT: USB" : ((str2.compareTo("Dell Streak") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("aloha") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("bahamas") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("bravo") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("buzz") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("delta") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("desirec") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("eagle") == 0) ? "WLAN: AR9271\nBT: None" : ((str1.compareTo("es209ra") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("espresso") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("eve") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("glacier") == 0) ? "WLAN/BT: BCM4329 (Dualband)" : ((str1.compareTo("goldfish") == 0) ? "WLAN: Emulated" : ((str1.compareTo("GT-I5700") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("GT-I5700R") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("GT-I7500") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("GT-I9000") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("hero") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("heroc") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("legend") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("liberty") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("mahimahi") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("herring") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("morrison") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("motus") == 0) ? "WLAN/BT: BCM4325" : ((str3.compareTo("harmony") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("Pulse") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("RBM2") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("salsa") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("sapphire") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("SCH-R880") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("SGH-T939") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("SHW-M110S") == 0) ? "WLAN/BT: BCM4329" : ((str2.compareTo("SCH-I800") == 0 || str2.compareTo("SPH-P100") == 0 || str2.compareTo("SGH-I987") == 0 || str2.compareTo("SC-01C") == 0 || str2.compareToIgnoreCase("SGH-t849") == 0 || str2.compareTo("GT-P1000") == 0 || str2.compareTo("SHW-M180S") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("GT-P7510") == 0) ? "WLAN/BT: BCM4330" : ((str1.compareTo("shadow") == 0) ? "WLAN/BT: TI WL1271" : ((str1.compareTo("stingray") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("sholes") == 0) ? "WLAN/BT: TI WL1271" : ((str1.compareTo("droid2") == 0) ? "WLAN/BT: TI WL1271" : ((str1.compareTo("droid2we") == 0) ? "WLAN/BT: TI WL1271" : ((str1.compareTo("SPH-M900") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("solana") == 0) ? "WLAN/BT: TI WL1285" : ((str1.compareTo("supersonic") == 0) ? "WLAN/BT: BCM4329" : ((str1.compareTo("swift") == 0) ? "WLAN: ???" : ((str1.compareTo("titanium") == 0) ? "WLAN/BT: TI WL1271" : ((str1.compareTo("trout") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("U8110") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("U8230") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str1.compareTo("x2a_v4") == 0) ? "WLAN/BT: 88W8688" : ((str1.compareTo("zeppelin") == 0) ? "WLAN/BT: BCM4325" : ((str1.compareTo("tuna") == 0) ? "WLAN/BT: BCM4330B2" : ((str1.compareTo("spyder") == 0) ? "WLAN/BT: TI WL1281" : ((str1.compareTo("zoom2") == 0) ? "WLAN/BT: TI WL1271" : ((str2.compareTo("LG KH5200") == 0) ? "WLAN: ???" : ((str2.compareTo("T-Mobile G2 Touch") == 0) ? "WLAN: TI1251\nBT: BRF6300" : ((str2.compareTo("WellcoM-A88") == 0) ? "WLAN: ???" : (str2.contains("NOVO7") ? "WLAN: BCM43362" : ((str2.compareTo("Unknown") == 0) ? "WLAN: Unknown" : "WLAN: Unknown"))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
//    }

//    public static boolean isOnline(Context paramActivity) {
//        ConnectivityManager connectivityManager = (ConnectivityManager)paramActivity.getSystemService(paramActivity.getClass());
//        if (connectivityManager != null) {
//            @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null)
//                return networkInfo.isConnected();
//        }
//        return false;
//    }
}
