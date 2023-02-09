package com.rettichlp.unicacityaddon.base.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenManager {

    public static String API_TOKEN;

    public static void createToken() {
        String uuid = UnicacityAddon.PLAYER.getUniqueId().toString().replace("-", "");
        String salt = "423WhKRMTfRv4mn6u8hLcPj7bYesKh4Ex4yRErYuW4KsgYjpo35nSU11QYj3OINAJwcd0TPDD6AkqhSq";
        String authToken = UnicacityAddon.MINECRAFT.sessionAccessor().session().getAccessToken();
        API_TOKEN = hash(uuid + salt + authToken);
        APIRequest.sendTokenCreateRequest();
    }

    public static String hash(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            // return the HashText
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}