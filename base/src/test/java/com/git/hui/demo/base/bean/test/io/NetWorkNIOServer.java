package com.git.hui.demo.base.bean.test.io;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * Created by yihui in 21:23 18/5/15.
 */
public class NetWorkNIOServer {

    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 非阻塞模型
        serverSocketChannel.bind(new InetSocketAddress(9001)); // 监听端口号


        // 创建并注册选择器
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        // 轮询判断是否有连接过来, 大于0表示成功
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                // 接收事件就绪
                if (selectionKey.isAcceptable()) {

                    // 获取客户端的链接
                    SocketChannel client = serverSocketChannel.accept();

                    // 切换成非阻塞状态
                    client.configureBlocking(false);

                    // 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                    client.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) { // 读事件就绪

                    // 获取当前选择器读就绪状态的通道
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    // 读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    // 得到文件通道，将客户端传递过来的图片写到本地项目下(写模式、没有则创建)
                    FileChannel outChannel = FileChannel.open(Paths.get("server_out.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

                    while (client.read(buffer) > 0) {
                        // 在读之前都要切换成读模式
                        buffer.flip();

                        outChannel.write(buffer);

                        // 读完切换成写模式，能让管道继续读取文件的数据
                        buffer.clear();
                    }
                }

                // 取消选择键(已经处理过的事件，就应该取消掉了)
                iterator.remove();
            }
        }
    }



    @Test
    public void testRun() throws IOException, InterruptedException {
        server();

        Thread.sleep(24 * 3600 * 1000);
    }

}
