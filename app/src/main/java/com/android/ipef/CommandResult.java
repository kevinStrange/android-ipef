package com.android.ipef;

/**
 * user: uidq0530， 2017-08-22.
 * description：
 *
 * @author xhunmon
 */

public class CommandResult {
    public static final int EXIT_VALUE_TIMEOUT = -1;

    private String output;
    private String error;
    private int exitValue;

    void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }


    public void setExitValue(int value) {
        exitValue = value;
    }

    public int getExitValue() {
        return exitValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "output='" + output + '\'' +
                ", error='" + error + '\'' +
                ", exitValue=" + exitValue +
                '}';
    }
}