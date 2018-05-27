package com.git.hui.demo.base.bean.test.io;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by yihui in 20:43 18/5/15.
 */
public class NioTest {

    @Test
    public void testFileNio() throws URISyntaxException, IOException {
        URL url = this.getClass().getClassLoader().getResource("test.txt");
        FileChannel inChannel = FileChannel.open(Paths.get(url.toURI()), StandardOpenOption.READ);


        FileChannel outChannel = FileChannel.open(Paths.get("out.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (inChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);

            byteBuffer.clear();
        }


        outChannel.close();
        inChannel.close();
    }
}
