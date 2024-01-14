package ru.clevertec.home.dto.request;

import java.util.UUID;

public record HouseRequest(
        UUID uuid,
        double area,
        AddressRequest address) {
}
