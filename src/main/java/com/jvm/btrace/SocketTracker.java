package com.jvm.btrace;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;
import java.net.*;
import java.nio.channels.SocketChannel;
/**
 * @author chendurex
 * @date 2018-11-18 01:12
 */


@BTrace public class SocketTracker {
    @TLS private static int port = -1;
    @TLS private static InetAddress inetAddr;
    @TLS private static SocketAddress sockAddr;

    @OnMethod(
            clazz="java.net.ServerSocket",
            method="<init>"
    )
    public static void onServerSocket(@Self ServerSocket self,
                                      int p, int backlog, InetAddress bindAddr) {
        port = p;
        inetAddr = bindAddr;
    }

    @OnMethod(
            clazz="java.net.ServerSocket",
            method="<init>",
            type="void (int, int, java.net.InetAddress)",
            location=@Location(Kind.RETURN)
    )
    public static void onSockReturn() {
        if (port != -1) {
            println("server socket at " + port);
            port = -1;
        }
        if (inetAddr != null) {
            println("server socket at " + inetAddr);
            inetAddr = null;
        }
    }

    @OnMethod(
            clazz="java.net.ServerSocket",
            method="bind"
    )
    public static void onBind(@Self ServerSocket self, SocketAddress addr, int backlog) {
        sockAddr = addr;
    }

    @OnMethod(
            clazz="java.net.ServerSocket",
            method="bind",
            type="void (java.net.SocketAddress, int)",
            location=@Location(Kind.RETURN)
    )
    public static void onBindReturn() {
        socketBound();
    }

    @OnMethod(
            clazz="sun.nio.ch.ServerSocketChannelImpl",
            method="bind"
    )
    public static void onBind(@Self Object self, SocketAddress addr, int backlog) {
        sockAddr = addr;
    }

    @OnMethod(
            clazz="sun.nio.ch.ServerSocketChannelImpl",
            method="bind",
            type="void (java.net.SocketAddress, int)",
            location=@Location(Kind.RETURN)
    )
    public static void onBindReturn2() {
        socketBound();
    }

    @OnMethod(
            clazz="java.net.ServerSocket",
            method="accept",
            location=@Location(Kind.RETURN)
    )
    public static void onAcceptReturn(@Return Socket sock) {
        clientSocketAcc(sock);
    }

    @OnMethod(
            clazz="sun.nio.ch.ServerSocketChannelImpl",
            method="socket",
            location=@Location(Kind.RETURN)
    )
    public static void onSocket(@Return ServerSocket ssock) {
        println(Strings.strcat("server socket at ", Strings.str(ssock)));
    }

    @OnMethod(
            clazz="sun.nio.ch.ServerSocketChannelImpl",
            method="accept",
            location=@Location(Kind.RETURN)
    )
    public static void onAcceptReturn(@Return SocketChannel sockChan) {
        clientSocketAcc(sockChan);
    }

    private static void socketBound() {
        if (sockAddr != null) {
            println("server socket bind " +sockAddr);
            sockAddr = null;
        }
    }

    private static void clientSocketAcc(Object obj) {
        if (obj != null) {
            println("client socket accept " + obj);
        }
    }
}
