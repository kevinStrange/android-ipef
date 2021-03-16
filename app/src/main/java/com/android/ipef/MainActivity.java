package com.android.ipef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.magicandroidapps.htclib.Exec;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {


    private static final String TAG = "MainActivity";
    private static final int IPERF_ERROR = 1;
    private static final int IPERF_SCCESS = 2;
    private TextView mAddress;
    private TextView mShroughput;
    private TextView mSignalStrength;
    private WifiManager mWm;
    private String iperfreply;
    /*** 1. 拷贝iperf到该目录下*/
    private static final String IPERF_PATH = "/data/data/com.android.ipef/files/iperf";
//    c：客户端模式，后接服务器ip。
//    p：设置端口，与服务器端的监听端口一致。
//    i：设置带宽报告的时间间隔，单位为秒。
//    t：设置传输的总时间。Iperf在指定的时间内，重复的发送指定长度的数据包。默认是10秒钟。
//    w：设置tcp窗口大小，一般可以不用设置，默认即可
    /*** 2. 在Android应用中执行iperf命令*/
    //-u   表示协议为UDP
    //-c   表示为客户端
    //192.168.1.100     为服务端的ip地址，根据实际情况填写
    //-b 90M     表示带宽限制为90Mbps
    //-i  1     周期性报告的时间间隔（interval）为1秒
    //-w 1M  缓冲区窗口大小为1M。设置套接字缓冲区为指定大小。对于TCP方式，此设置为TCP窗口大小。对于UDP方式，此设置为接受UDP数据包的缓冲区大小，限制可以接受数据包的最大值。
    //-t 10   表示测试时间为10秒
    private static String iperfClientCmd = "iperf -c 111.230.194.34 -u -t 1 -b 48k";
    //iperf -u -c 192.168.1.100 -b 90M -i 1 -w 1M -t 10
    private static final String iperfServiceCmd = IPERF_PATH + " -s &";
    private static String curIperfCmd = iperfServiceCmd;
    private boolean IPERF_OK = false;
    private String shellCmd = "/data/data/com.android.ipef/files/iperf  iperf -c 111.230.194.34 -u -t 1 -b 48k";
    private Switch mSwitch;
    Context mContext;
    String tempShellCmd;
    //111.230.194.34
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        tempShellCmd = shellCmd;
        File file = new File(IPERF_PATH);
        Log.i(TAG,"file.exists(): "+file.exists());
        if(!file.exists()){
            copyiperf();
        }
        PingNetWork pingNetWork = new PingNetWork();
        pingNetWork.ping();
    }

    private void initView() {
        findViewById(R.id.btn_wifi_address).setOnClickListener(this);
        findViewById(R.id.btn_wifi_throughput).setOnClickListener(this);
        findViewById(R.id.btn_wifi_signal_strength).setOnClickListener(this);

        mAddress = (TextView) findViewById(R.id.wifi_address);
        mShroughput = (TextView) findViewById(R.id.wifi_throughput_show);
        mSignalStrength = (TextView) findViewById(R.id.wifi_signal_strength);

        mWm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//        mSwitch = (Switch) findViewById(R.id.wifi_switch);
//        mSwitch.setChecked(true);
//        mSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wifi_address://获取wifi地址
                mAddress.setText(getLocalIpAddress());
                Log.i(TAG, "btn_wifi_address: " + getLocalIpAddress());
                break;
            case R.id.btn_wifi_throughput://获取wifi吞吐量
//                iperfClientCmd = IPERF_PATH + " -c "+getLocalIpAddress()+" -p 5001 -t 60 -P 1";
                String shellCmd = "/data/data/com.android.ipef/iperf  iperf -c 111.230.194.34 -u -t 10 -n 4k -T 2";
                sercomfun(tempShellCmd);
                break;
            case R.id.btn_wifi_signal_strength://获取wifi信号强度
                mSignalStrength.setText(getSignalStrength());
                Log.i(TAG, "btn_wifi_signal_strength: " + getSignalStrength());
                break;
        }
    }


