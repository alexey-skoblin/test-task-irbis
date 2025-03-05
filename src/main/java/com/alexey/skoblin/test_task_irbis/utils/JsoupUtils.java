package com.alexey.skoblin.test_task_irbis.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JsoupUtils {
    // Конфигурационные константы
    private static final List<String> USER_AGENTS = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.1 Safari/605.1.15",
            "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/117.0"
    );

    private static final int MIN_TIMEOUT_MS = 15000;
    private static final int MAX_TIMEOUT_MS = 20000;
    private static final Random random = new Random();

    private JsoupUtils() {} // Запрет инстанцирования

    /**
     * Создает настроенное соединение для JSoup
     * @param url целевой URL
     * @return настроенный объект Connection
     */
    public static Connection createHumanizedConnection(String url) {
        // Случайная задержка перед запросом
        humanDelay();

        return Jsoup.connect(url)
                .userAgent(getRandomUserAgent())
                .timeout(getRandomTimeout())
                .followRedirects(true)
                .ignoreHttpErrors(true)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .header("DNT", "1")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate");
    }

    /**
     * Выполняет GET-запрос с обработкой ошибок
     * @param url целевой URL
     * @return Document или null при ошибке
     */
    public static Document safeGet(String url) {
        try {
            return createHumanizedConnection(url).get();
        } catch (Exception e) {
            // Логирование ошибки
            return null;
        }
    }

    private static String getRandomUserAgent() {
        return USER_AGENTS.get(random.nextInt(USER_AGENTS.size()));
    }

    private static int getRandomTimeout() {
        return MIN_TIMEOUT_MS + random.nextInt(MAX_TIMEOUT_MS - MIN_TIMEOUT_MS);
    }

    private static void humanDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000 + random.nextInt(3000));
        } catch (InterruptedException ignored) {
        }
    }
}