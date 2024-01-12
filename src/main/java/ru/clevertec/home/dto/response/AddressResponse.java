package ru.clevertec.home.dto.response;

public record AddressResponse(
        String country,
        String city,
        String street,
        String number
) {
}
