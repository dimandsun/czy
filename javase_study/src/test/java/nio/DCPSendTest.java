package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @author chenzy
 * @since 2020-06-19
 */
public class DCPSendTest {
    public static void main(String[] args) throws IOException {
        var datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        var buffer = ByteBuffer.allocateDirect(1024);
        var scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            var s = scanner.next();
            buffer.put((new Date().toString() + ":" + s).getBytes());
            buffer.flip();
            datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 10089));
            buffer.clear();
        }
        datagramChannel.close();
    }
}
