package com.generoso.ft.training.simulacron.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkUtils {

    public static int findUnusedLocalPort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
