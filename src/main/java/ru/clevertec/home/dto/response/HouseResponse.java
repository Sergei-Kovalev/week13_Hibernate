package ru.clevertec.home.dto.response;

import ru.clevertec.home.entity.Address;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseResponse(
        UUID uuid,
        double area,
        Address address,
        LocalDateTime createDate
) {
}
