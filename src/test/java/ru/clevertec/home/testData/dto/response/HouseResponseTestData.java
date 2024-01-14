package ru.clevertec.home.testData.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.response.AddressResponse;
import ru.clevertec.home.dto.response.HouseResponse;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class HouseResponseTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("1f87a75f-8964-4460-9156-dd39be12ac17");

    @Builder.Default
    private double area = 50.0;

    @Builder.Default
    private AddressResponse addressResponse = AddressResponseTestData.builder().build().buildAddressResponse();

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(2024, Month.JANUARY, 14, 21, 5);

    public HouseResponse buildHouseResponse() {
        return new HouseResponse(uuid, area, addressResponse, createDate);
    }
}
