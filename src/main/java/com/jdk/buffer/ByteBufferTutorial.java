package com.jdk.buffer;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static org.junit.Assert.*;

/**
 * @author cheny.huang
 * @date 2019-04-15 14:29.
 */
public class ByteBufferTutorial {
    private static final String hello = "hello";
    private static final String world = "world";
    @Test
    public void testWriteData() throws IOException {
        RandomAccessFile file = new RandomAccessFile("/data/nio-data.txt","rw");
        FileChannel channel = file.getChannel();
        // 写入11个字节的数据，此时position=11,capacity=limit=18，如果定义的大小低于11那么会抛出异常，说明容量是不能动态扩容
        // capacity用于控制写缓冲大小，limit用于控制读缓冲大小，position在写缓冲中控制写入的数据多少，在读缓冲中控制读入数据的多少
        ByteBuffer buf = ByteBuffer.allocate(11).put(hello.getBytes()).put(" ".getBytes()).put(world.getBytes());
        // 将缓冲切换为可读缓冲，此时limit=position=11，position=0，那么此时可以读写11个字符数据
        // 注意：flip方法仅仅用于将写缓冲改为读缓冲，不能通过flip方法把读缓冲再次改为写缓冲
        // 如果想从当前position位置中再次重新读取数据的话，倒是可以再次调用flip方法进行重置操作或者使用标准的rewind
        buf.flip();
        // write(ByteBuffer)时，传入的ByteBuffer必须是读缓冲，所以将ByteBuffer写入其它流中时必须先调用flip方法
        // 而且在write(ByteBuffer)方法内部，也必须有一个重置position的方法，否则会导致此次操作读完流后下次操作无法再次读取流
        assertEquals(channel.write(buf), 11);
        // 清理缓冲，此时position变为0，limit=capacity，又可以重新写入新的数据
        buf.clear();
        // 将channel的数据写入buffer中，此时是直接在当前buffer的position位置开始继续写入数据，写入完毕后，buffer的position会发生改变
        // channel的position也要重置为0，表示从最开始出重新写入数据到新的buffer
        channel.position(0);
        int bytesRead = channel.read(buf);
        while (bytesRead != -1) {
            buf.flip(); // make buffer ready for read
            while (buf.hasRemaining()) {
                System.out.println((char)buf.get());
            }
            buf.clear();// make buffer ready for writing
            bytesRead = channel.read(buf);
        }
        file.close();
    }

    @Test
    public void testCompact() {
        ByteBuffer buffer = ByteBuffer.allocate(5).put(hello.getBytes());
        buffer.flip();
        assertEquals('h', buffer.get());
        assertEquals(4, buffer.remaining());
        assertEquals(1, buffer.position());
        // 类似clear，此方法是将未读取完毕的缓冲复制到起始位置，然后再修改position，继续写入数据
        buffer.compact();
        assertEquals(4, buffer.position());
        buffer.put((byte)'s');
        buffer.flip();
        assertEquals(new String(buffer.array()), "ellos");
    }

    @Test
    public void testReWind() {
        ByteBuffer buffer = ByteBuffer.allocate(5).put(hello.getBytes());
        buffer.flip();
        byte []b = new byte[5];
        buffer.get(b);
        assertEquals(new String(b), hello);
        assertEquals(5, buffer.position());
        assertFalse(buffer.hasRemaining());
        // 重新开始读数据
        buffer.rewind();
        assertTrue(buffer.hasRemaining());
        assertEquals(0, buffer.position());
        b = new byte[5];
        buffer.get(b);
        assertEquals(new String(b), hello);
    }

    @Test
    public void testSlice() {
        ByteBuffer buffer = ByteBuffer.allocate(5).put(hello.getBytes());
        buffer.slice();
    }

    @Test
    public void testMark() {
        ByteBuffer buffer = ByteBuffer.allocate(5).put(hello.getBytes());
        buffer.flip();
        buffer.get();
        buffer.mark();
        byte []b = new byte[4];
        buffer.get(b);
        assertEquals(new String(b), "ello");
        assertFalse(buffer.hasRemaining());
        buffer.reset();
        b = new byte[4];
        buffer.get(b);
        assertEquals(new String(b), "ello");
    }

    @Test
    public void testDuplicate() {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(hello.getBytes());
        // 复制一份内存引用，它们共享同一个内存地址，但是ByteBuffer中的position等不同
        ByteBuffer duplicate = buf.duplicate();
        assertTrue(new String(buf.array()).trim().equals(hello));
        assertTrue(new String(duplicate.array()).trim().equals(hello));
        // 其中一个更新了数据，另外一个也同样更新数据
        duplicate.put(hello.getBytes());
        assertEquals(new String(buf.array()).trim(), hello+hello);
        assertEquals(new String(duplicate.array()).trim(), hello+hello);
        // 数据虽然发生变更，但是因为position并未发生变更，所以数据还是从原来的位置写入，导致覆盖了duplicate写入的数据
        buf.put(world.getBytes());
        assertEquals(new String(buf.array()).trim(), hello+world);
        assertEquals(new String(duplicate.array()).trim(), hello+world);
    }
}
