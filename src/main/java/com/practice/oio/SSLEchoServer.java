package com.practice.oio;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class SSLEchoServer {

    static SSLServerSocket sslServerSocket;

    public static void start() {

    }

    public static SSLContext createServerSSLContext() throws Exception {
        // 私鑰
        String pass = "123456";
        // 加載 keyStoreFile， 生成的密鑰倉庫
        String keyStoreFile = "./server.jks";
        return createSslContext(pass, keyStoreFile);
    }

    private static SSLContext createSslContext(String pass, String keyStoreFile) throws Exception {
        char[] passArray = pass.toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        FileInputStream in = new FileInputStream(keyStoreFile);
        keyStore.load(in, passArray);
        String algorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
        kmf.init(keyStore, passArray);
        // 初始化 KeyManagerFactory 之後，創建 SSLContext 並初始化
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // 信任庫
        // 如果是單向認證，服務端不需要驗證客戶端的合法性，此時，TrustManager 可以為空
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }
}
