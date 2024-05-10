package com.practice.ssl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CertHelper {

    String dname = "C=TW, L=TPE, O=m1a2st, OU=m1a2, CN=m1";
    String CA_SHA = "SHA256WithRSAEncryption";

    public CertHelper(String dname) {
        this.dname = dname;
    }
}
