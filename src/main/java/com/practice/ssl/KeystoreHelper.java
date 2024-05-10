package com.practice.ssl;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

@Slf4j
@Data
public class KeystoreHelper {

    private static final byte[] CRLF = new byte[]{'\r', '\n'};

    // 儲存密鑰的文件
    private String keyStoreFile;

    // 獲取 keystore 的訊息所需的密碼
    private String storePass;

    // 設置指定別名條目的密碼，也就是私鑰密碼
    private String keyPass;

    // 每個 keystore 都關聯這一個獨一無二的 alias，這個 alias 通常不區分大小寫
    private String alias;

    // 指定證書擁有者訊息
    // ex: "CN=名字與姓氏,OU=組織單位名稱,O=組織名稱,L=城市或區域名稱,ST=州或省份名稱,C=單位的兩字母國家代碼"
    private String dname = "C=TW, L=TPE, O=m1a2st, OU=m1a2, CN=m1";
    private KeyStore keyStore;
    private static final String KEY_TYPE = "JKS";

    public KeystoreHelper(String keyStoreFile, String storePass, String keyPass, String alias) {
        this.keyStoreFile = keyStoreFile;
        this.storePass = storePass;
        this.keyPass = keyPass;
        this.alias = alias;
    }

    public void createKeyEntry() throws Exception {
        KeyStore keystore = loadStore();

    }

    public KeyStore loadStore() throws Exception {
        log.debug("keyStoreFile: {}", keyStoreFile);
        if (!new File(keyStoreFile).exists()) {
            createEmptyStore();
        }
        KeyStore ks = KeyStore.getInstance(KEY_TYPE);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(keyStoreFile);
            ks.load(fis, storePass.toCharArray());
        } finally {
            closeQuietly(fis);
        }
        return ks;
    }

    private void createEmptyStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEY_TYPE);
        File parentFile = new File(keyStoreFile).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        java.io.FileOutputStream fos = null;
        keyStore.load(null, storePass.toCharArray());
        try {
            fos = new java.io.FileOutputStream(keyStoreFile);
            keyStore.store(fos, storePass.toCharArray());
        } finally {
            closeQuietly(fos);
        }
    }

    public static void closeQuietly(java.io.Closeable o) {
        if (null == o) return;
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        KeystoreHelper keystoreHelper =
                new KeystoreHelper("./test.txt","123456","123456","test");
        keystoreHelper.createKeyEntry();
    }
}
