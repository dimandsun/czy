package socket;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chenzy
 * @date 2020-08-08
 */
public class InetAddressesTest {

    @Test
    public void localAddress() throws UnknownHostException {
        //本地主机的ip信息
        var localHost = InetAddress.getLocalHost();
        var localHostAddress = localHost.getAddress();
        for (int i = 0; i < localHostAddress.length; i++) {
            System.out.print(localHostAddress[i] + " ");
        }
        System.out.println();
        //返回回环/回调ip地址信息
        var loopbackAddress = InetAddress.getLoopbackAddress();
        var loopbackAddressAddress = loopbackAddress.getAddress();
        for (int i = 0; i < loopbackAddressAddress.length; i++) {
            System.out.print(loopbackAddressAddress[i] + " ");
        }
        System.out.println();


    }

    /*根据主机名获取ip地址*/
    @Test
    public void getInetAddress() throws UnknownHostException {
        var inetAddresses = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddresses.getHostAddress());

        var inetAddressess = InetAddress.getAllByName("www.baidu.com");
        for (int i = 0; i < inetAddressess.length; i++) {
            System.out.println(inetAddressess[i].getHostAddress());
        }

        InetAddress.getByAddress(new byte[]{});
    }

    @Test
    public void getInetAddress2() throws UnknownHostException {
        /*var inetAddresses=InetAddress.getByName("127.0.0.1");
        var bytes=inetAddresses.getAddress();

        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]+" ");
        }
        System.out.println();*/
        var inetAddress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        System.out.println(inetAddress.getHostAddress());
    }

    /*

     */
    @Test
    public void getCanonicalHostName() throws UnknownHostException {
        var inetAddresses = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddresses.getCanonicalHostName());
        System.out.println(inetAddresses.getHostName());
    }
}
