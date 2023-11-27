package automation.tests.model.runner;

import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageUtils {

    private static final HttpClient client = HttpClient.newBuilder().build();

    private static String sessionId;

    private static String getCrumbFromPage(String page) {
        final String CRUMB_TAG = "data-crumb-value=\"";

        int crumbTagBeginIndex = page.indexOf(CRUMB_TAG) + CRUMB_TAG.length();
        int crumbTagEndIndex = page.indexOf('"', crumbTagBeginIndex);

        return page.substring(crumbTagBeginIndex, crumbTagEndIndex);
    }

    private static Set<String> getSubstringsFromPage(String page, String from, String to) {
        // 255 - максимально возможная длинна имени, но если используется не латиница или специальные символы, страка будет длинней из-за кодирования (пробел - %20)
        return getSubstringsFromPage(page, from, to, 256);
    }

    private static Set<String> getSubstringsFromPage(String page, String from, String to, int maxSubstringLength) {
        Set<String> result = new HashSet<>();

        int index = page.indexOf(from);
        while (index != -1) {
            index += from.length();
            int endIndex = page.indexOf(to, index);

            if (endIndex != -1 && endIndex - index < maxSubstringLength) {
                result.add(page.substring(index, endIndex));
            } else {
                endIndex = index;
            }

            index = page.indexOf(from, endIndex);
        }

        return result;
    }

    private static String[] getHeader() {
        List<String> result = new ArrayList<>(List.of("Content-Type", "application/x-www-form-urlencoded"));
        if (sessionId != null) {
            result.add("Cookie");
            result.add(sessionId);
        }
        return result.toArray(String[]::new);
    }

    private static HttpResponse<String> getHttp(String url) {
        try {
            return client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .headers(getHeader())
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpResponse<String> postHttp(String url, String body) {
        try {
            return client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .headers(getHeader())
                            .POST(HttpRequest.BodyPublishers.ofString(body))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPage(String uri) {
        HttpResponse<String> page = getHttp(ProjectUtils.getUrl() + uri);
        if (page.statusCode() != 200) {
            final String HEAD_COOKIE = "set-cookie";

            HttpResponse<String> loginPage = getHttp(ProjectUtils.getUrl() + "login?from=%2F");
            sessionId = loginPage.headers().firstValue(HEAD_COOKIE).orElse(null);

            page = getHttp(ProjectUtils.getUrl() + uri);
        }



        return page.body();
    }

    private static void deleteByLink(String link, Set<String> names, String crumb) {
        String fullCrumb = String.format("Jenkins-Crumb=%s", crumb);
        for (String name : names) {
            postHttp(String.format(ProjectUtils.getUrl() + link, name), fullCrumb);
        }
    }

    private static void deleteJobs() {
        String mainPage = getPage("");
        deleteByLink("job/%s/doDelete",
                getSubstringsFromPage(mainPage, "href=\"job/", "/\""),
                getCrumbFromPage(mainPage));
    }

    private static void deleteViews() {
        String mainPage = getPage("");
        deleteByLink("view/%s/doDelete",
                getSubstringsFromPage(mainPage, "href=\"/view/", "/\""),
                getCrumbFromPage(mainPage));

        String viewPage = getPage("me/my-views/view/all/");
        deleteByLink("user/admin/my-views/view/%s/doDelete",
                getSubstringsFromPage(viewPage, "href=\"/user/admin/my-views/view/", "/\""),
                getCrumbFromPage(viewPage));
    }


    private static void deleteNodes() {
        String mainPage = getPage("");
        deleteByLink("manage/computer/%s/doDelete",
                getSubstringsFromPage(mainPage, "href=\"/computer/", "/\""),
                getCrumbFromPage(mainPage));
    }

    private static void deleteDescription() {
        String mainPage = getPage("");
        postHttp(ProjectUtils.getUrl() + "submitDescription",
                String.format(
                        "description=&Submit=&Jenkins-Crumb=%1$s&json=%%7B%%22description%%22%%3A+%%22%%22%%2C+%%22Submit%%22%%3A+%%22%%22%%2C+%%22Jenkins-Crumb%%22%%3A+%%22%1$s%%22%%7D",
                        getCrumbFromPage(mainPage)));
    }

    static void clearData() {
        System.out.println("Clear data");
    }

    static void login(WebDriver driver) {
        System.out.println("Login");
    }

    static void logout(WebDriver driver) {
        driver.quit();
    }
}

