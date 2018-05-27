package com.git.hui.demo.base.bean.test.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by yihui in 21:32 18/5/15.
 */
public class NetWorkNIOClient {

    public void client() throws Exception {
        SocketChannel client = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9001));
        client.configureBlocking(false);


        URL url = this.getClass().getClassLoader().getResource("test.txt");
        FileChannel inChannel = FileChannel.open(Paths.get(url.toURI()), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1023);

        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            client.write(buffer); // 传输数据到服务端
            buffer.clear();
        }

        inChannel.close();
        client.close();
    }


    @Test
    public void testClient() throws Exception {
        client();

        Thread.sleep(1000 * 60);
    }
}
