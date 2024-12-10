package com.frankefelipee.myissuertracker.auth;

import lombok.Getter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Getter
public class Key {

    private final KeyPair keyPair;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final RSAPrivateKey rsaPrivateKey;
    private final RSAPublicKey rsaPublicKey;

    public Key() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        this.keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = this.keyPair.getPrivate();
        this.publicKey = this.keyPair.getPublic();
        this.rsaPrivateKey = (RSAPrivateKey) this.keyPair.getPrivate();
        this.rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();

    }

    public static Key setKey() {

        try {

            return new Key();

        } catch (NoSuchAlgorithmException e) {

            return null;

        }

    }

}
