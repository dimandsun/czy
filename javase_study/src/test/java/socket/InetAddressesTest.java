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
        var localHost=InetAddress.getLocalHost();
        var localHostAddress=localHost.getAddress();
        for (int i = 0; i < localHostAddress.length; i++) {
            System.out.print(localHostAddress[i]+" ");
        }
        System.out.println();
        //返回回环/回调ip地址信息
        var loopbackAddress=InetAddress.getLoopbackAddress();
        var loopbackAddressAddress=loopbackAddress.getAddress();
        for (int i = 0; i < loopbackAddressAddress.length; i++) {
            System.out.print(loopbackAddressAddress[i]+" ");
        }
        System.out.println();


    }

    /*根据主机名获取ip地址*/
    @Test
    public void getInetAddress() throws UnknownHostException {
        InetAddress.getAllByName("www.baidu.com");
    }
}
