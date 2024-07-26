package org.example.models;

import java.util.Objects;
import java.util.UUID;

public class SecretMessage {
    private UUID id;
    private String message;

    public SecretMessage(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public SecretMessage(String message) {
        this.id = UUID.randomUUID();
        this.message = message;
    }

    public SecretMessage() {
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretMessage{");
        sb.append("id=").append(id);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecretMessage that = (SecretMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }
}
