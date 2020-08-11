package socket;

import com.czy.util.text.StringUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @author chenzy
 * @date 2020-08-07
 */
public class NetWorkInterfaceTest {
    /*获取网络设备基本信息*/
    @Test
    public void getInfo() throws SocketException {
        var enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            var networkInterface = enumeration.nextElement();
            networkInterface.supportsMulticast();//是否支持多播
            networkInterface.isPointToPoint();//是否是点对点设备
            StringUtil.println("设备名称:{},设备显示名称:{}，网络接口索引:{},是否回调/回环接口：{},是否开启并运行:{}"
                    , networkInterface.getName(), networkInterface.getDisplayName(), networkInterface.getIndex()
                    , networkInterface.isLoopback(), networkInterface.isUp());

            /*一个NetworkInterface有多个InterfaceAddresses,
              一个InterfaceAddresses有一个InetAddress
             */
            networkInterface.getInterfaceAddresses().forEach(interfaceAddress -> {
                interfaceAddress.getAddress();
            });
        }
    }
    /*网络传输中以数据包为基本传输单位，可以设置MTU(Maximum Transmission Unit，最大传输单元)来规定网络传输最大数据包的大小
    以太网的网卡大多默认1500字节，IPV6中，范围为1280-65535
    MTU大——》数据包少——》速度快，延迟大
    MTU小——》数据包多——》速度慢
    更改网卡的MTU有可能造成网络传输数据故障，导致数据传输不完整，出现丢包
    */
    @Test
    public void mtuTest() throws SocketException {
        var enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            var networkInterface = enumeration.nextElement();
            StringUtil.println("设备名称:{},设备显示名称:{}，最大传输单元:{}"
                    , networkInterface.getName(), networkInterface.getDisplayName(), networkInterface.getMTU());
        }
    }

    /*
    网络子接口:
    虚拟网卡：基于原有的网络接口设备再创建出一个虚拟的网络接口设备进行通信

        Windows不支持网络子接口
    */
    @Test
    public void virtualInface() {

    }

    /*获取硬件地址*/
    @Test
    public void hardwareAddress() throws SocketException, UnsupportedEncodingException {
        var enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            var networkInterface = enumeration.nextElement();
            var hardwareAddress=networkInterface.getHardwareAddress();
            if (hardwareAddress!=null&&hardwareAddress.length!=0){
                String address="";
                for (int i = 0; i < hardwareAddress.length; i++) {
                    address+=hardwareAddress[i]+" ";
                }
                StringUtil.println("设备名称:{},设备显示名称:{}，硬件地址:{}"
                        , networkInterface.getName(), networkInterface.getDisplayName(), address);
            }

        }
    }

    /*获取ip信息*/
    @Test
    public void ipInfo() throws SocketException {
        var enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            var networkInterface = enumeration.nextElement();
            var addresses=networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                var inetAddress=addresses.nextElement();
                //ip地址完全限定域名
                inetAddress.getCanonicalHostName();
                //IP主机名
                inetAddress.getHostName();
                //IP地址字符串
                inetAddress.getHostAddress();
                //原始IP地址
                inetAddress.getAddress();
            }
        }
    }
}
