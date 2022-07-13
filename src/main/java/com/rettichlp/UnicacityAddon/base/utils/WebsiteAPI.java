package com.rettichlp.UnicacityAddon.base.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class WebsiteAPI {

    public static String websiteToString(String urlString) {

        Scanner scanner;
        StringBuilder websiteSource = new StringBuilder();

        if (urlString != null) {
            try {
                URLConnection openConnection = new URL(urlString).openConnection();
                openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                scanner = new Scanner(new InputStreamReader(openConnection.getInputStream()));
                while (scanner.hasNextLine()) websiteSource.append(scanner.nextLine()).append("\n\r");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return websiteSource.toString();
    }
}
