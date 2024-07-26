package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import org.example.controllers.SecretMessageController;


public class Main {

    private static final HikariDataSource dataSource;

    private static final Dotenv dotenv = Dotenv.load();

    private static final String dbUrl = dotenv.get("DB_URL");

    private static final String dbUser = dotenv.get("DB_USER");

    private static final String dbPassword = dotenv.get("DB_PASSWORD");

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        config.addDataSourceProperty("maximumPoolSize", "25");

        dataSource = new HikariDataSource(config);
    }

    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8003, "localhost", ROUTES)
                .build();
        server.start();
    }

    private static final HttpHandler ROUTES = new RoutingHandler()
            .get("/java-secret/secret/{id}", exchange -> SecretMessageController.getMessage(exchange, dataSource))
            .post("/java-secret/secret", exchange -> SecretMessageController.postMessage(exchange, dataSource));
}