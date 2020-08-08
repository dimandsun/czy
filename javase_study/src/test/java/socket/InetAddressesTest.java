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
    public void getInetAddress() throws UnknownHostException {
        var address=InetAddress.getLocalHost();
        InetAddress.getAllByName("www.baidu.com");
    }
}