//    /**
//     * 获取wifi地址
//     */
//    private String getLocalIpAddress() {
//        int paramInt = 0;
//        WifiInfo info = mWm.getConnectionInfo();
//        if (info.getBSSID() == null) {
//            return "请连接再试";
//        } else {
//            paramInt = info.getIpAddress();
//            Log.i(TAG, "paramInt: " + paramInt+"0xFF&paramInt"+(0xFF & paramInt));
//            return new StringBuffer()
//                    .append(0xFF & paramInt).append(".")
//                    .append(0xFF & paramInt >> 8).append(".")
//                    .append(0xFF & paramInt >> 16).append(".")
//                    .append(0xFF & paramInt >> 24).toString();
//        }
//    }
//
//    /**
//     * Wifi的连接速度及信号强度
//     */
    public String getSignalStrength() {
        int strength = 0;
//        WifiInfo info = mWm.getConnectionInfo();
//        if (info.getBSSID() != null) {
//            // 链接信号强度，5为获取的信号强度值在5以内
//            strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);
//            // int speed = info.getLinkSpeed(); // 链接速度
//            // String units = WifiInfo.LINK_SPEED_UNITS; // 链接速度单位
//            // String ssid = info.getSSID(); // Wifi源名称
//        }
        return strength + "";
    }
