package com.magicandroidapps.htclib;

import java.io.FileDescriptor;

public class Exec {


    static  {
        System.loadLibrary("Exec");
    }

    public static native int chdir(String paramString);

    public static native int chmod(String paramString, int paramInt);

    public static native void close(FileDescriptor paramFileDescriptor);

    public static native FileDescriptor createSubprocess(String paramString1, String paramString2, String paramString3, int[] paramArrayOfInt);

    public static native FileDescriptor createSubprocessV(String paramString, int[] paramArrayOfInt);

    public static native void hangupProcessGroup(int paramInt);

    public static native void setPtyWindowSize(FileDescriptor paramFileDescriptor, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    public static native int setuid(int paramInt);

    public static native int waitFor(int paramInt);
}

