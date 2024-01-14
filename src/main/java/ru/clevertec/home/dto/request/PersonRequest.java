package ru.clevertec.home.dto.request;

import java.util.UUID;

public record PersonRequest(
        UUID uuid,
        String name,
        String surname,
        String sex,
        PassportRequest passport) {
}
