package org.example.models;

import java.util.Objects;
import java.util.UUID;

public record SecretMessage(
        UUID id,
        String message
) {
    public SecretMessage {
        Objects.requireNonNull(id);
        Objects.requireNonNull(message);
    }
}