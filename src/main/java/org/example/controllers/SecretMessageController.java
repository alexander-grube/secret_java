package org.example.controllers;

import com.alibaba.fastjson2.JSON;
import com.zaxxer.hikari.HikariDataSource;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.example.models.SecretMessage;
import org.jboss.logging.Logger;

import java.util.UUID;

public class SecretMessageController {

    private static final Logger LOGGER = Logger.getLogger(SecretMessageController.class);

    public static void getMessage(HttpServerExchange exchange, HikariDataSource dataSource) {
        UUID id = UUID.fromString(exchange.getQueryParameters().get("id").getFirst());
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM secret_message WHERE id = ?");
            statement.setObject(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var secretMessage = new SecretMessage(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("message")
                );
                exchange.setStatusCode(200);
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send(JSON.toJSONString(secretMessage));
            } else {
                exchange.setStatusCode(404);
                exchange.getResponseSender().send("Message not found");
            }
        } catch (Exception e) {
            exchange.setStatusCode(500);
            exchange.getResponseSender().send("Internal server error");
            LOGGER.error("Error connecting to database", e);
        }
    }

    public static void postMessage(HttpServerExchange exchange, HikariDataSource dataSource) {
        exchange.getRequestReceiver().receiveFullBytes((exchange1, message) -> {
            try (var connection = dataSource.getConnection()) {
                var secretMessage = JSON.parseObject(message, SecretMessage.class);
                var statement = connection.prepareStatement("INSERT INTO secret_message (message) VALUES (?) RETURNING ID");
                statement.setString(1, secretMessage.getMessage());
                var resultSet = statement.executeQuery();
                resultSet.next();
                secretMessage.setId(UUID.fromString(resultSet.getString("id")));
                exchange.setStatusCode(201);
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send(JSON.toJSONString(secretMessage));
            } catch (Exception e) {
                exchange.setStatusCode(500);
                exchange.getResponseSender().send("Internal server error");
                LOGGER.error("Error connecting to database", e);
            }
        });
    }
}
