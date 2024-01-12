package ru.clevertec.home.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseResponse(
        UUID uuid,
        double area,
        AddressResponse address,
        LocalDateTime createDate
) {
}
