package com.frankefelipee.myissuertracker.auth;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Key {

    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private RSAPrivateKey rsaPrivateKey;
    private RSAPublicKey rsaPublicKey;

    public Key() throws NoSuchAlgorithmException {

        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        this.keyPairGenerator.initialize(2048);
        this.keyPair = this.keyPairGenerator.generateKeyPair();
        this.privateKey = this.keyPair.getPrivate();
        this.publicKey = this.keyPair.getPublic();
        this.rsaPrivateKey = (RSAPrivateKey) this.keyPair.getPrivate();
        this.rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();

    }

    public RSAPrivateKey getRsaPrivateKey() {

        return rsaPrivateKey;

    }

    public RSAPublicKey getRsaPublicKey() {

        return rsaPublicKey;

    }

    public PrivateKey getPrivateKey() {

        return privateKey;

    }

    public PublicKey getPublicKey() {

        return publicKey;

    }

}