//
//
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case IPERF_ERROR:
                    mShroughput.setText((String)msg.obj);
                    break;
                case IPERF_SCCESS:
                    mShroughput.setText((String)msg.obj);
                    break;
            }
        }
    };

    /**
     * 1. 拷贝iperf到该目录下
     */
    public void copyiperf() {
        File localfile;
        Process p;
        try {
            localfile = new File(IPERF_PATH);
            Log.i(TAG,localfile.exists()+"======");
            p = Runtime.getRuntime().exec("chmod 777 " + localfile.getAbsolutePath());
            InputStream localInputStream = getAssets().open("iperf");
            Log.i(TAG,"chmod 777 " + localfile.getAbsolutePath());
            FileOutputStream localFileOutputStream = new FileOutputStream(localfile.getAbsolutePath());
            FileChannel fc = localFileOutputStream.getChannel();
            FileLock lock = fc.tryLock(); //给文件设置独占锁定
            if (lock == null) {
                Toast.makeText(this,"has been locked !",Toast.LENGTH_SHORT).show();
                return;
            } else {
                FileOutputStream fos = new FileOutputStream(new File(IPERF_PATH));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = localInputStream.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                    Log.i(TAG, "byteCount: "+byteCount);
                }
                fos.flush();// 刷新缓冲区
                localInputStream.close();
                fos.close();
            }
            //两次才能确保开启权限成功
            p = Runtime.getRuntime().exec("chmod 777 " + localfile.getAbsolutePath());
            lock.release();
            p.destroy();
            Log.i(TAG, "the iperf file is ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 在Android应用中执行iperf命令
     */
    private void sercomfun(final String cmd) {
        Log.i(TAG, "sercomfun = " + cmd);
        Thread lthread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String errorreply = "";
                    CommandHelper.DEFAULT_TIMEOUT = 150000;
                    CommandResult result = CommandHelper.exec(cmd);
                        //start to connect the service
                        if (result.getError() != null) {
                            errorreply = result.getError();
                            Message m = new Message();
                            m.obj = errorreply;
                            m.what = IPERF_ERROR;
                            handler.sendMessage(m);
                            Log.i(TAG,"Error:" + errorreply);
                        }
                        if (result.getOutput() != null) {
                            iperfreply = getThroughput(result.getOutput());
                            String data1 = result.getOutput().replaceAll(" ","");
                            StringBuilder builder = new StringBuilder();
                            builder.append("延迟："+data1.substring(data1.lastIndexOf("ts/sec") + 6, data1.lastIndexOf("ms"))+"ms\n")
                                    .append("丢包率："+data1.substring(data1.lastIndexOf("ms")+2));
                            IPERF_OK = true;
                            Message m = new Message();
                            m.obj = builder.toString();
                            m.what = IPERF_SCCESS;
                            handler.sendMessage(m);
                            Log.i(TAG,"Output:" + iperfreply);
                        }
                        Log.i(TAG,"result.getExitValue(): "+result.getExitValue());
                } catch (Exception e) {
                    if (e.toString().contains("Permission denied")){
                        tempShellCmd = iperfClientCmd;
                    }
                    Log.e(TAG,e.toString());
                }
            }
        });
        lthread.start();
    }

    /**
     * 从获取到的吞吐量信息中截取需要的信息，如：
     * 0.0-10.0 sec  27.5 MBytes  23.0 Mbits/sec
     */
    private String getThroughput(String str) {
        String regx = "0.0-.+?/sec";
        String result = "";
        Matcher matcher = Pattern.compile(regx).matcher(str);
        Log.i(TAG,"matcher regx : "+regx+" is "+matcher.matches());
        if(matcher.find()){
            Log.i(TAG,"group: "+matcher.group());
            result = matcher.group();
        }
        return result;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if(buttonView.getId() == R.id.wifi_switch){
//            curIperfCmd = (isChecked?iperfServiceCmd
//                    :(IPERF_PATH + " -c "+getLocalIpAddress()+" -p 5001 -t 60 -P 1"));
//            Log.i(TAG,"isChecked: "+isChecked+", curIperfCmd: "+curIperfCmd);
//        }
    }


    public static final boolean DEBUG = false;

    public static final String FLURRY_KEY_IPERF = "9EJIV1LT5VC1M8W5KP6D";

    private static final String IPERF_EXTRACT_PATH = "/data/data/com.android.ipef/";

    private static final int IPERF_START = 2;

    private static final int IPERF_STOP = 3;

    public static final boolean LOG_INSTALL = false;

    public static final String LOG_TAG = "iPerf";

    public static final boolean LOG_UI = false;

    private static final int UPDATE = 1;

    private static String mAbi = "";

    private String WifiInfo = "";

    private String charBuffer = "";


    private final Handler mHandler = new Handler() {
        public void handleMessage(Message param1Message) {
            switch (param1Message.what) {
                default:
                    Log.e("iPerf", "Got Update = unknown.");
                    return;
                case 1:
                    MainActivity.this.charBuffer = "";
                    return;
                case 2:
                    Log.i("iPerf", "Got Update = IPERF_START");
                    return;
                case 3:
                    break;
            }
            Log.i("iPerf", "Got Update = IPERF_STOP");
        }
    };

    private Thread mPollingThread;

    private SharedPreferences mPrefs;

    private FileDescriptor mTermFd;

    private FileInputStream mTermIn;

    private FileOutputStream mTermOut;

    private int procId;

    private String shell = "";



    private void init(){
        String shell = "/data/data/com.android.ipef/ipef  -c 111.230.194.34 -u -t 10 -n 64K -T 2";
        int[] arrayOfInt = new int[1];
        FileDescriptor mTermFd = Exec.createSubprocessV(shell, arrayOfInt);
        this.mTermOut = new FileOutputStream(mTermFd);
        this.mTermIn = new FileInputStream(mTermFd);
        this.mPollingThread = new Thread(new Runnable() {
            private byte[] mBuffer = new byte[4096];
            @Override
            public void run() {
                try {
                    while (true) {
                        int i = mTermIn.read(this.mBuffer);
                        for (byte b = 0;; b++) {
                            if (b >= i) {
                                mHandler.sendMessage(mHandler.obtainMessage(1));
                                continue ;
                            }
                            if (this.mBuffer[b] != 13) {
                                Log.d("iperf", " ======= "+(char)this.mBuffer[b]);
                            }
                        }
                    }
                } catch (Exception exception) {
                    Log.e("iPerf", exception.toString());
                }
            }
        });
        this.mPollingThread.setName("iPerf Input Reader");
        this.mPollingThread.start();
    }

    private void createSubprocess(int[] paramArrayOfInt) {
        String[] arrayOfString = this.shell.split("#", 2);
        Log.i("iPerf", "Command: '" + arrayOfString[0] + "'");
        try {
            Exec.chdir(arrayOfString[1]);
            Log.i("iPerf", "chdir() to '" + arrayOfString[1] + "'");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            Log.w("iPerf", "No path specified, using default directory.");
        }
        Log.i("iPerf", "Starting subprocess '" + arrayOfString[0] + "'");
        this.mTermFd = Exec.createSubprocessV(arrayOfString[0], paramArrayOfInt);
    }




    private void startListening() {
        int[] arrayOfInt = new int[1];
        createSubprocess(arrayOfInt);
        this.procId = arrayOfInt[0];
//        (new Thread(new Runnable(this, new Handler() {
//            public void handleMessage(Message param1Message) {
//                if (param1Message.what != 0) {
//                    Log.e("iPerf", "iPerf exited with error code " + param1Message.what);
//                    return;
//                }
//                Log.i("iPerf", "Subprocesses exited gracefully.");
//            }
//        }) {
//            public void run() {
//                Log.i("iPerf", "waiting for: " + MainActivity.this.procId);
//                MainActivity.this.mHandler.sendMessage(MainActivity.this.mHandler.obtainMessage(2));
//                int i = Exec.waitFor(MainActivity.this.procId);
//                Log.i("iPerf", "Subprocess exited: " + i);
//                MainActivity.this.mHandler.sendMessage(MainActivity.this.mHandler.obtainMessage(3));
//                mHandler.sendEmptyMessage(i);
//                MainActivity.this.mHandler.sendMessage(MainActivity.this.mHandler.obtainMessage(1));
//            }
//        })).start();
        this.mTermOut = new FileOutputStream(this.mTermFd);
        this.mTermIn = new FileInputStream(this.mTermFd);
        this.mPollingThread = new Thread(new Runnable() {
            private byte[] mBuffer = new byte[4096];
            @Override
            public void run() {
                try {
                    while (true) {
                        int i = mTermIn.read(this.mBuffer);
                        for (byte b = 0;; b++) {
                            if (b >= i) {
                                mHandler.sendMessage(mHandler.obtainMessage(1));
                                continue ;
                            }
                            if (this.mBuffer[b] != 13) {
                                Log.d("iperf", " ======= "+(char)this.mBuffer[b]);
                            }
                        }
                    }
                } catch (Exception exception) {
                    Log.e("iPerf", exception.toString());
                }
            }
        });
        this.mPollingThread.setName("iPerf Input Reader");
        this.mPollingThread.start();
    }

    public String getLocalIpAddress() {
        String str = "";
        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (true) {
                if (!enumeration.hasMoreElements()) {
                    Log.e("iPerf", str);
                    return str;
                }
                NetworkInterface networkInterface = (NetworkInterface)enumeration.nextElement();
                Enumeration enumeration1 = networkInterface.getInetAddresses();
                String str1 = str;
                while (true) {
                    str = str1;
                    if (enumeration1.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress)enumeration1.nextElement();
                        if (!inetAddress.getHostAddress().toString().contains("fe80") && !inetAddress.isLoopbackAddress())
                            str1 = String.valueOf(str1) + "Interface: " + networkInterface.getDisplayName() + " ipaddr: " + inetAddress.getHostAddress().toString() + "\n";
                        continue;
                    }
                    continue ;
                }
            }
        } catch (SocketException socketException) {
            Log.e("iPerf", socketException.toString());
            return null;
        }
    }

    public void onConfigurationChanged(Configuration paramConfiguration) {
        Log.e("iPerf", "onConfigurationChanged newConfig =" + paramConfiguration);
        switch (paramConfiguration.orientation) {
            default:
                Log.e("iPerf", "Unknown Orientation");
                super.onConfigurationChanged(paramConfiguration);
                return;
            case 2:
                Log.e("iPerf", "Orientation is landscape");
                super.onConfigurationChanged(paramConfiguration);
                return;
            case 1:
                Log.e("iPerf", "Orientation is portrait");
                super.onConfigurationChanged(paramConfiguration);
                return;
            case 3:
                break;
        }
        Log.e("iPerf", "Orientation is square");
        super.onConfigurationChanged(paramConfiguration);
    }





    public void onDestroy() {
        Log.e("iPerf", "onDestroy: killing subprocess PID=" + Integer.toString(this.procId));
//        Process.killProcess(this.procId);
        super.onDestroy();
    }



    public void onPause() {
//        SharedPreferences.Editor editor = this.mPrefs.edit();
//        editor.putString("cmdline", this.cmdlineargs.getText().toString());
//        editor.commit();
        Log.e("iPerf", "onPause()");
        super.onPause();
    }

    public void onStart() {
        super.onStart();
//        FlurryAgent.onStartSession(this, "9EJIV1LT5VC1M8W5KP6D");
//        this.ipaddr.append((this.WifiInfo = DeviceInfomation.getWifiInfo()).valueOf(DeviceInfomation.getDeviceModel()) + " (" + DeviceInfomation.getDeviceHardware() + ") [" + mAbi.toUpperCase() + "]");
//        this.ipaddr.append("\n" + this.WifiInfo);
    }

    public void onStop() {
        super.onStop();
//        FlurryAgent.onEndSession(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}