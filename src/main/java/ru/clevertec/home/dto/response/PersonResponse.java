package ru.clevertec.home.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PersonResponse(
        UUID uuid,
        String name,
        String surname,
        String sex,
        PassportResponse passport,
        LocalDateTime createDate,
        LocalDateTime updateDate) {
}
