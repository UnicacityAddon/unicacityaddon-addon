package com.rettichlp.UnicacityAddon.base.api;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.StringJoiner;

public class AuthHash {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final String username;
    private final long currentTime;
    private final long randomLong;
    private final String hash;

    public AuthHash(String username) {
        this.username = username;
        this.currentTime = System.currentTimeMillis();
        this.randomLong = RANDOM.nextLong();
        this.hash = hash(username + this.currentTime + this.randomLong);
    }

    private static String hash(String str) {
        try {
            byte[] digest = digest(str, "SHA-1");
            return (new BigInteger(digest)).toString(16);
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException(var2);
        }
    }

    private static byte[] digest(String str, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        return md.digest(strBytes);
    }

    public String getUsername() {
        return this.username;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }

    public long getRandomLong() {
        return this.randomLong;
    }

    public String getHash() {
        return this.hash;
    }

    public String toString() {
        return (new StringJoiner(", ", AuthHash.class.getSimpleName() + "[", "]")).add("username='" + this.username + "'").add("currentTime=" + this.currentTime).add("randomLong=" + this.randomLong).add("hash='" + this.hash + "'").toString();
    }
}