package com.android.ipef;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingNetWork {

    private static final String TAG = "PingNetWork";

    public void ping() {
        try {
            Log.i(TAG, "System.currentTimeMillis() start " + System.currentTimeMillis());
            Process process = Runtime.getRuntime().exec("ping  192.168.50.50");
            BufferedReader errorStreamReader = null;
            BufferedReader inputStreamReader = null;

            try {
                errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                //timeout control
                boolean isFinished = false;
                Log.i(TAG, "wait isFinished: " + isFinished);
                //                        //parse error info
                if (errorStreamReader.ready()) {
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = errorStreamReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    Log.i(TAG, "for(;;) error: " + buffer.toString());
                }
                Log.i(TAG, "for(;;) parse: inputStreamReader.ready() " + inputStreamReader.read());
                //parse info
                if (inputStreamReader.ready()) {
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = inputStreamReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    Log.i(TAG, "System.currentTimeMillis() end " + System.currentTimeMillis());
                    Log.i(TAG, "for(;;) parse: " + buffer.toString());

                }
//                for (;;) {
//                    Log.i(TAG,"wait  for(;;) isFinished: "+isFinished);
//
//                    if (isFinished) {
//                        CommandResult result = new CommandResult();
//                        result.setExitValue(process.waitFor());  //process.waitFor() 表示 等这条语句执行完后再往下执行
//
//                        //parse error info
//                        if (errorStreamReader.ready()) {
//                            StringBuilder buffer = new StringBuilder();
//                            String line;
//                            while ((line = errorStreamReader.readLine()) != null) {
//                                buffer.append(line);
//                            }
//                            result.setError(buffer.toString());
//                            Log.i(TAG,"for(;;) error: "+buffer.toString());
//                        }
//
//                        //parse info
//                        if (inputStreamReader.ready()) {
//                            StringBuilder buffer = new StringBuilder();
//                            String line;
//                            while ((line = inputStreamReader.readLine()) != null) {
//                                buffer.append(line);
//                            }
//                            result.setOutput(buffer.toString());
//                            Log.i(TAG,"for(;;) parse: "+buffer.toString());
//                        }
//                    }
//                    try {
//                        isFinished = true;
//                        process.exitValue();
//                    } catch (IllegalThreadStateException e) {
//                        // process hasn't finished yet
//                        isFinished = false;
//                    }
//                }
            } finally {
                if (errorStreamReader != null) {
                    try {
                        errorStreamReader.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                    }
                }
            }
        } catch (Exception e) {

        }

    }

}
