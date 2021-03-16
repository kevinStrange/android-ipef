package com.android.ipef;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
//        String data = "------------------------------------------------------------Client connecting to 111.230.194.34, UDP port 5001Sending 1470 byte datagramsUDP buffer size:  224 KByte (default)------------------------------------------------------------[  3] local 192.168.3.111 port 42462 connected with 111.230.194.34 port 5001[ ID] Interval       Transfer     Bandwidth[  3]  0.0-10.3 sec  60.3 KBytes  48.0 Kbits/sec[  3] Sent 42 datagrams[  3] Server Report:[  3]  0.0-12525.8 sec  60.3 KBytes  39.4 bits/sec  16.011 ms    0/   42 (0%)";
//        String sec = "0.0- 0.5 sec  64.6 KBytes  1.05 Mbits/sec";
//        String data1 = data.replaceAll(" ", "");
////        String sec1 = sec.replaceAll(" ","");
////        System.out.println(data.replaceAll(" ",""));
////        System.out.println(sec1.indexOf("sec"));
//        System.out.println(data1.substring(data1.lastIndexOf("Sent") + 4, data1.lastIndexOf("datagrams")));
//        System.out.println(data1.substring(data1.lastIndexOf("ts/sec") + 6, data1.lastIndexOf("ms")));
//        String d = data1.substring(data1.lastIndexOf("ms") + 2);
//        //丢包数据
//        System.out.println(data1.substring(data1.lastIndexOf("ms") + 2, data1.lastIndexOf("/")));
//        System.out.println(data1.substring(data1.lastIndexOf("/") + 1, data1.lastIndexOf("(")));
//        int i = Integer.parseInt(data1.substring(data1.lastIndexOf("ms") + 2, data1.lastIndexOf("/")));
//        int k = Integer.parseInt(data1.substring(data1.lastIndexOf("/") + 1, data1.lastIndexOf("(")));
//        int pk = Integer.parseInt(data1.substring(data1.lastIndexOf("(") + 1, data1.lastIndexOf("%)")));
//        if (i == 0) {
//            System.out.println("网络很好"+pk);
//        }
//        String indexi = "16.011";
//        System.out.println(indexi.substring(0,indexi.lastIndexOf(".")));

        String pingDataResult = "ING 192.168.50.50 (192.168.50.50) 56(84) bytes of data.64 bytes from 192.168.50.50: icmp_seq=1 ttl=64 time=8.50 ms64 bytes from 192.168.50.50: icmp_seq=2 ttl=64 time=7.55 ms64 bytes from 192.168.50.50: icmp_seq=3 ttl=64 time=8.66 ms--- 192.168.50.50 ping statistics ---3 packets transmitted, 3 received, 0% packet loss, time 2003msrtt min/avg/max/mdev = 7.558/8.241/8.666/0.499 ms";

        String pingDataResult1 = "ING 192.168.50.50 (192.168.50.50) 56(84) bytes of data.64 bytes from 192.168.50.50: icmp_seq=2 ttl=64 time=20.4 ms64 bytes from 192.168.50.50: icmp_seq=3 ttl=64 time=142 ms--- 192.168.50.50 ping statistics ---3 packets transmitted, 2 received, 33% packet loss, time 2014msrtt min/avg/max/mdev = 20.474/81.344/142.214/60.870 ms";

        String pingDataResult2 = "ING 192.168.50.50 (192.168.50.50) 56(84) bytes of data.64 bytes from 192.168.50.50: icmp_seq=2 ttl=64 time=870 ms--- 192.168.50.50 ping statistics ---3 packets transmitted, 1 received, 66% packet loss, time 2007msrtt min/avg/max/mdev = 870.951/870.951/870.951/0.000 ms";

        String pingDataResult3 = "ING 192.168.50.50 (192.168.50.50) 56(84) bytes of data.--- 192.168.50.50 ping statistics ---3 packets transmitted, 0 received, 100% packet loss, time 2024ms";
        String pingData = pingDataResult.replaceAll(" ", "");
        if (Integer.parseInt(pingData.substring(pingData.lastIndexOf("received,") + 9, pingData.lastIndexOf("%packetloss"))) == 0) {
            String avgResult = pingData.substring(pingData.lastIndexOf("mdev=") + 5);
            String[] resultArray = avgResult.split("/");
            int i = 0;
            for (String result : resultArray) {
                if (i == 1) {
                    if (result.contains(".")) {
                       System.out.println("-----========="+Integer.parseInt(result.substring(0, result.indexOf("."))));
                    } else {
                        System.out.println("========="+result);
                    }
                    break;
                }
                i++;
            }
        }
        int da = 30;
        if (da > 20 && da <= 40) {
            System.out.println("========="+da);
        }else {
            System.out.println("===---======"+da);
        }
    }
}