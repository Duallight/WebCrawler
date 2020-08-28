package crawler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
        private static final Pattern DOMAIN_PATTERN = Pattern.compile("(([a-z]+)(\\.[a-z]+)+)|([a-z]+:\\d+)");
        private static final Pattern LAST_SLASH_PATTERN = Pattern.compile("[\\w]+(\\.[a-z]+)?$");
        // Use matcher.group(1) to get the link/title
        private static final Pattern LINK_PATTERN = Pattern.compile("<a [\\w\\s.,:;\"']*href=[\"']([\\w.:/]+)[\"']");
        private static final Pattern TITLE_PATTERN = Pattern.compile("<title>(.*)</title>");

        private static final Pattern PROTOCOL_PATTERN = Pattern.compile("^[a-z]+://");
        private static final Pattern NO_PROTOCOL_PATTERN = Pattern.compile("^(//)?(([a-z]+)(\\.[a-z]+)+)|([a-z]+:\\d+)");

        private final String url;
        private final String protocol;
        private final String domain;

        public Parser(String url) {
            this.url = url.replaceAll("/*$", "");

            Matcher pm = PROTOCOL_PATTERN.matcher(url);
            Matcher dp = DOMAIN_PATTERN.matcher(url);
            if (pm.find()) {
                protocol = pm.group();
            } else {
                protocol = "http://";
            }
            if (dp.find()) {
                domain = dp.group();
            } else {
                domain = "www.google.com";
            }
        }

        public String getTitle() throws IOException {
            return getTitleFromCode(downloadCode(url));
        }

        public Map<String, String> getLinksAndTitles() throws IOException {
            String code = downloadCode(url);
            Map<String, String> result = new LinkedHashMap<>();

            result.put(url, getTitleFromCode(code));

            //System.out.println(url);

            for (String link : getLinks(code)) {
                link = fixLink(link);
                //System.out.println(link);
                URLConnection connection = new URL(link).openConnection();
                if (connection.getContentType() != null && connection.getContentType().startsWith("text/html")) {
                    result.put(link, getTitleFromCode(downloadCode(connection)));
                }
            }

            return Map.copyOf(result);
        }

        private String fixLink(String link) {
            link = link.replaceAll("^/+", "");
            if (PROTOCOL_PATTERN.matcher(link).find()) {
                return link;
            } else if (NO_PROTOCOL_PATTERN.matcher(link).find()) {
                return protocol + link;
            } else {
                if (url.equals(protocol + domain)) {
                    return url + link;
                } else {
                    return LAST_SLASH_PATTERN.matcher(url).replaceAll("") + link;
                }
            }
        }

        private static String[] getLinks(String code) {
            ArrayList<String> links = new ArrayList<>();
            Matcher matcher = LINK_PATTERN.matcher(code);

            while (matcher.find()) {
                links.add(matcher.group(1));
            }

            return links.toArray(new String[0]);
        }

        private static String getTitleFromCode(String code) {
            Matcher matcher = TITLE_PATTERN.matcher(code);
            if (matcher.find()) {
                return (matcher.group(1));
            } else {
                return "";
            }
        }

        private static String downloadCode(String link) throws IOException {
            return downloadCode(new URL(link).openConnection());
        }

        private static String downloadCode(URLConnection connection) throws IOException {
            try (InputStream is = new BufferedInputStream(connection.getInputStream())) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }

}
