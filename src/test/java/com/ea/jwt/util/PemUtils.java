package com.ea.jwt.util;


import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PemUtils {

    private static byte[] parsePEMFile(File pemFile) {
        try {
            if (!pemFile.isFile() || !pemFile.exists()) {
                throw new FileNotFoundException(String.format("The file '%s' doesn't exist.", pemFile.getAbsolutePath()));
            }
            PemReader reader = new PemReader(new FileReader(pemFile));
            PemObject pemObject = reader.readPemObject();
            byte[] content = pemObject.getContent();
            reader.close();
            return content;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static PublicKey getPublicKeyFromPemBytes(byte[] keyBytes, String algorithm) {
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = kf.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static PrivateKey getPrivateKeyFromPemBytes(byte[] keyBytes, String algorithm) {
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateKey = kf.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static PublicKey getPublicKeyFromPemFile(String filepath, String algorithm) {
        byte[] bytes = PemUtils.parsePEMFile(new File(filepath));
        return PemUtils.getPublicKeyFromPemBytes(bytes, algorithm);
    }

    public static PublicKey getPublicKeyFromPemFile(String filepath) {
        return getPublicKeyFromPemFile(filepath, "RSA");
    }

    public static PrivateKey getPrivateKeyFromPemFile(String filepath, String algorithm) {
        byte[] bytes = PemUtils.parsePEMFile(new File(filepath));
        return PemUtils.getPrivateKeyFromPemBytes(bytes, algorithm);
    }

    public static PrivateKey getPrivateKeyFromPemFile(String filepath) {
        return getPrivateKeyFromPemFile(filepath, "RSA");
    }

    public static PublicKey getPublicKeyFromFile(String filename) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static PrivateKey getPrivateKeyFromFile(String filename) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}