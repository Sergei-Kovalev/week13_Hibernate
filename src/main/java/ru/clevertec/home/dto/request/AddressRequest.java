package ru.clevertec.home.dto.request;

public record AddressRequest(
        String country,
        String city,
        String street,
        String number
) {
}
