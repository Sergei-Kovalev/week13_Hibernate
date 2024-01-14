package ru.clevertec.home.testData.entity;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.entity.Address;
import ru.clevertec.home.entity.House;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class HouseTestData {

    @Builder.Default
    private Long id = 1000L;

    @Builder.Default
    private UUID uuid = UUID.fromString("1f87a75f-8964-4460-9156-dd39be12ac17");

    @Builder.Default
    private double area = 50.0;

    @Builder.Default
    private Address address = AddressTestData.builder().build().buildAddress();

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(2024, Month.JANUARY, 14, 21, 5);

    public House buildHouseTestData() {
        return new House(id, uuid, area, address, createDate);
    }
}
