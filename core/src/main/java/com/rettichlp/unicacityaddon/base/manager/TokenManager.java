package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import net.labymod.api.client.session.Session;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h3>Session token</h3>
 * An important function of the addon is to collect statistics and make data available to all players.
 * I use a private server for this. This provides data for:
 * <ul>
 *     <li>addon groups <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player">API</a></li>
 *     <li>banners <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner">API</a></li>
 *     <li>blacklist reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/LEMILIEU">API</a> (unauthorized)</li>
 *     <li>broadcasts <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/broadcast/queue">API</a></li>
 *     <li>events <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event">API</a></li>
 *     <li>house bans <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban?advanced=false">API</a> (unauthorized for <code>advanced=true</code>)</li>
 *     <li>house ban reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason">API</a></li>
 *     <li>users <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/users">API</a></li>
 *     <li>navi points <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint">API</a></li>
 *     <li>revives <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive">API</a> (unauthorized)</li>
 *     <li>statistics <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP">API</a></li>
 *     <li>wanted reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason">API</a></li>
 *     <li>yasin <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin">API</a></li>
 * </ul>
 * This data can change constantly and can therefore not be entered statically in the code.<br>
 * <br>
 * For example, the number of revives should only be seen by medics, as well as the name of the
 * person who entered a house ban (advanced house ban view).<br>
 * For editing any data, a certain faction and rank in this faction is required.<br><br>
 * I can read the faction and rank from the Unicacity website (<a href="https://unicacity.de/fraktionen">https://unicacity.de/fraktionen</a>).<br>
 * But in order to be able to assign the faction information to a player, I need his UUID. I could pass these as
 * parameters, but you could mess that up by calling the endpoint with a different UUID that isn't your own.<br>
 * I needed a way to pass the UUID so that it cannot (so easily) be falsified. For this I use the session token, because
 * I can use it to read the UUID via the Mojang API and nobody else knows the session token.<br><br>
 * The session token is never saved ore logged. Only my specially generated token is saved in a database.<br>
 * If necessary I can give access to the server code and give an insight into all stored data.
 *
 * @author RettichLP
 */
public class TokenManager {

    public static String API_TOKEN;

    private UnicacityAddon unicacityAddon;

    public TokenManager(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void createToken() {
        Session session = this.unicacityAddon.labyAPI().minecraft().sessionAccessor().session();
        String uuid = session.getUniqueId().toString().replace("-", "");
        String salt = "423WhKRMTfRv4mn6u8hLcPj7bYesKh4Ex4yRErYuW4KsgYjpo35nSU11QYj3OINAJwcd0TPDD6AkqhSq";
        String authToken = session.getAccessToken();
        API_TOKEN = hash(uuid + salt + authToken);

        try {
            this.unicacityAddon.api().sendTokenCreateRequest();
        } catch (APIResponseException e) {
            e.sendInfo();
        }
    }

    public String hash(String input) {
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