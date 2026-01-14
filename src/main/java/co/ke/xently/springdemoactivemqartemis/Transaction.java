package co.ke.xently.springdemoactivemqartemis;

import java.util.UUID;

public record Transaction(
        UUID uuid,
        double amount
) {
    public Transaction(double amount) {
        this(UUID.randomUUID(), amount);
    }
}
