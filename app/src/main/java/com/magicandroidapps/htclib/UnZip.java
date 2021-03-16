package com.magicandroidapps.htclib;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {
    public static final String LOG_TAG = "UnZip";

    public static void UnZipFile(InputStream paramInputStream, File paramFile) throws IOException {
        if (!paramFile.isDirectory())
            throw new IOException("Invalid Unzip destination " + paramFile);
        ZipInputStream zipInputStream = new ZipInputStream(paramInputStream);
        label20: while (true) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if (zipEntry == null)
                return;
            String str = String.valueOf(paramFile.getAbsolutePath()) + File.separator + zipEntry.getName();
            if (zipEntry.isDirectory()) {
                File file = new File(str);
                Log.e("UnZip", "mkdir: " + str);
                file.mkdir();
                continue;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            Log.e("UnZip", " file: " + str);
            byte[] arrayOfByte = new byte[1024];
            for (int i = paramInputStream.read(arrayOfByte);; i = paramInputStream.read(arrayOfByte)) {
                if (i == -1) {
                    fileOutputStream.close();
                    paramInputStream.close();
                    continue label20;
                }
                fileOutputStream.write(arrayOfByte, 0, i);
            }
        }
    }
}
