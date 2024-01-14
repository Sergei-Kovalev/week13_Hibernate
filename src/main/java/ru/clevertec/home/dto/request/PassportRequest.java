package ru.clevertec.home.dto.request;

public record PassportRequest(
        String passportSeries,
        String passportNumber) {
}
